package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.signUpButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        });

        findViewById(R.id.loginButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
    }
}
