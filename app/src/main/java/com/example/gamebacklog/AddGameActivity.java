package com.example.gamebacklog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddGameActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.gamebacklog.EXTRA_TITLE";
    public static final String EXTRA_PLATFORM = "com.example.gamebacklog.EXTRA_PLATFORM";
    public static final String EXTRA_STATUS = "com.example.gamebacklog.EXTRA_STATUS";
    private EditText editTextTitle;
    private EditText editTextPlatform;
    private NumberPicker numberPickerStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextPlatform = findViewById(R.id.edit_text_platform);
        numberPickerStatus = findViewById(R.id.number_picker_status);

        numberPickerStatus.setMinValue(0);
        numberPickerStatus.setMaxValue(3);
        numberPickerStatus.setDisplayedValues(new String[]{"Want to play", "Playing", "Stalled"
                , "Dropped"});

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");


    }

    private void saveGame(){
        String title = editTextTitle.getText().toString();
        String platform = editTextPlatform.getText().toString();
        int statusValue = numberPickerStatus.getValue();

        if(title.trim().isEmpty() || platform.trim().isEmpty()){
            Toast.makeText(this, R.string.make_game, Toast.LENGTH_SHORT).show();
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_PLATFORM, platform);
        data.putExtra(EXTRA_STATUS, statusValue);

        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_game:
                saveGame();

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
