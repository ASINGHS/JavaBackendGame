package com.king.backend.manager;

import com.king.backend.models.HighScore;
import com.king.backend.models.UserScore;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class ScoreManager {

   
     // ConcurrentMap<levelId, HighScore> with all the HighScore for all the Levels
     
    private ConcurrentMap<Integer, HighScore> highScoresMap;

    
    public ScoreManager() {
        highScoresMap = new ConcurrentHashMap<>();
    }

   
    public synchronized void saveScore(final UserScore userScore, final Integer levelId) {
        HighScore highScore = highScoresMap.get(levelId);
        if (highScore == null) {
            highScore = new HighScore();
            highScoresMap.putIfAbsent(levelId, highScore);
        }
        highScore.add(userScore);
        System.out.println("highScoresMap: "+highScoresMap);
    }

   
    public HighScore getHighScore(final int levelId) {
        if (!highScoresMap.containsKey(levelId)) {
            return new HighScore();
        }
        return highScoresMap.get(levelId);
    }


}
