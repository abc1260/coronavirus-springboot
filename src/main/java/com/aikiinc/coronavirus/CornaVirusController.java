package com.aikiinc.coronavirus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class CornaVirusController {
	
	@GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
	public HelloMessage index() {
		return new HelloMessage();
	}

	class HelloMessage {
		public String message = "Greetings from Spring Boot!";		
	}

}