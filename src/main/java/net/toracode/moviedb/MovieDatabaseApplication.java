package net.toracode.moviedb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MovieDatabaseApplication {
	public static void main(String[] args) {
		SpringApplication.run(MovieDatabaseApplication.class, args);
	}
}
