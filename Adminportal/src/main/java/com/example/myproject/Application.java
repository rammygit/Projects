package com.example.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @Configuration, @EnableAutoConfiguration and @ComponentScan 
 * are put together with @SpringBootApplication
 * @author ramkumarsundarajan
 *
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
	/**
	 * need to know why this is required for the compilation. 
	 * throws error the main not found when using with the spring boot. 
	 * doesnt look clean enough to deploy into container based servers, when this is not required. 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
   }

}
