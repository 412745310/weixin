package com.chelsea.weixin.domain.message.resp;

/**
 * 文本消息
 * 
 * @author baojun
 *
 */
public class TextMessage extends BaseMessage {

	// 回复的消息内容
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
