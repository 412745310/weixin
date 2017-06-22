package com.chelsea.weixin.util;

import java.io.UnsupportedEncodingException;

/**
 * 通用工具类
 * @author shevchenko
 *
 */
public class CommonUtil {

	/**
	 * URL编码（utf-8）
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

	
}
