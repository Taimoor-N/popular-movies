package com.example.android.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.popularmovies.adapters.ReviewAdapter;
import com.example.android.popularmovies.adapters.TrailerAdapter;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.databinding.ActivityDetailBinding;
import com.example.android.popularmovies.database.Movie;
import com.example.android.popularmovies.database.Review;
import com.example.android.popularmovies.database.Trailer;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {

    private ActivityDetailBinding mBinding;

    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;

    private RecyclerView mTrailerRecyclerView;
    private RecyclerView mReviewRecyclerView;

    private Movie mMovieData;
    private ArrayList<Trailer> mMovieTrailers;
    private ArrayList<Review> mMovieReviews;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        Object movieObj = intent.getSerializableExtra(MainActivity.INTENT_MOVIE_DATA);
        mMovieData = (Movie) movieObj;

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        mTrailerRecyclerView = findViewById(R.id.rv_trailers);
        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTrailerAdapter = new TrailerAdapter(this, this);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);
        mTrailerRecyclerView.setNestedScrollingEnabled(false);

        mReviewRecyclerView = findViewById(R.id.rv_reviews);
        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewAdapter = new ReviewAdapter(this);
        mReviewRecyclerView.setAdapter(mReviewAdapter);
        mReviewRecyclerView.setNestedScrollingEnabled(false);

        if (mMovieData != null) {
            populateMovieDetails(mMovieData);
        } else {
            Toast.makeText(this, getString(R.string.movie_data_not_available), Toast.LENGTH_LONG).show();
        }

        loadTrailers(mMovieData.getId());
        loadReviews(mMovieData.getId());
    }

    private void populateMovieDetails(Movie movie) {
        if (notNullOrEmpty(movie.getTitle()) && notNullOrEmpty(movie.getReleaseDate())) {
            String title = movie.getTitle();
            String releaseDate = movie.getReleaseDate().substring(0, 4);
            mBinding.tvMovieTitle.setText(getString(R.string.movie_detail_title, title, releaseDate));
        } else if (notNullOrEmpty(movie.getTitle())) {
            mBinding.tvMovieTitle.setText(movie.getTitle());
        } else {
            mBinding.tvMovieTitle.setText(getString(R.string.unavailable_movie_title));
        }
        if (notNullOrEmpty(movie.getPlot())) {
            mBinding.tvMoviePlot.setText(movie.getPlot());
        } else {
            mBinding.tvMoviePlot.setText(getString(R.string.unavailable_movie_plot));
        }

        mBinding.tvMovieRating.setText(getString(R.string.movie_rating_text, movie.getRating()));

        Picasso.with(this)
                .load(movie.getImage())
                .error(R.drawable.no_image_available)
                .into(mBinding.ivMovieImage);
    }

    /**
     * This method will get movie trailers in the background
     */
    private void loadTrailers(int movieId) {
        new FetchTrailerTask().execute(movieId);
    }

    /**
     * This method will get movie reviews in the background
     */
    private void loadReviews(int movieId) {
        new FetchReviewTask().execute(movieId);
    }

    @Override
    public void onClick(Trailer trailer) {
        String trailerUrl = trailer.getTrailerUrl();
        Uri webpage = Uri.parse(trailerUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private class FetchTrailerTask extends AsyncTask<Integer, Void, ArrayList<Trailer>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Trailer> doInBackground(Integer... movieId) {
            URL trailerSearchUrl = NetworkUtils.buildTrailerURL(movieId[0]);
            try {
                String jsonTrailerResponse = NetworkUtils.getResponseFromHttpUrl(trailerSearchUrl);
                return JsonUtils.parseTrailerJson(jsonTrailerResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Trailer> trailers) {
            if (trailers != null && trailers.size() > 0) {
                showMovieTrailers();
                mMovieTrailers = trailers;
                mTrailerAdapter.setTrailerData(trailers);
            } else {
                hideMovieTrailers();
            }
        }
    }

    private class FetchReviewTask extends AsyncTask<Integer, Void, ArrayList<Review>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Review> doInBackground(Integer... movieId) {
            URL reviewSearchUrl = NetworkUtils.buildReviewURL(movieId[0]);
            try {
                String jsonReviewResponse = NetworkUtils.getResponseFromHttpUrl(reviewSearchUrl);
                return JsonUtils.parseReviewJson(jsonReviewResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Review> reviews) {
            if (reviews != null && reviews.size() > 0) {
                showMovieReviews();
                mMovieReviews = reviews;
                mReviewAdapter.setReviewData(reviews);
            } else {
                hideMovieReviews();
            }
        }
    }

    private void showMovieTrailers() {
        mBinding.tvTitleMovieTrailers.setVisibility(View.VISIBLE);
        mBinding.rvTrailers.setVisibility(View.VISIBLE);
    }

    private void hideMovieTrailers() {
        mBinding.tvTitleMovieTrailers.setVisibility(View.GONE);
        mBinding.rvTrailers.setVisibility(View.GONE);
    }

    private void showMovieReviews() {
        mBinding.tvTitleMovieReviews.setVisibility(View.VISIBLE);
        mBinding.rvReviews.setVisibility(View.VISIBLE);
    }

    private void hideMovieReviews() {
        mBinding.tvTitleMovieReviews.setVisibility(View.GONE);
        mBinding.rvReviews.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_details, menu);

        // Initialize the favourite menu item icon
        MenuItem favouriteItem = menu.findItem(R.id.action_add_to_favourites);
        if (favouriteItem != null) {
            if (mMovieData.getFavourite()) {
                favouriteItem.setIcon(R.drawable.ic_favourite_star_filled);
            } else {
                favouriteItem.setIcon(R.drawable.ic_favourite_star_border);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_to_favourites) {
            if (mMovieData.getFavourite()) {
                mMovieData.setFavourite(false);
                item.setIcon(R.drawable.ic_favourite_star_border);
                // Remove Movie, and the corresponding Trailers and Reviews from the database
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.movieDao().deleteMovie(mMovieData);
                        mDb.trailerDao().deleteTrailersForMovie(mMovieData.getId());
                        mDb.reviewDao().deleteReviewsForMovie(mMovieData.getId());
                    }
                });
            } else {
                mMovieData.setFavourite(true);
                item.setIcon(R.drawable.ic_favourite_star_filled);
                // Insert Movie data, trailers and reviews to the database
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.movieDao().insertMovie(mMovieData);
                        if (mMovieTrailers != null && !mMovieTrailers.isEmpty()) {
                            mDb.trailerDao().insertAllTrailers(mMovieTrailers);
                        }
                        if (mMovieReviews != null && !mMovieReviews.isEmpty()) {
                            mDb.reviewDao().insertAllReviews(mMovieReviews);
                        }
                    }
                });
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /*************************************************************************
     *************************** Utility Functions ***************************
     *************************************************************************/

    private boolean notNullOrEmpty(String text) {
        return ((text != null) && (!text.equals("")));
    }

}
