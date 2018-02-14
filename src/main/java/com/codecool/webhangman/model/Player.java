package com.codecool.webhangman.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nick;
    private Integer score;
    private transient Integer healthPoints;

    public Player(String nick, Integer score, Integer healthPoints) {
        this.nick = nick;
        this.score = score;
        this.healthPoints = healthPoints;
    }

    //region setters and getters
    public String getNick() {
        return nick;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getHealthPoints() {
        return healthPoints;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }
    //endregion

    public void addPoints(Integer points) {
        this.score += points;
    }

    public void reduceHealthPoints(Integer amount) {
        this.healthPoints -= amount;
    }

    public boolean isAlive() {
        return this.healthPoints > 0;
    }
}
