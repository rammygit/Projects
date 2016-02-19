package com.analysis.sql.util;

import java.util.regex.Pattern;

/**
 * Input Pattern validator
 * @author ram
 *
 */
public class InputPattern {
	
	
	public static void main(String args[]) throws Exception {
		String line = "USE [epostrx]";
		System.out.println(isLineAComment(line));
		line = "   USE [epostrx]";
		System.out.println(isLineAComment(line));
		line = "/**";
		System.out.println(isLineAComment(line));
		line = "/****** Object:";
		System.out.println(isLineAComment(line));
		line = "*/";
		System.out.println(isLineAComment(line));
		line = "*";
		System.out.println(isLineAComment(line));
		line = " *ram";
		System.out.println(isLineAComment(line));
		line = " *";
		System.out.println(isLineAComment(line));
	}
	
	/*
	 * (\/\*\*)|(\s*\/\*\s)|(\s*\*\*)|(\s*\*\/)
	 */
	public static String commentStartPattern = "(^(\\/\\*\\**\\s)(.*)$)|(^(\\/\\*\\**)$)|(^(\\s+\\/\\*\\s+)$)";
	
	/**
	 * <space>atleast once and then a star followed by any character without line breaks.
	 */
	public static String commentMidPattern = "^(\\s+\\*+.*)$";
	
	/**
	 * matches the end of comment started on new line.
	 */
	public static String commentEndPattern = "(^(\\s*\\*\\/)$)|(^(\\**\\/)$)";
	
	
	/**
	 * if the line is starts /**
	 * or <space>*
	 * or 
	 * @param line
	 * @return
	 */
	public static boolean isLineAComment(String line){
		
		System.out.println(line);
		
		
		Pattern pattern = Pattern.compile(commentStartPattern);
		if(pattern.matcher(line).matches()){
			System.out.println("matching start comment pattern");
			return true;
		}
		
		Pattern pattern2 = Pattern.compile(commentMidPattern);
		if(pattern2.matcher(line).matches()) {
			System.out.println("matching commentMidPattern pattern");
			return true;
		}
		
		Pattern pattern3 = Pattern.compile(commentEndPattern);
		if( pattern3.matcher(line).matches()){
			System.out.println("matching commentEndPattern pattern");
			return true;
		}
		
		return false;
	}
	
	
	
	
	

}
