package com.example.soundsight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser ;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser  currentUser  = FirebaseAuth.getInstance().getCurrentUser ();

        // Check if the user is logged in
        if (currentUser  == null) {
            // User is not logged in, redirect to sign_in
            startActivity(new Intent(this, sign_in.class));
        } else {
            // User is logged in, check impairment type
            SharedPreferences sharedPreferences = getSharedPreferences("User Session", MODE_PRIVATE);
            String impairmentType = sharedPreferences.getString("ImpairmentType", null);

            if (impairmentType == null) {
                // Impairment type not selected, redirect to ImpairmentSelectionActivity
                startActivity(new Intent(this, ImpairmentSelectionActivity.class));
            } else {
                // Redirect to the appropriate home page based on impairment type
                if (impairmentType.equals(getString(R.string.auditory_impaired))) {
                    startActivity(new Intent(this, DeafHomeActivity.class));
                } else {
                    startActivity(new Intent(this, BlindHomeActivity.class));
                }
            }
        }

        finish(); // Close MainActivity
    }
}