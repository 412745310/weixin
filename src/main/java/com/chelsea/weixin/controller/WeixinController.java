package com.chelsea.weixin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chelsea.weixin.domain.SNSUserInfo;
import com.chelsea.weixin.domain.WeixinConfig;
import com.chelsea.weixin.domain.WeixinUserInfo;
import com.chelsea.weixin.domain.menu.Button;
import com.chelsea.weixin.domain.menu.CommonButton;
import com.chelsea.weixin.domain.menu.ComplexButton;
import com.chelsea.weixin.domain.menu.Menu;
import com.chelsea.weixin.service.WeixinService;
import com.chelsea.weixin.util.CommonUtil;
import com.chelsea.weixin.util.Constant;
import com.chelsea.weixin.util.SignUtil;

/**
 * 微信controller
 * 
 * @author shevchenko
 *
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController extends SpringBootServletInitializer{

	private Logger logger = LoggerFactory.getLogger(WeixinController.class);

	// 与接口配置信息中的Token要一致
	@Value("${weixin.token}")
	private String token;
	
	@Value("${weixin.appid}")
	private String appid;
	
	@Value("${weixin.oauth2AuthorizeUrl}")
	private String oauth2AuthorizeUrl;
	
	@Value("${weixin.oauth2RedirectUrl}")
	private String oauth2RedirectUrl;

	@Autowired
	private WeixinService weixinService;
	
	@Value("${weixin.setIndustryUrl}")
	private String setIndustryUrl;
	
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
	@ResponseBody
	public String getMessage(String signature, String timestamp, String nonce,
			String echostr) {
		logger.info("signature:" + signature);
		logger.info("timestamp:" + timestamp);
		logger.info("nonce:" + nonce);
		logger.info("echostr:" + echostr);
		boolean flag = SignUtil.checkSignature(signature, timestamp, nonce,
				token);
		if (flag) {
		    logger.info("验证成功");
			return echostr;
		} else {
		    logger.info("验证失败");
			return null;
		}
	}

	/**
	 * 处理微信服务器发来的消息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get_message", method = RequestMethod.POST)
	@ResponseBody
	public String getMessage(HttpServletRequest request) {
		String respXml = weixinService.processRequest(request);
		return respXml;
	}

	/**
	 * 创建菜单
	 */
	@RequestMapping("/create_menu")
	@ResponseBody
	public void createMenu() {
		CommonButton btn11 = new CommonButton();
		btn11.setName("百度");
		btn11.setType("view");
		btn11.setUrl("http://www.baidu.com");
		
		CommonButton btn12 = new CommonButton();
		btn12.setName("用户信息");
		btn12.setType("view");
		String url = oauth2AuthorizeUrl.replace("APPID", appid);
		url = url.replace("STATE", "123");
		// url = url.replace("REDIRECT_URI", CommonUtil.urlEncodeUTF8(oauth2RedirectUrl));
		url = url.replace("SCOPE", Constant.SNSAPI_USERINFO);
		btn12.setUrl(url);
		
		CommonButton btn21 = new CommonButton();
		btn21.setName("身份证识别");
		btn21.setType("pic_photo_or_album");
		btn21.setKey("21");

		CommonButton btn31 = new CommonButton();
		btn31.setName("Q友圈");
		btn31.setType("click");
		btn31.setKey("31");

		/**
		 * 微信： mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
		 */

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("生活助手");
		// 一级下有4个子菜单
		mainBtn1.setSub_button(new CommonButton[] { btn11, btn12});

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("AI工具");
		mainBtn2.setSub_button(new CommonButton[] { btn21});

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("更多体验");
		mainBtn3.setSub_button(new CommonButton[] { btn31});

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
	
	/**
	 * 授权认证
	 */
	@RequestMapping("/authorize")
	public String authorize(ModelMap map, String code, String state){
		SNSUserInfo snsUserInfo = new SNSUserInfo();
		if(!"authdeny".equals(code)){
			snsUserInfo = weixinService.authorize(code);
		}
		map.put("snsUserInfo", snsUserInfo);
		map.put("state", 200);
	    return "index";
	}
	
	/**
	 * 获取微信配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getWxConfig")
	@ResponseBody
	public WeixinConfig getWxConfig(String url){
		Map<String, Object> wxConfigMap = weixinService.getWxConfig(url);
		String appId = wxConfigMap.get("appId").toString();
		String timestamp = wxConfigMap.get("timestamp").toString();
		String nonceStr = wxConfigMap.get("nonceStr").toString();
		String signature = wxConfigMap.get("signature").toString();
		WeixinConfig config = new WeixinConfig();
		config.setAppId(appId);
		config.setTimestamp(timestamp);
		config.setNonceStr(nonceStr);
		config.setSignature(signature);
		return config;
	}
	
	@RequestMapping("share")
	public String share(){
		return "share";
	}
	
	/**
	 * 设置所属行业
	 */
	@RequestMapping("/setIndustry")
	@ResponseBody
	public String setIndustry() {
	    return weixinService.setIndustry();
	}
	
	/**
	 * 新增素材
	 * 
	 * @return
	 */
	@RequestMapping("/addMedia")
    @ResponseBody
	public String addMedia() {
	    return weixinService.addMedia();
	}
	
	/**
     * 获得素材
     * 
     * @return
     */
    @RequestMapping("/getMedia")
    public String getMedia() {
        return weixinService.getMedia();
    }
    
    /**
     * 获得二维码
     * 
     * @return
     */
    @RequestMapping("/getQrCode")
    public String getQrCode() {
        return weixinService.getQrCode();
    }
	
}
