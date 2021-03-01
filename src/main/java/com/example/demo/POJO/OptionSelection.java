package com.example.demo.POJO;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class OptionSelection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private int id;
	
	@JsonProperty
	private String option;
	
	@JsonProperty
	private String answer;
	
	public String getSingleOption() {
		return option;
	}
	public void setSingleOption(String singleOption) {
		this.option = singleOption;
	}
	public String getSingleRadio() {
		return answer;
	}
	public void setSingleRadio(String singleRadio) {
		this.answer = singleRadio;
	}
	

}
