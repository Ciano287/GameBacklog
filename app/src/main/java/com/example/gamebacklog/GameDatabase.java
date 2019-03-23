package com.example.gamebacklog;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = Game.class, version = 1)
public abstract class GameDatabase extends RoomDatabase {
    private static GameDatabase instance;

    public abstract GameDao gameDao();

    public static synchronized GameDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), GameDatabase.class,
                    "game_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private GameDao gameDao;

        private PopulateDbAsyncTask(GameDatabase db) {
            gameDao = db.gameDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            gameDao.insert(new Game("title 1", "platform 1", "status 1"));
            gameDao.insert(new Game("title 2", "platform 2", "status 2"));
            gameDao.insert(new Game("title 3", "platform 3", "status 3"));
            return null;
        }
    }

}
