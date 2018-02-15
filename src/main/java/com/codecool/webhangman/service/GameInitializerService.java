package com.codecool.webhangman.service;

import com.codecool.webhangman.enums.Identity;
import com.codecool.webhangman.model.Country;
import com.codecool.webhangman.model.GuessTable;
import com.codecool.webhangman.model.Player;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class GameInitializerService {
    private RandomCountryLoaderService randomCountryLoaderService;
    private UserCreatorService userCreatorService;

    public GameInitializerService(RandomCountryLoaderService randomCountryLoaderService,
                                  UserCreatorService userCreatorService) {

        this.randomCountryLoaderService = randomCountryLoaderService;
        this.userCreatorService = userCreatorService;
    }

    public void initialize(HttpServletRequest request) {
        Player player = this.userCreatorService.createPlayer(request);
        this.userCreatorService.registerUserInSession(player, request);

        GuessTable guessTable = createGuessTable(request);
        registerGuessTableInSession(request, guessTable);
    }

    private GuessTable createGuessTable(HttpServletRequest request) {
        Country loadedCountry = this.randomCountryLoaderService.loadRandomCountry();
        return new GuessTable(loadedCountry);
    }

    private void registerGuessTableInSession(HttpServletRequest request, GuessTable guessTable) {
        final String key = Identity.GUESS_TABLE.getKey();
        request.getSession().setAttribute(key, guessTable);
    }
}
