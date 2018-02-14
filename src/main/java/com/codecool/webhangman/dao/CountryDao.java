package com.codecool.webhangman.dao;

import com.codecool.webhangman.model.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryDao extends CrudRepository<Country, Integer> {}
