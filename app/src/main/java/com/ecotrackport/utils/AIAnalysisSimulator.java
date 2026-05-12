package com.ecotrackport.utils;

import com.ecotrackport.models.AIAnalysisResult;

import java.util.Random;

public class AIAnalysisSimulator {
    private static final String[] POLLUTION_TYPES = {
        "water_pollution", "air_pollution", "solid_waste", "industrial_pollution"
    };
    private static final String[] EVOLUTION_TYPES = {
        "apparition_soudaine", "degradation_progressive", "pollution_cyclique"
    };
    private static final String[] SOLUTIONS = {
        "Déploiement immédiat de barrières de confinement",
        "Mobilisation des équipes d'intervention d'urgence",
        "Installation de systèmes de filtration avancés",
        "Coordination avec les autorités environnementales",
        "Mise en place de mesures de prévention"
    };

    public static AIAnalysisResult simulateAnalysis() {
        Random random = new Random();
        AIAnalysisResult result = new AIAnalysisResult();

        result.pollutionType = POLLUTION_TYPES[random.nextInt(POLLUTION_TYPES.length)];
        result.confidence = 0.75 + (random.nextDouble() * 0.2);
        result.evolutionType = EVOLUTION_TYPES[random.nextInt(EVOLUTION_TYPES.length)];
        result.recommendedSolution = SOLUTIONS[random.nextInt(SOLUTIONS.length)];
        result.implementationPriority = random.nextInt(3) == 0 ? "high" : "medium";
        result.estimatedCost = 5000 + (random.nextInt(15) * 1000);
        result.timeToResolve = 2 + random.nextInt(6);
        result.reductionPercentage = 70 + random.nextInt(25);
        result.interpretation = "Analyse IA - Confiance: " + (int)(result.confidence * 100) + "%";

        return result;
    }
}
