package com.example.soundsight;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnSignIn = findViewById(R.id.btnSignIn);

        btnSignUp.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SignUpActivity.class)));
        btnSignIn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Sign_in.class)));
    }
}
