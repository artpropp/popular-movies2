package com.artpropp.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM favoriteMovies")
    LiveData<List<FavoriteMovieEntry>> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteMovieEntry favoriteMovieEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(FavoriteMovieEntry favoriteMovieEntry);

    @Delete
    void delete(FavoriteMovieEntry favoriteMovieEntry);

    @Query("DELETE FROM favoriteMovies")
    void clear();

    @Query("SELECT * FROM favoriteMovies WHERE id = :id")
    LiveData<FavoriteMovieEntry> loadById(int id);

}
