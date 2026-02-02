package com.tweb.movies.movie_service.countries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountriesRepository extends JpaRepository<Country, Integer> {
}