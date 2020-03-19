package com.chelsea.weixin.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import redis.clients.jedis.JedisCluster;

import com.chelsea.weixin.domain.SNSUserInfo;
import com.chelsea.weixin.domain.WeixinOauth2Token;
import com.chelsea.weixin.domain.WeixinUserInfo;
import com.chelsea.weixin.domain.menu.Menu;
import com.chelsea.weixin.domain.message.resp.TextMessage;
import com.chelsea.weixin.util.BaiduAIUtil;
import com.chelsea.weixin.util.CommonUtil;
import com.chelsea.weixin.util.Constant;
import com.chelsea.weixin.util.HttpUtil;
import com.chelsea.weixin.util.HttpsUtil;
import com.chelsea.weixin.util.MenuUtil;
import com.chelsea.weixin.util.MessageUtil;
import com.chelsea.weixin.util.TokenUtil;
import com.chelsea.weixin.util.WeixinUtil;

/**
 * 微信service
 * 
 * @author shevchenko
 *
 */
@Service
public class WeixinService {

    private Logger logger = LoggerFactory.getLogger(WeixinService.class);

    @Autowired
    MenuUtil menuUtil;

    @Autowired
    TokenUtil tokenUtil;

    @Resource(name = "jedisCluster")
    JedisCluster jedisCluster;

    @Value("${weixin.userinfoUrl}")
    private String userinfoUrl;

    @Value("${weixin.oauth2UserinfoUrl}")
    private String oauth2UserinfoUrl;

    @Value("${juhe.chat-robot.url}")
    private String juheChatRobotUrl;

    @Value("${juhe.chat-robot.key}")
    private String juheChatRobotKey;

    @Autowired
    private WeixinUtil weixinUtil;

    @Value("${weixin.setIndustryUrl}")
    private String setIndustryUrl;

    @Value("${weixin.addMediaUrl}")
    private String addMediaUrl;
    
    @Value("${weixin.getMediaUrl}")
    private String getMediaUrl;

    /**
     * 处理微信发来的请求
     * 
     * @param request
     * @return xml
     */
    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent = "未知的消息类型！";
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号
            String fromUserName = requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            logger.info("receive message : {}", requestMap);
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                String content = requestMap.get("Content");
                respContent = getChat(content);
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                String picUrl = requestMap.get("PicUrl");
                respContent = getImage(picUrl);
            }
            // 语音消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
                respContent = "您发送的是小视频消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 关注
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                }
                // 取消关注
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }
                // 扫描带参数二维码
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    respContent = requestMap.get("EventKey");
                }
            }
            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setContent(respContent);
            // 将文本消息对象转换成xml
            respXml = MessageUtil.beanToXml(textMessage);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return respXml;
    }

    /**
     * 获取图片信息
     * 
     * @param picUrl
     * @return
     */
    private String getImage(String picUrl) {
        // 微信远程图片保存到本地
        String downloadPicture = CommonUtil.downloadPicture(picUrl);
        // 调用百度AI身份证识别接口
        String content = BaiduAIUtil.IdCardIdentification(downloadPicture);
        return content;
    }

    /**
     * 获取聊天内容
     * 
     * @return
     */
    private String getChat(String content) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("key", juheChatRobotKey);
        paramMap.add("info", content);
        JSONObject jsonObject = HttpUtil.post(juheChatRobotUrl, paramMap);
        String res = jsonObject.getJSONObject("result").getString("text");
        return res;
    }

    /**
     * 创建菜单
     * 
     * @param menu
     */
    public void createMenu(Menu menu) {
        menuUtil.createMenu(menu);
    }

    /**
     * 获取用户信息
     */
    public WeixinUserInfo getUserInfo(String openid) {
        try {
            String accessToken = jedisCluster.get(Constant.ACCESS_TOKEN_KEY);
            String requestUrl = userinfoUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
            JSONObject jsonObject = HttpsUtil.get(requestUrl);
            WeixinUserInfo weixinUserInfo = null;
            if (null != jsonObject) {
                weixinUserInfo = new WeixinUserInfo();
                // 用户的标识
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                // 用户关注时间
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            }
            return weixinUserInfo;
        } catch (Exception e) {
            logger.error("获取用户信息出错" + e.getMessage());
            throw new RuntimeException("获取用户信息出错" + e.getMessage());
        }
    }

    /**
     * 授权认证
     * 
     * @param code
     */
    @SuppressWarnings({"unchecked", "deprecation"})
    public SNSUserInfo authorize(String code) {
        SNSUserInfo snsUserInfo = null;
        String value = jedisCluster.get(code);
        String accessToken = "";
        String openId = "";
        if (StringUtils.isBlank(value)) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = tokenUtil.getOauth2AccessToken(code);
            // 网页授权接口访问凭证
            accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            openId = weixinOauth2Token.getOpenId();
            jedisCluster.set(code, accessToken + "|" + openId, "NX", "EX", 300);
        } else {
            String[] arr = value.split("\\|");
            accessToken = arr[0];
            openId = arr[1];
        }
        String requestUrl = oauth2UserinfoUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = HttpsUtil.get(requestUrl);
        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                logger.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
    }

    /**
     * 获取微信配置信息
     * 
     * @param request
     * @return
     */
    public Map<String, Object> getWxConfig(String url) {
        return weixinUtil.getWxConfig(url);
    }

    /**
     * 设置所属行业
     */
    public String setIndustry() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("industry_id1", "1");
        paramMap.put("industry_id2", "4");
        JSONObject paramJson = JSONObject.fromObject(paramMap);
        String accessToken = jedisCluster.get(Constant.ACCESS_TOKEN_KEY);
        String url = setIndustryUrl.replace("ACCESS_TOKEN", accessToken);
        JSONObject result = HttpUtil.post(url, paramJson);
        return result.toString();
    }

    /**
     * 新增图片素材
     * 
     * @return
     */
    public String addMedia() {
        File file = new File("C:/Users/Administrator/Desktop/1.jpg");
        // 设置请求体，注意是LinkedMultiValueMap
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("file", fileSystemResource);
        paramMap.add("filename", file.getName());
        String accessToken = jedisCluster.get(Constant.ACCESS_TOKEN_KEY);
        // type: 图片（image）、语音（voice）、视频（video）和缩略图（thumb）
        String url = addMediaUrl.replace("ACCESS_TOKEN", accessToken).replace("TYPE", "image");
        JSONObject result = HttpsUtil.postFile(url, paramMap);
        return result.toString();
    }
    
    /**
     * 获得素材
     * 
     * @return
     */
    public String getMedia() {
        String accessToken = jedisCluster.get(Constant.ACCESS_TOKEN_KEY);
        String url = getMediaUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", "QK_sj5olN6WLAqMW4CtZVsIfdqo1NDRTWFXcdTd-jolq3OhgFQBsCxh4R2_vrKRQ");
        return "redirect:" + url;
    }
    
}
