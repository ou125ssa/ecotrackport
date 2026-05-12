package com.ecotrackport.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ecotrackport.R;
import com.ecotrackport.utils.SharedPreferencesHelper;

public class SettingsActivity extends AppCompatActivity {
    private Switch switchNotifications;
    private Spinner spinnerRefreshInterval, spinnerTheme;
    private Button btnSave, btnLogout;
    private SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferencesHelper = new SharedPreferencesHelper(this);
        initializeViews();
        loadSettings();
        setupEventListeners();
    }

    private void initializeViews() {
        switchNotifications = findViewById(R.id.switchNotifications);
        spinnerRefreshInterval = findViewById(R.id.spinnerRefreshInterval);
        spinnerTheme = findViewById(R.id.spinnerTheme);
        btnSave = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void loadSettings() {
        switchNotifications.setChecked(preferencesHelper.isNotificationsEnabled());
        // TODO: Load other settings
    }

    private void setupEventListeners() {
        btnSave.setOnClickListener(v -> saveSettings());
        btnLogout.setOnClickListener(v -> logout());
    }

    private void saveSettings() {
        preferencesHelper.setNotificationsEnabled(switchNotifications.isChecked());
        Toast.makeText(this, "Paramètres sauvegardés!", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        preferencesHelper.clearUserData();
        preferencesHelper.setUserLoggedIn(false);
        startActivity(new android.content.Intent(this, LoginActivity.class));
        finish();
    }
}
