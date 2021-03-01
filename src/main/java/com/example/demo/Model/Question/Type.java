package com.example.demo.Model.Question;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="type_master")
public class Type {
	
	@Id
	public int question_type_id;
	
	@Enumerated(EnumType.STRING)
	private EQuestionType name;

	public Type() {}
	
	public Type(EQuestionType name) {
		
		this.name = name;
	}

	public int getType_id() {
		return question_type_id;
	}

	public void setType_id(int type_id) {
		this.question_type_id = type_id;
	}

	public EQuestionType getQuestion_type() {
		return name;
	}

	public void setQuestion_type(EQuestionType question_type) {
		this.name = question_type;
	}

	


}
