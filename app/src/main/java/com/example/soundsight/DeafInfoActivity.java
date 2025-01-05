package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class DeafInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_deaf_info);

        // Handle home button click
        findViewById(R.id.home_button).setOnClickListener(v -> {
            // Navigate back to DeafHomeActivity
            Intent intent = new Intent(DeafInfoActivity.this, DeafHomeActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        });
    }
}
