package com.example.controller.rest;


import com.example.model.Question;
import com.example.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Question> getAllQuestions() {

        return questionRepository.findAll();
    }

}
