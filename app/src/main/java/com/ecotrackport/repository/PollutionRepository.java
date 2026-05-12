package com.ecotrackport.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ecotrackport.database.AppDatabase;
import com.ecotrackport.database.PollutionDao;
import com.ecotrackport.models.Pollution;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PollutionRepository {
    private final PollutionDao pollutionDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public PollutionRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        pollutionDao = db.pollutionDao();
    }

    // Insert
    public void insertPollution(Pollution pollution) {
        executor.execute(() -> pollutionDao.insertPollution(pollution));
    }

    // Update
    public void updatePollution(Pollution pollution) {
        executor.execute(() -> pollutionDao.updatePollution(pollution));
    }

    // Delete
    public void deletePollution(Pollution pollution) {
        executor.execute(() -> pollutionDao.deletePollution(pollution));
    }

    // Queries
    public LiveData<Pollution> getPollutionById(int id) {
        return pollutionDao.getPollutionById(id);
    }

    public LiveData<List<Pollution>> getAllPollutions() {
        return pollutionDao.getAllPollutions();
    }

    public LiveData<List<Pollution>> getPollutionsByType(String type) {
        return pollutionDao.getPollutionsByType(type);
    }

    public LiveData<List<Pollution>> getPollutionsByGravity(String gravity) {
        return pollutionDao.getPollutionsByGravity(gravity);
    }

    public LiveData<List<Pollution>> getPollutionsByZone(String zone) {
        return pollutionDao.getPollutionsByZone(zone);
    }

    public LiveData<Integer> getTotalPollutionCount() {
        return pollutionDao.getTotalPollutionCount();
    }

    public LiveData<Integer> getCriticalPollutionCount() {
        return pollutionDao.getCriticalPollutionCount();
    }

    public LiveData<Integer> getResolvedPollutionCount() {
        return pollutionDao.getResolvedPollutionCount();
    }

    public LiveData<Integer> getActivePollutionCount() {
        return pollutionDao.getActivePollutionCount();
    }

    public LiveData<List<Pollution>> getPollutionsByLocation(double minLat, double maxLat,
                                                             double minLng, double maxLng) {
        return pollutionDao.getPollutionsByLocation(minLat, maxLat, minLng, maxLng);
    }
}
