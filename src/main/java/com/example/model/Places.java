package com.example.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="place")
public class Places {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(length = 65535,columnDefinition="Text")
    private String description;
    private int points;
    private double rating;
    private int year;
    private int visits;
    private double GPS_N;
    private double GPS_E;


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

    public double getGPS_N() {
        return GPS_N;
    }

    public void setGPS_N(double GPS_N) {
        this.GPS_N = GPS_N;
    }

    public double getGPS_E() {
        return GPS_E;
    }

    public void setGPS_E(double GPS_W) {
        this.GPS_E = GPS_W;
    }
}
