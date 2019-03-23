package com.example.gamebacklog;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "game_table")
public class Game {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String platform;
    private String status;

    public Game(String title, String platform, String status) {
        this.title = title;
        this.platform = platform;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPlatform() {
        return platform;
    }

    public String getStatus(){
        return status;
    }



}

