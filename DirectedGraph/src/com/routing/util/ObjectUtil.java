package com.routing.util;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class ObjectUtil {
	
	/**
	 * to avoid external initialization
	 */
	private ObjectUtil(){
		
	}

	/**
	 * convert char to String.
	 * utility to change later if needs to have better implementation
	 * @param c
	 * @return
	 */
	public static String charToString(char c){
		
		return String.valueOf(c);
	}
	
	/**
	 * converts char to int value.
	 * utility to change later if needs to have better implementation.
	 * @param c
	 * @return
	 */
	public static int charToInt(char c){
		return Character.getNumericValue(c);
	}
}
