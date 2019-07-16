package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView mMovieTitle;
    private TextView mMoviePlot;
    private TextView mMovieRating;
    private TextView mMovieReleaseDate;
    private TextView mMovieDuration;

    private ImageView mMovieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieTitle = findViewById(R.id.tv_movie_title);
        mMoviePlot = findViewById(R.id.tv_movie_plot);
        mMovieRating = findViewById(R.id.tv_movie_rating);
        mMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        mMovieDuration = findViewById(R.id.tv_movie_duration);
        mMovieImage = findViewById(R.id.iv_movie_image);

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
        if (notNullOrEmpty(movie.getTitle())) {
            mMovieTitle.setText(movie.getTitle());
        } else {
            mMovieTitle.setText("N/A");
        }
        if (notNullOrEmpty(movie.getPlot())) {
            mMoviePlot.setText(movie.getPlot());
        } else {
            mMovieTitle.setText("N/A");
        }
        if (notNullOrEmpty(movie.getReleaseDate())) {
            mMovieReleaseDate.setText(movie.getReleaseDate().substring(0, 4));
        } else {
            mMovieTitle.setText("N/A");
        }

        mMovieRating.setText(getString(R.string.movie_rating_text, movie.getRating()));
        mMovieDuration.setText(getString(R.string.movie_duration_in_minutes, movie.getDuration()));

        Picasso.with(this)
                .load(movie.getImage())
                .error(R.drawable.no_image_available)
                .into(mMovieImage);
    }

    private boolean notNullOrEmpty(String text) {
        return ((text != null) && (!text.equals("")));
    }
}
