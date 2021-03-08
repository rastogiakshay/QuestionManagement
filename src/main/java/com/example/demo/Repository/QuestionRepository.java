package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.User;
import com.example.demo.Model.Question.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	List<Question> findByUser(User user);
	Page<Question> findAllByUser(User user, Pageable pageable);
	
	
//	Map<Question, List<Options>> findByQuestion(Question user);
}
