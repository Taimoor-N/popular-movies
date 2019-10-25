package com.example.android.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String FAVOURITE_MOVIES = "favourite";
    private static final String SAVE_INSTANCE_SORT_CRITERIA = "save_instance_sort_criteria";

    public static final String INTENT_MOVIE_DATA = "INTENT_MOVIE_DATA";

    private String mSortCriteria;

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

        GridLayoutManager layoutManager = new GridLayoutManager(this, getRequiredGridCols());
        mRecyclerView.setLayoutManager(layoutManager);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mSortCriteria = NetworkUtils.SORT_POPULAR;
        // Gets movie sort criteria from savedInstanceState
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SAVE_INSTANCE_SORT_CRITERIA)) {
                mSortCriteria = savedInstanceState.getString(SAVE_INSTANCE_SORT_CRITERIA);
            }
        }

        if (mSortCriteria.equals(FAVOURITE_MOVIES)) {
            loadMoviesFromViewModel();
        } else {
            loadMoviesFromServer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSortCriteria != null) {
            outState.putString(SAVE_INSTANCE_SORT_CRITERIA, mSortCriteria);
        }
    }

    /**
     * This method will get movie data in the background
     */
    private void loadMoviesFromServer() {
        new FetchMovieTask().execute();
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

    /**
     * This method will load movies from database and observe changes with LiveData.
     * If stored movie data is changed, observer will set mMovieAdapter to updated
     * data only if sorting criteria is FAVOURITE_MOVIES.
     */
    private void loadMoviesFromViewModel() {
        showMovieResults();
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (mSortCriteria.equals(FAVOURITE_MOVIES)) {
                    Log.d(LOG_TAG, "Updating list of tasks from LiveData in ViewModel");
                    mMovieAdapter.setMovieData(new ArrayList<>(movies));
                }
            }
        });
    }

    /**
     * This function returns the number of columns for grid layout
     * - 2 columns for portrait and 3 columns for landscape layout
     * @return number of columns for grid layout
     */
    private int getRequiredGridCols() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3;
    }

    private class FetchMovieTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            URL movieSearchUrl = NetworkUtils.buildURL(mSortCriteria);
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
        switch (item.getItemId()) {
            case (R.id.action_sort_popular):
                mSortCriteria = NetworkUtils.SORT_POPULAR;
                loadMoviesFromServer();
                return true;
            case (R.id.action_sort_top_rated):
                mSortCriteria = NetworkUtils.SORT_TOP_RATED;
                loadMoviesFromServer();
                return true;
            case (R.id.action_favourite_movies):
                mSortCriteria = FAVOURITE_MOVIES;
                loadMoviesFromViewModel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
