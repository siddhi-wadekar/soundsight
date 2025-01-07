package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private RadioGroup impairmentRadioGroup;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailInput = findViewById(R.id.etEmail);
        passwordInput = findViewById(R.id.etPassword);
        impairmentRadioGroup = findViewById(R.id.rgImpairmentType);
        sessionManager = new SessionManager(this);

        Button signUpButton = findViewById(R.id.btnSignUp);
        signUpButton.setOnClickListener(v -> handleSignUp());

        TextView alreadyHaveAccount = findViewById(R.id.already_have_acc);
        alreadyHaveAccount.setOnClickListener(v -> navigateToLogin());
    }

    private void handleSignUp() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        int selectedId = impairmentRadioGroup.getCheckedRadioButtonId();

        String impairmentType = (selectedId == R.id.rbAuditoryImpaired) ? "Deaf" : "Blind";

        if (email.isEmpty() || password.isEmpty() || selectedId == -1) {
            Toast.makeText(SignUpActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save user details to session
        sessionManager.setLogin(false, email, impairmentType);

        Toast.makeText(SignUpActivity.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();
        navigateToLogin();
    }

    private void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
