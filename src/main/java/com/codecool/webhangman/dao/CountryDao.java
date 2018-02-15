package com.codecool.webhangman.dao;

import com.codecool.webhangman.model.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryDao extends CrudRepository<Country, Integer> {
    @Query(value="SELECT * FROM country ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Country getRandomCountry();
}
