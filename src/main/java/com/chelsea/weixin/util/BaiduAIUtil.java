package com.chelsea.weixin.util;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.baidu.aip.ocr.AipOcr;

/**
 * 百度AI工具类
 * 
 * @author shevchenko
 *
 */
@Component
public class BaiduAIUtil {
    
    private static String appId;
    private static String apiKey;
    private static String secretKey;
    
    @Value("${baidu.appId}")
    public void setAppId(String appId) {
        BaiduAIUtil.appId = appId;
    }
    
    @Value("${baidu.apiKey}")
    public void setApiKey(String apiKey) {
        BaiduAIUtil.apiKey = apiKey;
    }

    @Value("${baidu.secretKey}")
    public void setSecretKey(String secretKey) {
        BaiduAIUtil.secretKey = secretKey;
    }

    /**
     * 身份证识别
     * 
     * @param client
     */
    public static String IdCardIdentification(String picUrl) {
        StringBuffer buffer = new StringBuffer();
        try {
            // 初始化一个AipOcr
            AipOcr client = new AipOcr(appId, apiKey, secretKey);
            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);
            String idCardSide = "front";
            HashMap<String, String> options = new HashMap<String, String>();
            JSONObject res = client.idcard(picUrl, idCardSide, options);
            JSONObject info = res.getJSONObject("words_result");
            String name = info.getJSONObject("姓名").getString("words");
            String sex = info.getJSONObject("性别").getString("words");
            String address = info.getJSONObject("住址").getString("words");
            buffer.append("姓名：" + name);
            buffer.append("\n性别：" + sex);
            buffer.append("\n住址：" + address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

}
