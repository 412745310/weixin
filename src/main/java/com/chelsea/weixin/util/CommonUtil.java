package com.chelsea.weixin.util;

import java.io.UnsupportedEncodingException;
import java.util.Formatter;

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
	
	/**
	 * 字符串加密辅助方法
	 * @param hash
	 * @return
	 */
	public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

	/**
	 * 方法名：byteToStr</br> 详述：将字节数组转换为十六进制字符串</br>
	 * 
	 * @param byteArray
	 * @return
	 * @throws
	 */
	public static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 方法名：byteToHexStr</br> 详述：将字节转换为十六进制字符串</br>
	 * 
	 * @param mByte
	 * @return
	 * @throws
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}
	
}
