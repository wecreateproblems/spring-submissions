package com.student.api;

import com.student.api.controller.StudentController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication extends StudentController {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
