package com.example.soundsight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private Button btnSignUpSubmit;
    private TextView tvSignInOption;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignUpSubmit = findViewById(R.id.btnSignUpSubmit);
        tvSignInOption = findViewById(R.id.already_have_acc);

        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        btnSignUpSubmit.setOnClickListener(v -> signUp());
        tvSignInOption.setOnClickListener(v -> navigateToSignIn());
    }

    private void signUp() {
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("username", username);
        json.addProperty("email", email);
        json.addProperty("password", password);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json.toString(), okhttp3.MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000/signup")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show());
                Log.e("SignUp", "Error: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        String message = jsonResponse.getString("message");

                        runOnUiThread(() -> {
                            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, Sign_in.class));
                            finish();
                        });
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Error parsing server response", Toast.LENGTH_SHORT).show());
                        Log.e("SignUp", "Response Parsing Error: " + e.getMessage());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Sign-up failed!", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void navigateToSignIn() {
        startActivity(new Intent(SignUpActivity.this, Sign_in.class));
    }
}