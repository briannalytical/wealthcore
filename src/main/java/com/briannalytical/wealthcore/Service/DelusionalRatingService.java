package com.briannalytical.wealthcore.Service;

import com.briannalytical.wealthcore.Dto.DelusionalRatingResult;
import com.briannalytical.wealthcore.Model.Entity.Trip;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Destination;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DelusionalRatingService {

    public DelusionalRatingResult calculateDelusionalRating(Trip trip) {
        int score = 0;
        Map<String, Integer> breakdown = new HashMap<>();

        // calculate each metric
        int costScore = calculateCostScore(trip);
        int durationScore = calculateTripDurationScore(trip);
        int extendedStayScore = calculateExtendedStayScore(trip);
        int costPerDayScore = calculateCostPerDayScore(trip);
        int dailySpendingScore = calculateDailySpendingScore(trip);

        breakdown.put("costScore", costScore);
        breakdown.put("durationScore", durationScore);
        breakdown.put("extendedStayScore", extendedStayScore);
        breakdown.put("costPerDayScore", costPerDayScore);
        breakdown.put("dailySpendingScore", dailySpendingScore);

        score = costScore + durationScore + extendedStayScore + costPerDayScore + dailySpendingScore;

        String rating = getRatingLabel(score);

        return new DelusionalRatingResult(score, rating, breakdown);
    }

    // total cost metric (0-25 points)
    private int calculateCostScore(Trip trip) {
        BigDecimal totalCost = trip.getTotalCost();
        if (totalCost == null) return 0;

        double cost = totalCost.doubleValue();

        // TODO: research and adjust these thresholds
        if (cost >= 500000) return 25;
        else if (cost >= 250000) return 22;
        else if (cost >= 100000) return 18;
        else if (cost >= 50000) return 14;
        else if (cost >= 25000) return 10;
        else if (cost >= 10000) return 6;
        else if (cost >= 5000) return 3;
        else return 1;
    }

    // trip duration metric (0-25 points)
    private int calculateTripDurationScore(Trip trip) {
        long tripDays = getTripDurationInDays(trip);
        if (tripDays == 0) return 0;

        // TODO: Adjust these thresholds based on research
        if (tripDays >= 60) return 25;          // 2+ months
        else if (tripDays >= 42) return 20;      // 6+ weeks
        else if (tripDays >= 21) return 15;      // 3-4 weeks
        else if (tripDays >= 10) return 8;       // 10-14 days
        else if (tripDays >= 7) return 3;        // 1 week
        else return 1;
    }

    // duration of specific destination (0-20 points)
    private int calculateExtendedStayScore(Trip trip) {
        List<Destination> destinations = trip.getDestinations();
        if (destinations == null || destinations.isEmpty()) return 0;

        long extendedStayCount = 0;
        long totalExtendedDays = 0;

        // TODO: adjust stay length thresholds
        for (Destination destination : destinations) {
            long stayDays = getDestinationDurationInDays(destination);

            if (stayDays >= 30) {           // 1+ month stay
                extendedStayCount += 3;
                totalExtendedDays += stayDays;
            } else if (stayDays >= 21) {    // 3+ weeks
                extendedStayCount += 2;
                totalExtendedDays += stayDays;
            } else if (stayDays >= 14) {    // 2+ weeks
                extendedStayCount += 1;
                totalExtendedDays += stayDays;
            }
        }

        int score = Math.toIntExact(Math.min(extendedStayCount * 5, 15));

        if (totalExtendedDays >= 60) score += 5;

        return Math.min(score, 20);
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