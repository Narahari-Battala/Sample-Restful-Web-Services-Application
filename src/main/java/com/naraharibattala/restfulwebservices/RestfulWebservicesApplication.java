package com.naraharibattala.restfulwebservices;


import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class RestfulWebservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebservicesApplication.class, args);
	}
	
	@Bean
	public LocaleResolver localResolver() {
		
		//SessionLocaleResolver resolver = new SessionLocaleResolver();
		AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
		resolver.setDefaultLocale(Locale.US);
		return resolver;
	}
	
	/*@Bean
	public ResourceBundleMessageSource messageSource() {
		
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		
		source.addBasenames("messages");
		
		return source;
		
	}*/
	
	
}
