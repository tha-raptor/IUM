package com.tweb.movies.movie_service.posters;

import com.tweb.movies.movie_service.movies.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "posters")
public class Poster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @JsonBackReference
    private Movie movie;

    public Poster() {}

    public Poster(String link, Movie movie) {
        this.link = link;
        this.movie = movie;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    @Override
    public String toString() {
        return "Poster{" +
                "id=" + id +
                ", link='" + link + '\'' +
                '}';
    }
}