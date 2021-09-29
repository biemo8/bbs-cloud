package com.biemo.cloud.bbs.core.oauth.oauth;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biemo.cloud.bbs.core.oauth.util.TokenUtil;
import com.biemo.cloud.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OauthWeixin extends Oauth {

	private static final String AUTH_URL = "https://open.weixin.qq.com/connect/qrconnect";
	// https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	private static final String TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	//https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	private static final String TOKEN_INFO_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	//https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
	private static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

	public static final String SESSION_STATE = "_SESSION_STATE_WEIXIN_";
	public static final String OAUTH_TYPE = "weixin";

	//@Value("${oauthWx.openid}")
	private String clientId;
	//@Value("${oauthWx.openkey}")
	private String clientSecret;
	//@Value("${oauthWx.redirect}")
	private String redirectUri;

	/**
	 * 获取授权url
	 * DOC：
	 * @param state OAuth2.0标准协议建议，利用state参数来防止CSRF攻击
	 * @return String 返回类型
	 */
	public String getAuthorizeUrl(String state) {
		return super.getAuthorizeUrl_weixin(AUTH_URL, state,clientId,redirectUri,"snsapi_login");
	}

	/**
	 * 获取token
	 * @param code 根据code获取token
	 * @return String 返回类型
	 */
	public String getTokenByCode(String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		params.put("appid", clientId);
		params.put("secret", clientSecret);
		params.put("grant_type", "authorization_code");
		// access_token=FE04************************CCE2&expires_in=7776000
		String json = super.doGet(TOKEN_URL, params);
		LOGGER.debug("OauthWeixin Token ："+json);
		return json;
	}

	/**
	 * 获取TokenInfo
	 * @param accessToken AccessToken
	 * @return String 返回类型
	 */
	public String getTokenInfo(String accessToken) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		// callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
		String openid = TokenUtil.getOpenId(super.doGet(TOKEN_INFO_URL, params));
		LOGGER.debug("OauthQQ OpenId ："+openid );
		return openid;
	}

	/**
	 * 获取用户信息
	 * DOC：http://wiki.connect.qq.com/get_user_info
	 * @param accessToken AccessToken
	 * @param uid 用户id
	 * @return String 返回类型
	 */
	public String getUserInfo(String accessToken, String uid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		params.put("appid", clientId);
		params.put("openid", uid);
		// // {"ret":0,"msg":"","nickname":"YOUR_NICK_NAME",...}
		String userinfo = super.doGet(USER_INFO_URL, params);
		LOGGER.debug("WeiXinOauth UserInfo ："+userinfo);
		return userinfo;
	}

	/**
	 * 根据code一步获取用户信息
	 * @param code oauth code
	 * @return JSONObject 返回类型
	 */
	public JSONObject getUserInfoByCode(String code) {
		String json = getTokenByCode(code);
		JSONObject jsonObject = JSONObject.parseObject(json);
		String accessToken = jsonObject.getString("access_token");
		if (StringUtils.isBlank(accessToken)) {
			throw new RuntimeException("accessToken is Blank!");
		}
		//String openId = getTokenInfo(accessToken);
		String openId = jsonObject.getString("openid");
		if (StringUtils.isBlank(openId)) {
			throw new RuntimeException("openId is Blank!");
		}
		JSONObject dataMap = JSON.parseObject(getUserInfo(accessToken, openId));
		dataMap.put("openid", openId);
		dataMap.put("access_token", accessToken);
		return dataMap;
	}

}
