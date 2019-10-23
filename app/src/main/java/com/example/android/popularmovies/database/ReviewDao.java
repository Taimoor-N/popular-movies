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

    @Query("SELECT * FROM review ORDER BY author")
    List<ReviewEntity> loadAllReviews();

    @Insert
    void insertReview(ReviewEntity review);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateReview(ReviewEntity review);

    @Delete
    void deleteReview(ReviewEntity review);

    @Query("SELECT * FROM review WHERE id = :id")
    ReviewEntity loadReviewById(int id);

    @Query("SELECT * FROM review WHERE movieId = :id")
    List<ReviewEntity> loadReviewByMovieId(int id);

}
