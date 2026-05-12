package com.ecotrackport.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "user_profiles")
public class UserProfile implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String professionName;
    public String zone;
    public String email;
    public String phone;
    public double currentLatitude;
    public double currentLongitude;
    public String lastLocationUpdate;
    public boolean notificationsEnabled;
    public int refreshInterval; // in seconds
    public String theme; // light, dark, auto
    public String profileCreatedAt;
    public String lastLoginAt;

    public UserProfile() {}

    public UserProfile(String professionName, String zone) {
        this.professionName = professionName;
        this.zone = zone;
        this.notificationsEnabled = true;
        this.refreshInterval = 60;
        this.theme = "light";
        this.profileCreatedAt = System.currentTimeMillis() + "";
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "professionName='" + professionName + '\'' +
                ", zone='" + zone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
