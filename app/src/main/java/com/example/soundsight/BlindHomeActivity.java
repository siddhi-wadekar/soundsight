package com.example.soundsight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

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
                // Clear user session
                SharedPreferences.Editor editor = getSharedPreferences("User  Session", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                // Sign out from Firebase
                FirebaseAuth.getInstance().signOut();

                // Redirect to sign_in
                Intent intent = new Intent(BlindHomeActivity.this, sign_in.class);
                startActivity(intent);
                finish();
            });
        }
    }
}