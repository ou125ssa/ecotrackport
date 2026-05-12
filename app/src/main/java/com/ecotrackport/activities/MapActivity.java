package com.ecotrackport.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ecotrackport.R;
import com.ecotrackport.models.Pollution;
import com.ecotrackport.viewmodels.PollutionViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private PollutionViewModel pollutionViewModel;
    private static final LatLng SOUSSE_PORT = new LatLng(35.8256, 10.6369);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        pollutionViewModel = new ViewModelProvider(this).get(PollutionViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SOUSSE_PORT, 14));

        // Load and display pollutions
        pollutionViewModel.getAllPollutions().observe(this, pollutions -> {
            mMap.clear();
            for (Pollution pollution : pollutions) {
                addPollutionMarker(pollution);
            }
        });
    }

    private void addPollutionMarker(Pollution pollution) {
        LatLng location = new LatLng(pollution.latitude, pollution.longitude);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(location)
                .title(pollution.getPollutionTypeLabel())
                .snippet("Gravité: " + pollution.gravity);

        mMap.addMarker(markerOptions);
    }
}
