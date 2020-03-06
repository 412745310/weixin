package com.chelsea.weixin.domain.message.resp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文本消息
 * 
 * @author baojun
 *
 */
@XStreamAlias("xml")
public class TextMessage extends BaseMessage {

	// 回复的消息内容
    @XStreamAlias("Content")
	private String content;

	public TextMessage() {
	}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
