package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;

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
        final String JSON_ID = "id";
        final String JSON_TITLE = "title";
        final String JSON_IMAGE = "poster_path";
        final String JSON_PLOT = "overview";
        final String JSON_RATING = "vote_average";
        final String JSON_RELEASE_DATE = "release_date";

        final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";

        int id; //Movie id
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

                id = movieData.getInt(JSON_ID);
                title = movieData.getString(JSON_TITLE);
                image = movieData.getString(JSON_IMAGE);
                plot = movieData.getString(JSON_PLOT);
                rating = movieData.getDouble(JSON_RATING);
                releaseDate = movieData.getString(JSON_RELEASE_DATE);

                String imageURL = IMAGE_BASE_URL + image;

                movies.add(new Movie(id, title, imageURL, plot, rating, releaseDate));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return movies;
    }

    /**
     * This method takes a JSON string, parses it and creates the corresponding Trailer list
     * @param json JSON string
     * @return ArrayList<Trailer>
     */
    public static ArrayList<Trailer> parseTrailerJson(String json) throws JSONException {

        final String TRAILER_RESULTS = "results";
        final String TRAILER_NAME = "name";
        final String TRAILER_KEY = "key";

        final String TRAILER_BASE_URL = "https://www.youtube.com/watch?v=";

        String trailerName;
        String trailerKey;
        String trailerUrl;

        ArrayList<Trailer> trailers = new ArrayList<>();

        JSONObject trailerJson = new JSONObject(json);
        JSONArray trailerArray = trailerJson.getJSONArray(TRAILER_RESULTS);

        for (int i = 0; i < trailerArray.length(); i++) {
            try {
                JSONObject movieData = trailerArray.getJSONObject(i);

                trailerName = movieData.getString(TRAILER_NAME);
                trailerKey = movieData.getString(TRAILER_KEY);

                trailerUrl = TRAILER_BASE_URL + trailerKey;

                trailers.add(new Trailer(trailerName, trailerUrl));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return trailers;
    }

    /**
     * This method takes a JSON string, parses it and creates the corresponding Review list
     * @param json JSON string
     * @return ArrayList<Review>
     */
    public static ArrayList<Review> parseReviewJson(String json) throws JSONException {

        final String REVIEW_RESULTS = "results";
        final String REVIEW_AUTHOR = "author";
        final String REVIEW_CONTENT = "content";

        String reviewAuthor;
        String reviewContent;

        ArrayList<Review> reviews = new ArrayList<>();

        JSONObject reviewJson = new JSONObject(json);
        JSONArray reviewArray = reviewJson.getJSONArray(REVIEW_RESULTS);

        for (int i = 0; i < reviewArray.length(); i++) {
            try {
                JSONObject movieData = reviewArray.getJSONObject(i);

                reviewAuthor = movieData.getString(REVIEW_AUTHOR);
                reviewContent = movieData.getString(REVIEW_CONTENT);

                reviews.add(new Review(reviewAuthor, reviewContent));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return reviews;
    }

}