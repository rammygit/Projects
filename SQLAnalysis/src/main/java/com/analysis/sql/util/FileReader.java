package com.analysis.sql.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author RXS1683
 *
 */
public class FileReader {
	
	/**
	 * reads the file into buffered stream for more buffering.
	 * ands to scanner for unchecked exceptions read.
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<String> readFile(String fileName) throws FileNotFoundException{
			List<String> fileContentList = new ArrayList<String>();
			/* more decoration for the stream buffering from a file */
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			Scanner fileScanner = new Scanner(input);
			while(fileScanner.hasNextLine()){
				
				fileContentList.add(fileScanner.nextLine());
				
			}
			return fileContentList;
		}

}
