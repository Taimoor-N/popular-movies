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

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    public static final String INTENT_MOVIE_DATA = "INTENT_MOVIE_DATA";

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_movies);

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
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(INTENT_MOVIE_DATA, movie);
        startActivity(intent);
    }

    private class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
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
            if (movies != null) {
                mMovieAdapter.setMovieData(movies);
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
