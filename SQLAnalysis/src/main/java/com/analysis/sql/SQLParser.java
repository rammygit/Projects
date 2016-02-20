package com.analysis.sql;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.analysis.sql.util.FileReader;
import com.analysis.sql.util.InputPattern;

/**
 * 
 * @author ram
 *
 */
public class SQLParser {

	/**
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		List<String> parsedLines = FileReader.readFile("src\\main\\resources\\input\\SampleSP.sql");
		
		List<String> finalList = new ArrayList<String>();
		
		
		/* test loop and removing lines we dont need see */
		for(int i=0; i< parsedLines.size();i++){
			
			if(!canRemoveLine(parsedLines.get(i))) {
				/**
				 * if the line is not eligible to be removed , then sanitize before adding it to the final list.
				 */
				finalList.add(sanitizeLine(parsedLines.get(i)));
			}
		}
		
		System.out.println("printing final list ..... ");
		for(int i=0; i< finalList.size();i++){
			System.out.println(finalList.get(i));
			
		}
	}
	
	private static boolean canRemoveLine(String line){
		
		
		if( InputPattern.isLineAComment(line)) return true;
		
		if(InputPattern.isKeywordDecorator()) return true;
		
		return false;
	}
	
	
	private static String sanitizeLine(String line){
		
		if(line.contains("--")){
			/**
			 * will remove the 
			 */
			line = line.substring(0,line.indexOf("--"));
			
		}
		
		return line;
	}
	
	
	

}
