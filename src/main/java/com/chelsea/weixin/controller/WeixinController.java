package com.chelsea.weixin.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chelsea.weixin.domain.WeixinUserInfo;
import com.chelsea.weixin.domain.menu.Button;
import com.chelsea.weixin.domain.menu.CommonButton;
import com.chelsea.weixin.domain.menu.ComplexButton;
import com.chelsea.weixin.domain.menu.Menu;
import com.chelsea.weixin.service.WeixinService;
import com.chelsea.weixin.util.SignUtil;

/**
 * 微信controller
 * 
 * @author shevchenko
 *
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController {

	private Logger logger = LoggerFactory.getLogger(WeixinController.class);

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

	/**
	 * 创建菜单
	 */
	@RequestMapping("/create_menu")
	public void createMenu() {
		CommonButton btn11 = new CommonButton();
		btn11.setName("天气预报");
		btn11.setType("click");
		btn11.setKey("11");

		CommonButton btn12 = new CommonButton();
		btn12.setName("公交查询");
		btn12.setType("click");
		btn12.setKey("12");

		CommonButton btn13 = new CommonButton();
		btn13.setName("周边搜索");
		btn13.setType("click");
		btn13.setKey("13");

		CommonButton btn14 = new CommonButton();
		btn14.setName("历史上的今天");
		btn14.setType("click");
		btn14.setKey("14");

		CommonButton btn21 = new CommonButton();
		btn21.setName("歌曲点播");
		btn21.setType("click");
		btn21.setKey("21");

		CommonButton btn22 = new CommonButton();
		btn22.setName("经典游戏");
		btn22.setType("click");
		btn22.setKey("22");

		CommonButton btn23 = new CommonButton();
		btn23.setName("美女电台");
		btn23.setType("click");
		btn23.setKey("23");

		CommonButton btn24 = new CommonButton();
		btn24.setName("人脸识别");
		btn24.setType("click");
		btn24.setKey("24");

		CommonButton btn25 = new CommonButton();
		btn25.setName("聊天唠嗑");
		btn25.setType("click");
		btn25.setKey("25");

		CommonButton btn31 = new CommonButton();
		btn31.setName("Q友圈");
		btn31.setType("click");
		btn31.setKey("31");

		CommonButton btn32 = new CommonButton();
		btn32.setName("电影排行榜");
		btn32.setType("click");
		btn32.setKey("32");

		CommonButton btn33 = new CommonButton();
		btn33.setName("幽默笑话");
		btn33.setType("click");
		btn33.setKey("33");
		
		CommonButton btn34 = new CommonButton();
		btn34.setName("百度");
		btn34.setType("view");
		btn34.setUrl("http://www.baidu.com");

		/**
		 * 微信： mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
		 */

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("生活助手");
		// 一级下有4个子菜单
		mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("休闲驿站");
		mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24,
				btn25 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("更多体验");
		mainBtn3.setSub_button(new CommonButton[] { btn31, btn32, btn33, btn34 });

		/**
		 * 封装整个菜单
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		weixinService.createMenu(menu);
	}
	
	/**
	 * 获取用户信息
	 */
	@RequestMapping("/getuserinfo")
	@ResponseBody
	public WeixinUserInfo getUserInfo(String openid){
		return weixinService.getUserInfo(openid);
	}

}
