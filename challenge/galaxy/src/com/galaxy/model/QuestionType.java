package com.galaxy.model;

/**
 * Specifies the question type and patterns to look for and
 * outputs the value into console based on the type of question asked.
 * @author ram
 *
 */
public enum QuestionType {
	
	
		
	HowMuchIs,HowManyCredit,other;
	
	private final String errorMessage = "I have no idea what you are talking about";
	
	public void printOutput(String question,Object value) {
        switch (this) {
            case HowMuchIs:
                System.out.println(question.substring(question.indexOf("is")+2,question.length()-1).trim()+" is "+value);
                break;
                    
            case HowManyCredit:
                System.out.println(question.substring(question.indexOf("is")+2,question.length()-1).trim()+" is "+Math.round((Double)value)+" Credits");
                break;
                
            default:
                System.out.println(errorMessage);
                break;
        }
    }
	
	public String getPattern() {
        switch (this) {
            case HowMuchIs:
                return "^how much is (([A-Za-z\\s])+)\\?$";
                //break;
                    
            case HowManyCredit:
            	 return "^how many [c|C]redits is (([A-Za-z\\s])+)\\?$";
                
            default:
            	 return "";
        }
    }
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
