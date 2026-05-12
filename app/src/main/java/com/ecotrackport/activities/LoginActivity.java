package com.ecotrackport.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ecotrackport.R;
import com.ecotrackport.models.UserProfile;
import com.ecotrackport.utils.SharedPreferencesHelper;
import com.ecotrackport.viewmodels.UserProfileViewModel;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity {
    private EditText editProfession, editZone, editEmail;
    private Button btnLogin;
    private UserProfileViewModel userProfileViewModel;
    private SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferencesHelper = new SharedPreferencesHelper(this);
        userProfileViewModel = new UserProfileViewModel(getApplication());

        // Check if user already logged in
        if (preferencesHelper.isUserLoggedIn()) {
            startMainActivity();
            return;
        }

        initializeViews();
        setupEventListeners();
    }

    private void initializeViews() {
        editProfession = findViewById(R.id.editProfession);
        editZone = findViewById(R.id.editZone);
        editEmail = findViewById(R.id.editEmail);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void setupEventListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String profession = editProfession.getText().toString().trim();
        String zone = editZone.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if (profession.isEmpty() || zone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create user profile
        UserProfile userProfile = new UserProfile(profession, zone);
        userProfile.email = email;
        userProfile.lastLoginAt = System.currentTimeMillis() + "";

        // Save to database
        userProfileViewModel.insertUserProfile(userProfile);

        // Save to preferences
        preferencesHelper.saveUserProfile(userProfile);
        preferencesHelper.setUserLoggedIn(true);

        Toast.makeText(this, "Bienvenue " + profession + "!", Toast.LENGTH_SHORT).show();
        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
