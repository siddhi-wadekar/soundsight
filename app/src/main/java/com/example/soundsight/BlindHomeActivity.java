package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class BlindHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blind_home);

        // Set click listener for the translation icon
        ImageView translationImage = findViewById(R.id.translation_blind);
        translationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to BlindDetectionActivity
                Intent intent = new Intent(BlindHomeActivity.this, BlindDetectionActivity.class);
                startActivity(intent);
            }
        });
    }
}
