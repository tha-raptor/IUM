package com.tweb.movies.movie_service.posters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostersRepository extends JpaRepository<Poster, Integer> {
}