package com.tavisha.moviehub;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.Date;
import java.util.List;

public class Movie {
    private String id;
    private String name;
    private String description;
    private String review;
    private float rating;
    private Date releaseDate;
    private List<String> genres;

    public Movie() {
        // Empty constructor needed for Firestore
    }

    public Movie(String name, String description, String review, float rating, Date releaseDate, List<String> genres) {
        this.name = name;
        this.description = description;
        this.review = review;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Drawable getPosterDrawable(Context context) {
        return PosterGenerator.generatePoster(context, this);
    }
}

