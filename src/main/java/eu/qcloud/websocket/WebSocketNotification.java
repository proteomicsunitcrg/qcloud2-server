package eu.qcloud.websocket;

public class WebSocketNotification {
	
	private String action;
	
	private Object object;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	public WebSocketNotification() {}

	public WebSocketNotification(String action, Object object) {
		this.action = action;
		this.object = object;
	}
	
	
}
