package com.tweb.movies.movie_service.releases;

import com.tweb.movies.movie_service.movies.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="releases")
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "country", columnDefinition = "TEXT")
    private String country;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @Column(name = "type", columnDefinition = "TEXT")
    private String type;

    @Column(name = "age_min")
    private Integer age_min;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @JsonBackReference
    private Movie movie;

    public Release() {}

    public Release(String country, LocalDateTime date, String type, Integer age_min, Movie movie) {
        this.country = country;
        this.date = date;
        this.type = type;
        this.age_min = age_min;
        this.movie = movie;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getAge_min() { return age_min; }
    public void setAge_min(Integer age_min) { this.age_min = age_min; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    @Override
    public String toString() {
        return "Release{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", age_min=" + age_min +
                '}';
    }
}