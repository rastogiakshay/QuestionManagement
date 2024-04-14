package com.qm.Payload;

import java.util.Set;

import com.qm.Model.Question.Category;
import com.qm.Model.Question.Options;
import com.qm.Model.Question.Type;

public class QuestionResponse {

private int question_id;

private int user_id;

private String question;

private String is_scored;

private Type questionType;

private Set<Category> category;

private String instructions;

private Set<Options> options;

public Set<Options> getOptions() {
	return options;
}

public QuestionResponse(int questionId,int user_id,String question, String instructions, Type questionType,
		Set<Category> category, Set<Options> options, String isScored) {
	super();
	this.question_id = questionId;
	this.user_id = user_id;
	this.question = question;
	this.instructions = instructions;
	this.questionType = questionType;
	this.category = category;
	this.options = options;
	this.is_scored = isScored;
}

public int getQuestion_id() {
	return question_id;
}

public void setQuestion_id(int question_id) {
	this.question_id = question_id;
}

public String getQuestion() {
	return question;
}

public void setQuestion(String question) {
	this.question = question;
}

public int getId() {
	return user_id;
}

public void setId(int id) {
	this.user_id = id;
}

public String getInstructions() {
	return instructions;
}

public void setInstructions(String instructions) {
	this.instructions = instructions;
}

public Type getQuestionType() {
	return questionType;
}

public void setQuestionType(Type questionType) {
	this.questionType = questionType;
}

public String getIs_scored() {
	return is_scored;
}

public void setIs_scored(String is_scored) {
	this.is_scored = is_scored;
}

public void setOptions(Set<Options> options) {
	this.options = options;
}

public void setCategoryType(Set<Category> category) {
	this.category = category;
}

public Set<Category> getCategory() {
	return category;
}

public void setCategory(Set<Category> category) {
	this.category = category;
}



	
}
