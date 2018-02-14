package com.codecool.webhangman.dao;

import com.codecool.webhangman.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerDao extends CrudRepository<Player, Integer> {
}
