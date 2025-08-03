package com.tavisha.moviehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewMoviesActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    private static final String TAG = "ViewMoviesActivity";
    private RecyclerView moviesRecyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;
    private SearchView searchView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movies);

        moviesRecyclerView = findViewById(R.id.moviesRecyclerView);
        searchView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBar);
        movieList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        movieAdapter = new MovieAdapter(this, movieList, this);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        moviesRecyclerView.setAdapter(movieAdapter);

        loadMovies();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieAdapter.getFilter().filter(newText);
                return true;
            }
        });

        findViewById(R.id.sortButton).setOnClickListener(this::showSortMenu);
    }

    private void showSortMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.sort_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.sort_title) {
                sortMovies(Comparator.comparing(Movie::getName));
            } else if (itemId == R.id.sort_date) {
                sortMovies(Comparator.comparing(Movie::getReleaseDate).reversed());
            } else if (itemId == R.id.sort_rating) {
                sortMovies(Comparator.comparing(Movie::getRating).reversed());
            }
            return true;
        });
        popup.show();
    }

    private void sortMovies(Comparator<Movie> comparator) {
        Collections.sort(movieList, comparator);
        movieAdapter.notifyDataSetChanged();
    }

    private void loadMovies() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("Movies").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    movieList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        try {
                            Movie movie = document.toObject(Movie.class);
                            movie.setId(document.getId());
                            movieList.add(movie);
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing movie document: " + document.getId(), e);
                        }
                    }
                    movieAdapter.notifyDataSetChanged();
                    movieAdapter.updateFullList(movieList);
                    progressBar.setVisibility(View.GONE);

                    if (movieList.isEmpty()) {
                        Toast.makeText(ViewMoviesActivity.this, "No movies found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error loading movies", e);
                    Toast.makeText(ViewMoviesActivity.this, "Error loading movies: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                });
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, EditMovieActivity.class);
        intent.putExtra("movieId", movie.getId());
        startActivity(intent);
    }

    @Override
    public void onMovieDelete(Movie movie) {
        db.collection("Movies").document(movie.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    movieList.remove(movie);
                    movieAdapter.notifyDataSetChanged();
                    Toast.makeText(ViewMoviesActivity.this, "Movie deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error deleting movie", e);
                    Toast.makeText(ViewMoviesActivity.this, "Error deleting movie: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMovies();
    }
}

