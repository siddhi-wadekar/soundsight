package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.etEmail);
        passwordInput = findViewById(R.id.etPassword);

        Button loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        // Replace with actual authentication logic
        if (email.equals("test@demo.com") && password.equals("password123")) {
            SessionManager sessionManager = new SessionManager(LoginActivity.this);
            sessionManager.setLogin(true, email, sessionManager.getImpairmentType());

            // Navigate based on impairment type
            if ("Deaf".equals(sessionManager.getImpairmentType())) {
                startActivity(new Intent(LoginActivity.this, DeafHomeActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, BlindHomeActivity.class));
            }
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
