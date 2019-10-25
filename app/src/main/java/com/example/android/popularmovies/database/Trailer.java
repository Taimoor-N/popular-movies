package com.example.android.popularmovies.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "trailer")
public class Trailer {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int movieId;
    private String trailerName;
    private String trailerUrl;

    @Ignore
    public Trailer(int movieId, String trailerName, String trailerUrl) {
        this.movieId = movieId;
        this.trailerName = trailerName;
        this.trailerUrl = trailerUrl;
    }

    public Trailer(int id, int movieId, String trailerName, String trailerUrl) {
        this.id = id;
        this.movieId = movieId;
        this.trailerName = trailerName;
        this.trailerUrl = trailerUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

}
