package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.etEmail);
        passwordInput = findViewById(R.id.etPassword);
        sessionManager = new SessionManager(this);

        Button loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(v -> handleLogin());

        TextView signUpLink = findViewById(R.id.SignUp);
        signUpLink.setOnClickListener(v -> navigateToSignUp());
    }

    private void handleLogin() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check credentials
        if (email.equals(sessionManager.getEmail()) && password.equals("password123")) { // Adjust password check
            String impairmentType = sessionManager.getImpairmentType();
            if ("Deaf".equals(impairmentType)) {
                navigateToDeafHome();
            } else {
                navigateToBlindHome();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    private void navigateToDeafHome() {
        startActivity(new Intent(this, DeafHomeActivity.class));
        finish();
    }

    private void navigateToBlindHome() {
        startActivity(new Intent(this, BlindHomeActivity.class));
        finish();
    }
}
