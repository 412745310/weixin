package com.chelsea.weixin.job.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chelsea.weixin.job.BaseJob;

/**
 * 凭证job
 * @author baojun
 *
 */
public class TokenJob extends BaseJob{
	
	Logger logger = LoggerFactory.getLogger(TokenJob.class);
	
	/**
	 * 获取access_token并更新到redis
	 */
	@Override
	public void process() {
		logger.info("do TokenJob process");
	}
	
}
