package com.example.soundsight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class DeafHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_deaf_home);

        // Button to navigate to DeafDetectionActivity
        ImageView icTranslation = findViewById(R.id.ic_translation);
        if (icTranslation != null) {
            icTranslation.setOnClickListener(v -> {
                Intent intent = new Intent(DeafHomeActivity.this, DeafDetectionActivity.class);
                startActivity(intent);
            });
        }

        // Button to navigate to DeafHelpActivity
        ImageView helplineButton = findViewById(R.id.helpline_button);
        if (helplineButton != null) {
            helplineButton.setOnClickListener(v -> {
                Intent intent = new Intent(DeafHomeActivity.this, DeafHelpActivity.class);
                startActivity(intent);
            });
        }

        // Button to navigate to DeafInfoActivity
        ImageView signEncyclopediaButton = findViewById(R.id.sign_encyclopedia_button);
        if (signEncyclopediaButton != null) {
            signEncyclopediaButton.setOnClickListener(v -> {
                Intent intent = new Intent(DeafHomeActivity.this, DeafInfoActivity.class);
                startActivity(intent);
            });
        }

        // Logout button functionality
        Button logoutButton = findViewById(R.id.btnLogout2);
        if (logoutButton != null) {
            logoutButton.setOnClickListener(v -> {
                SharedPreferences.Editor editor = getSharedPreferences("UserSession", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(DeafHomeActivity.this, sign_in.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
