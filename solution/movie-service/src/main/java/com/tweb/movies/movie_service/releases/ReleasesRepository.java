package com.tweb.movies.movie_service.releases;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleasesRepository extends JpaRepository<Release, Integer> {
}