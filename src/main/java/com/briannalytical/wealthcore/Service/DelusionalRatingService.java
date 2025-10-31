package com.briannalytical.wealthcore.Service;

import com.briannalytical.wealthcore.Dto.DelusionalRatingResult;
import com.briannalytical.wealthcore.Model.Entity.Trip;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class DelusionalRatingService {

    public DelusionalRatingResult calculateDelusionalRating(Trip trip) {
        int score = 0;
        Map<String, Integer> breakdown = new HashMap<>();

        // calculate each metric
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

        String rating = getRatingLabel(score);

        return new DelusionalRatingResult(score, rating, breakdown);
    }


    // cost factor 0-30 points
    private int calculateCostScore(Trip trip) {
        BigDecimal totalCost = trip.getTotalCost();
        if (totalCost == null) return 0;

        double cost = totalCost.doubleValue();

        //TODO: metrics for cost
        return 0;
    }

    // destination chaos (how many places traveled) (0-20 points)
    // TODO: needs destination map/collection to be implemented
    private int calculateDestinationChaosScore(Trip trip) {
        // return zero until db is mapped
        return 0;
    }

    // distance traveled (0-20 points)
    // TODO: This needs destination coordinates to calculate (API?)
    private int calculateDistanceScore(Trip trip) {
        return 0;
    }

    // cost per day (0-20 points)
    // TODO: requires a trip duration
    private int calculateCostPerDayScore(Trip trip) {
        // will return 0 until trip days can be calculated
        return 0;
    }

    // activity density (0-10 points)
    // TODO: itinerary items map/collection
    private int calculateActivityDensityScore(Trip trip) {
        // will return 0 until collection is made
        return 0;
    }

    // determine rating label based on score
    private String getRatingLabel(int score) {
        if (score >= 81) return "Transcended the Fourth Dimension of Delusion";
        else if (score >= 61) return "Soul Has Departed the Body";
        else if (score >= 41) return "Absolutely Unhinged";
        else if (score >= 21) return "Reasonably Delusional";
        else return "Optimistically Rational";
    }
}