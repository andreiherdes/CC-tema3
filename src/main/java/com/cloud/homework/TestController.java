package com.cloud.homework;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/")
	public String hello() {
		return "Hello world - springboot-appengine-flex!";
	}
}
