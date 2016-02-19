package com.galaxy.model;

/**
 * Roman Numeral for easy retrival of data and base knowledge to the application
 * @author ram
 *
 */
public enum RomanNumeral {
	
	I("I",1),
	V("V",5),
	X("X",10),
	L("L",50),
	C("C",100),
	D("D",500),
	M("M",1000);
	
	private int value;
	
	private String numeral;
	
	private RomanNumeral(String numeral,int value){
		
		this.numeral = numeral;
		this.value = value;
		
	}
	
	public Integer getValue(){
		return value;
	}
	
	public String getName(){
		return numeral;
	}
	
	

}
