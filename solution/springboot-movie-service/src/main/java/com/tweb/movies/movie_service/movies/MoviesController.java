package com.tweb.movies.movie_service.movies;

import com.tweb.movies.movie_service.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
@CrossOrigin(origins = "*")
@Tag(name = "Movies", description = "Endpoints for managing and retrieving movies")
public class MoviesController {

    private final MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Operation(summary = "Get all movies", description = "Returns a list of all movies in the database")
    @GetMapping
    public List<Movie> getAllMovies() {
        return moviesService.getAllMovies();
    }

    @Operation(summary = "Get movie by ID", description = "Returns a single movie details by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
        Optional<Movie> movie = moviesService.getMovieById(id);
        return movie.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Top 10 Rated Movies", description = "Returns the top 10 highest rated movies with descriptions")
    @GetMapping("/top5")
    public List<MovieTitlePosterDescDTO> getTop10Movies() {
        return moviesService.getTop10Movies();
    }

    @Operation(summary = "Search movies", description = "Search for movies by name (supports partial matches)")
    @GetMapping("/search")
    public List<MovieByNameDTO> searchMovies(@RequestParam String name) {
        return moviesService.searchMoviesByName(name);
    }

    @Operation(summary = "Get movies by Genre", description = "Returns top 20 movies for a specific genre")
    @GetMapping("/genre/{genreName}")
    public List<MovieTitlePosterDTO> getMoviesByGenre(@PathVariable String genreName) {
        return moviesService.getMoviesByGenre(genreName);
    }

    @Operation(summary = "Get movies by Age Rating", description = "Returns movies suitable for a minimum age")
    @GetMapping("/age/{ageMin}")
    public List<MovieTitlePosterRatingDTO> getMoviesByAge(@PathVariable int ageMin) {
        return moviesService.getMoviesByAge(ageMin);
    }

    @Operation(summary = "Get Worldwide Movies", description = "Returns movies sorted by release count across countries")
    @GetMapping("/worldwide")
    public List<MovieTitlePosterCountDTO> getWorldwideMovies() {
        return moviesService.getWorldwideMovies();
    }

    @Operation(summary = "Get Cult Movies", description = "Returns movies by specific language")
    @GetMapping("/cult/{language}")
    public List<MovieTitlePosterDTO> getCultMovies(@PathVariable String language) {
        return moviesService.getCultMovies(language);
    }
}