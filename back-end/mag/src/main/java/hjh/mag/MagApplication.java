package hjh.mag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MagApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagApplication.class, args);
	}

}
