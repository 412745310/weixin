package com.chelsea.weixin.message.event;

/**
 * 自定义菜单事件
 * 
 * @author baojun
 *
 */
public class MenuEvent extends BaseEvent {

	// 事件KEY值，与自定义菜单接口中KEY值对应
	private String EventKey;

	public MenuEvent() {
		super();
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

}
