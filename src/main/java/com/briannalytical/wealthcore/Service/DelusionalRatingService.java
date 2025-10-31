package com.briannalytical.wealthcore.Service;

import com.briannalytical.wealthcore.Dto.DelusionalRatingResult;
import com.briannalytical.wealthcore.Model.Entity.ItineraryItem;
import com.briannalytical.wealthcore.Model.Entity.Trip;
import com.briannalytical.wealthcore.Model.Enum.ItemType;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Destination;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
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

        // TODO: adjust based on research
        if (tripDays >= 60) return 25;          // 2+ months
        else if (tripDays >= 42) return 20;      // 6+ weeks
        else if (tripDays >= 21) return 15;      // 3-4 weeks
        else if (tripDays >= 10) return 8;       // 10-14 days
        else if (tripDays >= 7) return 3;        // 1 week
        else return 1;
    }

    // duration of specific destination (0-20 points)
    private int calculateExtendedStayScore(Trip trip) {
        List<Destination> destination = trip.getDestinations();
        if (destination == null || destination.isEmpty()) return 0;

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

    // estimated daily expense cost (1-15 points)
    private int calculateCostPerDayScore(Trip trip) {
        BigDecimal totalCost = trip.getTotalCost();
        if (totalCost == null) return 0;

        long tripDays = getTripDurationInDays(trip);
        if (tripDays == 0) return 0;

        BigDecimal costPerDay = BigDecimal.valueOf(totalCost.doubleValue() / tripDays);

        // TODO: adjust thresholds based on research
        if (costPerDay.compareTo(BigDecimal.valueOf(10000)) >= 0) return 15;
        else if (costPerDay.compareTo(BigDecimal.valueOf(5000)) >= 0) return 12;
        else if (costPerDay.compareTo(BigDecimal.valueOf(2000)) >= 0) return 9;
        else if (costPerDay.compareTo(BigDecimal.valueOf(1000)) >= 0) return 6;
        else if (costPerDay.compareTo(BigDecimal.valueOf(500)) >= 0) return 3;
        else return 1;
    }

    // daily spending on experiences (0-15 points)
    private int calculateDailySpendingScore(Trip trip) {
        List<Destination> destinations = trip.getDestinations();
        if (destinations == null || destinations.isEmpty()) return 0;

        long tripDays = getTripDurationInDays(trip);
        if (tripDays == 0) return 0;

        // add up all DAILY_SPENDING items
        BigDecimal totalDailySpending = BigDecimal.ZERO;

        for (Destination destination : destinations) {
            if (destination.getItineraryItems() == null) continue;

            for (ItineraryItem item : destination.getItineraryItems()) {
                if (item.getItemType() == ItemType.DAILY_SPENDING && item.getCost() != null) {
                    totalDailySpending = totalDailySpending.add(item.getCost());
                }
            }
        }
        double avgDailySpending = totalDailySpending.doubleValue() / tripDays;

        // TODO: research daily spending amounts
        if (avgDailySpending >= 2000) return 15;
        else if (avgDailySpending >= 1000) return 12;
        else if (avgDailySpending >= 500) return 9;
        else if (avgDailySpending >= 250) return 6;
        else if (avgDailySpending >= 100) return 3;
        else return 1;
    }

    // Determine rating label based on score
    private String getRatingLabel(int score) {
        if (score >= 81) return "Transcended the Fourth Dimension of Delusion";
        else if (score >= 61) return "Soul Has Departed the Body";
        else if (score >= 41) return "Absolutely Unhinged";
        else if (score >= 21) return "Reasonably Delusional";
        else return "Optimistically Rational";
    }


    // helper
    // get trip duration in days
    private long getTripDurationInDays(Trip trip) {
        List<com.briannalytical.wealthcore.Model.Entity.Destination> destinations = trip.getDestinations();
        if (destinations == null || destinations.isEmpty()) return 0;

        com.briannalytical.wealthcore.Model.Entity.Destination firstDestination = destinations.stream()
                .filter(destination -> destination.getArrivalDate() != null)
                .min((d1, d2) -> d1.getArrivalDate().compareTo(d2.getArrivalDate()))
                .orElse(null);

        com.briannalytical.wealthcore.Model.Entity.Destination lastDestination = destinations.stream()
                .filter(destination -> destination.getDepartureDate() != null)
                .max((d1, d2) -> d2.getDepartureDate().compareTo(d1.getDepartureDate()))
                .orElse(null);

        if (firstDestination == null || lastDestination == null) return 0;
        if (firstDestination.getArrivalDate() == null || lastDestination.getDepartureDate() == null) return 0;

        return ChronoUnit.DAYS.between(
                firstDestination.getArrivalDate(),
                lastDestination.getDepartureDate()
        );
    }

    // Helper: Get destination duration in days
    private long getDestinationDurationInDays(Destination destination) {
        if (destination.getArrivalDate() == null || destination.getDepartureDate() == null) return 0;

        return ChronoUnit.DAYS.between(
                destination.getArrivalDate(),
                destination.getDepartureDate()
        );
    }
}