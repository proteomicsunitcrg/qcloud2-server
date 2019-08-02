package eu.qcloud.troubleshooting.action;

import java.util.UUID;

import javax.persistence.Entity;

import eu.qcloud.troubleshooting.Troubleshooting;

@Entity(name = "action")
public class Action extends Troubleshooting {

	public Action() {
	}

	public Action(String name, String description, String qccv, UUID apiKey) {
		super(name, description, qccv, apiKey);
	}

}
