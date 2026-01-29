package com.tweb.movies.movie_service.dtos;

public class MovieTitlePosterCountDTO {
    private Integer id;
    private String name;
    private String poster;
    private Double rating;
    private Long count;

    public MovieTitlePosterCountDTO(Integer id, String name, String poster, Double rating, Long count) {
        this.id = id;
        this.name = name;
        this.poster = poster;
        this.rating = rating;
        this.count = count;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getPoster() { return poster; }
    public Double getRating() { return rating; }
    public Long getCount() { return count; }
}