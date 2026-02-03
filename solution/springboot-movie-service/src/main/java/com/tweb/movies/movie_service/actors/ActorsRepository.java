package com.tweb.movies.movie_service.actors;

import com.tweb.movies.movie_service.actors.dtos.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing Actors data from the database.
 *
 * @author Beqiraj-Nada
 * @version 1.0
 */
@Repository
public interface ActorsRepository extends JpaRepository<Actor, Integer> {
    // Ricerca per nome
    @Query("SELECT a FROM Actor a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ActorNameDTO> findByNameContaining(@Param("name") String name);

    // Ricerca per ID Film
    @Query("SELECT a FROM Actor a WHERE a.movie.id = :movieId")
    List<ActorDTO> findByMovieId(@Param("movieId") Integer movieId);
}