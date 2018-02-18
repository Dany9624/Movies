package com.example.dany.movies.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.dany.movies.R;
import com.example.dany.movies.Utils.Constants;

public class AddMovieActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextActors;
    private EditText editTextDirector;
    private EditText editTextYear;
    private RatingBar ratingBar;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        initUI();
        setListeners();
    }

    private void initUI() {
        editTextName = findViewById(R.id.editTextName);
        editTextActors = findViewById(R.id.editTextActors);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextYear = findViewById(R.id.editTextYear);
        ratingBar = findViewById(R.id.ratingBar);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(this);
    }

    private boolean checkForEmptyFields() {
        return (TextUtils.isEmpty(editTextName.getText()) || TextUtils.isEmpty(editTextActors.getText()) ||
                TextUtils.isEmpty(editTextDirector.getText()) || TextUtils.isEmpty(editTextYear.getText()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton:
                if(!checkForEmptyFields()) {
                    Intent replyIntent = new Intent(AddMovieActivity.this, MoviesActivity.class);
                    replyIntent.putExtra(Constants.NAME, String.valueOf(editTextName.getText()));
                    replyIntent.putExtra(Constants.ACTORS, String.valueOf(editTextActors.getText()));
                    replyIntent.putExtra(Constants.DIRECTOR, String.valueOf(editTextDirector.getText()));
                    replyIntent.putExtra(Constants.YEAR, Integer.valueOf(editTextYear.getText().toString()));
                    replyIntent.putExtra(Constants.RATING, (int)ratingBar.getRating());
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
                else {
                    new AlertDialog.Builder(this)
                            .setMessage(getResources().getString(R.string.empty_fields))
                            .setPositiveButton(getResources().getString(R.string.ok), null)
                            .show();
                }
        }
    }
}
