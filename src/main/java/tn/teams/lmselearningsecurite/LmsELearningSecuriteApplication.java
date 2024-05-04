package tn.teams.lmselearningsecurite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@SpringBootApplication
public class LmsELearningSecuriteApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsELearningSecuriteApplication.class, args);
	}

}
