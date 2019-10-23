package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.adapters.MovieAdapter;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.Movie;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    public static final String INTENT_MOVIE_DATA = "INTENT_MOVIE_DATA";

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private MovieAdapter mMovieAdapter;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());

        mRecyclerView = findViewById(R.id.rv_movies);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData(NetworkUtils.SORT_POPULAR);
    }

    /**
     * This method will get movie data in the background
     */
    private void loadMovieData(String sortCriteria) {
        new FetchMovieTask().execute(sortCriteria);
    }

    /**
     * This method handles RecyclerView item clicks.
     * @param movie Movie data.
     */
    @Override
    public void onClick(final Movie movie) {
        final Intent intent = new Intent(this, DetailActivity.class);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final int movieId = movie.getId();
                final Movie movieInDb = mDb.movieDao().loadMovieById(movieId);
                if (movieInDb != null) {
                    final Movie updatedMovieData = new Movie(movieId, movie.getTitle(), movie.getImage(), movie.getPlot(), movie.getRating(), movie.getReleaseDate(), movieInDb.getFavourite());
                    intent.putExtra(INTENT_MOVIE_DATA, updatedMovieData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                        }
                    });
                } else {
                    intent.putExtra(INTENT_MOVIE_DATA, movie);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    /**
     * This method will make the movie search results and hide the error message
     */
    public void showMovieResults() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * The method will show the error message and hide movie search results
     */
    public void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... sortCriteria) {
            URL movieSearchUrl = NetworkUtils.buildURL(sortCriteria[0]);
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieSearchUrl);
                return JsonUtils.parseMovieJson(jsonMovieResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMovieResults();
                mMovieAdapter.setMovieData(movies);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort_popular) {
            loadMovieData(NetworkUtils.SORT_POPULAR);
            return true;
        } else if (id == R.id.action_sort_top_rated) {
            loadMovieData(NetworkUtils.SORT_TOP_RATED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
