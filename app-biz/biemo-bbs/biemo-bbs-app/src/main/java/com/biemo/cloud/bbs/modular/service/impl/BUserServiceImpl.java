package com.biemo.cloud.bbs.modular.service.impl;

import java.io.BufferedReader;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biemo.cloud.auth.api.context.LoginContext;
import com.biemo.cloud.bbs.api.vo.BUserVo;
import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.domain.BMessage;
import com.biemo.cloud.bbs.modular.service.IBMessageService;
import com.biemo.cloud.core.constant.Constants;
import com.biemo.cloud.core.util.MD5Util;
import com.biemo.cloud.core.util.StringUtils;
import com.biemo.cloud.core.util.redis.RedisCache;
import com.biemo.cloud.core.util.uuid.IdUtils;
import com.biemo.cloud.kernel.jwt.utils.JwtTokenUtil;
import com.biemo.cloud.kernel.model.response.ResponseData;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biemo.cloud.bbs.modular.mapper.BUserMapper;
import com.biemo.cloud.bbs.modular.domain.BUser;
import com.biemo.cloud.bbs.modular.service.IBUserService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service业务层处理
 *
 * @author makesoft
 * @date 2021-08-11
 */
@Service
public class BUserServiceImpl extends ServiceImpl<BUserMapper,BUser> implements IBUserService {

    @Autowired
    RedisCache redisCache;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    IBMessageService ibMessageService;
    @Override
    public ResponseData signin(String captchaId, String captchaCode, String username, String password){
        String code = redisCache.getCacheObject(Constants.CAPTCHA_CODE_KEY + captchaId);
        ResponseData responseData = new ResponseData();
        responseData.setCode(200);
        responseData.setSuccess(false);
        responseData.setData(null);
        if(code==null||!code.equals(captchaCode)){
            //验证码不正确
            responseData.setMessage("验证码不正确！");
            return responseData;
        }
        BUser buser = this.baseMapper.selectOne(new QueryWrapper<BUser>().eq("username",username));
        if(buser==null){
            //用户不存在
            responseData.setMessage("用户不存在！");
            return responseData;
        }
        if(!MD5Util.encrypt(password).equals(buser.getPassword())){
            //密码不正确
            responseData.setMessage("密码不正确！");
            return responseData;
        }
        if(buser.getStatus()!=0){
            responseData.setMessage("该用户被禁用！");
            return responseData;
        }
        Map<String,Object> claims = new HashMap<>();
        claims.put("user",buser);
        String token = jwtTokenUtil.generateToken(String.valueOf(buser.getId()),claims);
        claims.put("token",token);
        responseData.setSuccess(true);
        responseData.setData(claims);
        responseData.setMessage("登录成功");
        redisCache.setCacheObject(Constants.LOGIN_TOKEN_KEY+token,buser,jwtTokenUtil.getDefaultExpiredDate().intValue(), TimeUnit.SECONDS);
        return responseData;
    }

    @Override
    public BUser getcurrent(){
        String token = LoginContext.me().getCurrentUserToken();
        if(StringUtils.isNotEmpty(token)){
            token = token.substring(7);
        }
        if(token!=null&&jwtTokenUtil.checkToken(token)&&!jwtTokenUtil.isTokenExpired(token)){
            //Claims claims =  jwtTokenUtil.getClaimFromToken(token);
            //Map bUser = claims.get("user",Map.class);
            BUser bUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY+token);
            return bUser;
        }
        return null;
    }

    @Override
    public ResponseData msgrecent() {
        BUser user = this.getcurrent();
        if(user!=null){
            Long userId = user.getId();
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id",userId);
            queryWrapper.eq("status",0);
            List<BMessage> messageList = ibMessageService.list(queryWrapper);
            if(messageList!=null&&messageList.size()>0){
                return ResponseData.success(new HashMap(){{put("count",messageList.size());put("messages",messageList);}});
            }
        }
        return ResponseData.success(new HashMap(){{put("count",0);put("messages",null);}});
    }

    public boolean signout(String userToken){
        if(StringUtils.isNotEmpty(userToken)){
            redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY+userToken);
        }
        return true;
    }

    @Override
    public int updateCache(BUser user) {
        String token = LoginContext.me().getCurrentUserToken();
        if(StringUtils.isEmpty(token)||user==null){
            return 0;
        }
        token = token.substring(7);
        redisCache.setCacheObject(Constants.LOGIN_TOKEN_KEY+token,user,jwtTokenUtil.getDefaultExpiredDate().intValue(), TimeUnit.SECONDS);
        return 1;
    }

    @Override
    @Transactional
    public ResponseData signup(BUserVo bUserVo) {
        String code = redisCache.getCacheObject(Constants.CAPTCHA_CODE_KEY + bUserVo.getCaptchaId());
        ResponseData responseData = new ResponseData();
        responseData.setCode(200);
        responseData.setSuccess(false);
        responseData.setData(null);
        if(code==null||!code.equals(bUserVo.getCaptchaCode())){
            //验证码不正确
            responseData.setMessage("验证码不正确！");
            return responseData;
        }
        BUser buser = this.baseMapper.selectOne(new QueryWrapper<BUser>().eq("username",bUserVo.getUsername()));
        if(buser!=null){
            //用户不存在
            responseData.setMessage("用户名已经存在！");
            return responseData;
        }
        if(StringUtils.isEmpty(bUserVo.getEmail())||!StringUtils.isEmail(bUserVo.getEmail())){
            responseData.setMessage("邮箱格式不正确！");
            return responseData;
        }
        if(StringUtils.isEmpty(bUserVo.getPassword())||!bUserVo.getPassword().equals(bUserVo.getRePassword())){
            //密码不正确
            responseData.setMessage("二次确认密码不一致！");
            return responseData;
        }
        buser = new BUser();
        BeanUtils.copyProperties(bUserVo,buser);
        buser.setPassword(MD5Util.encrypt(bUserVo.getPassword()));
        buser.setScore(0L);
        buser.setCreateTime(System.currentTimeMillis());
        buser.setStatus(0L);
        buser.setType(0L);
        buser.setTopicCount(0L);
        buser.setCommentCount(0L);
        buser.setForbiddenEndTime(0L);
        buser.setBackgroundImage("");
        buser.setAvatar("");
        buser.setDescription("");
        buser.setRoles("");
        //id太长 让其自增
        //buser.setId(IdUtils.fastUUIDLong());
        this.baseMapper.insert(buser);
        Map<String,Object> claims = new HashMap<>();
        claims.put("user",buser);
        String token = jwtTokenUtil.generateToken(String.valueOf(buser.getId()),claims);
        claims.put("token",token);
        responseData.setSuccess(true);
        responseData.setData(claims);
        responseData.setMessage("注册成功！");
        redisCache.setCacheObject(Constants.LOGIN_TOKEN_KEY+token,buser,jwtTokenUtil.getDefaultExpiredDate().intValue(), TimeUnit.SECONDS);
        return responseData;
    }

}
