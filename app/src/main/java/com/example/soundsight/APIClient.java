package com.example.soundsight;

import okhttp3.*;

public class APIClient {
    private static final String BASE_URL = "http://10.0.2.2:5000"; // Localhost for Android emulator
    private static final OkHttpClient client = new OkHttpClient();

    public static void post(String endpoint, String jsonBody, Callback callback) {
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
