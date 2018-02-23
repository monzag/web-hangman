package com.codecool.webhangman.service;

import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;
import com.codecool.webhangman.model.PlayerActivity;
import com.codecool.webhangman.service.sessioninterpreter.RequestInterpreter;
import com.codecool.webhangman.service.sessioninterpreter.SessionInterpreterService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SessionInterpreterFacade {
    private SessionInterpreterService sessionInterpreterService;

    public SessionInterpreterFacade(SessionInterpreterService sessionInterpreterService) {
        this.sessionInterpreterService = sessionInterpreterService;
    }

    public Player getPlayer(HttpServletRequest request) {
        RequestInterpreter requestInterpreter = this.sessionInterpreterService.create(request);
        return requestInterpreter.retrievePlayer();
    }

    public GuessTable getGuessTable(HttpServletRequest request) {
        RequestInterpreter requestInterpreter = this.sessionInterpreterService.create(request);
        return requestInterpreter.retrieveGuessTable();
    }

    public PlayerActivity getPlayerActivity(HttpServletRequest request) {
        RequestInterpreter requestInterpreter = this.sessionInterpreterService.create(request);
        return new PlayerActivity(requestInterpreter.retrievePlayer(), requestInterpreter.retrieveGuessTable());
    }
}
