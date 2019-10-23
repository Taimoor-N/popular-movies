package com.example.android.popularmovies.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TrailerDao {

    @Query("SELECT * FROM trailer ORDER BY trailerName")
    List<TrailerEntity> loadAllTrailers();

    @Insert
    void insertTrailer(TrailerEntity trailer);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTrailer(TrailerEntity trailer);

    @Delete
    void deleteTrailer(TrailerEntity trailer);

    @Query("SELECT * FROM trailer WHERE id = :id")
    TrailerEntity loadTrailerById(int id);

    @Query("SELECT * FROM trailer WHERE movieId = :id")
    List<TrailerEntity> loadTrailerByMovieId(int id);

}
