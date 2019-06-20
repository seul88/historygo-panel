package com.historygo.controller.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.historygo.model.Question;
import com.historygo.repository.QuestionRepository;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class QuestionViewController {

}
/*
    @Autowired
    QuestionRepository questionRepository;


    @PostMapping("/listQuestions")
    public String listPlaces(ModelMap model){
        ArrayList<Question> questions = new ArrayList<>();
        questions = (ArrayList<Question>) questionRepository.findAll();
        model.addAttribute("questions", questions);

        return "questionList";
    }






    @PostMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam("id") Integer id){
        Question question;
        question = questionRepository.findById(id);
        if (question != null) {
            questionRepository.delete(question);
        }
        return "redirect:/admin/menu";
    }


    @PostMapping("/addQuestionWithDetails")
    public String addQuestionWithDetails (@RequestParam("a") String a,
                                          @RequestParam("b") String b,
                                          @RequestParam("c") String c,
                                          @RequestParam("d") String d,
                                          @RequestParam("correctAnswer") String correctAnswer,
                                          @RequestParam("question") String question,
                                          Model model)
    {
        Question q = new Question();


        q.setA(a);
        q.setB(b);
        q.setC(c);
        q.setD(d);
        q.setQuestion(question);
        q.setCorrectAnswer(correctAnswer);

        questionRepository.save(q);

        return "redirect:/admin/menu";
    }




}
*/