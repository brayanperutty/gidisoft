package com.ufps.gidisoft;

import com.ufps.gidisoft.seeders.Seed;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GidisoftApplication {

	private final Seed seed;

    public GidisoftApplication(Seed seed) {
        this.seed = seed;
    }

    public static void main(String[] args) {
		SpringApplication.run(GidisoftApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args ->
				seed.seed();
	}

}
