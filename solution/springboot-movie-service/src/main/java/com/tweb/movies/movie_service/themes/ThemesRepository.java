package com.tweb.movies.movie_service.themes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemesRepository extends JpaRepository<Theme, Integer> {
}