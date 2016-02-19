package com.galaxy.model;

/**
 * Metal pojo for holding the metal literal.
 * 
 * @author ram
 *
 */
public class Metal {
	
	private Integer creditValue;
	
	private Integer unit;
	
	private String name;

	public Integer getCreditValue() {
		return creditValue;
	}

	public void setCreditValue(Integer creditValue) {
		this.creditValue = creditValue;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * returns the value of the single unit of metal.
	 * does implicit conversion before multiplying and 
	 * no need of explicit cast or changing the variable type.
	 * @return
	 */
	public double getSingleUnitValue(){
		if(creditValue!=null && unit!=null)
			return creditValue * 1.0 / unit;
		return 0.0;
	}

	@Override
	public String toString() {
		return "Metal [creditValue=" + creditValue + ", unit=" + unit
				+ ", name=" + name + "]";
	}
	
	
}
