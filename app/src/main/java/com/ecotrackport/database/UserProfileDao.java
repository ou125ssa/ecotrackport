package com.ecotrackport.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ecotrackport.models.UserProfile;

import java.util.List;

@Dao
public interface UserProfileDao {
    @Insert
    long insertUserProfile(UserProfile userProfile);

    @Update
    void updateUserProfile(UserProfile userProfile);

    @Delete
    void deleteUserProfile(UserProfile userProfile);

    @Query("SELECT * FROM user_profiles WHERE id = :id")
    LiveData<UserProfile> getUserProfileById(int id);

    @Query("SELECT * FROM user_profiles LIMIT 1")
    LiveData<UserProfile> getCurrentUserProfile();

    @Query("SELECT * FROM user_profiles ORDER BY profileCreatedAt DESC")
    LiveData<List<UserProfile>> getAllUserProfiles();

    @Query("DELETE FROM user_profiles")
    void deleteAllUserProfiles();
}
