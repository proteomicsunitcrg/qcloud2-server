package eu.qcloud.troubleshooting.test;

import java.util.UUID;

import javax.persistence.Entity;

import eu.qcloud.troubleshooting.Troubleshooting;

@Entity(name = "test")
public class Test extends Troubleshooting {

	public Test() {
	}

	public Test(String name, String description, String qccv, UUID apiKey) {
		super(name, description, qccv, apiKey);
	}

}
