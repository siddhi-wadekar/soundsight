package com.example.soundsight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ImpairmentSelectionActivity extends AppCompatActivity {

    private RadioGroup rgImpairmentSelection;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impairment_selection);

        rgImpairmentSelection = findViewById(R.id.rgImpairmentSelection);
        Button btnNext = findViewById(R.id.btnNext);

        // Initialize SharedPreferences to save session
        sharedPreferences = getSharedPreferences("User Session", MODE_PRIVATE);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rgImpairmentSelection.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(ImpairmentSelectionActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedButton = findViewById(selectedId);
                    String impairmentType = selectedButton.getText().toString();

                    // Save selected option in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("ImpairmentType", impairmentType);
                    editor.apply();

                    // Navigate to respective home page
                    if (impairmentType.equals(getString(R.string.auditory_impaired))) {
                        Intent intent = new Intent(ImpairmentSelectionActivity.this, DeafHomeActivity.class);
                        startActivity(intent);
                    } else if (impairmentType.equals(getString(R.string.visually_impaired))) {
                        Intent intent = new Intent(ImpairmentSelectionActivity.this, BlindHomeActivity.class);
                        startActivity(intent);
                    }

                    finish(); // Close current activity
                }
            }
        });
    }
}