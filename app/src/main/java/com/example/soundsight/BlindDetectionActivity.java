package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BlindDetectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blind_detection);

        // Home button handling
        TextView homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to BlindHomeActivity
                Intent intent = new Intent(BlindDetectionActivity.this, BlindHomeActivity.class);
                startActivity(intent);
            }
        });

        // Stop button handling
        TextView stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to BlindHomeActivity
                Intent intent = new Intent(BlindDetectionActivity.this, BlindHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
