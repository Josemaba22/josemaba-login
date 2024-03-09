package com.josemaba.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class SpringSecurityCursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityCursoApplication.class, args);
	}

	// @Bean
	// public CommandLineRunner createPasswordsCommand(PasswordEncoder passwordEncoder){
	// 	return (args) -> {
	// 		System.out.println(passwordEncoder.encode("clave001"));
	// 		System.out.println(passwordEncoder.encode("clave002"));
	// 		System.out.println(passwordEncoder.encode("clave003"));
	// 	};
	// }

}
