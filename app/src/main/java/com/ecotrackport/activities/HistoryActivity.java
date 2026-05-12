package com.ecotrackport.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecotrackport.R;
import com.ecotrackport.adapters.PollutionHistoryAdapter;
import com.ecotrackport.viewmodels.PollutionViewModel;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PollutionHistoryAdapter adapter;
    private PollutionViewModel pollutionViewModel;
    private Spinner spinnerType, spinnerGravity;
    private Button btnFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        pollutionViewModel = new ViewModelProvider(this).get(PollutionViewModel.class);

        initializeViews();
        setupRecyclerView();
        setupFilters();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerGravity = findViewById(R.id.spinnerGravity);
        btnFilter = findViewById(R.id.btnFilter);
    }

    private void setupRecyclerView() {
        adapter = new PollutionHistoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        pollutionViewModel.getAllPollutions().observe(this, adapter::submitList);
    }

    private void setupFilters() {
        btnFilter.setOnClickListener(v -> applyFilters());
    }

    private void applyFilters() {
        // TODO: Implement filter logic
    }
}
