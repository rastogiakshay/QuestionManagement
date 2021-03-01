package com.example.demo.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Question.Options;
import com.example.demo.Model.Question.Question;

@Repository
public interface OptionRepository extends JpaRepository<Options, Integer> {

	Set<Options> findByQuestion(Question question);
	
}
