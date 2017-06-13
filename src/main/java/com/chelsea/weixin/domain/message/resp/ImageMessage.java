package com.chelsea.weixin.domain.message.resp;

/**
 * 图片消息
 * 
 * @author baojun
 *
 */
public class ImageMessage extends BaseMessage {

	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}

}
