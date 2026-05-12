package com.ecotrackport.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ecotrackport.models.Pollution;
import com.ecotrackport.repository.PollutionRepository;

import java.util.List;

public class PollutionViewModel extends AndroidViewModel {
    private final PollutionRepository repository;

    public PollutionViewModel(Application application) {
        super(application);
        repository = new PollutionRepository(application);
    }

    // Insert
    public void insertPollution(Pollution pollution) {
        repository.insertPollution(pollution);
    }

    // Update
    public void updatePollution(Pollution pollution) {
        repository.updatePollution(pollution);
    }

    // Delete
    public void deletePollution(Pollution pollution) {
        repository.deletePollution(pollution);
    }

    // Queries
    public LiveData<Pollution> getPollutionById(int id) {
        return repository.getPollutionById(id);
    }

    public LiveData<List<Pollution>> getAllPollutions() {
        return repository.getAllPollutions();
    }

    public LiveData<List<Pollution>> getPollutionsByType(String type) {
        return repository.getPollutionsByType(type);
    }

    public LiveData<List<Pollution>> getPollutionsByGravity(String gravity) {
        return repository.getPollutionsByGravity(gravity);
    }

    public LiveData<List<Pollution>> getPollutionsByZone(String zone) {
        return repository.getPollutionsByZone(zone);
    }

    public LiveData<Integer> getTotalPollutionCount() {
        return repository.getTotalPollutionCount();
    }

    public LiveData<Integer> getCriticalPollutionCount() {
        return repository.getCriticalPollutionCount();
    }

    public LiveData<Integer> getResolvedPollutionCount() {
        return repository.getResolvedPollutionCount();
    }

    public LiveData<Integer> getActivePollutionCount() {
        return repository.getActivePollutionCount();
    }

    public LiveData<List<Pollution>> getPollutionsByLocation(double minLat, double maxLat,
                                                             double minLng, double maxLng) {
        return repository.getPollutionsByLocation(minLat, maxLat, minLng, maxLng);
    }
}
