package com.tweb.movies.movie_service.movies;

import com.tweb.movies.movie_service.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
public class MoviesService {

    private final MoviesRepository movieRepository;

    @Autowired
    public MoviesService(MoviesRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Tutti i film
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Film per ID
    public Optional<Movie> getMovieById(Integer id) {
        return movieRepository.findMovieById(id);
    }

    // Top 10 Film per rating
    public List<MovieTitlePosterDescDTO> getTop10Movies() {
        return movieRepository.findTop10ByRating();
    }

    // Film per titolo
    public List<MovieByNameDTO> searchMoviesByName(String name) {
        return movieRepository.findMovieByNameStartingWith(name);
    }

    //Film per genere
    public List<MovieTitlePosterDTO> getMoviesByGenre(String genreName) {
        return movieRepository.findTop20MoviesByGenre(genreName);
    }

    // Film per età minima
    public List<MovieTitlePosterRatingDTO> getMoviesByAge(int ageMin) {
        return movieRepository.findTop20MoviesByAgeMin(ageMin);
    }

    // Tutti i film globali + count
    public List<MovieTitlePosterCountDTO> getWorldwideMovies() {
        return movieRepository.findWorldwideMovies();
    }

    // Film per lingua e rating
    public List<MovieTitlePosterDTO> getCultMovies(String language) {
        return movieRepository.findCultLanguage(language);
    }

    //Film paginati
    public Page<MovieTitlePosterRatingDTO> getMoviesPaged(int page,int size) {
        return movieRepository.findAllByRatingDesc(PageRequest.of(page, size));
    }
}