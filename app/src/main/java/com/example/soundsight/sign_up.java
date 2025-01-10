package com.example.soundsight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class sign_up extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText etEmailSignUp, etPasswordSignUp;
    private Button btnSignUpSubmit;
    private TextView tvGoToSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Initialize Firebase manually if not initialized
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        auth = FirebaseAuth.getInstance();

        etEmailSignUp = findViewById(R.id.etEmailSignUp);
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp);
        btnSignUpSubmit = findViewById(R.id.btnSignUpSubmit);
        tvGoToSignIn = findViewById(R.id.tvGoToSignIn);

        btnSignUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailSignUp.getText().toString();
                String password = etPasswordSignUp.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(sign_up.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(sign_up.this, task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(sign_up.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(sign_up.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(sign_up.this, "Sign-Up Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        tvGoToSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(sign_up.this, sign_in.class);
            startActivity(intent);
        });
    }
}
