package com.example.android.popularmovies.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {

    private int id;
    private String title;
    private String image;
    private String plot;
    private double rating;
    private String releaseDate;
    private Boolean favourite;
    private ArrayList<Trailer> trailers;
    private ArrayList<Review> reviews;

    public Movie() {}

    public Movie(int id, String title, String image, String plot, double rating, String releaseDate) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.favourite = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPlot() {
        return plot;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    /**
     * This method adds a trailer to the list of trailers
     * @param trailer
     */
    public void addTrailer(Trailer trailer) {
        if (trailers == null) {
            trailers = new ArrayList<>();
        }
        trailers.add(trailer);
    }

    /**
     * This method adds a review to the list of reviews
     * @param review
     */
    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        reviews.add(review);
    }
}
