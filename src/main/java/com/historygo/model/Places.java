package com.historygo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="place")
public class Places {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable = false)
    private Integer id;
    
    @Column(unique=true, nullable = false)
    private String name;
    
    @Column(length = 65535, columnDefinition="Text")
    private String description;
    
    @Column(nullable = false)
    private int points;
    
    @Column(nullable = false, columnDefinition="int default 0")
    private double rating;
    
    @Column(nullable = false)
    private int year;
    
    @Column(nullable = false, columnDefinition="int default 0")
    private int visits;
    
    @Column(nullable = false)
    private double latitude;
    
    @Column(nullable = false)
    private double longitude;


    @ManyToMany
    @JoinTable(name = "place_user",
                joinColumns =  { @JoinColumn(name = "fk_place")  },
                inverseJoinColumns = { @JoinColumn(name="fk_user")})
    private Set<User> users = new HashSet<>();


    public void addUser(User user){
        this.users.add(user);
        user.getPlaces().add(this);
    }

    public void removeUser(User user){
        this.users.remove(user);
        user.getPlaces().remove(user);
    }

    @JsonIgnore
    public Set<User> getUsers() {
        return users;
    }

    public Places(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Places place = (Places) o;
        return Objects.equals(name, place.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

/*
    public List<Users> getUsersAsList() {

        List<Users>  temp = new ArrayList<>();
        for (Users user : this.getUsers()){
            temp.add(user);
        }

        return temp;
    }
*/

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
