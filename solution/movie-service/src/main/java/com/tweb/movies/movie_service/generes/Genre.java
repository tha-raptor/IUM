package com.example.spring_boot_server.genres;

import com.example.spring_boot_server.movies.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "genre", columnDefinition = "TEXT")
    private String genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @JsonBackReference
    private Movie movie;

    public Genre() {}

    public Genre(String genre, Movie movie) {
        this.genre = genre;
        this.movie = movie;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                '}';
    }
}