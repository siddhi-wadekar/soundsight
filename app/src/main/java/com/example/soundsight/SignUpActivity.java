package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private RadioGroup impairmentRadioGroup;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        emailInput = findViewById(R.id.etEmail);
        passwordInput = findViewById(R.id.etPassword);
        impairmentRadioGroup = findViewById(R.id.rgImpairmentType);

        Button signUpButton = findViewById(R.id.btnSignUp);
        signUpButton.setOnClickListener(v -> handleSignUp());
    }

    private void handleSignUp() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        int selectedId = impairmentRadioGroup.getCheckedRadioButtonId();

        String impairmentType = selectedId == R.id.rbAuditoryImpaired ? "Deaf" : "Blind";

        if (email.isEmpty() || password.isEmpty() || selectedId == -1) {
            Toast.makeText(SignUpActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save data to Firebase (optional)
        String userId = databaseReference.push().getKey();
        if (userId != null) {
            databaseReference.child(userId).setValue(new User(email, impairmentType));
        }

        // Save to SessionManager
        SessionManager sessionManager = new SessionManager(SignUpActivity.this); // Use proper context here
        sessionManager.setLogin(false, email, impairmentType);

        Toast.makeText(SignUpActivity.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class)); // Use proper context here
        finish();
    }
}
