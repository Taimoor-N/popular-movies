package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private String[] mMovieImages;
    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(String movieImageUrl);
    }

    /**
     * Constructor for MovieAdapter.
     * @param movieImages The urls for the movie posters.
     * @param clickHandler The onClick handler for MovieAdapter. This is called when an
     *                     item is clicked.
     */
    public MovieAdapter(String[] movieImages, MovieAdapterOnClickHandler clickHandler) {
        mMovieImages = movieImages;
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a movies list item.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public final ImageView mMovieImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieImageView = view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String movieImageUrl = mMovieImages[adapterPosition];
            mClickHandler.onClick(movieImageUrl);
        }
    }

    /**
     * This function is called when a new ViewHolder gets created.
     *
     * @param viewGroup The ViewGroup that contains each of the ViewHolders.
     * @param viewType Specifies the type of item in the RecyclerView.
     * @return A new MovieAdapterViewHolder that hold the view of each movie list item.
     */
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movies_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    /**
     * This function is called to display the movie poster at the specified position.
     * @param movieAdapterViewHolder The ViewHolder that should be updated to represent the
     *                               contents of movie list item at the given position in data set.
     * @param position The position of the movie list item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String imageForThisItem = mMovieImages[position];
        Context context = movieAdapterViewHolder.mMovieImageView.getContext();
        Picasso.with(context)
                .load(imageForThisItem)
                .error(R.drawable.no_image_available)
                .into(movieAdapterViewHolder.mMovieImageView);
    }

    /**
     * This function returns the total number of movie posters to display.
     * @return The number of movie list items available.
     */
    @Override
    public int getItemCount() {
        if (mMovieImages == null) {
            return 0;
        }
        return mMovieImages.length;
    }
}
