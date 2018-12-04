package com.example.repository;

import com.example.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("questionRepository")
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    public Question findById(Integer id);

}
