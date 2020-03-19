package com.chelsea.weixin.util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chelsea.weixin.domain.Token;
import com.chelsea.weixin.domain.WeixinOauth2Token;

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
	
	@Value("${weixin.oauth2AccessTokenUrl}")
	private String oauth2AccessTokenUrl;

	private static Logger logger = LoggerFactory.getLogger(TokenUtil.class);

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
		JSONObject jsonObject = HttpsUtil.get(requestUrl);

		if (null != jsonObject) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				token = null;
				// 获取token失败
				logger.error("获取token失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return token;
	}
	
	/**
	 * 获得网页授权token
	 * @param code
	 * @return
	 */
	public WeixinOauth2Token getOauth2AccessToken(String code) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = oauth2AccessTokenUrl.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = HttpsUtil.get(requestUrl);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                logger.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }


}
