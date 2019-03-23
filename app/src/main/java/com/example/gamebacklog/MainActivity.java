package com.example.gamebacklog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GameViewModel gameViewModel;
    public static final int ADD_GAME_REQUEST= 1;
    public static final String[] picker = new String[]{"Want to play", "Playing", "Stalled"
            , "Dropped"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddGame = findViewById(R.id.button_add_game);

        buttonAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGameActivity.class);

                startActivityForResult(intent,ADD_GAME_REQUEST);
            }

        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final GameAdapter adapter = new GameAdapter();
        recyclerView.setAdapter(adapter);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.getAllGames().observe(this, new Observer <List<Game>>(){

            @Override
            public void onChanged(@Nullable List<Game> games) {
                adapter.setGames(games);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_GAME_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddGameActivity.EXTRA_TITLE);
            String platform = data.getStringExtra(AddGameActivity.EXTRA_PLATFORM);
            int statusValue = data.getIntExtra(AddGameActivity.EXTRA_STATUS, 0);

            Game game = new Game(title,platform,picker[statusValue]);
            gameViewModel.insert(game);

            Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.note_not_saved, Toast.LENGTH_SHORT).show();

        }
    }
}
