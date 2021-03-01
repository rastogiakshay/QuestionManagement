package com.example.demo.Model.Question;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.Model.User;

@Entity
@Table(name = "question_master")
public class Question implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int question_id;
	
	private	String question;
	
	private	String is_score;
	
	private	String instruction;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_type_id")		
	private Type questionType;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "question_category",
				joinColumns = @JoinColumn(name = "question_id"),
				inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> question_category;
	

	@OneToMany(mappedBy = "question")
	private Set<Options> options;
	
	public Set<Options> getOptions() {
		return options;
	}

	public void setOptions(Set<Options> option_name) {
		this.options = option_name;
	}

	public Set<Category> getQuestion_category() {
		return question_category;
	}

	public void setQuestion_category(Set<Category> question_category) {
		this.question_category = question_category;
	}

	public Question() {}

	public Question(String question, String is_scored, String instruction) {
		super();
		this.question = question;
		this.is_score = is_scored;
		this.instruction = instruction;
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

	public Type getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Type questionType) {
		this.questionType = questionType;
	}

	public Set<Category> getCategoryType() {
		return question_category;
	}

	public void setCategoryType(Set<Category> categoryType) {
		this.question_category = categoryType;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getIs_scored() {
		return is_score;
	}

	public void setIs_scored(String is_scored) {
		this.is_score = is_scored;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	
	
	

}
