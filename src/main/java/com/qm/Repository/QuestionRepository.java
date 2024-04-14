package com.qm.Repository;

import java.util.List;

import com.qm.Model.Question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qm.Model.User;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	List<Question> findByUser(User user);
	Page<Question> findAllByUser(User user, Pageable pageable);
	
	
//	Map<Question, List<Options>> findByQuestion(Question user);
}
