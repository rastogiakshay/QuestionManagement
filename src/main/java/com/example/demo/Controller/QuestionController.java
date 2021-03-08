package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.User;
import com.example.demo.Model.Question.Category;
import com.example.demo.Model.Question.EQuestionCategory;
import com.example.demo.Model.Question.EQuestionType;
import com.example.demo.Model.Question.Options;
import com.example.demo.Model.Question.Question;
import com.example.demo.Model.Question.Type;
import com.example.demo.POJO.OptionSelection;
import com.example.demo.POJO.PageDetails;
import com.example.demo.Payload.QuestionRequest;
import com.example.demo.Payload.QuestionResponse;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.OptionRepository;
import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.TypeRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.QuestionService;
import com.example.demo.jwt.JwtUtility;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/question")
public class QuestionController {

	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	UserRepository userRepo;

	@Autowired
	JwtUtility jwtUtils;

	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	CategoryRepository categoryRepo;

	@Autowired
	OptionRepository optRepo;

	@Autowired
	QuestionService questionService;

	private Category questCategory;

	@Autowired
	private TypeRepository typeRepo;

	/**
	 * getting questions in List form.
	 * 
	 * @return
	 */
	@GetMapping("/show")
	public List<QuestionResponse> getQuestion() {
		return questionService.getQuestions();
	}

	/**
	 * getting questions in pages.
	 * 
	 * @param pageDetails
	 * @return
	 */
	@GetMapping("/showall/{page}")
	public ResponseEntity getPagedQuestions(@PathVariable("page") int pageDetails) {
		return ResponseEntity.ok().body(questionService.getAllQuestions(pageDetails));
	}

	/**
	 * this method is used in fetching question by id.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Question getQuestionById(@PathVariable("id") int id) {
		return questionService.getQuestionById(id);
	}

	@GetMapping("/all")
	public String testingPage() {
		return "This is the testing page";
	}

	/**
	 * Deleting questions by Id.
	 * 
	 * @param id
	 */
	@DeleteMapping(path = "/{id}")
	public void deleteQuestion(@PathVariable("id") int id) {
		questionService.deleteQuestion(id);

	}

	/**
	 * This method is use for updating question by id.
	 * 
	 * @param id
	 * @param questionRequest
	 * @return
	 */
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> updateQuestion(@PathVariable("id") int id, @RequestBody QuestionRequest questionRequest) {
		return questionService.updateQuestions(id, questionRequest);
	}

	/**
	 * This method is use for saving questions.
	 * 
	 * @param questionRequest
	 * @return
	 */
	@PostMapping("/save-quest")
	@Transactional
	public ResponseEntity<Object> saveQuestion(@RequestBody QuestionRequest questionRequest) {
		return questionService.save(questionRequest);
	}

	/**
	 * This method is used for fetching categories of questions.
	 * 
	 * @return
	 */
	@GetMapping("/category")
	public List<Category> getCategory() {
		return questionService.getCategorys();
	}

	/**
	 * This method is used for fetching types of questions.
	 * 
	 * @return
	 */
	@GetMapping("/type")
	public List<Type> getType() {
		return questionService.getTypes();
	}
}
