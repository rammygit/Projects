package com.galaxy.util;

/**
 * 
 * @author ram
 * utility class to add logger to the application
 * currently this is used to print error thrown from the catch block.
 */
public class ApplicationLogger {
	
	public static void logError(Class clazz,String error){
		
		System.out.println("printing debug from "+clazz.getSimpleName()+" - "+error);
		
	}
	
	public static void logDebug(Class clazz,String value){
		System.out.println("printing debug from "+clazz.getSimpleName()+" - "+value);
	}
	
	public static void logTrace(Class clazz,String value){
		System.out.println("printing trace from "+clazz.getSimpleName()+" - "+value);
	}

}
