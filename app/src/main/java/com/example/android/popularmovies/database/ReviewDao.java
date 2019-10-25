package com.example.android.popularmovies.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ReviewDao {

    @Query("SELECT * FROM Review ORDER BY author")
    List<Review> loadAllReviews();

    @Insert
    void insertReview(Review review);

    @Insert
    void insertAllReviews(List<Review> review);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateReview(Review review);

    @Delete
    void deleteReview(Review review);

    @Query("DELETE FROM review WHERE movieId = :movieId")
    void deleteReviewsForMovie(int movieId);

    @Query("SELECT * FROM Review WHERE id = :id")
    Review loadReviewById(int id);

    @Query("SELECT * FROM Review WHERE movieId = :id")
    List<Review> loadReviewByMovieId(int id);

}
