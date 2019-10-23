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

    @Query("SELECT * FROM Trailer ORDER BY trailerName")
    List<Trailer> loadAllTrailers();

    @Insert
    void insertTrailer(Trailer trailer);

    @Insert
    void insertAllTrailers(List<Trailer> trailer);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTrailer(Trailer trailer);

    @Delete
    void deleteTrailer(Trailer trailer);

    @Query("DELETE FROM trailer WHERE movieId = :movieId")
    void deleteTrailersForMovie(int movieId);

    @Query("SELECT * FROM Trailer WHERE id = :id")
    Trailer loadTrailerById(int id);

    @Query("SELECT * FROM Trailer WHERE movieId = :id")
    List<Trailer> loadTrailersByMovieId(int id);

}
