package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.popularmovies.model.Movie;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    public static final String INTENT_MOVIE_DATA = "INTENT_MOVIE_DATA";

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Movie[] sampleMovies =
                 {new Movie("Chappie", "https://picsum.photos/id/210/768/1366", "Plot Placeholder", 8.0, "2019-05-25", 120),
                  new Movie("Morgano", "https://picsum.photos/id/220/768/1366", "Plot Placeholder", 8.4, "2019-05-25", 113),
                  new Movie("Dumboro", "https://picsum.photos/id/230/768/1366", "Plot Placeholder", 5.5, "2019-05-25", 100),
                  new Movie("Doritto", "https://picsum.photos/id/240/768/1366", "Plot Placeholder", 7.0, "2019-05-25", 155),
                  new Movie("Fudgeos", "https://picsum.photos/id/250/768/1366", "Plot Placeholder", 9.7, "2019-05-25", 140),
                  new Movie("Ruffles", "https://picsum.photos/id/260/768/1366", "Plot Placeholder", 4.6, "2019-05-25", 125),
                  new Movie("Chainsy", "https://picsum.photos/id/270/768/1366", "Plot Placeholder", 6.7, "2019-05-25", 130),
                  new Movie("Hualbio", "https://picsum.photos/id/280/768/1366", "Plot Placeholder", 9.0, "2019-05-25", 155),
                  new Movie("Mouseto", "https://picsum.photos/id/290/768/1366", "Plot Placeholder", 3.9, "2019-05-25", 165),
                  new Movie("Jalapen", "https://picsum.photos/id/300/768/1366", "Plot Placeholder", 9.8, "2019-05-25", 180)};

        mRecyclerView = findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(new MovieAdapter(sampleMovies, this));
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
}
