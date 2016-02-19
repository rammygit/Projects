package com.galaxy.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.galaxy.processor.InputProcessor;

/**
 * 
 * @author ram
 *
 */
public class FileReader {
	
	/**
	 * reads and returns the arrayList of fileContents
	 * every line is an entry into the array
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<String> readInputFile(String fileName) throws FileNotFoundException{
		List<String> fileContentList = new ArrayList<String>();
		Scanner fileScanner = new Scanner(new File(fileName));
		InputProcessor processor = new InputProcessor();
		while(fileScanner.hasNextLine()){
			
			fileContentList.add(fileScanner.nextLine());
			
		}
		return fileContentList;
	}
	

}
