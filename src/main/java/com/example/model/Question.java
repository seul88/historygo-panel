package com.example.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Objects;



@Entity
@Table(name="question")
public class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotEmpty(message = "*Please fill Question A field")
    private String questionA;
    @NotEmpty(message = "*Please fill Question B field")
    private String questionB;
    @NotEmpty(message = "*Please fill Question C field")
    private String questionC;
    @NotEmpty(message = "*Please fill Question D field")
    private String questionD;
    @NotEmpty(message = "*Please fill Correct Answer field")
    private String correctAnswer;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question= (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionA() {
        return questionA;
    }

    public void setQuestionA(String questionA) {
        this.questionA = questionA;
    }

    public String getQuestionB() {
        return questionB;
    }

    public void setQuestionB(String questionB) {
        this.questionB = questionB;
    }

    public String getQuestionC() {
        return questionC;
    }

    public void setQuestionC(String questionC) {
        this.questionC = questionC;
    }

    public String getQuestionD() {
        return questionD;
    }

    public void setQuestionD(String questionD) {
        this.questionD = questionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
