package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class DeafHelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_deaf_help);

        // Handle home button click
        findViewById(R.id.home_button).setOnClickListener(v -> {
            // Navigate back to DeafHomeActivity using Intent
            Intent intent = new Intent(DeafHelpActivity.this, DeafHomeActivity.class);
            startActivity(intent);
            finish(); // Optionally finish this activity to remove it from the back stack
        });
    }
}
