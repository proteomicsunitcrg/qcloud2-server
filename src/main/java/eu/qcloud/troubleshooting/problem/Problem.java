package eu.qcloud.troubleshooting.problem;

import java.util.UUID;

import javax.persistence.Entity;

import eu.qcloud.troubleshooting.Troubleshooting;

@Entity(name = "problem")
public class Problem extends Troubleshooting {
	
	public Problem() {}

	public Problem(String name, String description, String qccv, UUID apiKey) {
		super(name, description, qccv, apiKey);
	}

}
