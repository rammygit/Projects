package com.example.myproject.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class ApplicationExecutorService {

	private final ExecutorService executorService = Executors.newFixedThreadPool(3);

	public void runWorker(){

		Runnable task = ()->{
			System.out.println("thread name -> "+Thread.currentThread().getName());
		};

		for(char c: "string".toCharArray())
			runTask(task);
		
		executorService.shutdown();

	}

	private void runTask(Runnable runnable){

		executorService.execute(runnable);
	}
	
	
	public static void main(String args[]) throws Exception {
		
		ApplicationExecutorService applicationExecutorService = new ApplicationExecutorService();
		applicationExecutorService.runWorker();
	}

}
