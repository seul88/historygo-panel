package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.User;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
	 User findById(int id);
	 User findByName(String name);
	List<User> findByPointsGreaterThanEqual(@Param("points") int points);
	List<User> findByCountry(String country);
}
