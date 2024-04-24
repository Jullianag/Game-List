package com.meusprojetos.Game.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GameListApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameListApplication.class, args);
	}


	/*
	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("crip 123456 "+encoder.encode("123456"));
	}

	 */



}
