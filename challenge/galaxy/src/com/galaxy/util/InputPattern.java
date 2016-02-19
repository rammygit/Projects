package com.galaxy.util;

import java.util.regex.Pattern;

import com.galaxy.model.QuestionType;

/**
 * Input Pattern validator
 * @author ram
 *
 */
public class InputPattern {
	
	public static String sourceDataPattern = "^([A-Za-z]+) is ([I|V|X|L|C|D|M])$";
	
	public static String creditDataPattern = "^([A-Za-z]+)([A-Za-z\\s]*) is ([0-9]+) ([c|C]redits)$";
	
	
	public static boolean isHowMuchQuestion(String question){
		Pattern pattern = Pattern.compile(QuestionType.HowMuchIs.getPattern(), Pattern.CASE_INSENSITIVE);
		return pattern.matcher(question).matches();
	}
	
	public static boolean isHowManyQuestion(String question){
		Pattern pattern = Pattern.compile(QuestionType.HowManyCredit.getPattern(), Pattern.CASE_INSENSITIVE);
		return pattern.matcher(question).matches();
	}
	
	
	public static boolean isQuestion(String input){
		if(isHowManyQuestion(input) || isHowMuchQuestion(input))
			return true;
		return false;
	}
	
	public static boolean isCreditData(String input){
		Pattern pattern = Pattern.compile(creditDataPattern, Pattern.CASE_INSENSITIVE);
		return pattern.matcher(input).matches();
	}
	
	public static boolean isConversionData(String input){
		Pattern pattern = Pattern.compile(sourceDataPattern, Pattern.CASE_INSENSITIVE);
		return pattern.matcher(input).matches();
	}
	

}
