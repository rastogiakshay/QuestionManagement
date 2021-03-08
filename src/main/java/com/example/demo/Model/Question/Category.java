package com.example.demo.Model.Question;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category_master")
public class Category implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public int category_id;

	@Enumerated(EnumType.STRING)
	private EQuestionCategory name;

	public Category() {
	}

	public Category(EQuestionCategory name) {

		this.name = name;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public EQuestionCategory getCategory_name() {
		return name;
	}

	public void setCategory_name(EQuestionCategory category_name) {
		this.name = category_name;
	}

}
