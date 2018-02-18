package com.example.dany.movies.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.dany.movies.Model.Movie;
import com.example.dany.movies.Utils.Constants;
import com.example.dany.movies.db.MoviesRepository;

import java.util.List;

/**
 * Created by Dany on 11.2.2018 г..
 */

public class MoviesViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> listMovies;
    private MoviesRepository moviesRepository;

    private String observeWhat;
    private int lastSearchedRating;
    private int lastSearchedYear;

    public MoviesViewModel(Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);
        listMovies = moviesRepository.getListMovies();
        observeWhat = Constants.OBSERVE_ALL_MOVIES;
    }

    public void setObserveWhat(String observeWhat) {
        this.observeWhat = observeWhat;
    }

    public String getObserveWhat() {
        return this.observeWhat;
    }

    public void setLastSearchedRating(int lastSearchedRating) {
        this.lastSearchedRating = lastSearchedRating;
    }

    public int getLastSearchedRating() {
        return this.lastSearchedRating;
    }

    public void setLastSearchedYear(int lastSearchedYear) {
        this.lastSearchedYear = lastSearchedYear;
    }

    public int getLastSearchedYear() {
        return this.lastSearchedYear;
    }

    public LiveData<List<Movie>> getAllMovies() {
        return listMovies;
    }

    public LiveData<List<Movie>> getMoviesByRating(int rating) {
        return moviesRepository.getMoviesByRating(rating);
    }

    public LiveData<List<Movie>> getMoviesByYear(int year) {
        return moviesRepository.getMoviesByYear(year);
    }

    public void insertMovie(Movie movie) {
        moviesRepository.insertMovie(movie);
    }

}
