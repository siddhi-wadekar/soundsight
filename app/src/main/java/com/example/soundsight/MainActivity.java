package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signin(View view) {
        Intent intent = new Intent(MainActivity.this, sign_in.class);
        startActivity(intent);
    }

    public void signup(View view) {
        Intent intent = new Intent(MainActivity.this, sign_up.class);
        startActivity(intent);
    }
}

