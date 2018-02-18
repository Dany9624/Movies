package com.example.dany.movies.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dany.movies.Model.Movie;
import com.example.dany.movies.R;
import com.example.dany.movies.Utils.Constants;
import com.example.dany.movies.ViewModel.MovieDetailsViewModel;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextActors;
    private EditText editTextDirector;
    private EditText editTextYear;
    private RatingBar ratingBar;
    private TextView textViewName;
    private TextView textViewActors;
    private TextView textViewDirector;
    private TextView textViewYear;
    private FloatingActionButton floatingActionButtonDelete;
    private FloatingActionButton floatingActionButtonEdit;

    private MovieDetailsViewModel movieDetailsViewModel;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initUI();
        setListeners();

        movieDetailsViewModel = ViewModelProviders.of(MovieDetailsActivity.this).get(MovieDetailsViewModel.class);

        id = getIntent().getExtras().getInt(Constants.ID);
        movieDetailsViewModel.getMovieById(id).observe(MovieDetailsActivity.this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if(movie != null) {
                    textViewName.setText(movie.getName());
                    textViewActors.setText(movie.getActors());
                    textViewDirector.setText(movie.getDirector());
                    ratingBar.setRating((int)movie.getRating());
                    textViewYear.setText(String.valueOf(movie.getYear()));
                }
            }
        });
    }

    private void initUI() {
        editTextName = findViewById(R.id.editTextName);
        editTextActors = findViewById(R.id.editTextActors);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextYear = findViewById(R.id.editTextYear);
        ratingBar = findViewById(R.id.ratingBar);
        textViewName = findViewById(R.id.textViewName);
        textViewActors = findViewById(R.id.textViewActors);
        textViewDirector = findViewById(R.id.textViewDirector);
        textViewYear = findViewById(R.id.textViewYear);
        floatingActionButtonEdit = findViewById(R.id.floatingActionButtonEdit);
        floatingActionButtonDelete = findViewById(R.id.floatingActionButtonDelete);
    }

    private void setListeners() {
        floatingActionButtonEdit.setOnClickListener(this);
        floatingActionButtonDelete.setOnClickListener(this);
    }

    private boolean checkForEmptyFields() {
        return (TextUtils.isEmpty(editTextName.getText()) || TextUtils.isEmpty(editTextActors.getText()) ||
                TextUtils.isEmpty(editTextDirector.getText()) || TextUtils.isEmpty(editTextYear.getText()));
    }

    private void allowEditing() {
        editTextName.setVisibility(View.VISIBLE);
        editTextActors.setVisibility(View.VISIBLE);
        editTextDirector.setVisibility(View.VISIBLE);
        ratingBar.setIsIndicator(false);
        editTextYear.setVisibility(View.VISIBLE);
        textViewName.setVisibility(View.GONE);
        textViewActors.setVisibility(View.GONE);
        textViewDirector.setVisibility(View.GONE);
        textViewYear.setVisibility(View.GONE);
        editTextName.setText(textViewName.getText());
        editTextActors.setText(textViewActors.getText());
        editTextDirector.setText(textViewDirector.getText());
        editTextYear.setText(textViewYear.getText());
    }

    private void editMovie() {
        if(!movieDetailsViewModel.getEdit()) {
            movieDetailsViewModel.setEdit(true);
            floatingActionButtonEdit.setImageResource(R.mipmap.ic_done_white_24dp);
            allowEditing();
        }
        else {
            if(!checkForEmptyFields()) {
                Movie movie = new Movie(editTextName.getText().toString(), editTextActors.getText().toString(),
                        editTextDirector.getText().toString(), Integer.valueOf(editTextYear.getText().toString()),
                        (int)ratingBar.getRating());
                movie.setId(id);
                movieDetailsViewModel.updateMovie(movie);
                finish();
            }
        }
    }

    private void deleteMovie() {
        Movie movie = new Movie(textViewName.getText().toString(), textViewActors.getText().toString(), textViewDirector.getText().toString(),
                Integer.valueOf(textViewYear.getText().toString()), (int)ratingBar.getRating());
        movie.setId(id);
        movieDetailsViewModel.deleteMovie(movie);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButtonEdit:
                editMovie();
                break;
            case R.id.floatingActionButtonDelete:
                deleteMovie();
                break;
        }
    }
}
