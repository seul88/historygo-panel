package com.historygo.controller.rest;


import com.historygo.model.Places;
import com.historygo.repository.PlaceRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
@RequestMapping(path="/places")
@CrossOrigin(origins = "http://localhost:4200")
public class PlaceController {
   
	
	@Autowired
    private PlaceRepository placeRepository;

    
	
    @PostMapping()
    public ResponseEntity<Object> addPlace(@RequestBody Places place) {
		
    	HttpStatus status;
    
    	try {
    		this.placeRepository.save(place);
    		status = HttpStatus.CREATED;
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}").buildAndExpand(place.getId()).toUri();
    		return new ResponseEntity<>(uri, status);
    	}
    	catch(DataAccessException dataAccessException) {
    		 System.err.println("Data access layer releted error occured during Product insertion.");
			 status = HttpStatus.BAD_REQUEST;
			 String response = "Data access layer releted error occured during Product insertion.";
			 return new ResponseEntity<>(response, status);
    	}
    }
    	
    
    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getPlaceById(@PathVariable("id") int id){
    	
    	Optional<Places> placeToFind;
    	
    	try {
    	 placeToFind = this.placeRepository.findById(id);
    		if (placeToFind.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	catch(DataAccessException dataAccessException) {	
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	 	
    	return new ResponseEntity<Object>(placeToFind, HttpStatus.OK);
    }
    
    
    
    @DeleteMapping(path="/id/{id}")
    public ResponseEntity<String> deletePlaceById(@PathVariable("id") int id) {
    
    	 try {
    		 Places placeToRemove = placeRepository.getOne(id);
    		 placeRepository.delete(placeToRemove);
			 return new ResponseEntity<String>("Place succesfully removed.", HttpStatus.OK);
    	 }
    	 catch(DataAccessException dataAccessException) {
    		 System.err.println("Data access layer error occured during deleting the place.");
             return new ResponseEntity<String>("Data access layer error occured during deleting the place.", HttpStatus.BAD_REQUEST);
    	 }
    }
    
    
    @PutMapping(path="/id/{id}")
    public ResponseEntity<Object> modifyExistingPlace(@RequestBody Places place, @PathVariable("id") int id){
    	
    	Places placeToModify;
    	Places newValues = place;
    	try {
    		placeToModify = this.placeRepository.getOne(id);
    		newValues.setId(placeToModify.getId());
    		placeToModify = place;
    		this.placeRepository.save(newValues);
    		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(place.getId()).toUri();
    		return new ResponseEntity<>(uri, HttpStatus.OK); 	
    	}
    	catch(DataAccessException dataAccessException) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    	}
    	
    	
    }
    
    
     @GetMapping(path="/all")
     public ResponseEntity<List<Places>> getAllPlaces(){
    	 HttpStatus status;
    	 List<Places> allPlaces;
    	 try {
    		 allPlaces = this.placeRepository.findAll();
			 status = HttpStatus.OK;
			 return new ResponseEntity<List<Places>>(allPlaces, status);
    	 }
    	 catch(DataAccessException dataAccessException) {
    		 dataAccessException.printStackTrace();
			 System.err.println("Error occured - no data found.");
			 status = HttpStatus.NOT_FOUND;
			 return new ResponseEntity<>(status); 		 
    	 } 
     }
    
}