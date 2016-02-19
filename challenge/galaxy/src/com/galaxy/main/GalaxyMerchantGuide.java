package com.galaxy.main;

import java.io.FileNotFoundException;
import java.util.List;

import com.galaxy.processor.InputProcessor;
import com.galaxy.util.ApplicationLogger;
import com.galaxy.util.ExceptionHandler;
import com.galaxy.util.FileReader;
import com.galaxy.validator.Validator;
import com.galaxy.validator.impl.FileInputValidator;

/**
 * Application starts here. 
 * Main program for the galaxy guide application
 * Initially validates the file content
 * @author ram
 *
 */
public class GalaxyMerchantGuide {

	/**
	 * reads from the input file and processes the data. 
	 * @param fileName
	 */
	public void doGuide(String fileName){

		try {
			
			List<String> fileContentList = FileReader.readInputFile(fileName);
			InputProcessor processor = new InputProcessor();
			
			//validate this list.
			Validator validator = new FileInputValidator();
			
			
			for(String data :fileContentList){
				if(validator.validate(data)) {
					processor.process(data);
				} else {
					break;
				}
				//processor.process(data);	
				
			}
			//if all fine , then call processor.
			
			
		} catch (FileNotFoundException e) {
			ApplicationLogger.logError(GalaxyMerchantGuide.class, ExceptionHandler.getStackTrace(e));
		}
	}

}
