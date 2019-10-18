package com.example.android.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.popularmovies.adapters.ReviewAdapter;
import com.example.android.popularmovies.adapters.TrailerAdapter;
import com.example.android.popularmovies.databinding.ActivityDetailBinding;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding mBinding;

    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;

    private RecyclerView mTrailerRecyclerView;
    private RecyclerView mReviewRecyclerView;

    private Movie mMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Object movieObj = intent.getSerializableExtra(MainActivity.INTENT_MOVIE_DATA);
        mMovieData = (Movie) movieObj;

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        mTrailerRecyclerView = findViewById(R.id.rv_trailers);
        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTrailerAdapter = new TrailerAdapter(this);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);
        mTrailerRecyclerView.setNestedScrollingEnabled(false);

        mReviewRecyclerView = findViewById(R.id.rv_reviews);
        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewAdapter = new ReviewAdapter(this);
        mReviewRecyclerView.setAdapter(mReviewAdapter);
        mReviewRecyclerView.setNestedScrollingEnabled(false);

        if (mMovieData != null) {
            populateUI(mMovieData);
        } else {
            Toast.makeText(this, getString(R.string.movie_data_not_available), Toast.LENGTH_LONG).show();
        }

        loadTraileraData(mMovieData.getId());
        mReviewAdapter.setReviewData(getFakeReviewData());
    }

    /**
     * This method will get trailer data in the background
     */
    private void loadTraileraData(int movieId) {
        new FetchTrailerTask().execute(movieId);
    }

    private ArrayList<Review> getFakeReviewData() {
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(new Review("John Cena", "A pretty odd choice too undercut every scare in the movie, but I was less disappointed with Chapter Two than everyone else seems to be."));
        reviews.add(new Review("Johnny Gaddar", "The cards are drawn to the screen with a default elevation, which causes the system to draw a shadow underneath them. You can provide a custom elevation for a card with the card_view:cardElevation attribute. This will draw a more pronounced shadow with a larger elevation, and a lower elevation will result in a lighter shadow."));
        reviews.add(new Review("Bhutto", "Apps often need to display data in similarly styled containers. These containers are often used in lists to hold each item's information. The system provides the CardView API as an easy way for you show information inside cards that have a consistent look across the platform."));
        reviews.add(new Review("Ganji", "Android Dev Summit, October 23-24: two days of technical content, directly from the Android team. Sign-up for livestream updates."));
        reviews.add(new Review("Ricky Ponting", "Cricket is just great! What a great game. Great way to spend your time. Best sport ever. I think I'm the only one in the world with that belief."));
        return reviews;
    }

    private void populateUI(Movie movie) {
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

    private boolean notNullOrEmpty(String text) {
        return ((text != null) && (!text.equals("")));
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
            if (trailers != null) {
                mTrailerAdapter.setTrailerData(trailers);
            }
        }
    }
}
