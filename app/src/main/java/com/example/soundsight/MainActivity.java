// MainActivity.java
package com.example.soundsight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // User is logged in, check impairment type
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            String impairmentType = sharedPreferences.getString("ImpairmentType", null);

            if (impairmentType != null) {
                if (impairmentType.equals(getString(R.string.auditory_impaired))) {
                    startActivity(new Intent(this, DeafHomeActivity.class));
                } else {
                    startActivity(new Intent(this, BlindHomeActivity.class));
                }
            } else {
                startActivity(new Intent(this, ImpairmentSelectionActivity.class));
            }
        } else {
            startActivity(new Intent(this, sign_in.class));
        }

        finish();
    }
}
