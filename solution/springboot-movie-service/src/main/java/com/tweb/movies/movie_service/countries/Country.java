package com.tweb.movies.movie_service.countries;

import jakarta.persistence.*;

import com.tweb.movies.movie_service.movies.Movie;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "movie_id", nullable = false, columnDefinition = "BIGINT")
    private Integer movie_id;

    @Column(name = "country", nullable = false, columnDefinition = "TEXT")
    private String country;


    public Country() {}

    public Country(Integer movie_id, String country) {
        this.movie_id = movie_id;
        this.country = country;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getMovie_film() {return movie_id;}
    public void setMovie_film(Integer movie_id) {this.movie_id = movie_id;}

    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}
}