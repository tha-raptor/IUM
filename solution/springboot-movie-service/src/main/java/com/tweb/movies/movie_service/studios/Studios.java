package com.tweb.movies.movie_service.studios;

import com.tweb.movies.movie_service.movies.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name="studios")
public class Studios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "studio", columnDefinition = "TEXT")
    private String studio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @JsonBackReference
    private Movie movie;

    public Studios() {}

    public Studios(String studio, Movie movie) {
        this.studio = studio;
        this.movie = movie;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getStudio() { return studio; }
    public void setStudio(String studio) { this.studio = studio; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    @Override
    public String toString() {
        return "Studios{" +
                "id=" + id +
                ", studio='" + studio + '\'' +
                '}';
    }
}