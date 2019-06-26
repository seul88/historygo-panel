package com.historygo.controller.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.historygo.model.Places;
import com.historygo.model.Question;
import com.historygo.repository.QuestionRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(path="/questions")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionController {


    @Autowired
    private QuestionRepository questionRepository;


    @PostMapping()
    public ResponseEntity<Object> addQuestion(@RequestBody Question question) {
    	try {
    		 Question q = this.questionRepository.save(question);
             URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}").buildAndExpand(q.getId()).toUri();
             return new ResponseEntity<>(uri, HttpStatus.CREATED);
    	}
    	catch(DataAccessException dataAccessException) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
    


    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getQuestionById(@PathVariable("id") Integer id){

    	Optional<Question> questionToFind;
    	try {
    	 questionToFind = this.questionRepository.findById(id);
    		if (questionToFind.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	catch(DataAccessException dataAccessException) {	
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<Object>(questionToFind, HttpStatus.OK);
    }

    
    
    @DeleteMapping(path="/id/{id}")
    public ResponseEntity<Object> deleteQuestionById(@PathVariable("id") int id) {
    
    	 try {
    		 Question questioToRemove = questionRepository.getOne(id);
    		 questionRepository.delete(questioToRemove);
			 return new ResponseEntity<>(HttpStatus.OK);
    	 }
    	 catch(DataAccessException dataAccessException) {
    		 System.err.println("Data access layer error occured during deleting of question.");
             return new ResponseEntity<>("Data access layer error occured during deleting of question.", HttpStatus.BAD_REQUEST);
    	 }
    }
    
    
    
    @PutMapping()
    public ResponseEntity<Object> updateQuestion(@RequestBody Question question){
    	
    	try {
    		//Question questionToUpdate = this.questionRepository.getOne(question.getId());
    		//question.setId(questionToUpdate.getId());
    		this.questionRepository.save(question);
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
	 catch(DataAccessException dataAccessException) {
		 System.err.println("Data access layer error occured during updating of question.");
        return new ResponseEntity<>("Data access layer error occured during updating of question.", HttpStatus.BAD_REQUEST);
	 }
    }
    
    
        
    @GetMapping("/random")
    public ResponseEntity<Object> getFiveRandomQuestions(){

    	try {
	        ArrayList <Optional<Question>> questionsResponse = new ArrayList<>();
	        ArrayList<Question> allQuestions = (ArrayList<Question>) questionRepository.findAll();
	        ArrayList<Integer> ids = new ArrayList<Integer>();
	        for (Question question : allQuestions){
	            ids.add(question.getId());
	        }
	
	        Random rand = new Random();
	        for (int i = 0; i < 5; i++) {
	            Integer randomElement = ids.get(rand.nextInt(ids.size()));
	            Optional<Question> temp = questionRepository.findById(randomElement);
	            if (temp.isPresent()) {
		            questionsResponse.add(temp);
		            ids.remove(randomElement);
	            }
	        }
	        return new ResponseEntity<>(questionsResponse, HttpStatus.OK);
    	}
    	catch (Exception ex) {
    		 return new ResponseEntity<>("Error occured when generating questions!", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
 
    }

    
    
    @GetMapping(path = "/all")
    public ResponseEntity<Object> getAllQuestions() {
         return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
    }

}