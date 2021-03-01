package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Question.EQuestionType;
import com.example.demo.Model.Question.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

	
	Type findByName(EQuestionType type);
}
