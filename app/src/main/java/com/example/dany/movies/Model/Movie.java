package com.example.dany.movies.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Dany on 10.2.2018 г..
 */
@Entity
public class Movie {

    @PrimaryKey (autoGenerate = true)
    public int id;
    private String name;
    private String actors;
    private String director;
    private int year;
    private int rating;

    public Movie(String name, String actors, String director, int year, int rating) {
        this.name = name;
        this.actors = actors;
        this.director = director;
        this.year = year;
        this.rating = rating;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getActors() {
        return this.actors;
    }

    public String getDirector() {
        return this.director;
    }

    public int getYear() {
        return this.year;
    }

    public int getRating() {
        return this.rating;
    }

}
