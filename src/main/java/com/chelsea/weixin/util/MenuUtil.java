package com.chelsea.weixin.util;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

import com.chelsea.weixin.domain.menu.Menu;

/**
 * 菜单工具类
 * 
 * @author shevchenko
 *
 */
@Component
public class MenuUtil {
	
	private static Logger log = LoggerFactory.getLogger(MenuUtil.class);
	
	@Autowired
	TokenUtil tokenUtil;
	
	@Value("${weixin.menuCeateUrl}")
	private String menuCeateUrl;
	
	@Resource(name = "jedisCluster")
	JedisCluster jedisCluster;
	
	/**
	 * 创建菜单
	 * @param menu
	 * @return
	 */
	public int createMenu(Menu menu) {
		int result = 0;
		String accessToken = jedisCluster.get(Constant.ACCESS_TOKEN_KEY);
		// 拼装创建菜单的url
		String url = menuCeateUrl.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = HttpsUtil.get(url);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return result;
	}

}
