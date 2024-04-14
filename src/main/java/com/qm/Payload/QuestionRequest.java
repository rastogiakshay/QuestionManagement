package com.qm.Payload;

import java.util.List;
import java.util.Set;

import com.qm.POJO.OptionSelection;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionRequest {
	
	
	public String getIsScored() {
		return isScored;
	}

	@JsonProperty
	private String question;
	
	@JsonProperty
	private String questionType;

	@JsonProperty
	private String isScored;
	
	@JsonProperty
	private String instructions;

	@JsonProperty
	private Set<String> category;
	
	@JsonProperty
	private List<OptionSelection> option;
	
	
	@JsonProperty
	private String booleanOne;
	
	@JsonProperty
	private String booleanTwo;
	
	public String getBooleanOne() {
		return booleanOne;
	}

	public void setBooleanOne(String booleanOne) {
		this.booleanOne = booleanOne;
	}

	public String getBooleanTwo() {
		return booleanTwo;
	}

	public void setBooleanTwo(String booleanTwo) {
		this.booleanTwo = booleanTwo;
	}

	public String getBooleanRadio() {
		return booleanRadio;
	}

	public void setBooleanRadio(String booleanRadio) {
		this.booleanRadio = booleanRadio;
	}

	@JsonProperty
	private String booleanRadio;
	
//	@JsonProperty
//	private List<MultipleOptionSelection> multiple;
//	
//	public List<MultipleOptionSelection> getMultipleOption() {
//		return multiple;
//	}
//
//	public void setMultipleOption(List<MultipleOptionSelection> multiple) {
//		this.multiple = multiple;
//	}

	public List<OptionSelection> getOption() {
		return option;
	}

	public void setOption(List<OptionSelection> option) {
		this.option = option;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getInstruction() {
		return instructions;
	}

	public void setInstruction(String instruction) {
		this.instructions = instruction;
	}

	public String isIs_scored() {
		return isScored;
	}

	public void setIs_scored(String is_scored) {
		this.isScored = is_scored;
	}

	public Set<String> getCategory() {
		return category;
	}

	public void setCategory(Set<String> category) {
		this.category = category;
	}

	public String getType() {
		return questionType;
	}

	public void setType(String type) {
		this.questionType = type;
	}

	@Override
	public String toString() {
		return "QuestionRequest [question=" + question + ", questionType=" + questionType + ", isScored=" + isScored
				+ ", instructions=" + instructions + ", category=" + category + ", option=" + option + ", booleanOne="
				+ booleanOne + ", booleanTwo=" + booleanTwo + ", booleanRadio=" + booleanRadio + "]";
	}


	

	

}
