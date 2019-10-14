package com.example.android.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.popularmovies.databinding.ActivityDetailBinding;
import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();
        Object movieObj = intent.getSerializableExtra(MainActivity.INTENT_MOVIE_DATA);
        Movie mMovieData = (Movie) movieObj;

        if (mMovieData != null) {
            populateUI(mMovieData);
        } else {
            Toast.makeText(this, getString(R.string.movie_data_not_available), Toast.LENGTH_LONG).show();
        }

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
}
