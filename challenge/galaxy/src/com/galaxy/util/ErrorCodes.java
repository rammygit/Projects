package com.galaxy.util;

import com.galaxy.model.QuestionType;

/**
 * Error codes for the application.
 *  
 * @author ram
 *
 */
public enum ErrorCodes {
	
	Invalid_question ("00",QuestionType.other.getErrorMessage()),
	invalid_data("01","\n ***** Invalid RomanNumeral or source data ********** \n "),
	invalid_credit_data("02","\n ***** Invalid credit data ********** \n");
	
	private String code;
	
	private String message;
	
	private ErrorCodes(String errorCode, String errorMessage){
		this.code = errorCode;
		this.message = errorMessage;
	}
	
	public String getErrorCode(){
		return this.code;
	}
	
	public String getErrorMessage(){
		return this.message;
	}
	
	

}
