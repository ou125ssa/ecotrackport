package com.ecotrackport.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ecotrackport.R;
import com.ecotrackport.models.Pollution;
import com.ecotrackport.models.UserProfile;
import com.ecotrackport.utils.AIAnalysisSimulator;
import com.ecotrackport.utils.SharedPreferencesHelper;
import com.ecotrackport.viewmodels.PollutionViewModel;

public class ReportFragment extends Fragment {
    private Spinner spinnerType, spinnerGravity;
    private EditText editDescription;
    private Button btnCapture, btnGallery, btnSubmit;
    private ImageView imagePreview;
    private PollutionViewModel pollutionViewModel;
    private SharedPreferencesHelper preferencesHelper;
    private static final int CAMERA_PERMISSION = 101;
    private static final int GALLERY_PERMISSION = 102;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferencesHelper = new SharedPreferencesHelper(requireContext());
        pollutionViewModel = new ViewModelProvider(this).get(PollutionViewModel.class);

        initializeViews(view);
        setupSpinners();
        setupEventListeners();
    }

    private void initializeViews(View view) {
        spinnerType = view.findViewById(R.id.spinnerType);
        spinnerGravity = view.findViewById(R.id.spinnerGravity);
        editDescription = view.findViewById(R.id.editDescription);
        btnCapture = view.findViewById(R.id.btnCapture);
        btnGallery = view.findViewById(R.id.btnGallery);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        imagePreview = view.findViewById(R.id.imagePreview);
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.pollution_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        ArrayAdapter<CharSequence> gravityAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.gravity_levels, android.R.layout.simple_spinner_item);
        gravityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGravity.setAdapter(gravityAdapter);
    }

    private void setupEventListeners() {
        btnCapture.setOnClickListener(v -> requestCameraPermission());
        btnGallery.setOnClickListener(v -> requestGalleryPermission());
        btnSubmit.setOnClickListener(v -> submitReport());
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION);
        } else {
            openCamera();
        }
    }

    private void requestGalleryPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    GALLERY_PERMISSION);
        } else {
            openGallery();
        }
    }

    private void openCamera() {
        Toast.makeText(requireContext(), "Caméra non implémentée", Toast.LENGTH_SHORT).show();
    }

    private void openGallery() {
        Toast.makeText(requireContext(), "Galerie non implémentée", Toast.LENGTH_SHORT).show();
    }

    private void submitReport() {
        String type = spinnerType.getSelectedItem().toString();
        String gravity = spinnerGravity.getSelectedItem().toString();
        String description = editDescription.getText().toString().trim();

        if (description.isEmpty()) {
            Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

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

        var aiResult = AIAnalysisSimulator.simulateAnalysis();
        pollution.aiConfidence = (int)(aiResult.confidence * 100) + "%";
        pollution.aiEvolutionType = aiResult.evolutionType;
        pollution.aiInterpretation = aiResult.interpretation;
        pollution.recommendedSolution = aiResult.recommendedSolution;
        pollution.estimatedCost = aiResult.estimatedCost;
        pollution.timeToResolve = aiResult.timeToResolve;
        pollution.priority = aiResult.implementationPriority;

        pollutionViewModel.insertPollution(pollution);

        Toast.makeText(requireContext(), "Signalement envoyé avec succès!", Toast.LENGTH_SHORT).show();
        editDescription.setText("");
    }
}
