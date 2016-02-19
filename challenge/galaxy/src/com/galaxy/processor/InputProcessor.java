package com.galaxy.processor;

import com.galaxy.model.KnowledgeBase;
import com.galaxy.model.Metal;
import com.galaxy.model.QuestionType;
import com.galaxy.model.RomanNumeral;
import com.galaxy.util.ApplicationLogger;
import com.galaxy.util.Constants;
import com.galaxy.util.ExceptionHandler;
import com.galaxy.util.InputPattern;

/**
 * Processes the input for creating the knowledgebase 
 * and answering the question asked.
 * @author ram
 *
 */
public class InputProcessor {


	/**
	 * reads the input given and creates a knowledge base 
	 * for the application to execute.
	 * 
	 * @param input
	 */
	public void process(String input){
		
		try{
			processInput(input);
			
		}catch(Exception ex){
			
			ApplicationLogger.logError(InputProcessor.class,ExceptionHandler.getStackTrace(ex));
		}
		
	}


	/**
	 * assumption if the text ends with '?' then it is the question 
	 * which we need to process
	 * @param input
	 */
	private void processInput(String input) throws Exception {

		if(InputPattern.isCreditData(input)){
			
			processCreditDetails(input);
			
		} else if(InputPattern.isConversionData(input)) {
			
			processSourceData(input);
			
		} else if(InputPattern.isQuestion(input)){
			
			processQuestion(input);
			
		} else {
			
			QuestionType.other.printOutput(null, null);
		}
	}

	/**
	 * assumes we have space and we split with space in the text.
	 * 
	 * @param input
	 */
	private void processCreditDetails(String input) throws Exception{

		String[] mainArray = input.trim().split(Constants.INPUT_SPLITTER);
		String[] wordsArray = mainArray[0].trim().split(Constants.SPACE);
		String[] valueArray = mainArray[1].trim().split(Constants.SPACE);

		Metal metal = new Metal();
		metal.setCreditValue( Integer.valueOf(valueArray[0]));

		Integer currentVal = 0;
		Integer nextVal = 0;


		for(int i=0;i < wordsArray.length;i++){

			currentVal =  KnowledgeBase.getDataMap().get(wordsArray[i]);

			if(i != wordsArray.length-1) 				
				nextVal =  KnowledgeBase.getDataMap().get(wordsArray[i+1]);

			if(currentVal !=null && nextVal !=null){
				if(nextVal > currentVal ){
					metal.setUnit(nextVal - currentVal);
				} else {
					metal.setUnit(nextVal + currentVal);
				}
			} else if(KnowledgeBase.getDataMap().get(wordsArray[i]) == null){
				//value not in datamap.
				//if value comes as null in datamap then it is a string literal - Metal Type.
				metal.setName(wordsArray[i]);
			}
		}

		//add only if the map does not have the key already
		if(!KnowledgeBase.getMetalMap().containsKey(metal.getName())){
			KnowledgeBase.getMetalMap().put(metal.getName(), metal);
		}

	}

	/**
	 * 
	 * @param question
	 * @throws Exception
	 */
	private void processQuestion(String question) throws Exception{


		String[] questionArray = question.split(Constants.INPUT_SPLITTER);
		questionArray[1] = questionArray[1].replace(questionArray[1].charAt(questionArray[1].length()-1),Constants.EMPTY_CHAR);
		String[] askArray = questionArray[1].trim().split(Constants.SPACE);

		Integer unit = 0;

		/**
		 * pish tegj glob glob
		 */
		if(InputPattern.isHowMuchQuestion(question)){

			/**
			 * iterating in the steps of 2.			 * 
			 */
			unit = iterateQuestion(askArray, askArray.length-1);

			QuestionType.HowMuchIs.printOutput(question,unit);

		} 

		/**
		 * how many Credits is glob prok Iron ?
		 * Assumption is Metal Type occurs in the question
		 * Metal name is specified in the last string in the question.
		 */
		else if(InputPattern.isHowManyQuestion(question)){

			String metalName = askArray[askArray.length-1];

			unit = iterateQuestion(askArray, askArray.length-2);

			/* so 4 Silver = */
			Metal metal = KnowledgeBase.getMetalMap().get(metalName);
			double output = unit * metal.getSingleUnitValue();
			QuestionType.HowManyCredit.printOutput(question,output);

		}  else {
			QuestionType.other.printOutput(null,null);
		}
	}


	private Integer iterateQuestion(String[] askArray,int startpoint) throws Exception{

		Integer unit = 0;
		Integer currentVal = 0;
		Integer prevVal = 0;

		for(int i=startpoint;i>=0;i-=2){

			currentVal =  KnowledgeBase.getDataMap().get(askArray[i]);

			if(i!=0)
				prevVal = KnowledgeBase.getDataMap().get(askArray[i-1]);

			unit += getRomanValue(currentVal, prevVal);
		}

		return unit;
	}

	/**
	 * i and i-1
	 * current = 50 
	 * prev = 10
	 * @param currentVal
	 * @param prevVal
	 * @return
	 */
	private int getRomanValue(Integer currentVal , Integer prevVal) throws Exception{

		int result = 0;

		if(currentVal!=null && prevVal!=null){
			if(currentVal > prevVal ){
				result = currentVal - prevVal;
			} else {
				result =  currentVal + prevVal;
			}
		}

		return result;
	}


	/**
	 * input pattern => "glob is I"
	 * @param data
	 */
	private void processSourceData(String data) throws Exception{

		String[] arr = data.split(Constants.INPUT_SPLITTER);

		RomanNumeral numeral = RomanNumeral.valueOf(arr[1].trim());

		KnowledgeBase.getDataMap().put(arr[0].trim(), numeral.getValue());

	}

}
