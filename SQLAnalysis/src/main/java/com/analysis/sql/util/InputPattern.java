package com.analysis.sql.util;

import java.util.regex.Pattern;

/**
 * Input Pattern validator
 * @author ram
 *
 */
public class InputPattern {
	
	
	public static void main(String args[]) throws Exception {
		String line = "USE [huuuhhu]";
		System.out.println(isLineAComment(line));
		line = "   USE [dfdfdd]";
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
	public static String commentMidPattern = "\\s*\\*+\\s*.*";
	
	/**
	 * matches the end of comment started on new line.
	 */
	public static String commentEndPattern = "(\\s*\\**\\/)|(\\**\\/)|(^\\*+\\/$)";
	
	/**
	 * looks for -- comment patter and consider a space before the pattern
	 */
	public static String mssqlQueryCommentPattern = "\\s*--.*";
	
	
	/**
	 * if the line is starts /**
	 * or <space>*
	 * or 
	 * @param line
	 * @return
	 */
	public static boolean isLineAComment(String line){
		
		
		
		Pattern pattern = Pattern.compile(commentStartPattern,Pattern.CASE_INSENSITIVE);
		if(pattern.matcher(line).matches()){
			//System.out.println("matching start comment pattern");
			return true;
		}
		
		Pattern pattern2 = Pattern.compile(commentMidPattern,Pattern.CASE_INSENSITIVE);
		if(pattern2.matcher(line).matches()) {
			//System.out.println("matching commentMidPattern pattern");
			return true;
		}
		
		Pattern pattern3 = Pattern.compile(commentEndPattern,Pattern.CASE_INSENSITIVE);
		if( pattern3.matcher(line).matches()){
			//System.out.println("matching commentEndPattern pattern");
			return true;
		}
		
		Pattern pattern4 = Pattern.compile(mssqlQueryCommentPattern,Pattern.CASE_INSENSITIVE);
		if( pattern4.matcher(line).matches()){
			//System.out.println("matching commentEndPattern pattern");
			return true;
		}
		
		return false;
	}
	
	
	public static boolean isKeywordDecorator(){
		
		//Pattern pattern4 = Pattern.compile(mssqlQueryCommentPattern,Pattern.CASE_INSENSITIVE);
		
		return false;
	}
	
	
	
	
	

}
