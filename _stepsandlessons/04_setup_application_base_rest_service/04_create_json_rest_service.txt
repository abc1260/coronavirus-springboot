Create Basic SpringBooot SpringFramework Rest Response As JSON
--------------------------------------------------------------------------------
By default SpringFramework creates Rest Services As Json Objects.
But we need an object to really see how easy it is to create.

Change code to this:
package com.aikiinc.coronavirus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class CornaVirusController {
	
	@GetMapping(value="/")
	public HelloMessage index() {
		return new HelloMessage();
	}

	class HelloMessage {
		public String message = "Greetings from Spring Boot!";		
	}

}


* Explained
-----------------
Create a basic HelloMessage class:
	class HelloMessage {
		public String message = "Greetings from Spring Boot!";		
	}

    Note:
        We did not create setters and getters. But made the message public.
        This will allow us access to the message data member.

Change The Rest endpoint "/" to return the HelloMessage object.
	@GetMapping(value="/")
	public HelloMessage index() {
		return new HelloMessage();
	}



Run The Springboot Application
--------------------------------------------------------------------------------
cd coronavirus_springboot_restservice

mvn spring-boot:run

Open browser to:
http://localhost:8080/


* Explained
--------------------
If we look at the raw JSON data we see:
    {"message":"Greetings from Spring Boot!"}

The message as a JSON response. Great!



Clearly Define The Endpoint
--------------------------------------------------------------------------------
It's good to clearly state for the next developer what the method is doing:

@GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
public HelloMessage index() {
    return new HelloMessage();
}

* Explained
--------------------
Clearly state tha endpoint generates JSON data:
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)

    produces = MediaType.APPLICATION_JSON_VALUE means it produces
    http mime type : "application/json".

Restart server and see the same results.


