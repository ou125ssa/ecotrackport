package com.ecotrackport.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecotrackport.R;
import com.ecotrackport.adapters.PollutionHistoryAdapter;
import com.ecotrackport.viewmodels.PollutionViewModel;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private PollutionHistoryAdapter adapter;
    private PollutionViewModel pollutionViewModel;
    private Spinner spinnerType, spinnerGravity;
    private Button btnFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pollutionViewModel = new ViewModelProvider(this).get(PollutionViewModel.class);

        initializeViews(view);
        setupRecyclerView();
        setupFilters();
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        spinnerType = view.findViewById(R.id.spinnerType);
        spinnerGravity = view.findViewById(R.id.spinnerGravity);
        btnFilter = view.findViewById(R.id.btnFilter);
    }

    private void setupRecyclerView() {
        adapter = new PollutionHistoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        pollutionViewModel.getAllPollutions().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    private void setupFilters() {
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.pollution_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        ArrayAdapter<CharSequence> gravityAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.gravity_levels, android.R.layout.simple_spinner_item);
        gravityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGravity.setAdapter(gravityAdapter);

        btnFilter.setOnClickListener(v -> applyFilters());
    }

    private void applyFilters() {
        String type = spinnerType.getSelectedItem().toString();
        String gravity = spinnerGravity.getSelectedItem().toString();

        if ("Tous les types".equals(type) && "Tous les niveaux".equals(gravity)) {
            pollutionViewModel.getAllPollutions().observe(getViewLifecycleOwner(), adapter::submitList);
        } else if (!"Tous les types".equals(type)) {
            pollutionViewModel.getPollutionsByType(type).observe(getViewLifecycleOwner(), adapter::submitList);
        } else {
            pollutionViewModel.getPollutionsByGravity(gravity).observe(getViewLifecycleOwner(), adapter::submitList);
        }
    }
}
