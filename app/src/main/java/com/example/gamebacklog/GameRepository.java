package com.example.gamebacklog;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class GameRepository {

    private GameDao gamedao;
    private LiveData<List<Game>> allGames;

    public GameRepository(Application application) {
        GameDatabase database = GameDatabase.getInstance(application);
        gamedao = database.gameDao();
        allGames = gamedao.getAllGames();
    }

    public void insert(Game game) {
        new InsertGameAsyncTask(gamedao).execute(game);
    }

    public void update(Game game) {
        new UpdateGameAsyncTask(gamedao).execute(game);
    }

    public void delete(Game game) {
        new DeleteGameAsyncTask(gamedao).execute(game);
    }

    public void deleteAllGames() {
        new DeleteAllGamesAsyncTask(gamedao).execute();
    }

    public LiveData<List<Game>> getAllGames() {
        return allGames;
    }

    private static class InsertGameAsyncTask extends AsyncTask<Game, Void, Void> {
        private GameDao gameDao;

        private InsertGameAsyncTask(GameDao gameDao) {
            this.gameDao = gameDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            gameDao.insert(games[0]);
            return null;
        }
    }

    private static class UpdateGameAsyncTask extends AsyncTask<Game, Void, Void> {
        private GameDao gameDao;

        private UpdateGameAsyncTask(GameDao gameDao) {
            this.gameDao = gameDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            gameDao.update(games[0]);
            return null;
        }
    }

    private static class DeleteGameAsyncTask extends AsyncTask<Game, Void, Void> {
        private GameDao gameDao;

        private DeleteGameAsyncTask(GameDao gameDao) {
            this.gameDao = gameDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            gameDao.delete(games[0]);
            return null;
        }
    }

    private static class DeleteAllGamesAsyncTask extends AsyncTask<Void, Void, Void> {
        private GameDao gameDao;

        private DeleteAllGamesAsyncTask(GameDao gameDao) {
            this.gameDao = gameDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            gameDao.deleteAllGames();
            return null;
        }
    }


}
