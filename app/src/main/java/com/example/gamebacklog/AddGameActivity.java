package com.example.gamebacklog;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddGameActivity extends AppCompatActivity {

    final String[] items = new String[]{"Want to play", "Playing", "Stalled"
            , "Dropped"};
    public static final String EXTRA_TITLE = "com.example.gamebacklog.EXTRA_TITLE";
    public static final String EXTRA_PLATFORM = "com.example.gamebacklog.EXTRA_PLATFORM";
    public static final String EXTRA_STATUS = "com.example.gamebacklog.EXTRA_STATUS";
    public static final String EXTRA_ID = "com.example.gamebacklog.EXTRA_ID";
    private EditText editTextTitle;
    private EditText editTextPlatform;
    private Spinner spinnerStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextPlatform = findViewById(R.id.edit_text_platform);
        spinnerStatus = findViewById(R.id.spinner_status);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerStatus.setAdapter(adapter);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextPlatform.setText(intent.getStringExtra(EXTRA_PLATFORM));




        } else {
            setTitle("Add Note");

        }

        FloatingActionButton buttonSaveGame = findViewById(R.id.button_save_game);

        buttonSaveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGame();
            }

        });


    }

    private void saveGame() {
        String title = editTextTitle.getText().toString();
        String platform = editTextPlatform.getText().toString();
        String statusValue = spinnerStatus.getSelectedItem().toString();

        if (title.trim().isEmpty() || platform.trim().isEmpty()) {
            Toast.makeText(this, R.string.make_game, Toast.LENGTH_SHORT).show();
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_PLATFORM, platform);
        data.putExtra(EXTRA_STATUS, statusValue);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }


}
