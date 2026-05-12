package com.ecotrackport.models;

import java.io.Serializable;

public class AIAnalysisResult implements Serializable {
    public String pollutionType;
    public double confidence;
    public String evolutionType;
    public String[] visualFeatures;
    public String interpretation;
    public String recommendedSolution;
    public String implementationPriority;
    public int estimatedCost;
    public int timeToResolve;
    public int reductionPercentage;

    public AIAnalysisResult() {}

    public AIAnalysisResult(String pollutionType, double confidence, 
                           String evolutionType, String recommendedSolution) {
        this.pollutionType = pollutionType;
        this.confidence = confidence;
        this.evolutionType = evolutionType;
        this.recommendedSolution = recommendedSolution;
    }

    @Override
    public String toString() {
        return "AIAnalysisResult{" +
                "pollutionType='" + pollutionType + '\'' +
                ", confidence=" + confidence +
                ", priority='" + implementationPriority + '\'' +
                '}';
    }
}
