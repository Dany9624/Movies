package com.example.dany.movies.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.dany.movies.Model.Movie;
import com.example.dany.movies.db.MoviesRepository;

/**
 * Created by Dany on 11.2.2018 г..
 */

public class MovieDetailsViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;
    private LiveData<Movie> movie;
    private boolean edit;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);
        edit = false;
    }

    public LiveData<Movie> getMovieById(int id) {
        movie = moviesRepository.getMovieById(id);
        return movie;
    }

    public void setEdit(boolean value) {
        this.edit = value;
    }
    public boolean getEdit() {
        return this.edit;
    }

    public void updateMovie(Movie movie) {
        moviesRepository.updateMovie(movie);
    }

    public void deleteMovie(Movie movie) {
        moviesRepository.deleteMovie(movie);
    }


}
