package com.tweb.movies.movie_service.actors;

import com.tweb.movies.movie_service.actors.dtos.*;
import com.tweb.movies.movie_service.movies.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that handles the logic for Actor operations.
 * <p>
 * This class acts as an intermediary between the Controller and the Repository.
 * </p>
 *
 * @author
 * @version 1.0
 * @see MoviesRepository
 */
@Service
public class ActorsService {

    private final ActorsRepository actorsRepository;

    @Autowired
    public ActorsService(ActorsRepository actorsRepository) {
        this.actorsRepository = actorsRepository;
    }

    public List<ActorDTO> getAllActors() { return actorsRepository.getAllActors(); }

    public Optional<Actor> getActorById(Integer id) {
        return actorsRepository.findById(id);
    }

    public List<ActorNameDTO> searchActorsByName(String name) {
        return actorsRepository.findByNameContaining(name);
    }

    public List<ActorDTO> getActorsByMovieId(Integer movieId) {
        return actorsRepository.findByMovieId(movieId);
    }
}