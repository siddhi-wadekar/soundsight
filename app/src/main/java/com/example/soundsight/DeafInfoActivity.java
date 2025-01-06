package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DeafInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_deaf_info);

        // Navigate back to DeafHomeActivity using home button
        TextView homeButton = findViewById(R.id.home_button);
        if (homeButton != null) {
            homeButton.setOnClickListener(v -> {
                Intent intent = new Intent(DeafInfoActivity.this, DeafHomeActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
