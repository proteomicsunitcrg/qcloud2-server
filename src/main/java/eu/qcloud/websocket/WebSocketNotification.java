package eu.qcloud.websocket;

import java.util.UUID;

public class WebSocketNotification {

	private String action;

	private UUID apiKey;

	private String qccv;

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

	public UUID getApiKey() {
		return apiKey;
	}

	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}

	public String getQccv() {
		return qccv;
	}

	public void setQccv(String qccv) {
		this.qccv = qccv;
	}

	public WebSocketNotification() {
	}

	/**
	 * Apikey is not mandatory. Use only if needed.
	 * 
	 * @param action
	 * @param apiKey
	 * @param qccv
	 * @param object
	 */
	public WebSocketNotification(String action, UUID apiKey, String qccv, Object object) {
		this.action = action;
		this.qccv = qccv;
		this.object = object;
		this.apiKey = apiKey;
	}

}
