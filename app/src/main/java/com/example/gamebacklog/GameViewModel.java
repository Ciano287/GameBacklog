package com.example.gamebacklog;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;


public class GameViewModel extends AndroidViewModel {

    GameRepository repository;
    LiveData<List<Game>> allGames;

    public GameViewModel(@NonNull Application application) {
        super(application);

        repository = new GameRepository(application);
        allGames = repository.getAllGames();
    }

    public void insert(Game game){
        repository.insert(game);
    }

    public void update(Game game){
        repository.update(game);
    }

    public void delete(Game game){
        repository.delete(game);
    }

    public void deleteAllGames(){
        repository.deleteAllGames();
    }

    public LiveData<List<Game>> getAllGames() {
        return allGames;
    }
}
