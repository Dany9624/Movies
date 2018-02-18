package com.example.dany.movies.View;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;

import com.example.dany.movies.Adapters.MoviesAdapter;
import com.example.dany.movies.Model.Movie;
import com.example.dany.movies.R;
import com.example.dany.movies.Utils.Constants;
import com.example.dany.movies.ViewModel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerViewMovies;
    private ImageView imageViewSearch;
    private ImageView imageViewRefresh;

    private MoviesViewModel moviesViewModel;
    private MoviesAdapter adapter;

    private Observer<List<Movie>> moviesObserver;
    private Observer<List<Movie>> moviesRatingObserver;
    private Observer<List<Movie>> moviesYearObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        initUI();
        setListeners();

        moviesObserver = new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> list) {
                if(moviesViewModel.getObserveWhat().equals(Constants.OBSERVE_ALL_MOVIES)) {
                    adapter.addItems(list);
                }
            }
        };

        moviesRatingObserver = new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> list) {
                if(moviesViewModel.getObserveWhat().equals(Constants.OBSERVE_MOVIES_BY_RATING)) {
                    adapter.addItems(list);
                }
            }
        };

        moviesYearObserver = new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> list) {
                if(moviesViewModel.getObserveWhat().equals(Constants.OBSERVE_MOVIES_BY_YEAR)) {
                    adapter.addItems(list);
                }
            }
        };

        observeWhat(moviesViewModel.getObserveWhat());

    }

    private void initUI() {
        imageViewSearch = findViewById(R.id.imageViewSearch);
        imageViewRefresh = findViewById(R.id.imageViewRefresh);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        recyclerViewMovies.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewMovies.setLayoutManager(linearLayoutManager);
        adapter = new MoviesAdapter(new ArrayList<Movie>(), this);
        recyclerViewMovies.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewMovies.getContext(),
                linearLayoutManager.getOrientation());
        recyclerViewMovies.addItemDecoration(dividerItemDecoration);
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(this);
        imageViewSearch.setOnClickListener(this);
        imageViewRefresh.setOnClickListener(this);
    }

    private void observeWhat(String observeWhat) {
        switch (observeWhat) {
            case Constants.OBSERVE_ALL_MOVIES:
                moviesViewModel.getAllMovies().observe(MoviesActivity.this, moviesObserver);
                break;
            case Constants.OBSERVE_MOVIES_BY_RATING:
                moviesViewModel.getMoviesByRating(moviesViewModel.getLastSearchedRating()).observe(MoviesActivity.this, moviesRatingObserver);
                break;
            case Constants.OBSERVE_MOVIES_BY_YEAR:
                moviesViewModel.getMoviesByYear(moviesViewModel.getLastSearchedYear()).observe(MoviesActivity.this, moviesYearObserver);
                break;
        }
    }

    private void searchByRating() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.menu_search_by_rating));
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.ratingbar_alert, viewGroup, false);
        final RatingBar ratingBar = viewInflated.findViewById(R.id.ratingBar);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                moviesViewModel.setObserveWhat(Constants.OBSERVE_MOVIES_BY_RATING);
                moviesViewModel.setLastSearchedRating((int)ratingBar.getRating());
                moviesViewModel.getAllMovies().removeObserver(moviesObserver);
                moviesViewModel.getMoviesByYear(moviesViewModel.getLastSearchedYear()).removeObserver(moviesYearObserver);
                moviesViewModel.getMoviesByRating(moviesViewModel.getLastSearchedRating()).observe(MoviesActivity.this, moviesRatingObserver);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void searchByYear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.menu_search_by_year));
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!TextUtils.isEmpty(input.getText())) {
                    moviesViewModel.setObserveWhat(Constants.OBSERVE_MOVIES_BY_YEAR);
                    moviesViewModel.setLastSearchedYear(Integer.valueOf(input.getText().toString()));
                    moviesViewModel.getAllMovies().removeObserver(moviesObserver);
                    moviesViewModel.getMoviesByRating(moviesViewModel.getLastSearchedRating()).removeObserver(moviesRatingObserver);
                    moviesViewModel.getMoviesByYear(moviesViewModel.getLastSearchedYear()).observe(MoviesActivity.this, moviesYearObserver);
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void refresh() {
        moviesViewModel.setObserveWhat(Constants.OBSERVE_ALL_MOVIES);
        moviesViewModel.getMoviesByRating(moviesViewModel.getLastSearchedRating()).removeObserver(moviesRatingObserver);
        moviesViewModel.getMoviesByYear(moviesViewModel.getLastSearchedYear()).removeObserver(moviesYearObserver);
        moviesViewModel.getAllMovies().observe(MoviesActivity.this, moviesObserver);
    }

    private void showMenu() {
        PopupMenu popup = new PopupMenu(MoviesActivity.this, imageViewSearch);
        popup.getMenuInflater().inflate(R.menu.pop_up_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
               switch (item.getItemId()) {
                   case R.id.rating:
                       searchByRating();
                       break;
                   case R.id.year:
                       searchByYear();
                       break;
               }
                return true;
            }
        });

        popup.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.NEW_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Movie movie = new Movie(data.getExtras().getString(Constants.NAME), data.getExtras().getString(Constants.ACTORS),
                    data.getExtras().getString(Constants.DIRECTOR), data.getExtras().getInt(Constants.YEAR),
                    data.getExtras().getInt(Constants.RATING));
            moviesViewModel.insertMovie(movie);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton:
                Intent intentNewMovie = new Intent(MoviesActivity.this, AddMovieActivity.class);
                startActivityForResult(intentNewMovie, Constants.NEW_MOVIE_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.imageViewSearch:
                showMenu();
                break;
            case R.id.imageViewRefresh:
                refresh();
                break;
        }
    }
}
