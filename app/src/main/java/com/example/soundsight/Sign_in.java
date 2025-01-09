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

public class Sign_in extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnSignInSubmit;
    private TextView tvSignUpLink;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignInSubmit = findViewById(R.id.btnSignInSubmit);
        tvSignUpLink = findViewById(R.id.SignUp);

        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // Check if user is already logged in
        if (sharedPreferences.contains("auth_token")) {
            navigateToHome();
        }

        btnSignInSubmit.setOnClickListener(v -> signIn());
        tvSignUpLink.setOnClickListener(v -> navigateToSignUp());
    }

    private void signIn() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("password", password);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json.toString(), okhttp3.MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000/signin")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(Sign_in.this, "Failed to connect to server", Toast.LENGTH_SHORT).show());
                Log.e("SignIn", "Error: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        String token = jsonResponse.getString("token");

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("auth_token", token);
                        editor.apply();

                        runOnUiThread(() -> {
                            Toast.makeText(Sign_in.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            navigateToHome();
                        });
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(Sign_in.this, "Error parsing server response", Toast.LENGTH_SHORT).show());
                        Log.e("SignIn", "Response Parsing Error: " + e.getMessage());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(Sign_in.this, "Incorrect email or password", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void navigateToHome() {
        startActivity(new Intent(Sign_in.this, ImpairmentSelectionActivity.class));
        finish();
    }

    private void navigateToSignUp() {
        startActivity(new Intent(Sign_in.this, SignUpActivity.class));
    }
}