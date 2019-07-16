package com.example.android.popularmovies.model;

import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private String image;
    private String plot;
    private double rating;
    private String releaseDate;
    private int duration;

    public Movie() {}

    public Movie(String title, String image, String plot, double rating, String releaseDate, int duration) {
        this.title = title;
        this.image = image;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.duration = duration;
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

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

}
