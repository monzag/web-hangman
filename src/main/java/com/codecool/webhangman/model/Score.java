package com.codecool.webhangman.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.function.Function;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String playerName;
    private long millisecondsSpent;

    public Score() {}

    public Score(Player player) {
        this.playerName = player.getNick();

        Function<Date, Long> getMillis = Date::getTime;
        Date currentTime = new Date();
        this.millisecondsSpent = getMillis.apply(currentTime) - getMillis.apply(player.getCreationTime());
    }

    public Integer getId( ) {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayerName( ) {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getMillisecondsSpent( ) {
        return millisecondsSpent;
    }

    public void setMillisecondsSpent(long millisecondsSpent) {
        this.millisecondsSpent = millisecondsSpent;
    }
}
