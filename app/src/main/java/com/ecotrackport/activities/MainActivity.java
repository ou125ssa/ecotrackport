package com.ecotrackport.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ecotrackport.R;
import com.ecotrackport.fragments.DashboardFragment;
import com.ecotrackport.fragments.HistoryFragment;
import com.ecotrackport.fragments.MapFragment;
import com.ecotrackport.fragments.ReportFragment;
import com.ecotrackport.fragments.SettingsFragment;
import com.ecotrackport.models.UserProfile;
import com.ecotrackport.services.LocationTrackingService;
import com.ecotrackport.utils.SharedPreferencesHelper;
import com.ecotrackport.viewmodels.PollutionViewModel;
import com.ecotrackport.viewmodels.UserProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private FrameLayout fragmentContainer;
    private TextView headerTitle, headerSubtitle;
    private PollutionViewModel pollutionViewModel;
    private UserProfileViewModel userProfileViewModel;
    private SharedPreferencesHelper preferencesHelper;
    private static final int LOCATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencesHelper = new SharedPreferencesHelper(this);
        pollutionViewModel = new ViewModelProvider(this).get(PollutionViewModel.class);
        userProfileViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);

        initializeViews();
        setupBottomNavigation();
        requestLocationPermissions();
        startLocationTracking();

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new DashboardFragment());
            bottomNav.setSelectedItemId(R.id.nav_dashboard);
        }
    }

    private void initializeViews() {
        bottomNav = findViewById(R.id.bottomNav);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        headerTitle = findViewById(R.id.headerTitle);
        headerSubtitle = findViewById(R.id.headerSubtitle);
    }

    private void setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_dashboard:
                    loadFragment(new DashboardFragment());
                    updateHeader("Tableau de Bord", "Vue d'ensemble de la surveillance");
                    return true;
                case R.id.nav_map:
                    loadFragment(new MapFragment());
                    updateHeader("Carte Interactive", "Surveillance en temps réel");
                    return true;
                case R.id.nav_report:
                    loadFragment(new ReportFragment());
                    updateHeader("Signaler Pollution", "Signalez rapidement toute pollution");
                    return true;
                case R.id.nav_history:
                    loadFragment(new HistoryFragment());
                    updateHeader("Historique", "Consultez tous les signalements");
                    return true;
                case R.id.nav_settings:
                    loadFragment(new SettingsFragment());
                    updateHeader("Paramètres", "Personnalisez votre expérience");
                    return true;
            }
            return false;
        });
    }

    private void loadFragment(androidx.fragment.app.Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void updateHeader(String title, String subtitle) {
        headerTitle.setText(title);
        headerSubtitle.setText(subtitle);
    }

    private void requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationTracking();
            }
        }
    }

    private void startLocationTracking() {
        Intent serviceIntent = new Intent(this, LocationTrackingService.class);
        startService(serviceIntent);
    }
}
