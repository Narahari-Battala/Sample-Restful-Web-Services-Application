package com.naraharibattala.restfulwebservices;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@Autowired
	MessageSource msgSource;
	
	@GetMapping(value="/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path="/userBean")
	public User getUser() {
		
		return new User(1,"hari", new Date());
	}
	@GetMapping(path="/hello-world-international")
	//public String helloWorldInternational(@RequestHeader(name="Accept-Language", required=false) Locale locale){
		
	public String helloWorldInternational(@RequestHeader(name="Accept-Language", required=false) Locale locale){
		
		//return msgSource.getMessage("good-morning-msg", null,locale);
		
		return msgSource.getMessage("good-morning-msg", null,LocaleContextHolder.getLocale());
		
	}
}
