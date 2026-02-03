package com.tweb.movies.movie_service.movies.dtos;

public class MovieTitlePosterRatingDTO {
    private Integer id;
    private String name;
    private String poster;
    private Double rating;

    public MovieTitlePosterRatingDTO(Integer id, String name, String poster, Double rating) {
        this.id = id;
        this.name = name;
        this.poster = poster;
        this.rating = rating;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getPoster() { return poster; }
    public Double getRating() { return rating; }
}