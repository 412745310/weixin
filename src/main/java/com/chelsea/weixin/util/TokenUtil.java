package com.chelsea.weixin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.chelsea.weixin.domain.Token;

/**
 * 凭证工具类
 * 
 * @author baojun
 *
 */
@Component
public class TokenUtil {

	@Value("${weixin.appid}")
	private String appid;

	@Value("${weixin.appsecret}")
	private String appsecret;

	@Value("${weixin.tokenurl}")
	private String tokenurl;

	private static Logger log = LoggerFactory.getLogger(TokenUtil.class);

	/**
	 * 获取接口访问凭证
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public Token getToken() {
		Token token = null;
		String requestUrl = tokenurl.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		// 发起GET请求获取凭证
		JSONObject jsonObject = HttpsUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				token = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return token;
	}

}
