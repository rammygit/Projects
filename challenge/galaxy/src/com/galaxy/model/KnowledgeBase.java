package com.galaxy.model;

import java.util.HashMap;

/**
 * KnowledgeBase for the application to process the questions asked.
 * this stores the input values in the way we want to have it stored for efficient processing.
 * Can be evolved as we use it. 
 * @author ram
 *
 */
public class KnowledgeBase {
	
	/**
	 * <Glob,1>
	 */
	private static HashMap<String, Integer> dataMap;
	
	private static KnowledgeBase knowledgeBase;
	
	private static HashMap<String, Metal> metalMap;
	
	
	public KnowledgeBase(){
		dataMap = new HashMap<String, Integer>();
		metalMap = new HashMap<String, Metal>();
	}
	
	private static KnowledgeBase getInstance(){
		if(knowledgeBase == null)
			knowledgeBase = new KnowledgeBase();
		
		return knowledgeBase;
	}

	
	public static HashMap<String, Metal> getMetalMap(){
		getInstance();
		return metalMap;
	}
	
	
	public static HashMap<String, Integer> getDataMap() {
		getInstance();
		return dataMap;
	}


}
