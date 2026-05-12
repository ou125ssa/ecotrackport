package com.ecotrackport.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ecotrackport.R;
import com.ecotrackport.activities.LoginActivity;
import com.ecotrackport.utils.SharedPreferencesHelper;

public class SettingsFragment extends Fragment {
    private Switch switchNotifications;
    private Spinner spinnerRefreshInterval, spinnerTheme;
    private Button btnSave, btnLogout;
    private SharedPreferencesHelper preferencesHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferencesHelper = new SharedPreferencesHelper(requireContext());
        initializeViews(view);
        loadSettings();
        setupEventListeners();
    }

    private void initializeViews(View view) {
        switchNotifications = view.findViewById(R.id.switchNotifications);
        spinnerRefreshInterval = view.findViewById(R.id.spinnerRefreshInterval);
        spinnerTheme = view.findViewById(R.id.spinnerTheme);
        btnSave = view.findViewById(R.id.btnSave);
        btnLogout = view.findViewById(R.id.btnLogout);
    }

    private void loadSettings() {
        switchNotifications.setChecked(preferencesHelper.isNotificationsEnabled());

        ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.refresh_intervals, android.R.layout.simple_spinner_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRefreshInterval.setAdapter(intervalAdapter);

        ArrayAdapter<CharSequence> themeAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.themes, android.R.layout.simple_spinner_item);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheme.setAdapter(themeAdapter);
    }

    private void setupEventListeners() {
        btnSave.setOnClickListener(v -> saveSettings());
        btnLogout.setOnClickListener(v -> logout());
    }

    private void saveSettings() {
        preferencesHelper.setNotificationsEnabled(switchNotifications.isChecked());
        Toast.makeText(requireContext(), "Paramètres sauvegardés!", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        preferencesHelper.clearUserData();
        preferencesHelper.setUserLoggedIn(false);
        startActivity(new Intent(requireContext(), LoginActivity.class));
        requireActivity().finish();
    }
}
