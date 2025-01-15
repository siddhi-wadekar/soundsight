package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DeafDetectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deaf_detection);

        // Navigate to DeafHomeActivity using home button
        Button homeButton = findViewById(R.id.home_button);
        if (homeButton != null) {
            homeButton.setOnClickListener(v -> {
                Intent intent = new Intent(DeafDetectionActivity.this, DeafHomeActivity.class);
                startActivity(intent);
                finish();
            });
        }

        // Navigate to DeafHomeActivity using stop button
        Button stopButton = findViewById(R.id.stop_button);
        if (stopButton != null) {
            stopButton.setOnClickListener(v -> {
                Intent intent = new Intent(DeafDetectionActivity.this, DeafHomeActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
