package com.tweb.movies.movie_service.movies;

import com.tweb.movies.movie_service.movies.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing data related to movies.
 * provides endpoints for searching, filtering and paginated results.
 * * @author Beqiraj-Nada
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/movies")
@CrossOrigin(origins = "*")
@Tag(name = "Movies", description = "Endpoints for managing and retrieving movies")
public class MoviesController {

    private final MoviesService moviesService;

    /**
     * Dependency injector for MoviesService.
     * * @param moviesService The service handling movie business logic.
     */
    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    /**
     * Retrieves all movies stored in the database.
     * * @return A list of all Movie entities.
     */
    @Operation(summary = "Get all movies", description = "Returns a list of all movies in the database")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public List<Movie> getAllMovies() {
        return moviesService.getAllMovies();
    }

    /**
     * Retrieves a specific movie by its unique identifier.
     * * @param id The unique ID of the movie.
     * @return ResponseEntity containing the Movie object if found, or 404 status.
     */
    @Operation(summary = "Get movie by ID", description = "Returns detailed information about a single movie using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found"),
            @ApiResponse(responseCode = "404", description = "Movie not found with the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
        Optional<Movie> movie = moviesService.getMovieById(id);
        return movie.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves the top 10 highest-rated movies.
     * * @return A list of DTOs containing title, poster, and description of top movies.
     */
    @Operation(summary = "Get Top 10 Rated Movies", description = "Returns a curated list of the 10 highest-rated movies.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved top 10 movies")
    @GetMapping("/top10")
    public List<MovieTitlePosterDescDTO> getTop10Movies() {
        return moviesService.getTop10Movies();
    }


    /**
     * Searches for movies based on a title string.
     * * @param name The partial or full title of the movie.
     * @return A list of movies matching the search criteria.
     */
    @Operation(summary = "Search movies", description = "Search for movies by name (supports partial matches)")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    @GetMapping("/search")
    public List<MovieByNameDTO> searchMovies(@RequestParam String name) {
        return moviesService.searchMoviesByName(name);
    }

    /**
     * Filters movies by a specific genre.
     * * @param genreName The name of the genre (e.g., 'Action', 'Drama').
     * @return A list of movies belonging to the specified genre.
     */
    @Operation(summary = "Get movies by Genre", description = "Returns movies for a specific genre")
    @ApiResponse(responseCode = "200", description = "Genre-based list retrieved")
    @GetMapping("/genre/{genreName}")
    public List<MovieTitlePosterDTO> getMoviesByGenre(@PathVariable String genreName) {
        return moviesService.getMoviesByGenre(genreName);
    }

    /**
     * Filters movies based on a minimum age requirement.
     * * @param ageMin The minimum age for the rating filter.
     * @return A list of movies suitable for the specified age.
     */
    @Operation(summary = "Get movies by Age Rating", description = "Returns movies suitable for a minimum age")
    @ApiResponse(responseCode = "200", description = "Age-filtered list retrieved")
    @GetMapping("/age/{ageMin}")
    public List<MovieTitlePosterRatingDTO> getMoviesByAge(@PathVariable int ageMin) {
        return moviesService.getMoviesByAge(ageMin);
    }

    /**
     * Retrieves a paginated list of movies sorted by rating.
     * * @param page The page index to retrieve (starts at 0).
     * @param size The number of records per page.
     * @return A list of movies for the requested page.
     */
    @Operation(summary = "Get Paged Movies", description = "Returns paged movies sorted by rating")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paged")
    public List<MovieTitlePosterRatingDTO> getMoviesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        return moviesService.getMoviesPaged(page, size).getContent();
    }

    /**
     * Retrieves movies based on a specific language, often categorized as cult classics.
     * * @param language The language code or name (e.g., 'Italian', 'French').
     * @return A list of movies in the specified language.
     */
    @Operation(summary = "Get Cult Movies", description = "Returns Top 20 movies by specific language")
    @ApiResponse(responseCode = "200", description = "Language-based list retrieved")
    @GetMapping("/cult/{language}")
    public List<MovieTitlePosterDTO> getCultMovies(@PathVariable String language) {
        return moviesService.getCultMovies(language);
    }
}