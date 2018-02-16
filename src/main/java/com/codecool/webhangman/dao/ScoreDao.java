package com.codecool.webhangman.dao;

import com.codecool.webhangman.model.Score;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreDao extends CrudRepository<Score, Integer> {
    List<Score> findTop10ByOrderByMillisecondsSpentAsc();
}
