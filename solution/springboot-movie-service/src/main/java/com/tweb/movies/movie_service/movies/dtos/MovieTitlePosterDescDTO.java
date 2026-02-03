package com.tweb.movies.movie_service.movies.dtos;

public class MovieTitlePosterDescDTO {
    private Integer id;
    private String name;
    private String description;
    private Double rating;
    private String link;

    public MovieTitlePosterDescDTO(Integer id, String name, String description, Double rating, String link) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.link = link;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getRating() { return rating; }
    public String getLink() { return link; }
}