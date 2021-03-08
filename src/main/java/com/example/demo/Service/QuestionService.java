package com.example.demo.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.User;
import com.example.demo.Model.Question.Category;
import com.example.demo.Model.Question.EQuestionCategory;
import com.example.demo.Model.Question.EQuestionType;
import com.example.demo.Model.Question.Options;
import com.example.demo.Model.Question.Question;
import com.example.demo.Model.Question.Type;
import com.example.demo.POJO.OptionSelection;
import com.example.demo.Payload.QuestionRequest;
import com.example.demo.Payload.QuestionResponse;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.OptionRepository;
import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.TypeRepository;
import com.example.demo.Repository.UserRepository;

import Utility.GlobalConstants;

@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepo;
	@Autowired
	OptionRepository optRepo;
	@Autowired
	UserRepository userRepo;
	Iterator<Question> questionIterator;

	@Autowired
	CategoryRepository categoryRepo;

	@Autowired
	QuestionService questService;

	private Category questCategory;

	@Autowired
	private TypeRepository typeRepo;

	List<Options> tempOptions;
	Question temp;
	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * getting details by questions.
	 * 
	 * @param question
	 * @param user
	 * @return
	 */
	public Map<Question, List<Options>> findByQuestion(Question question, User user) {
		Map<Question, List<Options>> tempMap = new HashMap<>();
		temp = question;
		tempOptions.addAll(optRepo.findByQuestion(question));
		tempMap.put(question, tempOptions);
		return tempMap;
	}

	/**
	 * Mapping the questions.
	 * 
	 * @param question
	 * @return
	 */
	Map<Integer, User> getQuestionCreatorMap(List<Question> question) {
		List<User> creators = question.stream().map(Question::getUser).distinct().collect(Collectors.toList());
		Iterator<User> userIterate = creators.listIterator();
		List<Integer> creatorIds = new ArrayList<>();
		while (userIterate.hasNext()) {
			creatorIds.add(userIterate.next().getUserId());
		}
		Map<Integer, User> creatorMap = creators.stream().collect(Collectors.toMap(User::getUserId, e -> e));
		return creatorMap;
	}

	/**
	 * Saving the question to the database.
	 * 
	 * @param questionRequest
	 * @return
	 */
	public ResponseEntity<Object> save(QuestionRequest questionRequest) {
		System.out.println(questionRequest.toString());
		if (questionRequest.getQuestion() == null || questionRequest.getIsScored().isBlank()) {
			return ResponseEntity.badRequest().body(GlobalConstants.MANDATORY_FIELDS);
		}
		Question question = new Question(questionRequest.getQuestion(), questionRequest.isIs_scored(),
				questionRequest.getInstruction());
		String strType = questionRequest.getType();
		Set<String> strCategory = questionRequest.getCategory();
		SecurityContext a = SecurityContextHolder.getContext();
		Authentication auth = a.getAuthentication();
		User userQuestion = userRepo.getUserByEmail(auth.getName());
		question.setUser(userQuestion);
		Options tempOpt;
		Set<Category> categorySet = new HashSet<>();

		if (!strCategory.isEmpty()) {
			strCategory.forEach(category -> {
				switch (category) {
				case "CIVIL_SERVICES":
					questCategory = categoryRepo.findByName(EQuestionCategory.CIVIL_SERVICES);
					categorySet.add(questCategory);
					break;
				case "MANAGEMENT":
					questCategory = categoryRepo.findByName(EQuestionCategory.MANAGEMENT);
					categorySet.add(questCategory);
					break;
				case "ENGINEERING":
					questCategory = categoryRepo.findByName(EQuestionCategory.ENGINEERING);
					categorySet.add(questCategory);
					break;
				case "COMPUTER_SCIENCE":
					questCategory = categoryRepo.findByName(EQuestionCategory.COMPUTER_SCIENCE);
					categorySet.add(questCategory);
					break;
				default:
				}
			});
		} else {
			return ResponseEntity.badRequest().body(GlobalConstants.CATEGORY_NULL);
		}
		question.setQuestion_category(categorySet);

		if (strType != null) {
			int i = 0;
			switch (strType) {
			case "SINGLE_SELECTION":
				Type singleSelection = typeRepo.findByName(EQuestionType.SINGLE_SELECTION);
				if (singleSelection != null) {
					question.setQuestionType(singleSelection);
				}
				Set<Options> optionList = new HashSet<>();
				List<OptionSelection> tempOptionList = questionRequest.getOption();
				for (OptionSelection op : tempOptionList) {
					if (!op.getSingleOption().isBlank()) {
						tempOpt = new Options();
						tempOpt.setOption_name(op.getSingleOption());
						if (op.getSingleRadio().contains("true")) {
							i++;
						}
						tempOpt.setAnswer(op.getSingleRadio());
						tempOpt.setQuestion(question);
						LOG.log(Level.INFO,
								"option is " + tempOpt.getOption_name() + " and answer is " + tempOpt.getAnswer());
						optionList.add(tempOpt);
					} else {
						if (!(optionList.size() > 1 && i < 2)) {
							return ResponseEntity.badRequest().body(GlobalConstants.VALID_OPTIONS);
						}
					}
				}
				optionList.addAll(optRepo.saveAll(optionList));
				question.setOptions(optionList);
				questionRepo.save(question);
				break;

			case "MULTIPLE_SELECTION":
				Type multipleSelection = typeRepo.findByName(EQuestionType.MULTIPLE_SELECTION);
				if (multipleSelection != null) {
					question.setQuestionType(multipleSelection);
				}
				List<OptionSelection> tempMultipleOptionList = questionRequest.getOption();
				Set<Options> multileOptionList = new HashSet<>();
				for (OptionSelection op : tempMultipleOptionList) {
					if (!op.getSingleOption().isBlank()) {
						tempOpt = new Options();
						tempOpt.setOption_name(op.getSingleOption());
						if (!op.getSingleRadio().isBlank()) {
							i++;
						}
						tempOpt.setAnswer(op.getSingleRadio());
						tempOpt.setQuestion(question);
						multileOptionList.add(tempOpt);
					} else {
						if (!(multileOptionList.size() > 1 && i > 0)) {
							return ResponseEntity.badRequest().body(GlobalConstants.MANDATORY_FIELDS);
						}
					}
				}
				multileOptionList.addAll(optRepo.saveAll(multileOptionList));
				question.setOptions(multileOptionList);
				questionRepo.save(question);
				break;

			case "TRUE_FALSE":
				Type booleanSelection = typeRepo.findByName(EQuestionType.TRUE_FALSE);
				if (booleanSelection != null) {
					question.setQuestionType(booleanSelection);
				}
				Set<Options> booleanOptionList = new HashSet<>();
				tempOpt = new Options();
				tempOpt.setOption_name(questionRequest.getBooleanOne());
				tempOpt.setQuestion(question);
				if (questionRequest.getBooleanRadio().contains("booleanOne")) {
					tempOpt.setAnswer("true");
				}
				booleanOptionList.add(tempOpt);
				Options tempOpt2 = new Options();
				tempOpt2.setOption_name(questionRequest.getBooleanTwo());
				tempOpt2.setQuestion(question);
				if (questionRequest.getBooleanRadio().contains("booleanTwo")) {
					tempOpt2.setAnswer("true");
				}
				booleanOptionList.add(tempOpt2);
				if (questionRequest.getBooleanOne().isBlank() || questionRequest.getBooleanTwo().isBlank()
						|| questionRequest.getBooleanRadio().isBlank()) {
					return ResponseEntity.badRequest().body(GlobalConstants.VALID_OPTIONS);
				} else {
					booleanOptionList.addAll(optRepo.saveAll(booleanOptionList));
					question.setOptions(booleanOptionList);
					questionRepo.save(question);
				}
				break;
			default:
			}
		} else {
			LOG.log(Level.INFO, "Question type is NULL");
		}
		Map<String, String> resp = new HashMap<>();
		resp.put("message", "Question has been saved");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp);
	}

	/**
	 * Fetching saved categories
	 * 
	 * @return
	 */
	public List<Category> getCategorys() {
		return categoryRepo.findAll();
	}

	/**
	 * Fetching saved question types.
	 * 
	 * @return
	 */
	public List<Type> getTypes() {
		return typeRepo.findAll();
	}

	/**
	 * Getting question in List.
	 * 
	 * @return
	 */
	public List<QuestionResponse> getQuestions() {
		Set<Options> optList;
		Iterator<Question> iterator;
		Question tempQuestion;
		List<QuestionResponse> tempResponse = new ArrayList<>();
		QuestionResponse response;
		SecurityContext a = SecurityContextHolder.getContext();
		Authentication auth = a.getAuthentication();
		User currentUser = userRepo.getUserByEmail(auth.getName());
		List<Question> questionList = questionRepo.findByUser(currentUser);
		iterator = questionList.iterator();

		while (iterator.hasNext()) {
			tempQuestion = iterator.next();
			optList = tempQuestion.getOptions();
			response = new QuestionResponse(tempQuestion.getQuestion_id(), currentUser.getUserId(),
					tempQuestion.getQuestion(), tempQuestion.getInstruction(), tempQuestion.getQuestionType(),
					tempQuestion.getQuestion_category(), optList, tempQuestion.getIs_score());
			tempResponse.add(response);
		}
		return tempResponse;
	}

	/**
	 * Getting questions in paged form.
	 * 
	 * @param pageDetails
	 * @return
	 */
	public Page<QuestionResponse> getAllQuestions(int pageDetails) {
		int pageSize = 10;
		int pageNumber = (pageDetails < 1) ? 1 : pageDetails - 1;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		SecurityContext a = SecurityContextHolder.getContext();
		Authentication auth = a.getAuthentication();
		User currentUser = userRepo.getUserByEmail(auth.getName());
		Page<QuestionResponse> questResponse = questionRepo.findAllByUser(currentUser, pageable).map(tempQuestion -> {
			return new QuestionResponse(tempQuestion.getQuestion_id(), tempQuestion.getUser().getUserId(),
					tempQuestion.getQuestion(), tempQuestion.getInstruction(), tempQuestion.getQuestionType(),
					tempQuestion.getQuestion_category(), tempQuestion.getOptions(), tempQuestion.getIs_score());
		});
		return questResponse;
	}

	/**
	 * Getting question by Id.
	 * 
	 * @param id
	 * @return
	 */
	public Question getQuestionById(int id) {
		Optional<Question> optionalQuestion = questionRepo.findById(id);
		Question question = optionalQuestion.get();
		return question;
	}

	/**
	 * Deleting question by Id.
	 * 
	 * @param id
	 */
	public void deleteQuestion(int id) {
		questionRepo.deleteById(id);
	}

	/**
	 * Updating the question by the given Id.
	 * 
	 * @param id
	 * @param questionRequest
	 * @return
	 */
	public ResponseEntity<?> updateQuestions(int id, QuestionRequest questionRequest) {
		Optional<Question> optionalQuestion = questionRepo.findById(id);
		Question question = optionalQuestion.get();
		Options tempOpt;
		question.setQuestion(questionRequest.getQuestion());
		question.setInstruction(questionRequest.getInstruction());
		question.setIs_score(questionRequest.getIsScored());
		Set<String> strCategory = questionRequest.getCategory();
		Set<Category> categorySet = new HashSet<>();

		if (strCategory != null) {
			strCategory.forEach(category -> {
				switch (category) {
				case "CIVIL_SERVICES":
					questCategory = categoryRepo.findByName(EQuestionCategory.CIVIL_SERVICES);
					categorySet.add(questCategory);
					break;
				case "MANAGEMENT":
					questCategory = categoryRepo.findByName(EQuestionCategory.MANAGEMENT);
					categorySet.add(questCategory);
					break;
				case "ENGINEERING":
					questCategory = categoryRepo.findByName(EQuestionCategory.ENGINEERING);
					categorySet.add(questCategory);
					break;
				case "COMPUTER_SCIENCE":
					questCategory = categoryRepo.findByName(EQuestionCategory.COMPUTER_SCIENCE);
					categorySet.add(questCategory);
					break;
				default:
				}
			});
		} else {
			ResponseEntity.badRequest().body(GlobalConstants.CATEGORY_NULL);
		}
		question.setQuestion_category(categorySet);
		String strType = questionRequest.getType();
		Iterator<Options> optionIterator;
		Options currentOption;

		if (strType != null) {
			switch (strType) {
			case "SINGLE_SELECTION":
				Type singleSelection = typeRepo.findByName(EQuestionType.SINGLE_SELECTION);
				if (singleSelection != null) {
					question.setQuestionType(singleSelection);
				}
				Set<Options> optionSet = question.getOptions();
				List<OptionSelection> tempOptionList = questionRequest.getOption();
				optionIterator = optionSet.iterator();
				for (OptionSelection op : tempOptionList) {
					if (!op.getSingleOption().isBlank()) {
						if (optionIterator.hasNext()) {
							currentOption = optRepo.findById(optionIterator.next().option_id).get();
							currentOption.setOption_name(op.getSingleOption());
							currentOption.setAnswer(op.getSingleRadio());
							optionSet.add(currentOption);
						} else {
							tempOpt = new Options();
							tempOpt.setOption_name(op.getSingleOption());
							tempOpt.setAnswer(op.getSingleRadio());
							tempOpt.setQuestion(question);
							optionSet.add(tempOpt);
						}
					}
				}
				if (!optionSet.isEmpty()) {
					optRepo.saveAll(optionSet);
					question.setOptions(optionSet);
					questionRepo.save(question);
				}
				break;
			case "MULTIPLE_SELECTION":
				Type multipleSelection = typeRepo.findByName(EQuestionType.MULTIPLE_SELECTION);
				if (multipleSelection != null) {
					question.setQuestionType(multipleSelection);
				}
				List<OptionSelection> tempMultipleOptionList = questionRequest.getOption();
				Set<Options> multileOptionList = question.getOptions();
				optionIterator = multileOptionList.iterator();
				for (OptionSelection op : tempMultipleOptionList) {
					if (!op.getSingleOption().isBlank()) {
						if (optionIterator.hasNext()) {
							currentOption = optRepo.findById(optionIterator.next().option_id).get();
							currentOption.setOption_name(op.getSingleOption());
							currentOption.setAnswer(op.getSingleRadio());
							multileOptionList.add(currentOption);
						} else {
							tempOpt = new Options();
							tempOpt.setOption_name(op.getSingleOption());
							tempOpt.setAnswer(op.getSingleRadio());
							tempOpt.setQuestion(question);
							multileOptionList.add(tempOpt);
						}
					}
				}
				multileOptionList.addAll(optRepo.saveAll(multileOptionList));
				question.setOptions(multileOptionList);
				questionRepo.save(question);
				break;
			case "TRUE_FALSE":
				Type booleanSelection = typeRepo.findByName(EQuestionType.TRUE_FALSE);
				if (booleanSelection != null) {
					question.setQuestionType(booleanSelection);
				}
				Set<Options> booleanOptionSet = question.getOptions();
				optionIterator = booleanOptionSet.iterator();
				Options optArray[] = new Options[2];
				int i = 0;
				while (optionIterator.hasNext()) {
					optArray[i] = optRepo.findById(optionIterator.next().option_id).get();
					i++;
				}
				if (!questionRequest.getBooleanOne().isBlank()) {
					optArray[0].setOption_name(questionRequest.getBooleanOne());
				} else {
					return ResponseEntity.badRequest().body("Option one Cannot be empty");
				}
				if (questionRequest.getBooleanRadio().contains("booleanOne")) {
					optArray[0].setAnswer("true");
				}
				optArray[0].setQuestion(question);
				booleanOptionSet.add(optArray[0]);
				if (!questionRequest.getBooleanTwo().isBlank()) {
					optArray[1].setOption_name(questionRequest.getBooleanTwo());
				} else {
					return ResponseEntity.badRequest().body("Option one Cannot be empty");
				}
				if (questionRequest.getBooleanRadio().contains("booleanTwo")) {
					optArray[1].setAnswer("true");
				}
				booleanOptionSet.add(optArray[1]);
				if (booleanOptionSet.size() < 2 || questionRequest.getBooleanRadio().isBlank()) {
					return ResponseEntity.badRequest().body(GlobalConstants.VALID_OPTIONS);
				} else {
					optRepo.saveAll(booleanOptionSet);
					question.setOptions(booleanOptionSet);
					questionRepo.save(question);
				}
				break;
			default:
			}
		} else {
			LOG.log(Level.INFO, "Question type is NULL");
		}
		questionRepo.save(question);
		Map<String, String> resp = new HashMap<>();
		resp.put("message", "Question has been Updated");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp);
	}
}
