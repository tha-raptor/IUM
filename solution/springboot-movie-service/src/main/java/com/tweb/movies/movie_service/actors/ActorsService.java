package com.tweb.movies.movie_service.actors;

import com.tweb.movies.movie_service.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorsService {

    private final ActorsRepository actorsRepository;

    @Autowired
    public ActorsService(ActorsRepository actorsRepository) {
        this.actorsRepository = actorsRepository;
    }

    public List<Actor> getAllActors() {
        return actorsRepository.findAll();
    }

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