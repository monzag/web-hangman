package com.codecool.webhangman.dao;

import com.codecool.webhangman.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryDao extends CrudRepository<Country, Integer> {}
