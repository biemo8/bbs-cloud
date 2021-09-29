package com.biemo.cloud.bbs.core.oauth.oauth;



import com.biemo.cloud.bbs.core.oauth.util.HttpKit;
import com.biemo.cloud.bbs.core.oauth.util.HttpKitExt;
import com.biemo.cloud.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class Oauth {

	public Logger LOGGER= LoggerFactory.getLogger(this.getClass());

	/**
	 * 构造授权的Url
	 *
	 * @param authorize url
	 * @param state     OAuth2.0标准协议建议，利用state参数来防止CSRF攻击。可存储于session或其他cache中
	 * @return String 构造完成的url字符串
	 */
	protected String getAuthorizeUrl(String authorize, String state, String clientId, String redirectUri) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("response_type", "code");
		params.put("client_id", clientId);
		params.put("redirect_uri", redirectUri);
		if (StringUtils.isNotBlank(state)) {
			params.put("state", state);
		}
		return HttpKitExt.initParams(authorize, params);
	}
	//微信构建url
	protected String getAuthorizeUrl_weixin(String authorize, String state, String clientId, String redirectUri,String scope) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("response_type", "code");
		params.put("appid", clientId);
		params.put("redirect_uri", redirectUri);
		params.put("scope", scope);
		if (StringUtils.isNotBlank(state)) {
			params.put("state", state);
		}
		return HttpKitExt.initParams(authorize, params);
	}

	protected String doPost(String url, Map<String, String> params) {
		return HttpKit.post(url, HttpKitExt.map2Url(params));
	}

	protected String doGet(String url, Map<String, String> params) {
		return HttpKit.get(url, params);
	}

	protected String doGetWithHeaders(String url, Map<String, String> headers) {
		return HttpKit.get(url, null, headers);
	}
}

