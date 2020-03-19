package com.chelsea.weixin.job.impl;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

import com.chelsea.weixin.domain.Token;
import com.chelsea.weixin.job.BaseJob;
import com.chelsea.weixin.util.Constant;
import com.chelsea.weixin.util.HttpsUtil;
import com.chelsea.weixin.util.TokenUtil;

/**
 * 凭证job
 * 
 * @author baojun
 *
 */
@Component
public class TokenJob extends BaseJob {

	Logger logger = LoggerFactory.getLogger(TokenJob.class);

	@Autowired
	private TokenUtil tokenUtil;

	@Resource(name = "jedisCluster")
	JedisCluster jedisCluster;
	
	@Value("${weixin.jsapiTicketUrl}")
	private String ticketUrl;

	/**
	 * 获取access_token并更新到redis
	 */
	@Override
	public void process() {
		logger.info("开始执行TokenJob...");
		// 生成并缓存access_token
		Token token = tokenUtil.getToken();
		String accessToken = token.getAccessToken();
		int expiresIn = token.getExpiresIn();
		jedisCluster.del(Constant.ACCESS_TOKEN_KEY);
		jedisCluster.set(Constant.ACCESS_TOKEN_KEY, accessToken, "NX", "EX", expiresIn);
		// 生成并缓存jsapi_ticket
		String jsapi_ticket = "";
		String url = ticketUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject json = HttpsUtil.get(url);
		if (json != null) {
			jsapi_ticket = json.getString("ticket");
		}
		jedisCluster.del(Constant.JSAPI_TICKET);
		jedisCluster.set(Constant.JSAPI_TICKET, jsapi_ticket, "NX", "EX", expiresIn);
		logger.info("缓存access_token：" + accessToken);
		logger.info("缓存jsapi_ticket：" + jsapi_ticket);
		logger.info("有效期expiresIn：" + expiresIn);
	}

}
