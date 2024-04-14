package com.qm.Model.Question;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;

import com.qm.Model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "question_master")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int question_id;
	private String question;
	private String is_score;
	private String instruction;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_type_id")
	private Type questionType;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "question_category", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> question_category;

	@OneToMany(mappedBy = "question")
	private Set<Options> options;
	public Question(String question, String is_scored, String instruction) {
		super();
		this.question = question;
		this.is_score = is_scored;
		this.instruction = instruction;
	}
}
