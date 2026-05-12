package com.ecotrackport.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ecotrackport.models.Pollution;

import java.util.List;

@Dao
public interface PollutionDao {
    @Insert
    long insertPollution(Pollution pollution);

    @Update
    void updatePollution(Pollution pollution);

    @Delete
    void deletePollution(Pollution pollution);

    @Query("SELECT * FROM pollutions WHERE id = :id")
    LiveData<Pollution> getPollutionById(int id);

    @Query("SELECT * FROM pollutions ORDER BY date DESC")
    LiveData<List<Pollution>> getAllPollutions();

    @Query("SELECT * FROM pollutions WHERE type = :type ORDER BY date DESC")
    LiveData<List<Pollution>> getPollutionsByType(String type);

    @Query("SELECT * FROM pollutions WHERE gravity = :gravity ORDER BY date DESC")
    LiveData<List<Pollution>> getPollutionsByGravity(String gravity);

    @Query("SELECT * FROM pollutions WHERE zone = :zone ORDER BY date DESC")
    LiveData<List<Pollution>> getPollutionsByZone(String zone);

    @Query("SELECT * FROM pollutions WHERE status = :status ORDER BY date DESC")
    LiveData<List<Pollution>> getPollutionsByStatus(String status);

    @Query("SELECT COUNT(*) FROM pollutions")
    LiveData<Integer> getTotalPollutionCount();

    @Query("SELECT COUNT(*) FROM pollutions WHERE gravity = 'critique'")
    LiveData<Integer> getCriticalPollutionCount();

    @Query("SELECT COUNT(*) FROM pollutions WHERE status = 'resolu'")
    LiveData<Integer> getResolvedPollutionCount();

    @Query("SELECT COUNT(*) FROM pollutions WHERE status = 'nouveau' OR status = 'en_traitement'")
    LiveData<Integer> getActivePollutionCount();

    @Query("SELECT * FROM pollutions WHERE latitude BETWEEN :minLat AND :maxLat " +
           "AND longitude BETWEEN :minLng AND :maxLng ORDER BY date DESC")
    LiveData<List<Pollution>> getPollutionsByLocation(double minLat, double maxLat, 
                                                       double minLng, double maxLng);

    @Query("DELETE FROM pollutions")
    void deleteAllPollutions();
}
