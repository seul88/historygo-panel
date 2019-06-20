package com.historygo.controller.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.historygo.model.Places;
import com.historygo.model.User;
import com.historygo.repository.PlaceRepository;
import com.historygo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path="/visits")
public class Visits {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    @Qualifier("userRepository")
    UserRepository userRepository;

    @GetMapping(path = "/add")
    public @ResponseBody
    String addNewVisit(@RequestParam String userName, @RequestParam String placeName) {

        Places temp;
        temp = placeRepository.findByName(placeName);

        User tempUsr;
        tempUsr = userRepository.findByName(userName);

        boolean onList = false;
        String msg = "";

        Set<Places> places = tempUsr.getPlaces();
        for (Places place : places){
            if (place.getName().equals(placeName)){
                onList = true;
                msg = "ADDED";
            }
        }

        if (onList == false) {
            temp.addUser(tempUsr);

            int visits = temp.getVisits();
            visits++;
            temp.setVisits(visits);

            int pointsPlace = temp.getPoints();

            int pointsUser = tempUsr.getPoints();
            pointsUser += pointsPlace;
            tempUsr.setPoints(pointsUser);

            placeRepository.save(temp);
            userRepository.save(tempUsr);
            msg = "VISITED";
        }

        return msg;

    }





    @GetMapping(path = "/add/email")
    public @ResponseBody
    String ByEmail(@RequestParam String email, @RequestParam String placeName) {

        Places temp;
        temp = placeRepository.findByName(placeName);

        User tempUsr;
        tempUsr = userRepository.findByEmail(email);

        boolean onList = false;
        String msg = "";

        Set<Places> places = tempUsr.getPlaces();
        for (Places place : places){
            if (place.getName().equals(placeName)){
                onList = true;
                msg = "ADDED";
            }
        }

        if (onList == false) {
            temp.addUser(tempUsr);

            int visits = temp.getVisits();
            visits++;
            temp.setVisits(visits);

            int pointsPlace = temp.getPoints();

            int pointsUser = tempUsr.getPoints();
            pointsUser += pointsPlace;
            tempUsr.setPoints(pointsUser);

            placeRepository.save(temp);
            userRepository.save(tempUsr);
            msg = "VISITED";
        }

        return msg;

    }



/*
    @GetMapping(path="/places/user")
    public String showPlacesForUser(@RequestParam String userName){

        String places = this.userRepository.findByName(userName).getPlacesAsString();
        return places;

    }
*/
/*
    @GetMapping(path="/places/user")
    public List<Places> showPlacesForUserAsList(@RequestParam String userName){

        List<Places> places = (List<Places>) this.userRepository.findByName(userName).getPlaces();
        return places;

    }
*/

    @GetMapping(path="/places/user")
    public Set<Places> showPlacesForUser(@RequestParam String userName){

        Set<Places> places =  this.userRepository.findByName(userName).getPlaces();
        return places;

    }

    @GetMapping(path="/users/place")
    public List<User> showUsersForPlaces(@RequestParam String placeName) {
        List<User> users = this.userRepository.findAll();
        List<User> result = new ArrayList<>();

        for (User temp : users){
            for (Places tempPlace : temp.getPlaces()){
                if (tempPlace.getName().equals(placeName))
                    result.add(temp);
            }
        }

        return result;

    }

    /*
    @GetMapping(path="/users/place")
    public List<Users> showUsersForPlacesAsList(@RequestParam String placeName) {
        List<Users> users = this.placeRepository.findByName(placeName).getUsersAsList();
        return users;
    }
    */
}
