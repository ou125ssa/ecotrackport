package com.ecotrackport.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "pollutions")
public class Pollution implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String type; // water_pollution, air_pollution, etc
    public String gravity; // critique, moyen, faible
    public String description;
    public double latitude;
    public double longitude;
    public String zone;
    public String date;
    public String imagePath;
    public String status; // nouveau, en_traitement, resolu
    public String reporterProfession;
    public String reporterZone;
    
    // AI Analysis
    public String aiConfidence;
    public String aiEvolutionType;
    public String aiInterpretation;
    public String recommendedSolution;
    public int estimatedCost;
    public int timeToResolve;
    public String priority; // high, medium, low

    public Pollution() {}

    public Pollution(String type, String gravity, String description, 
                     double latitude, double longitude, String zone) {
        this.type = type;
        this.gravity = gravity;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zone = zone;
        this.status = "nouveau";
        this.date = System.currentTimeMillis() + "";
    }

    public String getPollutionTypeLabel() {
        switch (type) {
            case "water_pollution":
                return "Pollution de l'eau";
            case "air_pollution":
                return "Pollution de l'air";
            case "solid_waste":
                return "Déchets solides";
            case "noise_pollution":
                return "Pollution sonore";
            case "visual_pollution":
                return "Pollution visuelle";
            case "industrial_pollution":
                return "Pollution industrielle";
            default:
                return type;
        }
    }

    public int getGravityColor() {
        switch (gravity) {
            case "critique":
                return 0xFFDC3545; // Red
            case "moyen":
                return 0xFFFFC107; // Yellow
            case "faible":
                return 0xFF28A745; // Green
            default:
                return 0xFF6C757D; // Gray
        }
    }

    @Override
    public String toString() {
        return "Pollution{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", gravity='" + gravity + '\'' +
                ", zone='" + zone + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
