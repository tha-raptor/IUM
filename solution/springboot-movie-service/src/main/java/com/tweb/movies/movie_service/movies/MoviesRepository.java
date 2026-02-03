package com.tweb.movies.movie_service.movies;

import com.tweb.movies.movie_service.movies.dtos.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing Movie data from the database.
 *
 * @author Beqiraj-Nada
 * @version 1.0
 */
public interface MoviesRepository extends JpaRepository<Movie, Integer> {

	// Film paginati, ordine per rating
	@Query("SELECT new com.tweb.movies.movie_service.movies.dtos.MovieTitlePosterRatingDTO(m.id, m.name, m.poster.link, m.rating) " +
			"FROM Movie m " +
			"WHERE m.rating IS NOT NULL " +
			"ORDER BY m.rating DESC")
	Page<MovieTitlePosterRatingDTO> findAllMoviesPaged(Pageable pageable);

	// Film "leggeri", ordine per rating
	@Query("SELECT new com.tweb.movies.movie_service.movies.dtos.MovieTitlePosterDTO(m.id, m.name, m.poster.link) " +
			"FROM Movie m " +
			"ORDER BY m.rating DESC")
	List<MovieTitlePosterDTO> findAllLight();

	// Film top 10, ordine per rating
	@Query(value="SELECT new com.tweb.movies.movie_service.movies.dtos.MovieTitlePosterDescDTO(m.id, m.name, m.description, m.rating, m.poster.link) " +
			"FROM Movie m " +
			"JOIN Poster p ON m.id = p.movie.id " +
			"WHERE m.rating IS NOT NULL AND m.date IS NOT NULL " +
			"ORDER BY m.rating DESC, m.date DESC " +
			"LIMIT 10")
	List<MovieTitlePosterDescDTO> findTop10ByRating();

	// Film per nome, ordine per rating
	@Query(value = "SELECT m.id, m.name, m.date, p.link " +
			"FROM movies m " +
			"JOIN posters p ON m.id = p.movie_id " +
			"WHERE to_tsvector('english', m.name) @@ plainto_tsquery('english', REPLACE(:name, ' ', '&')) " +
			"ORDER BY " +
			"   CASE " +
			"       WHEN m.name ILIKE :name THEN 1 " +
			"       WHEN m.name ILIKE CONCAT(:name, '%') THEN 2 " +
			"       ELSE 3 " +
			"   END, " +
			"   COALESCE(m.rating, 0) DESC, " +
			"   m.date DESC " +
			"LIMIT 20", nativeQuery = true)
	List<MovieByNameDTO> findMovieByNameStartingWith(@Param("name") String name);

	// Film per genere, ordine per rating
	@Query(value = "SELECT new com.tweb.movies.movie_service.movies.dtos.MovieTitlePosterRatingDTO(m.id, m.name, m.poster.link, m.rating) " +
			"FROM Movie m " +
			"JOIN Genre g ON g.movie.id = m.id " +
			"WHERE g.genre = :genreName AND m.rating IS NOT NULL " +
			"ORDER BY m.rating DESC ")
	Page<MovieTitlePosterRatingDTO> findMoviesByGenre(@Param("genreName") String genreName, Pageable pageable);

	// Film per etÃ , ordine per rating
	@Query(value="SELECT DISTINCT new com.tweb.movies.movie_service.movies.dtos.MovieTitlePosterRatingDTO(m.id, m.name, m.poster.link, m.rating) " +
			"FROM Movie m " +
			"JOIN Release r on r.movie.id = m.id " +
			"Where r.age_min >= :age_min AND m.rating IS NOT NULL " +
			"ORDER BY m.rating DESC " +
			"LIMIT 20")
	List<MovieTitlePosterRatingDTO> findTop20MoviesByAgeMin(@Param("age_min") int age_min);

	// Film per ID
	Optional<Movie> findMovieById(Integer id);

	// Film per lingua, ordine per rating
	@Query(value="SELECT new com.tweb.movies.movie_service.movies.dtos.MovieTitlePosterDTO(m.id, m.name, m.poster.link) FROM Movie m " +
			"JOIN Language l ON l.movie.id = m.id " +
			"WHERE l.type IN ('Primary Language', 'Spoken language') AND l.language=:language AND m.rating IS NOT NULL " +
			"ORDER BY m.rating DESC, m.date DESC " +
			"LIMIT 20")
	List<MovieTitlePosterDTO> findCultLanguage(@Param("language") String language);

	// Film per studio, ordine per rating
	@Query("SELECT new com.tweb.movies.movie_service.movies.dtos.MovieTitlePosterDTO(m.id, m.name, m.poster.link) " +
			"FROM Movie m " +
			"JOIN m.studios s " +
			"WHERE s.id = :studioId " +
			"ORDER BY m.rating DESC")
	List<MovieTitlePosterDTO> findByStudioId(@Param("studioId") Integer studioId);
}