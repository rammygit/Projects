package com.example.myproject.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.domain.Greeting;

/**
 * controller class.
 * @author ramkumarsundarajan
 *
 */
@RestController
public class GreetingController {

	private static final String template = "Hey %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		
        return new Greeting(counter.incrementAndGet(),String.format(template, name));
    }
	
	@RequestMapping("/shortstring")
	public String shortenString(@RequestParam(value="input")String input) {

        StringBuilder builder = new StringBuilder(input);
        
        for(int i=0;i<builder.length()-1;i++){
        	
            if(builder.charAt(i) != -1 && builder.charAt(i+1) != -1 && builder.charAt(i) == builder.charAt(i+1)){
            	
                builder.delete(i,i+2);
                i = -1;
            }
        }
        
        return ((builder.length() == 0) ? "Empty String":builder.toString());
        
    
	}
	
	
	
	
}
