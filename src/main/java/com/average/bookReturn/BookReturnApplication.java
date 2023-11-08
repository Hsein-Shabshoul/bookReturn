package com.average.bookReturn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BookReturnApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookReturnApplication.class, args);
	}

}
