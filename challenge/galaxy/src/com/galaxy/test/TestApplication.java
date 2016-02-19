package com.galaxy.test;

import com.galaxy.main.GalaxyMerchantGuide;

/**
 * 
 * @author ram
 *
 */
public class TestApplication {
	
	public static void main(String args[]) {

		
		GalaxyMerchantGuide merchantGuide = new GalaxyMerchantGuide();
		
		System.out.println("\n <<<<<<<<<<<  Test Data - 1 : Asserting success response >>>>>>>>>>>>>>> \n");
		merchantGuide.doGuide("inputFile\\input.txt");
		
		System.out.println("\n <<<<<<<<<<<  Test Data - 2  : Asserting Bad Data output >>>>>>>>>>>>>>> \n");
		merchantGuide.doGuide("inputFile\\bad_input.txt");
		
		System.out.println("\n <<<<<<<<<<<  Test Data - 3 : Asserting exception -  file not found >>>>>>>>>>>>>>> \n");
		merchantGuide.doGuide("inputFile\\noFileInput.txt");
		
	}

}
