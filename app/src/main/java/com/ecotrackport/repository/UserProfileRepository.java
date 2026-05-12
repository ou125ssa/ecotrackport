package com.ecotrackport.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ecotrackport.database.AppDatabase;
import com.ecotrackport.database.UserProfileDao;
import com.ecotrackport.models.UserProfile;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserProfileRepository {
    private final UserProfileDao userProfileDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public UserProfileRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        userProfileDao = db.userProfileDao();
    }

    // Insert
    public void insertUserProfile(UserProfile userProfile) {
        executor.execute(() -> userProfileDao.insertUserProfile(userProfile));
    }

    // Update
    public void updateUserProfile(UserProfile userProfile) {
        executor.execute(() -> userProfileDao.updateUserProfile(userProfile));
    }

    // Delete
    public void deleteUserProfile(UserProfile userProfile) {
        executor.execute(() -> userProfileDao.deleteUserProfile(userProfile));
    }

    // Queries
    public LiveData<UserProfile> getUserProfileById(int id) {
        return userProfileDao.getUserProfileById(id);
    }

    public LiveData<UserProfile> getCurrentUserProfile() {
        return userProfileDao.getCurrentUserProfile();
    }

    public LiveData<List<UserProfile>> getAllUserProfiles() {
        return userProfileDao.getAllUserProfiles();
    }
}
