package com.chelsea.weixin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

/**
 * 微信工具类
 * 
 * @author shevchenko
 *
 */
@Component
public class WeixinUtil {
	
	private Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

	@Value("${weixin.appid}")
	private String appId;

	@Value("${weixin.appsecret}")
	private String secret;

	@Value("${weixin.tokenurl}")
	private String tokenUrl;

	@Resource(name = "jedisCluster")
	private JedisCluster jedisCluster;
	
	/**
	 * 获取微信的配置信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> getWxConfig(String url) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
		String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
		// 要注意，access_token需要缓存
		String jsapi_ticket = jedisCluster.get(Constant.JSAPI_TICKET);
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr
				+ "&timestamp=" + timestamp + "&url=" + url;
		logger.info("--------> sign : " + sign);
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(sign.getBytes("UTF-8"));
			signature = CommonUtil.byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ret.put("appId", appId);
		ret.put("timestamp", timestamp);
		ret.put("nonceStr", nonceStr);
		ret.put("signature", signature);
		return ret;
	}

}
