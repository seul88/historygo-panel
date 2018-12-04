package com.example.controller.rest;


import com.example.model.Question;
import com.example.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Random;

@RestController
@RequestMapping(path="/questions")
public class QuestionController {


    @Autowired
    @Qualifier("questionRepository")
    private QuestionRepository questionRepository;


    @PostMapping("/add")
    public String addQuestion(@RequestBody Question question) {

        Question temp = new Question();

        temp.setCorrectAnswer(question.getCorrectAnswer());
        temp.setA(question.getA());
        temp.setB(question.getB());
        temp.setC(question.getC());
        temp.setD(question.getD());
        temp.setQuestion(question.getQuestion());

        this.questionRepository.save(temp);

        return "Question saved.";
    }


    @GetMapping("/id/{id}")
    public @ResponseBody Question getQuestionById(@PathVariable("id") Integer id){

        return questionRepository.findById(id);

    }

    @GetMapping("/random")
    public @ResponseBody ArrayList<Question> getFiveRandomQuestions(){

        ArrayList<Question> questionsResponse = new ArrayList<>();

        ArrayList<Question> allQuestions = (ArrayList<Question>) questionRepository.findAll();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Question question : allQuestions){
            ids.add(question.getId());
        }

        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            Integer randomElement = ids.get(rand.nextInt(ids.size()));
            questionsResponse.add(questionRepository.findById(randomElement));
            ids.remove(randomElement);
        }


        return questionsResponse;
    }



    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Question> getAllQuestions() {

        return questionRepository.findAll();
    }

}
