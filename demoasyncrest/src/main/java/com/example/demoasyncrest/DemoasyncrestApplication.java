package com.example.demoasyncrest;

import com.example.demoasyncrest.service.MessageSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoasyncrestApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoasyncrestApplication.class, args);
	}

    @Autowired
    private MessageSender sender;

    @Override
    public void run(String... strings) throws Exception {
        sender.sendMessage("","Sending sample demo message");
    }
}
