package com.qm.Repository;

import com.qm.Model.Question.Category;
import com.qm.Model.Question.EQuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	Category findByName(EQuestionCategory category);

}
