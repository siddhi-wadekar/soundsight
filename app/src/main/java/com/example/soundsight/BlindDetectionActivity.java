package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BlindDetectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blind_detection);

        // Home button handling
        TextView homeButton = findViewById(R.id.home_button);
        if (homeButton != null) {
            homeButton.setOnClickListener(v -> {
                Intent intent = new Intent(BlindDetectionActivity.this, BlindHomeActivity.class);
                startActivity(intent);
                finish(); // Finish current activity
            });
        }

        // Stop button handling
        TextView stopButton = findViewById(R.id.stop_button);
        if (stopButton != null) {
            stopButton.setOnClickListener(v -> {
                Intent intent = new Intent(BlindDetectionActivity.this, BlindHomeActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
