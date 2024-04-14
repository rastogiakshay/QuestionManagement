package com.qm.Repository;

import com.qm.Model.Question.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qm.Model.Question.EQuestionType;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

	
	Type findByName(EQuestionType type);
}
