package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DeafHelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deaf_help);

        // Navigate back to DeafHomeActivity using home button
        TextView homeButton = findViewById(R.id.home_button);
        if (homeButton != null) {
            homeButton.setOnClickListener(v -> {
                Intent intent = new Intent(DeafHelpActivity.this, DeafHomeActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
