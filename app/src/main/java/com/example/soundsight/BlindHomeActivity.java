package com.example.soundsight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;


public class BlindHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind_home);

        // Translation button handling
        ImageView translation = findViewById(R.id.translation_blind);
        if (translation != null) {
            translation.setOnClickListener(v -> {
                Intent intent = new Intent(BlindHomeActivity.this, BlindDetectionActivity.class);
                startActivity(intent);
            });
        }

        // Log out button handling
        Button logoutButton = findViewById(R.id.btnLogout1);
        if (logoutButton != null) {
            logoutButton.setOnClickListener(v -> {
                SharedPreferences.Editor editor = getSharedPreferences("UserSession", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(BlindHomeActivity.this, sign_in.class);
                startActivity(intent);
                finish();
            });
        }

    }
}
