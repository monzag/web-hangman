package com.codecool.webhangman.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nick;
    private Date creationTime;
    private transient Integer healthPoints;

    public Player() {
    }

    public Player(String nick) {
        this.nick = nick;
        this.creationTime = new Date();
        this.healthPoints = 5;
    }

    //region setters and getters
    public String getNick() {
        return nick;
    }

    public Date getCreationTime( ) {
        return creationTime;
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

    public void setHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }
    //endregion

    public void reduceHealthPoints(Integer amount) {
        this.healthPoints -= amount;
    }

    public boolean isAlive() {
        return this.healthPoints > 0;
    }
}
