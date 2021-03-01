package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Question.Category;
import com.example.demo.Model.Question.EQuestionCategory;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	Category findByName(EQuestionCategory category);

}
