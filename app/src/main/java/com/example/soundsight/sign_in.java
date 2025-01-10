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
import com.google.firebase.auth.FirebaseUser;

public class sign_in extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText etEmailSignIn, etPasswordSignIn;
    private Button btnSignInSubmit;
    private TextView tvGoToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // Initialize Firebase manually if not initialized
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        auth = FirebaseAuth.getInstance();

        etEmailSignIn = findViewById(R.id.etEmailSignIn);
        etPasswordSignIn = findViewById(R.id.etPasswordSignIn);
        btnSignInSubmit = findViewById(R.id.btnSignInSubmit);
        tvGoToSignUp = findViewById(R.id.tvGoToSignUp);

        btnSignInSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailSignIn.getText().toString();
                String password = etPasswordSignIn.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(sign_in.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(sign_in.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                if (user != null) {
                                    Intent intent = new Intent(sign_in.this, ImpairmentSelectionActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(sign_in.this, "Sign-In Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        tvGoToSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(sign_in.this, sign_up.class);
            startActivity(intent);
        });
    }
}
