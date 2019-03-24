package com.example.gamebacklog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GameViewModel gameViewModel;
    public static final int ADD_GAME_REQUEST = 1;
    public static final int EDIT_GAME_REQUEST = 2;
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

                startActivityForResult(intent, ADD_GAME_REQUEST);
            }

        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final GameAdapter adapter = new GameAdapter();
        recyclerView.setAdapter(adapter);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.getAllGames().observe(this, new Observer<List<Game>>() {

            @Override
            public void onChanged(@Nullable List<Game> games) {
                adapter.setGames(games);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                gameViewModel.delete(adapter.getGameAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, R.string.delete_text, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game) {
                Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
                intent.putExtra(AddGameActivity.EXTRA_ID, game.getId());
                intent.putExtra(AddGameActivity.EXTRA_TITLE, game.getTitle());
                intent.putExtra(AddGameActivity.EXTRA_PLATFORM, game.getPlatform());
                intent.putExtra(AddGameActivity.EXTRA_STATUS, game.getStatus());

                startActivityForResult(intent, EDIT_GAME_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GAME_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddGameActivity.EXTRA_TITLE);
            String platform = data.getStringExtra(AddGameActivity.EXTRA_PLATFORM);
            String statusValue = data.getStringExtra(AddGameActivity.EXTRA_STATUS);

            Game game = new Game(title, platform, statusValue);
            gameViewModel.insert(game);

            Toast.makeText(this, R.string.game_saved, Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_GAME_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddGameActivity.EXTRA_ID, -1);

            String title = data.getStringExtra(AddGameActivity.EXTRA_TITLE);
            String platform = data.getStringExtra(AddGameActivity.EXTRA_PLATFORM);
            String statusValue = data.getStringExtra(AddGameActivity.EXTRA_STATUS);

            Game game = new Game(title, platform, statusValue);
            game.setId(id);
            gameViewModel.update(game);

            Toast.makeText(this, R.string.update_text, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, R.string.game_not_saved, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_games:
                gameViewModel.deleteAllGames();
                Toast.makeText(this, R.string.delete_all_text, Toast.LENGTH_SHORT).show();

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
