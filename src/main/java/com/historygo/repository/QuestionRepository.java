package com.historygo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.historygo.model.Question;

@Repository("questionRepository")
public interface QuestionRepository extends JpaRepository<Question, Integer> {

  //  public Question findById(Integer id);

}
