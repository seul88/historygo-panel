package com.example.controller.rest;


import com.example.model.Places;
import com.example.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/places")
public class PlaceController {
    @Autowired
    private PlaceRepository placeRepository;

    @GetMapping(path = "/add")
    public @ResponseBody
    String addNewPlace(@RequestParam String name
                       //, @RequestParam int points
    ) {

        Places n = new Places();
        n.setName(name);
        n.setPoints(0);
        n.setDescription("");
        n.setGPS_E(0.0);
        n.setGPS_N(0.0);
        n.setRating(0);
        n.setVisits(0);
        n.setYear(2000);

        placeRepository.save(n);
        return "Saved";
    }

    @PostMapping("/add")
    public String addPlace(@RequestBody Places place) {

        Places temp = new Places();

        temp.setName(place.getName());
        temp.setVisits(place.getVisits());
        temp.setRating(place.getRating());
        temp.setDescription(place.getDescription());
        temp.setGPS_N(place.getGPS_N());
        temp.setGPS_E(place.getGPS_E());
        temp.setPoints(place.getPoints());
        temp.setYear(place.getYear());


        this.placeRepository.save(temp);

        return "Place saved.";
    }


    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Places> getAllPlaces() {

        return placeRepository.findAll();
    }
}