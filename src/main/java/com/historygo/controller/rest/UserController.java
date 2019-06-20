package com.historygo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.historygo.model.Role;
import com.historygo.model.User;
import com.historygo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path="/users")
public class UserController {

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	/*
	@GetMapping(path="/add")
	public @ResponseBody
    String addNewUser (@RequestParam String name, @RequestParam int points) {

		User n = new User();
		n.setName(name);
		n.setPoints(points);

		userRepository.save(n);
		return "Saved";
	}
*/

	@PostMapping("/add")
	public void addUser(@RequestBody User user){

		User temp = new User();
		
		temp.setName(user.getName());
		temp.setEmail(user.getEmail());
		temp.setCountry(user.getCountry());
		// do zmiany :)
		temp.setPassword(user.getPassword());

		System.out.println(user.getCountry());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println(user.getId());
		System.out.println(user.getName());
		this.userRepository.save(temp);

	}

	
	@GetMapping(path="/all")
	public @ResponseBody
    Iterable<User> getAllUsers() {


		ArrayList<User> users = new ArrayList<>();
		users = (ArrayList<User>) userRepository.findAll();
		/*
		ArrayList<User> players = new ArrayList<>();

		for (User user : users){
			for (Role role : user.getRoles()){
				if (role.getRole().equals("ADMIN")){
				}
				if (role.getRole().equals("USER")) {
					players.add(user);
				}
			}
		}
		
		return players;
		*/
		return users;
	}


	@GetMapping("/id/{id}")
	public User getById(@PathVariable("id") int id){
		User user = this.userRepository.findById(id);

		return user;
	}




	@GetMapping("/name/{name}")
	public User getByName(@PathVariable("name") String name){
		User user = this.userRepository.findByName(name);

		return user;

	}


	@GetMapping("/name/{name}/points/{points}")
	public void addPointsForQuiz(@PathVariable("name") String name, @PathVariable("points") int points){
		User temp = userRepository.findByName(name);
		int userPoints = temp.getPoints();
		userPoints  += points;
		temp.setPoints(userPoints);
		this.userRepository.save(temp);
	}

	@GetMapping("/points/{points}")
	public List<User> getByPoints(@PathVariable("points") int points){

		List<User> users = this.userRepository.findByPointsGreaterThanEqual(points);

		return users;
	}


	@GetMapping("/country/{country}")
	public List<User> listByCountry(@PathVariable("country") String country){

		List<User> users = this.userRepository.findByCountry(country);

		return users;

	}



	@PostMapping("/email")
	public User findByEmail(@RequestParam("email") String email) {
		User user = this.userRepository.findByEmail(email);

		return user;
	}


	@GetMapping("/email/{email:.+}")
	public User getByEmail(@PathVariable("email") String email){

		User user = this.userRepository.findByEmail(email);

		return user;

	}


	@PutMapping("/name/{name}")
	public void setUserName(@PathVariable("name") String name, @RequestBody String nick){
		User user = this.userRepository.findByName(name);
		user.setName(nick);
		this.userRepository.save(user);
	}

	@PutMapping("/id/{id}")
	public void setUserNameById(@PathVariable("id") int id, @RequestBody String name){
		User user = this.userRepository.findById(id);
		user.setName(name);
		this.userRepository.save(user);
	}


	@GetMapping("/user/{user}/password/{password:.+}")
	public String loginByName(@PathVariable("user") String user, @PathVariable("password") String password ){
		User temp = this.userRepository.findByName(user);
		String response;

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String dbPassword = temp.getPassword();

		if (passwordEncoder.matches(password, dbPassword)) {
			response = "VERIFIED";
		} else {
			response = "DENIED";
		}

		return response;
	}



	@GetMapping("/email/{email}/password/{password:.+}")
	public String loginByEmail(@PathVariable("email") String email, @PathVariable("password") String password ){
		User temp = this.userRepository.findByEmail(email);
		String response;

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String dbPassword = temp.getPassword();

		if (passwordEncoder.matches(password, dbPassword)) {
			response = "VERIFIED";
		} else {
			response = "DENIED";
		}

		return response;
	}

}
