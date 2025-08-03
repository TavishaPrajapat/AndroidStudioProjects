package com.tavisha.moviehub;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText movieNameET, movieDescriptionET, movieReviewET, releaseDateET;
    private RatingBar movieRatingBar;
    private Button addMovieBtn, viewMoviesBtn;
    private TextView welcomeTV;
    private ChipGroup genreChipGroup;

    private FirebaseFirestore db;
    private CollectionReference moviesRef;

    private Date selectedReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        moviesRef = db.collection("Movies");

        initializeViews();

        String userName = getIntent().getStringExtra("userName");
        if (userName != null && !userName.isEmpty()) {
            welcomeTV.setText("Welcome, " + userName + "!");
        }

        setupListeners();
        setupGenreChips();
    }

    private void initializeViews() {
        welcomeTV = findViewById(R.id.welcomeTV);
        movieNameET = findViewById(R.id.movieNameET);
        movieDescriptionET = findViewById(R.id.movieDescriptionET);
        movieReviewET = findViewById(R.id.movieReviewET);
        movieRatingBar = findViewById(R.id.movieRatingBar);
        addMovieBtn = findViewById(R.id.addMovieBtn);
        viewMoviesBtn = findViewById(R.id.viewMoviesBtn);
        releaseDateET = findViewById(R.id.releaseDateET);
        genreChipGroup = findViewById(R.id.genreChipGroup);
    }

    private void setupListeners() {
        addMovieBtn.setOnClickListener(v -> addMovie());
        viewMoviesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewMoviesActivity.class);
            startActivity(intent);
        });
        releaseDateET.setOnClickListener(v -> showDatePickerDialog());
    }

    private void setupGenreChips() {
        String[] genres = {"Action", "Comedy", "Drama", "Sci-Fi", "Horror", "Romance"};
        for (String genre : genres) {
            Chip chip = new Chip(this);
            chip.setText(genre);
            chip.setCheckable(true);
            genreChipGroup.addView(chip);
        }
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    selectedReleaseDate = new Date(year1 - 1900, monthOfYear, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                    releaseDateET.setText(sdf.format(selectedReleaseDate));
                }, year, month, day);
        datePickerDialog.show();
    }

    private void addMovie() {
        String name = movieNameET.getText().toString().trim();
        String description = movieDescriptionET.getText().toString().trim();
        String review = movieReviewET.getText().toString().trim();
        float rating = movieRatingBar.getRating();

        if (name.isEmpty() || description.isEmpty() || review.isEmpty() || selectedReleaseDate == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> selectedGenres = new ArrayList<>();
        for (int i = 0; i < genreChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) genreChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                selectedGenres.add(chip.getText().toString());
            }
        }

        if (selectedGenres.isEmpty()) {
            Toast.makeText(this, "Please select at least one genre", Toast.LENGTH_SHORT).show();
            return;
        }

        Movie movie = new Movie(name, description, review, rating, selectedReleaseDate, selectedGenres);

        moviesRef.add(movie)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(MainActivity.this, "Movie added successfully", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                })
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error adding movie", Toast.LENGTH_SHORT).show());
    }

    private void clearInputFields() {
        movieNameET.setText("");
        movieDescriptionET.setText("");
        movieReviewET.setText("");
        movieRatingBar.setRating(0);
        releaseDateET.setText("");
        selectedReleaseDate = null;
        for (int i = 0; i < genreChipGroup.getChildCount(); i++) {
            ((Chip) genreChipGroup.getChildAt(i)).setChecked(false);
        }
    }
}

