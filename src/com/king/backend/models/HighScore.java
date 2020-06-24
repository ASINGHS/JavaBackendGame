package com.king.backend.models;

import java.io.Serializable;
import java.util.concurrent.ConcurrentSkipListSet;

public class HighScore implements Serializable {

    
    private static final long serialVersionUID = 1L;

	private static final int MAX_SCORES = 15;

    private ConcurrentSkipListSet<UserScore> highScores;

    
    public HighScore() {
        this.highScores = new ConcurrentSkipListSet<>();
    }

    
    public ConcurrentSkipListSet<UserScore> getHighScores() {
        return highScores;
    }

    
    public void setHighScores(ConcurrentSkipListSet<UserScore> highScores) {
        this.highScores = highScores;
    }

    
    public void add(UserScore userScore) {
        UserScore oldUserScore = findExistingUserScore(userScore);
        if (oldUserScore != null) {
            if (oldUserScore.getScore() >= userScore.getScore())
                return;
            highScores.remove(oldUserScore);
        }
        highScores.add(userScore);
        //System.out.println("highScores1: "+highScores);
        if (highScores.size() > MAX_SCORES) {
            highScores.pollLast();
        }
        //System.out.println("highScores2: "+highScores);
    }

    // Method to find an UserScore with the same UserId from the selected UserScore
   
   
    public UserScore findExistingUserScore(UserScore userScore) {
        for (UserScore u : highScores) {
            if (u.getUserId() == userScore.getUserId()) {
                return u;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return highScores.toString().replace("[", "").replace("]", "").replace(", ", ",");
    }
}
