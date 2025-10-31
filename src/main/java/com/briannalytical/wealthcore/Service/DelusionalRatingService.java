package com.briannalytical.wealthcore.Service;

import com.briannalytical.wealthcore.Dto.DelusionalRatingResult;
import com.briannalytical.wealthcore.Model.Entity.Trip;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class DelusionalRatingService {

    public DelusionalRatingResult calculateDelusionalRating(Trip trip) {
        int score = 0;
        Map<String, Integer> breakdown = new HashMap<>();

        // Calculate each metric
        int costScore = calculateCostScore(trip);
        int chaosScore = calculateDestinationChaosScore(trip);
        int distanceScore = calculateDistanceScore(trip);
        int costPerDayScore = calculateCostPerDayScore(trip);
        int activityScore = calculateActivityDensityScore(trip);

        breakdown.put("costScore", costScore);
        breakdown.put("destinationChaosScore", chaosScore);
        breakdown.put("distanceScore", distanceScore);
        breakdown.put("costPerDayScore", costPerDayScore);
        breakdown.put("activityDensityScore", activityScore);

        score = costScore + chaosScore + distanceScore + costPerDayScore + activityScore;

        // Determine rating label
        String rating = getRatingLabel(score);

        return new DelusionalRatingResult(score, rating, breakdown);
    }
}