package teamB.comicrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ComicrentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComicrentalApplication.class, args);
	}

}
