package com.chelsea.weixin.domain.message.req;

/**
 * 请求消息之文本消息
 * 
 * @author baojun
 *
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String Content;

	public TextMessage() {
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
