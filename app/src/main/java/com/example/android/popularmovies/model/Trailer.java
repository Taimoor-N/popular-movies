package com.example.android.popularmovies.model;

import java.io.Serializable;

public class Trailer implements Serializable {

    private String trailerName;
    private String trailerUrl;

    public Trailer(){}

    public Trailer(String name, String url) {
        trailerName = name;
        trailerUrl = url;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }
}
