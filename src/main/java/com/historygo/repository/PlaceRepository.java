package com.historygo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.historygo.model.Places;


public interface PlaceRepository extends JpaRepository<Places, Integer> {

    Places findByName(@Param("name") String name);

}
