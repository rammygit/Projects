package com.analysis.sql;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.analysis.sql.util.FileReader;
import com.analysis.sql.util.InputPattern;

/**
 * 
 * @author RXS1683
 *
 */
public class SQLParser {

	public static void main(String[] args) throws FileNotFoundException {

		List<String> parsedLines = FileReader.readFile("src\\main\\resources\\input\\SampleSP.sql");
		
		
		/* test loop and removing lines we dont need see */
		for(int i=0; i< parsedLines.size();i++){
			
			if(canRemoveLine(parsedLines.get(i))) {
				parsedLines.remove(i);
			}
			
		}
		
		
		for(int i=0; i< parsedLines.size();i++){
			System.out.println(parsedLines.get(i));
			
		}
		
		
	}
	
	private static boolean canRemoveLine(String line){
		
		System.out.println(" >> "+InputPattern.isLineAComment(line));
		
		return InputPattern.isLineAComment(line);
	}
	
	
	

}
