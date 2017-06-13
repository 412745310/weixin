package com.chelsea.weixin.job.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

import com.chelsea.weixin.domain.Token;
import com.chelsea.weixin.job.BaseJob;
import com.chelsea.weixin.util.Constant;
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

	/**
	 * 获取access_token并更新到redis
	 */
	@Override
	public void process() {
		logger.info("开始执行TokenJob...");
		Token token = tokenUtil.getToken();
		String accessToken = token.getAccessToken();
		int expiresIn = token.getExpiresIn();
		logger.info("获取到accessToken：" + accessToken);
		logger.info("获取到expiresIn：" + expiresIn);
		jedisCluster.del(Constant.ACCESS_TOKEN_KEY);
		jedisCluster.set(Constant.ACCESS_TOKEN_KEY, accessToken, "NX", "EX", expiresIn);
	}

}
