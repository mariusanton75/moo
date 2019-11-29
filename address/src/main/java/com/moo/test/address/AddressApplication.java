package com.moo.test.address;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.moo.*", "com.moo.mock.database", "com.moo.advice"})
public class AddressApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressApplication.class, args);
	}

}
