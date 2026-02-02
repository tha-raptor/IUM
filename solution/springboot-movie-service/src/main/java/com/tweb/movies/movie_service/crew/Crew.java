package com.tweb.movies.movie_service.crew;

import com.tweb.movies.movie_service.movies.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
@Table(name="crew")
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    @Column(name = "role", columnDefinition = "TEXT")
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @JsonBackReference
    private Movie movie;

    public Crew() {}

    public Crew(Integer id, String role, String name, Movie movie) {
        this.role = role;
        this.name = name;
        this.movie = movie;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }
}