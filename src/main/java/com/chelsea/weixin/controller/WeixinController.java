package com.chelsea.weixin.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chelsea.weixin.service.WeixinService;
import com.chelsea.weixin.util.SignUtil;

/**
 * 微信controller
 * 
 * @author baojun
 *
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController { 

	private Logger logger = LoggerFactory.getLogger(WeixinController.class);
	
	private int i = 0;
	
	private int j = 1;

	// 与接口配置信息中的Token要一致
	@Value("${weixin.token}")
	private String token;

	@Autowired
	private WeixinService weixinService;

	/**
	 * 确认请求来自微信服务器
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@RequestMapping(value = "/get_message", method = RequestMethod.GET)
	public String getMessage(String signature, String timestamp, String nonce,
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

	/**
	 * 处理微信服务器发来的消息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get_message", method = RequestMethod.POST)
	public String getMessage(HttpServletRequest request) {
		String respXml = weixinService.processRequest(request);
		return respXml;
	}

}
