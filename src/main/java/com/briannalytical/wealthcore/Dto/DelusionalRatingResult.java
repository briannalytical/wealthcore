package com.briannalytical.wealthcore.Dto;

import java.util.Map;

public class DelusionalRatingResult {

    private int score;
    private String rating;
    private Map<String, Integer> breakdown;


    public DelusionalRatingResult() {}

    public DelusionalRatingResult(int score, String rating, Map<String, Integer> breakdown) {
        this.score = score;
        this.rating = rating;
        this.breakdown = breakdown;
    }


    public int getScore() {return score;}

    public String getRating() {return rating;}

    public Map<String, Integer> getBreakdown() {return breakdown;}


    public void setScore(int score) {this.score = score;}

    public void setRating(String rating) {this.rating = rating;}

    public void setBreakdown(Map<String, Integer> breakdown) {this.breakdown = breakdown;}
}
