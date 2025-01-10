package com.example.soundsight;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.common.util.concurrent.ListenableFuture;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.DataType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class BlindDetectionActivity extends AppCompatActivity implements OnInitListener {

    private Interpreter tfliteInterpreter;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private TextToSpeech textToSpeech;
    private boolean isSpeaking = false; // Flag to check if TTS is speaking
    private boolean isAnalyzing = false; // Flag to check if the image is being analyzed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blind_detection);

        // Initialize Text-to-Speech
        textToSpeech = new TextToSpeech(this, this);

        // Check camera permissions
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            setupCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }

        // Home button handling
        findViewById(R.id.home_button).setOnClickListener(v -> navigateToHome());

        // Stop button handling
        findViewById(R.id.stop_button).setOnClickListener(v -> navigateToHome());

        // Load the TFLite model
        try {
            tfliteInterpreter = new Interpreter(FileUtil.loadMappedFile(this, "ssd_mobilenet_v1_1_metadata_1.tflite"));
        } catch (IOException e) {
            Toast.makeText(this, "Error loading model", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                // Set up Preview
                PreviewView previewView = findViewById(R.id.view_finder);
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                // Set up Image Analysis
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().build();
                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), imageProxy -> {
                    analyzeImage(imageProxy);
                });

                // Bind use cases to lifecycle
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error binding camera", Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void analyzeImage(ImageProxy imageProxy) {
        // If analysis is ongoing, skip the current image
        if (isAnalyzing) {
            imageProxy.close();
            return;
        }

        isAnalyzing = true;

        // Delay the analysis by a few seconds to prevent overload
        new Handler().postDelayed(() -> {
            // Convert the ImageProxy to a Bitmap
            Bitmap bitmap = imageToBitmap(imageProxy);

            // Preprocess the image using TensorImage
            TensorImage tensorImage = new TensorImage(DataType.UINT8);
            tensorImage.load(bitmap);

            // Resize the image to match the model input size (300x300)
            ImageProcessor imageProcessor = new ImageProcessor.Builder()
                    .add(new ResizeOp(300, 300, ResizeOp.ResizeMethod.BILINEAR))
                    .build();
            tensorImage = imageProcessor.process(tensorImage);

            ByteBuffer inputBuffer = tensorImage.getBuffer();

            // Prepare model outputs
            float[][][] locations = new float[1][10][4]; // Adjust 10 to match the max number of detections
            float[][] classes = new float[1][10];
            float[][] scores = new float[1][10];
            float[] numberOfDetections = new float[1];

            // Run inference
            Object[] inputs = {inputBuffer};
            Map<Integer, Object> outputs = new HashMap<>();
            outputs.put(0, locations);
            outputs.put(1, classes);
            outputs.put(2, scores);
            outputs.put(3, numberOfDetections);

            tfliteInterpreter.runForMultipleInputsOutputs(inputs, outputs);

            // Process the results
            processOutput(locations, classes, scores, numberOfDetections);

            // Close the ImageProxy
            imageProxy.close();

            // Reset analyzing flag after the delay
            isAnalyzing = false;
        }, 500); // Introduce a 1 second cooldown between image analyses
    }

    private void processOutput(float[][][] locations, float[][] classes, float[][] scores, float[] numberOfDetections) {
        String detectedObject = "";

        // Iterate through detected objects, but we only care about the first one with high confidence
        for (int i = 0; i < (int) numberOfDetections[0]; i++) {
            if (scores[0][i] > 0.5f) {  // Filter by confidence score
                int classIndex = (int) classes[0][i];
                detectedObject = getLabel(classIndex);
                break; // Only announce the first detected object
            }
        }

        // If an object was detected with high confidence, speak it
        if (!detectedObject.isEmpty()) {
            String message = "I see a " + detectedObject + ".";
            speakDetectedObjects(message);
            // Update the UI with the detected object
            TextView textView = findViewById(R.id.blind_text);
            textView.setText(message);
        } else {
            // If no object was detected
            String message = "I don't see anything.";
            speakDetectedObjects(message);
            // Update the UI with no detection
            TextView textView = findViewById(R.id.blind_text);
            textView.setText(message);
        }
    }

    private void speakDetectedObjects(String message) {
        // If it's already speaking, skip new requests
        if (isSpeaking) {
            return;
        }

        // Set the flag to true when speech starts
        isSpeaking = true;

        // Check if the Text-to-Speech engine is already speaking
        if (textToSpeech.isSpeaking()) {
            // If it's speaking, wait and then speak after a delay
            new Handler().postDelayed(() -> {
                textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
                // Reset the flag after the speech is completed
                isSpeaking = false;
            }, 2000); // 2 second delay (adjust as needed)
        } else {
            // If it's not speaking, just speak immediately
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
            // Reset the flag after the speech is completed
            isSpeaking = false;
        }
    }

    private String getLabel(int classIndex) {
        try {
            // Load labels from the label file
            List<String> labels = FileUtil.loadLabels(this, "labels.txt");
            if (classIndex >= 0 && classIndex < labels.size()) {
                return labels.get(classIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    private Bitmap imageToBitmap(ImageProxy imageProxy) {
        ImageProxy.PlaneProxy yPlane = imageProxy.getPlanes()[0];
        ImageProxy.PlaneProxy uPlane = imageProxy.getPlanes()[1];
        ImageProxy.PlaneProxy vPlane = imageProxy.getPlanes()[2];

        ByteBuffer yBuffer = yPlane.getBuffer();
        ByteBuffer uBuffer = uPlane.getBuffer();
        ByteBuffer vBuffer = vPlane.getBuffer();

        int ySize = yBuffer.remaining();
        int uvSize = uBuffer.remaining() + vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uvSize];
        yBuffer.get(nv21, 0, ySize);
        uBuffer.get(nv21, ySize, uBuffer.remaining());
        vBuffer.get(nv21, ySize + uBuffer.remaining(), vBuffer.remaining());

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, imageProxy.getWidth(), imageProxy.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, imageProxy.getWidth(), imageProxy.getHeight()), 100, out);
        byte[] jpegData = out.toByteArray();

        Bitmap bitmap = BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length);

        // Resize the bitmap to the model's expected input size
        return Bitmap.createScaledBitmap(bitmap, 300, 300, true);
    }

    private void navigateToHome() {
        Intent intent = new Intent(BlindDetectionActivity.this, BlindHomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tfliteInterpreter != null) {
            tfliteInterpreter.close();
        }
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int langResult = textToSpeech.setLanguage(java.util.Locale.US);
            if (langResult == TextToSpeech.LANG_MISSING_DATA
                    || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported or missing data.");
            }
        } else {
            Log.e("TTS", "Initialization failed.");
        }
    }
}
