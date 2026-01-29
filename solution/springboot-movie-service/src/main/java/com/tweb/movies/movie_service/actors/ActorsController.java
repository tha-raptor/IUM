package com.tweb.movies.movie_service.actors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tweb.movies.movie_service.dtos.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/actors")
@CrossOrigin(origins = "*")
@Tag(name = "Actors", description = "Endpoints for managing actors")
public class ActorsController {

    private final ActorsService actorsService;

    @Autowired
    public ActorsController(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

    @Operation(summary = "Get all actors", description = "Returns a list of all actors")
    @GetMapping
    public List<Actor> getAllActors() {
        return actorsService.getAllActors();
    }

    @Operation(summary = "Get actor by ID", description = "Returns details of a single actor")
    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Integer id) {
        Optional<Actor> actor = actorsService.getActorById(id);
        return actor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Search actors", description = "Search for actors by name (e.g. 'Leonardo')")
    @GetMapping("/search")
    public List<ActorNameDTO> searchActors(@RequestParam String name) {
        return actorsService.searchActorsByName(name);
    }

    @Operation(summary = "Get actors by Movie ID", description = "Returns all actors who played in a specific movie")
    @GetMapping("/movie/{movieId}")
    public List<ActorDTO> getActorsByMovie(@PathVariable Integer movieId) {
        return actorsService.getActorsByMovieId(movieId);
    }
}