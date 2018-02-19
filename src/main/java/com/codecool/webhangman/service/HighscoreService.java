package com.codecool.webhangman.service;

import com.codecool.webhangman.dao.ScoreDao;
import com.codecool.webhangman.model.Player;
import com.codecool.webhangman.model.Score;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HighscoreService {

    private ScoreDao scoreDao;

    public HighscoreService(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }

    public List<Score> getHighscore() {
        return scoreDao.findTop10ByOrderByMillisecondsSpentAsc();
    }

}
