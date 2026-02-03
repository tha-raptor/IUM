package com.tweb.movies.movie_service.movies.dtos;

public class MovieTitlePosterDTO {
    private Integer id;
    private String name;
    private String poster;

    public MovieTitlePosterDTO(Integer id, String name, String poster) {
        this.id = id;
        this.name = name;
        this.poster = poster;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getPoster() { return poster; }
}