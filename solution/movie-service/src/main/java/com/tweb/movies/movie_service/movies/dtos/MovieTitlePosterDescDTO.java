package com.tweb.movies.movie_service.dtos;

public class MovieTitlePosterDescDTO {
    private Integer id;
    private String name;
    private String description;
    private Double rating;
    private String poster;

    public MovieTitlePosterDescDTO(Integer id, String name, String description, Double rating, String poster) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.poster = poster;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getRating() { return rating; }
    public String getPoster() { return poster; }
}