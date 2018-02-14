package com.codecool.webhangman.dao;

import com.codecool.webhangman.model.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerDao extends CrudRepository<Player, Integer> {
}
