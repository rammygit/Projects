package com.galaxy.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Any generic exception handling logic goes here.
 * log to DB or journal.
 * @author ram
 *
 */
public class ExceptionHandler {
	
	
	/**
	 * wraps the stacktrace for efficient handling.
	 * @param aThrowable
	 * @return
	 */
	public static String getStackTrace(Throwable aThrowable) {
		System.out.println("********* Printing wrapper stacktrace *********** \n");
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}
	
	


}
