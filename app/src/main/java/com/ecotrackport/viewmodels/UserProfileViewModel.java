package com.ecotrackport.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ecotrackport.models.UserProfile;
import com.ecotrackport.repository.UserProfileRepository;

import java.util.List;

public class UserProfileViewModel extends AndroidViewModel {
    private final UserProfileRepository repository;

    public UserProfileViewModel(Application application) {
        super(application);
        repository = new UserProfileRepository(application);
    }

    // Insert
    public void insertUserProfile(UserProfile userProfile) {
        repository.insertUserProfile(userProfile);
    }

    // Update
    public void updateUserProfile(UserProfile userProfile) {
        repository.updateUserProfile(userProfile);
    }

    // Delete
    public void deleteUserProfile(UserProfile userProfile) {
        repository.deleteUserProfile(userProfile);
    }

    // Queries
    public LiveData<UserProfile> getUserProfileById(int id) {
        return repository.getUserProfileById(id);
    }

    public LiveData<UserProfile> getCurrentUserProfile() {
        return repository.getCurrentUserProfile();
    }

    public LiveData<List<UserProfile>> getAllUserProfiles() {
        return repository.getAllUserProfiles();
    }
}
