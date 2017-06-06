package com.chelsea.weixin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chelsea.weixin.util.SignUtil;

/**
 * 微信来源校验类
 * 
 * @author baojun
 *
 */
@RestController
@RequestMapping("/weixin/source")
public class SourceController {

	private Logger logger = LoggerFactory.getLogger(SourceController.class);

	// 与接口配置信息中的Token要一致
	@Value("${weixin.token}")
	private String token;

	@RequestMapping("/check")
	public String check(String signature, String timestamp, String nonce,
			String echostr) {
		logger.info("signature:" + signature);
		logger.info("timestamp:" + timestamp);
		logger.info("nonce:" + nonce);
		logger.info("echostr:" + echostr);
		boolean flag = SignUtil.checkSignature(signature, timestamp, nonce,
				token);
		if (flag) {
			return echostr;
		} else {
			return "";
		}
	}

}
