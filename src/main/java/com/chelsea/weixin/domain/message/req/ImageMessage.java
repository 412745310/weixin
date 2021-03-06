package com.chelsea.weixin.domain.message.req;

/**
 * 请求消息之图片消息
 * 
 * @author baojun
 *
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String PicUrl;
	private String MediaId;

	public ImageMessage() {
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
