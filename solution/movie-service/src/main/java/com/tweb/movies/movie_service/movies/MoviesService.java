package com.tweb.movies.movie_service.movies;

import com.tweb.movies.movie_service.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoviesService {

    private final MoviesRepository movieRepository;

    @Autowired
    public MoviesService(MoviesRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // 1. Get all movies (Simple)
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // 2. Get single movie by ID
    public Optional<Movie> getMovieById(Integer id) {
        return movieRepository.findMovieById(id);
    }

    // 3. Get Top 5 Rated Movies (Uses DTO)
    public List<MovieTitlePosterDescDTO> getTop10Movies() {
        return movieRepository.findTop10ByRating();
    }

    // 4. Search Movies by Name (Native Query)
    public List<MovieByNameDTO> searchMoviesByName(String name) {
        return movieRepository.findMovieByNameStartingWith(name);
    }

    // 5. Get Movies by Genre
    public List<MovieTitlePosterDTO> getMoviesByGenre(String genreName) {
        return movieRepository.findTop20MoviesByGenre(genreName);
    }

    // 6. Get Movies by Age Rating
    public List<MovieTitlePosterRatingDTO> getMoviesByAge(int ageMin) {
        return movieRepository.findTop20MoviesByAgeMin(ageMin);
    }

    // 7. Get Worldwide Movies (Count)
    public List<MovieTitlePosterCountDTO> getWorldwideMovies() {
        return movieRepository.findWorldwideMovies();
    }

    // 8. Get Cult Movies by Language
    public List<MovieTitlePosterDTO> getCultMovies(String language) {
        return movieRepository.findCultLanguage(language);
    }
}