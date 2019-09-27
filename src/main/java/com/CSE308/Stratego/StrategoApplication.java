package com.CSE308.Stratego;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@EnableAutoConfiguration
public class StrategoApplication {

	public static void main(String[] args) {
		disableWarning();
	    SpringApplication.run(StrategoApplication.class, args);

	}
	public static void disableWarning() {
		System.err.close();
		System.setErr(System.out);
	}
}


