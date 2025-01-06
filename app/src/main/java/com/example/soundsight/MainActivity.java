package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAuditory = findViewById(R.id.btnAuditory);
        Button btnBlind = findViewById(R.id.btnBlind);

        // Navigate to DeafHomeActivity
        if (btnAuditory != null) {
            btnAuditory.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, DeafHomeActivity.class);
                startActivity(intent);
            });
        }

        // Navigate to BlindHomeActivity
        if (btnBlind != null) {
            btnBlind.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, BlindHomeActivity.class);
                startActivity(intent);
            });
        }
    }
}
