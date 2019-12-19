package fr.insa.Services.Lightmanagemnt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
	 @JsonProperty("Mvmt_decision")
	 Boolean Mvmt_decision;
	 @JsonProperty("Temp_decision")
	 String Temp_decision;
	public Data() {
		// TODO Auto-generated constructor stub
	}
	public Boolean getMvmt_decision() {
		return Mvmt_decision;
	}

	public void setMvmt_decision(Boolean mvmt_decision) {
		Mvmt_decision = mvmt_decision;
	}

	public String getTemp_decision() {
		return Temp_decision;
	}

	public void setTemp_decision(String temp_decision) {
		Temp_decision = temp_decision;
	}
	
}
