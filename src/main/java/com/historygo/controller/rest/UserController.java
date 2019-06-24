package com.historygo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.historygo.model.Question;
import com.historygo.model.Role;
import com.historygo.model.User;
import com.historygo.repository.UserRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path="/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	
	
	@PostMapping()
	  public ResponseEntity<Object> addUser(@RequestBody User user){
		try {
			String dbPassword = user.getPassword();
			user.setPassword(this.passwordEncoder.encode(dbPassword));
			User newUser = this.userRepository.save(user);
		    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}").buildAndExpand(newUser.getId()).toUri();
			return new ResponseEntity<>(uri, HttpStatus.CREATED);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	
	
	@GetMapping(path="/all")
	public ResponseEntity<Object> getAllUsers() {
		try {
			return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}


	
	@GetMapping(path="/id/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable("id") int id){
		try {
			User result = this.userRepository.findById(id);
			if (result.equals(null)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
	}

	
	
	@DeleteMapping(path="/id/{id}")
	public ResponseEntity<Object> deleteUserById(@PathVariable("id") int id){
		try {
			User toDelete = this.userRepository.findById(id);
			toDelete.setPlaces(null);
			this.userRepository.delete(toDelete);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@PutMapping
	public ResponseEntity<Object> updateUserDetails(@RequestBody User user){
		try {
			this.userRepository.save(user);
    		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}").buildAndExpand(user.getId()).toUri();
			return new ResponseEntity<>(uri, HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
	}

	

	@GetMapping("/name/{name}")
	public ResponseEntity<Object> getUserByName(@PathVariable("name") String name){
		try {
			User result = this.userRepository.findByName(name);
			if (result.equals(null)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}


	
	@GetMapping("/name/{name}/points/{points}")
	public ResponseEntity<Object> addPointsForQuiz(@PathVariable("name") String name, @PathVariable("points") int points){
		try {
			User toPromote = userRepository.findByName(name);
			if (toPromote.equals(null)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			int userPoints = toPromote.getPoints();
			userPoints  += points;
			toPromote.setPoints(userPoints);
			this.userRepository.save(toPromote);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	
	
	@GetMapping("/points/{points}")
	public ResponseEntity<Object> getUserByPoints(@PathVariable("points") int points){
		try {
			return new ResponseEntity<>(this.userRepository.findByPointsGreaterThanEqual(points), HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}


	
	@GetMapping("/country/{country}")
	public ResponseEntity<Object> listByCountry(@PathVariable("country") String country){
		try {
			return new ResponseEntity<>(this.userRepository.findByCountry(country), HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}



	@PostMapping("/email")
	public ResponseEntity<Object> findUserByEmail(@RequestParam("email") String email) {
		try {
			User result = this.userRepository.findByEmail(email);
			if (result.equals(null)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}


	
	@GetMapping("/email/{email:.+}")
	public ResponseEntity<Object> getByEmail(@PathVariable("email") String email){
		try {
			User result = this.userRepository.findByEmail(email);
			if (result.equals(null)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	

	@GetMapping("/user/{user}/password/{password:.+}")
	public ResponseEntity<Object> loginByName(@PathVariable("user") String user, @PathVariable("password") String password ){
		try {
			String response;
			User temp = this.userRepository.findByName(user);
			if (temp.equals(null)) return new ResponseEntity<>("DENIED", HttpStatus.NOT_FOUND);
				
			String dbPassword = temp.getPassword();
	
			if (this.passwordEncoder.matches(password, dbPassword)) {
				response = "VERIFIED";
			} else {
				response = "DENIED";
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>("DENIED", HttpStatus.BAD_REQUEST);
		}
	}



	@GetMapping("/email/{email}/password/{password:.+}")
	public ResponseEntity<Object> loginByEmail(@PathVariable("email") String email, @PathVariable("password") String password ){
		try {
			User temp = this.userRepository.findByEmail(email);
			String response;
	
			String dbPassword = temp.getPassword();
	
			if (this.passwordEncoder.matches(password, dbPassword)) {
				response = "VERIFIED";
			} else {
				response = "DENIED";
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		catch(DataAccessException dataAccessException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}