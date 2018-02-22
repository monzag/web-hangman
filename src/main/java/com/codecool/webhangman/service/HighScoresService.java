package com.codecool.webhangman.service;

import com.codecool.webhangman.dao.ScoreDao;
import com.codecool.webhangman.model.Player;
import com.codecool.webhangman.model.Score;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HighScoresService {

    private ScoreDao scoreDao;

    public HighScoresService(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }

    public List<Score> getHighScores() {
        return scoreDao.findTop10ByOrderByMillisecondsSpentAsc();
    }

    public void addToHighScore(Player player) {
        Score score = new Score(player);
        scoreDao.save(score);
    }
}
