package com.historygo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.historygo.model.Question;


public interface QuestionRepository extends JpaRepository<Question, Integer> {

    public Optional<Question> findById(Integer id);

}
