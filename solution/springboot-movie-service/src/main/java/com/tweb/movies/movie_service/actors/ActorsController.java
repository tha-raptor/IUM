package com.tweb.movies.movie_service.actors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tweb.movies.movie_service.actors.dtos.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller responsible for managing data related to actors.
 * <p>
 * This controller provides endpoints to retrieve all actors, search them by name,
 * filter them by specific movies, and retrieve individual details.
 * </p>
 *
 * @author Beqiraj-Nada
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/actors")
@CrossOrigin(origins = "*")
@Tag(name = "Actors", description = "Endpoints for managing actors")
public class ActorsController {

    private final ActorsService actorsService;

    /**
     * Dependency injector for ActorsService.
     * * @param moviesService The service handling movie business logic.
     */
    @Autowired
    public ActorsController(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

    /**
     * Retrieves a complete list of all actors stored in the database.
     *
     * @return A list of {@link Actor} entities.
     */
    @Operation(summary = "Get all actors", description = "Returns a list of all actors")
    @GetMapping
    public List<ActorDTO> getAllActors() {
        return actorsService.getAllActors();
    }

    /**
     * Retrieves a specific actor by their unique ID.
     *
     * @param id The unique integer identifier of the actor.
     * @return A ResponseEntity containing the {@link Actor} if found, or a 404 Not Found status.
     */
    @Operation(summary = "Get actor by ID", description = "Returns details of a single actor")
    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Integer id) {
        Optional<Actor> actor = actorsService.getActorById(id);
        return actor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Searches for actors whose names match or contain the search query.
     *
     * @param name The partial or full name to search for.
     * @return A list of {@link ActorNameDTO} objects matching the criteria.
     */
    @Operation(summary = "Search actors", description = "Search for actors by name")
    @GetMapping("/search")
    public List<ActorNameDTO> searchActors(@RequestParam String name) {
        return actorsService.searchActorsByName(name);
    }

    /**
     * Retrieves all actors associated with a specific movie ID.
     *
     * @param movieId The unique identifier of the movie.
     * @return A list of {@link ActorDTO} representing the cast of the movie.
     */
    @Operation(summary = "Get actors by Movie ID", description = "Returns all actors who played in a specific movie")
    @GetMapping("/movie/{movieId}")
    public List<ActorDTO> getActorsByMovie(@PathVariable Integer movieId) {
        return actorsService.getActorsByMovieId(movieId);
    }
}