package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    /**
     * This method takes a JSON string, parses it and creates the corresponding Movie list
     * @param json JSON string
     * @return ArrayList<Movie>
     */
    public static ArrayList<Movie> parseMovieJson(String json) throws JSONException {

        final String MOVIE_RESULTS = "results";
        final String JSON_TITLE = "title";
        final String JSON_IMAGE = "poster_path";
        final String JSON_PLOT = "overview";
        final String JSON_RATING = "vote_average";
        final String JSON_RELEASE_DATE = "release_date";

        final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780";

        String title; //Movie title
        String image; //Movie image URL
        String plot; //Movie plot
        double rating; //Movie rating (out of 10)
        String releaseDate; //Movie release date

        ArrayList<Movie> movies = new ArrayList<>();

        JSONObject movieJson = new JSONObject(json);
        JSONArray movieArray = movieJson.getJSONArray(MOVIE_RESULTS);

        for (int i = 0; i < movieArray.length(); i++) {
            try {
                JSONObject movieData = movieArray.getJSONObject(i);

                title = movieData.getString(JSON_TITLE);
                image = movieData.getString(JSON_IMAGE);
                plot = movieData.getString(JSON_PLOT);
                rating = movieData.getDouble(JSON_RATING);
                releaseDate = movieData.getString(JSON_RELEASE_DATE);

                String imageURL = IMAGE_BASE_URL + image;

                movies.add(new Movie(title, imageURL, plot, rating, releaseDate));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return movies;
    }
}
