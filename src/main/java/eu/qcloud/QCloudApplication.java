package eu.qcloud;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QCloudApplication {

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Madrid"));
	}

	public static void main(String[] args) {
		SpringApplication.run(QCloudApplication.class, args);
	}
}
