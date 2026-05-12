package com.ecotrackport.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecotrackport.R;
import com.ecotrackport.adapters.PollutionAdapter;
import com.ecotrackport.viewmodels.PollutionViewModel;

public class DashboardFragment extends Fragment {
    private PollutionViewModel pollutionViewModel;
    private TextView tvTotalPollutions, tvCriticalPollutions, tvResolvedToday, tvActiveAlerts;
    private RecyclerView recyclerViewAlerts;
    private PollutionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pollutionViewModel = new ViewModelProvider(this).get(PollutionViewModel.class);

        initializeViews(view);
        loadStatistics();
        setupRecyclerView();
    }

    private void initializeViews(View view) {
        tvTotalPollutions = view.findViewById(R.id.tvTotalPollutions);
        tvCriticalPollutions = view.findViewById(R.id.tvCriticalPollutions);
        tvResolvedToday = view.findViewById(R.id.tvResolvedToday);
        tvActiveAlerts = view.findViewById(R.id.tvActiveAlerts);
        recyclerViewAlerts = view.findViewById(R.id.recyclerViewAlerts);
    }

    private void loadStatistics() {
        pollutionViewModel.getTotalPollutionCount().observe(getViewLifecycleOwner(),
                count -> tvTotalPollutions.setText(count != null ? count.toString() : "0"));

        pollutionViewModel.getCriticalPollutionCount().observe(getViewLifecycleOwner(),
                count -> tvCriticalPollutions.setText(count != null ? count.toString() : "0"));

        pollutionViewModel.getResolvedPollutionCount().observe(getViewLifecycleOwner(),
                count -> tvResolvedToday.setText(count != null ? count.toString() : "0"));

        pollutionViewModel.getActivePollutionCount().observe(getViewLifecycleOwner(),
                count -> tvActiveAlerts.setText(count != null ? count.toString() : "0"));
    }

    private void setupRecyclerView() {
        adapter = new PollutionAdapter();
        recyclerViewAlerts.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewAlerts.setAdapter(adapter);

        pollutionViewModel.getAllPollutions().observe(getViewLifecycleOwner(), pollutions -> {
            if (pollutions != null && !pollutions.isEmpty()) {
                adapter.submitList(pollutions.subList(0, Math.min(5, pollutions.size())));
            }
        });
    }
}
