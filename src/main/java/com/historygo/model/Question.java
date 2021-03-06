package com.historygo.model;


import javax.persistence.*;
import java.util.Objects;



@Entity
@Table(name="question")
public class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique=true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String a;

    @Column(nullable = false)
    private String b;

    @Column(nullable = false)
    private String c;

    @Column(nullable = false)
    private String d;

    @Column(nullable = false)
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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }
}
