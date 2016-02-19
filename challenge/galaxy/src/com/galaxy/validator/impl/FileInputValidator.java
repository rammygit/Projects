package com.galaxy.validator.impl;

import com.galaxy.model.RomanNumeral;
import com.galaxy.util.ErrorCodes;
import com.galaxy.util.InputPattern;
import com.galaxy.validator.Validator;

/**
 * vaidates the input data read from the file.
 * @author RXS1683
 *
 */
public class FileInputValidator implements Validator {

	

	@Override
	public boolean validate(String input) {
		
		if(input.charAt(input.length()-1) == '?') {
			if(!InputPattern.isQuestion(input)){
				System.out.println(ErrorCodes.Invalid_question.getErrorMessage());
				return false;
			}
		} else if(input.endsWith("Credits")) {
			if(!InputPattern.isCreditData(input)){
				System.out.println(ErrorCodes.invalid_credit_data.getErrorMessage());
				return false;
			}
		} else if(!isRomanNumeral(String.valueOf(input.charAt(input.length()-1)))){
			System.out.println(ErrorCodes.invalid_data.getErrorMessage());
			return false;
		}
		return true;
	
	}
	
	public boolean isRomanNumeral(String value){
		boolean flag = false;
		for(RomanNumeral numeral : RomanNumeral.values()){
			if(numeral.getName().equals(value)){
				flag =  true;
			}
		}
		return flag;
	}

}
