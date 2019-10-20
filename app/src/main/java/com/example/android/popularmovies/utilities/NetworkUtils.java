package com.example.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String MOVIEDB_REVIEW_URL_PATH = "reviews";
    private static final String MOVIEDB_VIDEO_URL_PATH = "videos";

    public static final String SORT_POPULAR = "popular";
    public static final String SORT_TOP_RATED = "top_rated";

    /**
     * Builds the URL to communicate with the Movie DB API.
     * @param sortCriteria Criteria for sorting the list of movies.
     * @return The URL to use to query the Movie DB server.
     */
    public static URL buildURL(String sortCriteria) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendEncodedPath(sortCriteria)
                .encodedQuery(API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * Builds the URL to get movie trailers for the provided movie ID
     * @param movieId Movie ID for which to fetch the trailers
     * @return The URL to get movie trailers from the Movie DB server
     */
    public static URL buildTrailerURL(int movieId) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendEncodedPath(String.valueOf(movieId))
                .appendEncodedPath(MOVIEDB_VIDEO_URL_PATH)
                .encodedQuery(API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built Trailer URI " + url);

        return url;
    }

    /**
     * Builds the URL to get movie reviews for the provided movie ID
     * @param movieId Movie ID for which to fetch the reviews
     * @return The URL to get movie reviews from the Movie DB server
     */
    public static URL buildReviewURL(int movieId) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendEncodedPath(String.valueOf(movieId))
                .appendEncodedPath(MOVIEDB_REVIEW_URL_PATH)
                .encodedQuery(API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built Review URI " + url);

        return url;
    }

    /**
     * (Function taken from Udacity - Android Developer Course)
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
