package com.tweb.movies.movie_service.themes;

import com.tweb.movies.movie_service.movies.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name="themes")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "theme", columnDefinition = "TEXT")
    private String theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @JsonBackReference
    private Movie movie;

    public Theme() {}

    public Theme(String theme, Movie movie) {
        this.theme = theme;
        this.movie = movie;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                '}';
    }
}