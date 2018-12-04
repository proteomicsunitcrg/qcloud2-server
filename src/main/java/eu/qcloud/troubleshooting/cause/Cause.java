package eu.qcloud.troubleshooting.cause;

import java.util.UUID;

import javax.persistence.Entity;

import eu.qcloud.troubleshooting.Troubleshooting;

@Entity(name="cause")
public class Cause extends Troubleshooting {
	
	public Cause() {}
	
	public Cause(String name, String description, String qccv, UUID apiKey) {
		super(name, description, qccv, apiKey);
	}
}
