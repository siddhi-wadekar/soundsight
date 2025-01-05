package com.example.soundsight;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    // Declare buttons globally so they can be accessed in any method
    private Button btnAuditory;
    private Button btnBlind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Buttons
        btnAuditory = findViewById(R.id.btnAuditory); // Assuming you have a button for auditory users
        btnBlind = findViewById(R.id.btnBlind); // Assuming you have a button for blind users

        // Auditory User Click (Opens FragmentDeafHome)
        btnAuditory.setOnClickListener(v -> openAuditoryFragment());

        // Blind User Click (Opens FragmentBlindHome)
        btnBlind.setOnClickListener(v -> openBlindFragment());
    }

    private void openAuditoryFragment() {
        // Hide buttons once clicked
        btnAuditory.setVisibility(Button.GONE);
        btnBlind.setVisibility(Button.GONE);

        // Switch to the Auditory Fragment (FragmentDeafHome)
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, new FragmentDeafHome()); // Replace with your FragmentDeafHome
        transaction.addToBackStack(null); // Optional: Add transaction to back stack
        transaction.commit();
    }

    private void openBlindFragment() {
        // Hide buttons once clicked
        btnAuditory.setVisibility(Button.GONE);
        btnBlind.setVisibility(Button.GONE);

        // Switch to the Blind Fragment (FragmentBlindHome)
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, new FragmentBlindHome()); // Replace with your FragmentBlindHome
        transaction.addToBackStack(null); // Optional: Add transaction to back stack
        transaction.commit();
    }
}
