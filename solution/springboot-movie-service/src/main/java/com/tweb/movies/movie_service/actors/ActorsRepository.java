package com.tweb.movies.movie_service.actors;

import com.tweb.movies.movie_service.dtos.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorsRepository extends JpaRepository<Actor, Integer> {
    // 1. Search by Name (Case insensitive, partial match)
    // Example: "Leo" finds "Leonardo DiCaprio"
    @Query("SELECT a FROM Actor a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ActorNameDTO> findByNameContaining(@Param("name") String name);

    // 2. Find Actors by Movie ID
    // We join the Actor table with the Movie table through the 'movie' relationship
    @Query("SELECT a FROM Actor a WHERE a.movie.id = :movieId")
    List<ActorDTO> findByMovieId(@Param("movieId") Integer movieId);
}