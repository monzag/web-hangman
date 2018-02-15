package com.codecool.webhangman.service;

import com.codecool.webhangman.dao.CountryDao;
import com.codecool.webhangman.model.Country;
import org.springframework.stereotype.Service;

@Service
public class RandomCountryLoaderService {
    private CountryDao countryDao;

    public RandomCountryLoaderService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public Country loadRandomCountry() {
        return this.countryDao.getRandomCountry();
    }
}
