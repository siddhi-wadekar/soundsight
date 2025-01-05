package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class DeafHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_deaf_home);

        // Handle button clicks
        findViewById(R.id.sign_encyclopedia_button).setOnClickListener(v -> {
            // Navigate to DeafInfoActivity
            Intent intent = new Intent(DeafHomeActivity.this, DeafInfoActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.helpline_button).setOnClickListener(v -> {
            // Navigate to DeafHelpActivity
            Intent intent = new Intent(DeafHomeActivity.this, DeafHelpActivity.class);
            startActivity(intent);
        });

        // Navigate to DeafDetectionFragment when ic_translation icon is clicked
        findViewById(R.id.ic_translation).setOnClickListener(v -> {
            Intent intent = new Intent(DeafHomeActivity.this, DeafDetectionFragment.class); // assuming DeafDetectionActivity is an activity that shows the fragment
            startActivity(intent);
        });
    }
}
