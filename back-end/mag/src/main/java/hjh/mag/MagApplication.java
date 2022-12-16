package hjh.mag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MagApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagApplication.class, args);
	}

}
