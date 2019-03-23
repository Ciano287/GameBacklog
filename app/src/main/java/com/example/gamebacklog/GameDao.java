package com.example.gamebacklog;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GameDao {

    @Insert
    void insert(Game game);

    @Update
    void update(Game game);

    @Delete
    void delete(Game game);

    @Query("DELETE FROM game_table")
    void deleteAllGames();

    @Query("SELECT * FROM game_table ORDER BY id DESC")
    LiveData<List<Game>> getAllGames();
}
