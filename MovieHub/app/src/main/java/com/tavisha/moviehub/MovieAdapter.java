package com.tavisha.moviehub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements Filterable {

    private Context context;
    private List<Movie> movieList;
    private List<Movie> movieListFull;
    private OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
        void onMovieDelete(Movie movie);
    }

    public MovieAdapter(Context context, List<Movie> movieList, OnMovieClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.movieListFull = new ArrayList<>(movieList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.movieNameTV.setText(movie.getName());
        holder.movieDescriptionTV.setText(movie.getDescription());
        holder.movieRatingBar.setRating(movie.getRating());

        if (movie.getReleaseDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            holder.releaseDateTV.setText("Release Date: " + sdf.format(movie.getReleaseDate()));
        } else {
            holder.releaseDateTV.setText("Release Date: Unknown");
        }

        StringBuilder genresBuilder = new StringBuilder("Genres: ");
        if (movie.getGenres() != null && !movie.getGenres().isEmpty()) {
            for (String genre : movie.getGenres()) {
                genresBuilder.append(genre).append(", ");
            }
            genresBuilder.setLength(genresBuilder.length() - 2);
        } else {
            genresBuilder.append("None");
        }
        holder.genresTV.setText(genresBuilder.toString());

        // Use the generated poster drawable
        holder.posterIV.setImageDrawable(movie.getPosterDrawable(context));

        holder.editButton.setOnClickListener(v -> listener.onMovieClick(movie));
        holder.deleteButton.setOnClickListener(v -> listener.onMovieDelete(movie));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(movieListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Movie movie : movieListFull) {
                    if (movie.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movieList.clear();
            movieList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void updateFullList(List<Movie> newList) {
        movieListFull = new ArrayList<>(newList);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieNameTV, movieDescriptionTV, releaseDateTV, genresTV;
        RatingBar movieRatingBar;
        ImageView posterIV;
        ImageButton editButton, deleteButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieNameTV = itemView.findViewById(R.id.movieNameTV);
            movieDescriptionTV = itemView.findViewById(R.id.movieDescriptionTV);
            movieRatingBar = itemView.findViewById(R.id.movieRatingBar);
            releaseDateTV = itemView.findViewById(R.id.releaseDateTV);
            genresTV = itemView.findViewById(R.id.genresTV);
            posterIV = itemView.findViewById(R.id.posterIV);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}

