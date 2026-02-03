package com.tweb.movies.movie_service.movies.dtos;

public class MovieTitlePosterCountDTO {
    private Integer id;
    private String name;
    private String link;
    private Double rating;
    private Long count;

    public MovieTitlePosterCountDTO(Integer id, String name, String link, Double rating, Long count) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.rating = rating;
        this.count = count;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getLink() { return link; }
    public Double getRating() { return rating; }
    public Long getCount() { return count; }
}