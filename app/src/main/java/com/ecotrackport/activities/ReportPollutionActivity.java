package com.ecotrackport.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ecotrackport.R;
import com.ecotrackport.models.Pollution;
import com.ecotrackport.models.UserProfile;
import com.ecotrackport.utils.AIAnalysisSimulator;
import com.ecotrackport.utils.SharedPreferencesHelper;
import com.ecotrackport.viewmodels.PollutionViewModel;

public class ReportPollutionActivity extends AppCompatActivity {
    private Spinner spinnerType, spinnerGravity;
    private EditText editDescription;
    private Button btnCapture, btnGallery, btnSubmit;
    private ImageView imagePreview;
    private PollutionViewModel pollutionViewModel;
    private SharedPreferencesHelper preferencesHelper;
    private Uri selectedImageUri;
    private static final int CAMERA_PERMISSION = 101;
    private static final int GALLERY_PERMISSION = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pollution);

        preferencesHelper = new SharedPreferencesHelper(this);
        pollutionViewModel = new ViewModelProvider(this).get(PollutionViewModel.class);

        initializeViews();
        setupEventListeners();
    }

    private void initializeViews() {
        spinnerType = findViewById(R.id.spinnerType);
        spinnerGravity = findViewById(R.id.spinnerGravity);
        editDescription = findViewById(R.id.editDescription);
        btnCapture = findViewById(R.id.btnCapture);
        btnGallery = findViewById(R.id.btnGallery);
        btnSubmit = findViewById(R.id.btnSubmit);
        imagePreview = findViewById(R.id.imagePreview);
    }

    private void setupEventListeners() {
        btnCapture.setOnClickListener(v -> requestCameraPermission());
        btnGallery.setOnClickListener(v -> requestGalleryPermission());
        btnSubmit.setOnClickListener(v -> submitReport());
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION);
        } else {
            openCamera();
        }
    }

    private void requestGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    GALLERY_PERMISSION);
        } else {
            openGallery();
        }
    }

    private void openCamera() {
        // TODO: Implement camera intent
        Toast.makeText(this, "Caméra non implémentée", Toast.LENGTH_SHORT).show();
    }

    private void openGallery() {
        // TODO: Implement gallery intent
        Toast.makeText(this, "Galerie non implémentée", Toast.LENGTH_SHORT).show();
    }

    private void submitReport() {
        String type = spinnerType.getSelectedItem().toString();
        String gravity = spinnerGravity.getSelectedItem().toString();
        String description = editDescription.getText().toString().trim();

        if (type.isEmpty() || gravity.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create pollution record
        Pollution pollution = new Pollution();
        pollution.type = type;
        pollution.gravity = gravity;
        pollution.description = description;
        pollution.latitude = preferencesHelper.getCurrentLatitude();
        pollution.longitude = preferencesHelper.getCurrentLongitude();
        pollution.zone = preferencesHelper.getCurrentZone();
        pollution.date = System.currentTimeMillis() + "";
        pollution.status = "nouveau";

        UserProfile profile = preferencesHelper.getUserProfile();
        if (profile != null) {
            pollution.reporterProfession = profile.professionName;
            pollution.reporterZone = profile.zone;
        }

        // Simulate AI analysis
        var aiResult = AIAnalysisSimulator.simulateAnalysis();
        pollution.aiConfidence = (int)(aiResult.confidence * 100) + "%";
        pollution.aiEvolutionType = aiResult.evolutionType;
        pollution.aiInterpretation = aiResult.interpretation;
        pollution.recommendedSolution = aiResult.recommendedSolution;
        pollution.estimatedCost = aiResult.estimatedCost;
        pollution.timeToResolve = aiResult.timeToResolve;
        pollution.priority = aiResult.implementationPriority;

        // Save to database
        pollutionViewModel.insertPollution(pollution);

        Toast.makeText(this, "Signalement envoyé avec succès!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else if (requestCode == GALLERY_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }
}
