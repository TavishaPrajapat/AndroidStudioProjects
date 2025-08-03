package com.tavisha.moviehub;


import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditMovieActivity extends AppCompatActivity {
    private EditText editMovieNameET, editMovieDescriptionET, editMovieReviewET, editReleaseDateET;
    private RatingBar editMovieRatingBar;
    private Button updateMovieBtn, deleteMovieBtn, returnToMainBtn;
    private ChipGroup editGenreChipGroup;

    private FirebaseFirestore db;
    private DocumentReference movieRef;
    private String movieId;

    private Date selectedReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        db = FirebaseFirestore.getInstance();

        movieId = getIntent().getStringExtra("movieId");
        if (movieId == null) {
            Toast.makeText(this, "Error: Movie not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        movieRef = db.collection("Movies").document(movieId);

        initializeViews();
        loadMovieData();
        setupListeners();
    }

    private void initializeViews() {
        editMovieNameET = findViewById(R.id.editMovieNameET);
        editMovieDescriptionET = findViewById(R.id.editMovieDescriptionET);
        editMovieReviewET = findViewById(R.id.editMovieReviewET);
        editMovieRatingBar = findViewById(R.id.editMovieRatingBar);
        updateMovieBtn = findViewById(R.id.updateMovieBtn);
        deleteMovieBtn = findViewById(R.id.deleteMovieBtn);
        returnToMainBtn = findViewById(R.id.returnToMainBtn);
        editReleaseDateET = findViewById(R.id.editReleaseDateET);
        editGenreChipGroup = findViewById(R.id.editGenreChipGroup);
    }


    private void setupListeners() {
        updateMovieBtn.setOnClickListener(v -> updateMovie());
        deleteMovieBtn.setOnClickListener(v -> deleteMovie());
        returnToMainBtn.setOnClickListener(v -> finish());
        editReleaseDateET.setOnClickListener(v -> showDatePickerDialog());

        String[] genres = {"Action", "Comedy", "Drama", "Sci-Fi", "Horror", "Romance"};
        for (String genre : genres) {
            Chip chip = new Chip(this);
            chip.setText(genre);
            chip.setCheckable(true);
            editGenreChipGroup.addView(chip);
        }
    }


    private void loadMovieData() {
        movieRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Movie movie = documentSnapshot.toObject(Movie.class);
                        if (movie != null) {
                            editMovieNameET.setText(movie.getName());
                            editMovieDescriptionET.setText(movie.getDescription());
                            editMovieReviewET.setText(movie.getReview());
                            editMovieRatingBar.setRating(movie.getRating());
                            selectedReleaseDate = movie.getReleaseDate();
                            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                            editReleaseDateET.setText(sdf.format(selectedReleaseDate));

                            for (int i = 0; i < editGenreChipGroup.getChildCount(); i++) {
                                Chip chip = (Chip) editGenreChipGroup.getChildAt(i);
                                chip.setChecked(movie.getGenres().contains(chip.getText().toString()));
                            }
                        }
                    } else {
                        Toast.makeText(EditMovieActivity.this, "Movie not found", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditMovieActivity.this, "Error loading movie data", Toast.LENGTH_SHORT).show();
                    finish();
                });
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
                    editReleaseDateET.setText(sdf.format(selectedReleaseDate));
                }, year, month, day);
        datePickerDialog.show();
    }

    private void updateMovie() {
        String name = editMovieNameET.getText().toString().trim();
        String description = editMovieDescriptionET.getText().toString().trim();
        String review = editMovieReviewET.getText().toString().trim();
        float rating = editMovieRatingBar.getRating();

        if (name.isEmpty() || description.isEmpty() || review.isEmpty() || selectedReleaseDate == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> selectedGenres = new ArrayList<>();
        for (int i = 0; i < editGenreChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) editGenreChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                selectedGenres.add(chip.getText().toString());
            }
        }

        if (selectedGenres.isEmpty()) {
            Toast.makeText(this, "Please select at least one genre", Toast.LENGTH_SHORT).show();
            return;
        }


        Movie updatedMovie = new Movie(name, description, review, rating, selectedReleaseDate, selectedGenres);

        movieRef.set(updatedMovie)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditMovieActivity.this, "Movie updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditMovieActivity.this, "Error updating movie", Toast.LENGTH_SHORT).show());
    }

    private void deleteMovie() {
        movieRef.delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditMovieActivity.this, "Movie deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditMovieActivity.this, "Error deleting movie", Toast.LENGTH_SHORT).show());
    }
}

