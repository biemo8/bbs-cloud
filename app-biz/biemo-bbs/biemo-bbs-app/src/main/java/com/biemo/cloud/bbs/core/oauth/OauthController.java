package com.biemo.cloud.bbs.core.oauth;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biemo.cloud.auth.api.context.LoginContext;
import com.biemo.cloud.bbs.api.OauthApi;
import com.biemo.cloud.bbs.config.BiemoLoginContextConfig;
import com.biemo.cloud.bbs.core.oauth.oauth.OauthQQ;
import com.biemo.cloud.bbs.core.oauth.oauth.OauthSina;
import com.biemo.cloud.bbs.core.oauth.oauth.OauthWeixin;
import com.biemo.cloud.bbs.core.oauth.util.TokenUtil;
import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.domain.BThirdAccount;
import com.biemo.cloud.bbs.modular.domain.BUser;
import com.biemo.cloud.bbs.modular.service.IBThirdAccountService;
import com.biemo.cloud.bbs.modular.service.IBUserService;
import com.biemo.cloud.core.constant.Constants;
import com.biemo.cloud.core.util.MD5Util;
import com.biemo.cloud.core.util.StringUtils;
import com.biemo.cloud.core.util.redis.RedisCache;
import com.biemo.cloud.core.util.uuid.IdUtils;
import com.biemo.cloud.kernel.jwt.utils.JwtTokenUtil;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class OauthController implements OauthApi {

    public static final Logger log = LoggerFactory.getLogger(OauthController.class);

    @Autowired
    private OauthQQ oauthQQ;
    @Autowired
    private OauthSina oauthSina;
    @Autowired
    private OauthWeixin oauthWeixin;
    @Autowired
    private IBUserService ibUserService;
    @Autowired
    private IBThirdAccountService ibThirdAccountService;
    @Autowired
    RedisCache redisCache;
    @Autowired
    JwtTokenUtil jwtTokenUtil;



    public ResponseData oauth(@PathVariable String oauthType, HttpSession session){
        String state = TokenUtil.randomState();
        if(oauthType.equals("qq")) {
            String jsessionId = session.getId();
            redisCache.setCacheObject(OauthQQ.SESSION_STATE+jsessionId,state,30,TimeUnit.SECONDS);
            return ResponseData.success(oauthQQ.getAuthorizeUrl(state));
        }
        if(oauthType.equals("sina")) {
            session.setAttribute(OauthSina.SESSION_STATE, state);
            return ResponseData.success(oauthSina.getAuthorizeUrl(state));
        }
        if(oauthType.equals("weixin")){
            session.setAttribute(OauthWeixin.SESSION_STATE, state);
            return ResponseData.success(oauthWeixin.getAuthorizeUrl(state));
        }
        return ResponseData.success();
    }

    @Transactional
    public ResponseData callback(@PathVariable String oauthType, @RequestParam("code") String code,
                           @RequestParam("state") String state,
                           Model model, HttpSession session, HttpServletRequest request) {
        String session_state="",openid = "", nickname ="", photoUrl ="";
        String jsessionId = request.getSession().getId();
        if(oauthType.equals("qq")){
            session_state = redisCache.getCacheObject(OauthQQ.SESSION_STATE+jsessionId);
        }
        if(oauthType.equals("sina")){
            session_state = (String) session.getAttribute(OauthSina.SESSION_STATE);
        }
        if(oauthType.equals("weixin")) {
            session_state = (String) session.getAttribute(OauthWeixin.SESSION_STATE);
        }
        if (StringUtils.isBlank(state) || StringUtils.isBlank(session_state) || !state.equals(session_state) || StringUtils.isBlank(code)) {
            System.out.println("biemo——debugger：error error");
            redisCache.deleteObject(OauthQQ.SESSION_STATE+jsessionId);
            try {
                throw new RuntimeException("登录异常！");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseData.error(500,"登录失败",null);
        }
        BUser member = null;
        if(oauthType.equals("qq")) {
            redisCache.deleteObject(OauthQQ.SESSION_STATE+jsessionId);
            JSONObject userInfo = oauthQQ.getUserInfoByCode(code);
            openid = userInfo.getString("openid");
            nickname = userInfo.getString("nickname");
            photoUrl = userInfo.getString("figureurl_2");
            QueryWrapper<BThirdAccount> queryWrapper = new QueryWrapper();
            queryWrapper.eq("third_type","qq");
            queryWrapper.eq("third_id",openid);
            BThirdAccount tCmsMemberOauth = ibThirdAccountService.getOne(queryWrapper);
            if(tCmsMemberOauth==null){
                //自動注冊一個用戶
                member = new BUser();
                member.setId(IdUtils.fastUUIDLong());
                member.setCreateTime(System.currentTimeMillis());
                member.setPassword(MD5Util.encrypt("123456"));
                member.setAvatar(photoUrl);
                member.setNickname(nickname);
                member.setUsername(member.getId().toString());
                member.setScore(0L);
                member.setCreateTime(System.currentTimeMillis());
                member.setStatus(0L);
                member.setType(0L);
                member.setTopicCount(0L);
                member.setCommentCount(0L);
                member.setForbiddenEndTime(0L);
                member.setBackgroundImage("");
                member.setDescription("");
                member.setRoles("");
                ibUserService.save(member);
                tCmsMemberOauth = new BThirdAccount();
                tCmsMemberOauth.setAvatar(photoUrl);
                tCmsMemberOauth.setCreateTime(System.currentTimeMillis());
                tCmsMemberOauth.setNickname(nickname);
                tCmsMemberOauth.setThirdType("qq");
                tCmsMemberOauth.setThirdId(openid);
                tCmsMemberOauth.setUserId(member.getId());
                tCmsMemberOauth.setId(IdUtils.fastUUIDLong());
                ibThirdAccountService.save(tCmsMemberOauth);
                System.out.println("biemo——debugger：自动注册账号完毕！"+member.getUsername());

            }else{
                //已经授权登录过
                member = ibUserService.getById(tCmsMemberOauth.getUserId());

            }
            Map<String,Object> claims = new HashMap<>();
            claims.put("user",member);
            String token = jwtTokenUtil.generateToken(String.valueOf(member.getId()),claims);
            claims.put("token",token);
            redisCache.setCacheObject(Constants.LOGIN_TOKEN_KEY+token,member,jwtTokenUtil.getDefaultExpiredDate().intValue(), TimeUnit.SECONDS);
            return ResponseData.success(claims);
        }
        if(oauthType.equals("weixin")) {
            session.removeAttribute(OauthWeixin.SESSION_STATE);
            JSONObject userInfo = oauthWeixin.getUserInfoByCode(code);
            openid = userInfo.getString("openid");
            nickname = userInfo.getString("nickname");
            photoUrl = userInfo.getString("headimgurl");
            QueryWrapper<BThirdAccount> queryWrapper = new QueryWrapper();
            queryWrapper.eq("third_type","qq");
            queryWrapper.eq("third_id",openid);
            BThirdAccount tCmsMemberOauth = ibThirdAccountService.getOne(queryWrapper);
            if(tCmsMemberOauth==null){
                //自動注冊一個用戶
                member = new BUser();
                member.setId(IdUtils.fastUUIDLong());
                member.setCreateTime(System.currentTimeMillis());
                member.setPassword(MD5Util.encrypt("123456"));
                member.setAvatar(photoUrl);
                member.setNickname(nickname);
                member.setUsername(IdUtils.fastUUID());
                ibUserService.save(member);
                tCmsMemberOauth = new BThirdAccount();
                tCmsMemberOauth.setAvatar(photoUrl);
                tCmsMemberOauth.setCreateTime(System.currentTimeMillis());
                tCmsMemberOauth.setNickname(nickname);
                tCmsMemberOauth.setThirdType("weixin");
                tCmsMemberOauth.setThirdId(openid);
                tCmsMemberOauth.setUserId(member.getId());
                tCmsMemberOauth.setId(IdUtils.fastUUIDLong());
                ibThirdAccountService.save(tCmsMemberOauth);
                model.addAttribute("member",member);
                System.out.println("biemo——debugger：自动注册账号完毕！"+member.getUsername());
            }else{
                //已经授权登录过
                member  = ibUserService.getById(tCmsMemberOauth.getUserId());
            }
            return ResponseData.success(member);
        }
        if(oauthType.equals("sina")) {
            session.removeAttribute(OauthSina.SESSION_STATE);
            JSONObject userInfo = oauthSina.getUserInfoByCode(code);
            log.debug(userInfo.toJSONString());
            openid = userInfo.getString("id");
            nickname = userInfo.getString("screen_name");
            photoUrl = userInfo.getString("profile_image_url");
        }
        return ResponseData.success();
    }


}
