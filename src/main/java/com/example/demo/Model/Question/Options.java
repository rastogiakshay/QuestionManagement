package com.example.demo.Model.Question;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "option_table")
public class Options {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int option_id;
	
	public Options() {}
	
	public Options(String option, String answer) {
		
		this.option_name = option;
		this.answer = answer;
	}
	
	
	public String option_name;
	

	public String answer;

	@ManyToOne(fetch = FetchType.LAZY)
	public Question question;

	@JsonIgnore
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Options(String option) {
		this.option_name = option;
	}

	public int getOption_id() {
		return option_id;
	}

	public void setOption_id(int option_id) {
		this.option_id = option_id;
	}

	public String getOption() {
		return option_name;
	}

	public void setOption(String option) {
		this.option_name = option;
	}
		
	

}
