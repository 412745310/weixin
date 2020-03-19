package com.chelsea.weixin.util;

import net.sf.json.JSONObject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * http工具类
 * 
 * @author shevchenko
 *
 */
public class HttpUtil {

    /**
     * 发送http post请求(form)
     * 
     * @param url
     * @param paramMap
     * @return
     */
    public static JSONObject post(String url, MultiValueMap<String, Object> paramMap) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, headers);
        JSONObject jsonObj = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        return jsonObj;
    }

    /**
     * 发送http post请求(json)
     * 
     * @param url
     * @param paramJson
     * @return
     */
    public static JSONObject post(String url, JSONObject paramJson) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(paramJson, headers);
        JSONObject jsonObj = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        return jsonObj;
    }

}
