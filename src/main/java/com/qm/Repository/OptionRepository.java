package com.qm.Repository;

import java.util.Set;

import com.qm.Model.Question.Options;
import com.qm.Model.Question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Options, Integer> {

	Set<Options> findByQuestion(Question question);
	
}
