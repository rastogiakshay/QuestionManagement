package com.qm.Model.Question;

import java.io.Serializable;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "option_table")
public class Options implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int option_id;

	public Options() {
	}

	public Options(String option, String answer) {

		this.option_name = option;
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Options [option_id=" + option_id + ", option_name=" + option_name + ", answer=" + answer + ", question="
				+ question + "]";
	}

	public String option_name;

	public String answer;

	@ManyToOne(fetch = FetchType.LAZY)
	public Question question;

	@JsonIgnore
	public Question getQuestion() {
		return question;
	}

}
