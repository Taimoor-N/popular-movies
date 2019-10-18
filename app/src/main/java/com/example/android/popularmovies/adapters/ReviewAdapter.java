package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private ArrayList<Review> mReviews;

    private final Context mContext;

    /**
     * Constructor for ReviewAdapter
     * @param context
     */
    public ReviewAdapter(Context context) {
        mContext = context;
    }

    /**
     * Cache of the children views for review list item.
     */
    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mReviewAuthorTextView;
        public final TextView mReviewContentTextView;

        public ReviewAdapterViewHolder(View view) {
            super(view);
            mReviewAuthorTextView = view.findViewById(R.id.tv_review_author);
            mReviewContentTextView = view.findViewById(R.id.tv_review_content);
        }

    }

    /**
     * This function is called when a new ViewHolder gets created.
     *
     * @param viewGroup The ViewGroup that contains each of the ViewHolders.
     * @param viewType Specifies the type of item in the RecyclerView.
     * @return A new ReviewAdapterViewHolder that hold the view of each review list item.
     */
    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutIdForListItem = R.layout.reviews_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ReviewAdapterViewHolder(view);
    }

    /**
     * This function is called to display the review at the specified position.
     * @param reviewAdapterViewHolder The ViewHolder that should be updated to represent the
     *                               contents of review list item at the given position in data set.
     * @param position The position of the review list item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder reviewAdapterViewHolder, int position) {
        String reviewAuthor = mReviews.get(position).getAuthor();
        String reviewContent = mReviews.get(position).getContent();
        reviewAdapterViewHolder.mReviewAuthorTextView.setText(reviewAuthor);
        reviewAdapterViewHolder.mReviewContentTextView.setText(reviewContent);
    }

    /**
     * This function returns the total number of reviews to display.
     * @return The number of review list items available.
     */
    @Override
    public int getItemCount() {
        if (mReviews == null) {
            return 0;
        }
        return mReviews.size();
    }

    public void setReviewData(ArrayList<Review> reviewData) {
        mReviews = reviewData;
        notifyDataSetChanged();
    }

}
