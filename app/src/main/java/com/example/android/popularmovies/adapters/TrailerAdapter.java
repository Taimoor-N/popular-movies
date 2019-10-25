package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.database.Trailer;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private ArrayList<Trailer> mTrailers;
    private final TrailerAdapterOnClickHandler mClickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    private final Context mContext;

    /**
     * Constructor for TrailerAdapter
     * @param context
     */
    public TrailerAdapter(Context context, TrailerAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for trailer list item.
     */
    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mTrailerTextView;

        public TrailerAdapterViewHolder(View view) {
            super(view);
            mTrailerTextView = view.findViewById(R.id.tv_trailer_name);
            view.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Trailer trailer = mTrailers.get(adapterPosition);
            mClickHandler.onClick(trailer);
        }
    }

    /**
     * This function is called when a new ViewHolder gets created.
     *
     * @param viewGroup The ViewGroup that contains each of the ViewHolders.
     * @param viewType Specifies the type of item in the RecyclerView.
     * @return A new TrailerAdapterViewHolder that hold the view of each trailer list item.
     */
    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutIdForListItem = R.layout.trailers_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new TrailerAdapterViewHolder(view);
    }

    /**
     * This function is called to display the trailer at the specified position.
     * @param trailerAdapterViewHolder The ViewHolder that should be updated to represent the
     *                               contents of trailer list item at the given position in data set.
     * @param position The position of the trailer list item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder trailerAdapterViewHolder, int position) {
        String trailerName = mTrailers.get(position).getTrailerName();
        trailerAdapterViewHolder.mTrailerTextView.setText(trailerName);
    }

    /**
     * This function returns the total number of trailers to display.
     * @return The number of trailer list items available.
     */
    @Override
    public int getItemCount() {
        if (mTrailers == null) {
            return 0;
        }
        return mTrailers.size();
    }

    public void setTrailerData(ArrayList<Trailer> trailerData) {
        mTrailers = trailerData;
        notifyDataSetChanged();
    }

}
