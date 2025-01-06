package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class BlindHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blind_home);

        // Translation button handling
        ImageView translation = findViewById(R.id.translation_blind);
        if (translation != null) {
            translation.setOnClickListener(v -> {
                Intent intent = new Intent(BlindHomeActivity.this, BlindDetectionActivity.class);
                startActivity(intent);
            });
        }
    }
}
