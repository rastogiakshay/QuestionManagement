package com.example.demo.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MultipleOptionSelection {

	@JsonProperty
	private String multipleOption;
	
	@JsonProperty
	private String multipleCheckbox;

	public String getMultipleOption() {
		return multipleOption;
	}

	public void setMultipleOption(String multipleOption) {
		this.multipleOption = multipleOption;
	}

	public String getMultipleCheckbox() {
		return multipleCheckbox;
	}

	public void setMultipleCheckbox(String multipleCheckbox) {
		this.multipleCheckbox = multipleCheckbox;
	}
	
	
	
}
