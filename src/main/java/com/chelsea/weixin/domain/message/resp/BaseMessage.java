package com.chelsea.weixin.domain.message.resp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 消息基类（公众帐号 -> 普通用户）
 * 
 * @author baojun
 *
 */
public class BaseMessage {
	// 接收方帐号（收到的OpenID）
    @XStreamAlias("ToUserName")
	private String toUserName;
	// 开发者微信号
    @XStreamAlias("FromUserName")
	private String fromUserName;
	// 消息创建时间 （整型）
    @XStreamAlias("CreateTime")
	private long createTime;
	// 消息类型
    @XStreamAlias("MsgType")
	private String msgType;

	public BaseMessage() {
	}

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

}
