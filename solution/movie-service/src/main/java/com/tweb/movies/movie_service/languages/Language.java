package com.tweb.movies.movie_service.languages;

import com.tweb.movies.movie_service.movies.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name="languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", columnDefinition = "TEXT")
    private String type;

    @Column(name = "language", columnDefinition = "TEXT")
    private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id") // Standardized to movie_id
    @JsonBackReference
    private Movie movie;

    public Language() {}

    public Language(String type, String language, Movie movie) {
        this.type = type;
        this.language = language;
        this.movie = movie;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}