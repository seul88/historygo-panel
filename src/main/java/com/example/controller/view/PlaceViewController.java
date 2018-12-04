package com.example.controller.view;


import com.example.model.Places;
import com.example.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class PlaceViewController {



    @Autowired
    PlaceRepository placeRepository;




    @RequestMapping("/placeDetails")
    public String showPlaceDetails(@RequestParam("name") String name, Model model){

        Places place;

        try {
            place = this.placeRepository.findByName(name);

            model.addAttribute("name", place.getName());
            model.addAttribute("id", place.getId());
            model.addAttribute("description", place.getDescription());
            model.addAttribute("points", place.getPoints());
            model.addAttribute("rating", place.getRating());
            model.addAttribute("year", place.getYear());
            model.addAttribute("visits", place.getVisits());
            model.addAttribute("GPS_N", place.getGPS_N());
            model.addAttribute("GPS_E", place.getGPS_E());
        }

        catch (NullPointerException ex){
            model.addAttribute("errorMessage", "Place not found!");
            return "errorMsg";
        }

        return "placeDetails";
    }





    @PostMapping("/deletePlace")
    public String deletePlace(@RequestParam("name") String name){
        Places place;
        place = placeRepository.findByName(name);
        if (place != null) {
            placeRepository.delete(place);
        }
        return "redirect:/admin/menu";
    }


    @PostMapping("/addPlaceWithDetails")
    public String addPlaceWithDetails (@RequestParam("name") String name,
                                       @RequestParam("description") String description,
                                       @RequestParam("year")  int year,
                                       @RequestParam("points")  int points,
                                       @RequestParam("GPS_N") double GPS_N,
                                       @RequestParam("GPS_E") double GPS_E,
                                       Model model
                                       )
    {
        Places place = new Places();


            place.setName(name);
            place.setDescription(description);
            place.setYear(year);
            place.setGPS_E(GPS_E);
            place.setGPS_N(GPS_N);
            place.setPoints(points);

            place.setRating(0);
            place.setVisits(0);

            if (place.getName() != null && place.getName() != "")
                if (placeRepository.findByName(name) == null)
                    try {
                        placeRepository.save(place);
                    }
                    catch (Exception e){
                          model.addAttribute("errorMessage","Error while inserting to database. Invalid data.");
                        return "errorMsg";
                    }

        return "redirect:/admin/menu";
    }


    @PostMapping("/addPlace")
    public String addPlace (@RequestParam("name") String name){
        Places place = new Places();
        place.setName(name);


        if (place.getName() != null && place.getName() != "")
            if (placeRepository.findByName(name) == null)
                placeRepository.save(place);

        return "redirect:/admin/menu";
    }


    @PostMapping("/listPlaces")
    public String listPlaces(ModelMap model){
        ArrayList<Places> places = new ArrayList<>();
        places = (ArrayList<Places>) placeRepository.findAll();
        model.addAttribute("places", places);

        return "placeList";
    }



}
