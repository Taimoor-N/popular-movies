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

    private ImageView mMovieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieTitle = findViewById(R.id.tv_movie_title);
        mMoviePlot = findViewById(R.id.tv_movie_plot);
        mMovieRating = findViewById(R.id.tv_movie_rating);
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
        if (notNullOrEmpty(movie.getTitle()) && notNullOrEmpty(movie.getReleaseDate())) {
            String title = movie.getTitle();
            String releaseDate = movie.getReleaseDate().substring(0, 4);
            mMovieTitle.setText(getString(R.string.movie_detail_title, title, releaseDate));
        } else if (notNullOrEmpty(movie.getTitle())) {
            mMovieTitle.setText(movie.getTitle());
        } else {
            mMovieTitle.setText("N/A");
        }
        if (notNullOrEmpty(movie.getPlot())) {
            mMoviePlot.setText(movie.getPlot());
        } else {
            mMoviePlot.setText("N/A");
        }

        mMovieRating.setText(getString(R.string.movie_rating_text, movie.getRating()));

        Picasso.with(this)
                .load(movie.getImage())
                .error(R.drawable.no_image_available)
                .into(mMovieImage);
    }

    private boolean notNullOrEmpty(String text) {
        return ((text != null) && (!text.equals("")));
    }
}
