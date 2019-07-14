package com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] sampleImages =
                  {"https://picsum.photos/id/10/768/1366",
                   "https://picsum.photos/id/20/768/1366",
                   "https://picsum.photos/id/30/768/1366",
                   "https://picsum.photos/id/40/768/1366",
                   "https://picsum.photos/id/50/768/1366",
                   "https://picsum.photos/id/60/768/1366",
                   "https://picsum.photos/id/70/768/1366",
                   "https://picsum.photos/id/80/768/1366",
                   "https://picsum.photos/id/90/768/1366",
                   "https://picsum.photos/id/100/768/1366",
                   "https://picsum.photos/id/110/768/1366",
                   "https://picsum.photos/id/120/768/1366",
                   "https://picsum.photos/id/130/768/1366",
                   "https://picsum.photos/id/140/768/1366",
                   "https://picsum.photos/id/150/768/1366",
                   "https://picsum.photos/id/160/768/1366",
                   "https://picsum.photos/id/170/768/1366",
                   "https://picsum.photos/id/180/768/1366",
                   "https://picsum.photos/id/190/768/1366",
                   "https://picsum.photos/id/200/768/1366"};

        mRecyclerView = findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(new MovieAdapter(sampleImages, this));
    }

    /**
     * This method handles RecyclerView item clicks.
     * @param movieImageUrl URL of the image clicked.
     */
    @Override
    public void onClick(String movieImageUrl) {
        Toast.makeText(this, movieImageUrl, Toast.LENGTH_LONG).show();
    }
}
