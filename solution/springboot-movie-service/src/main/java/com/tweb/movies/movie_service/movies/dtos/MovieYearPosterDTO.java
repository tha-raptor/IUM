package com.tweb.movies.movie_service.dtos;

public class MovieYearPosterDTO {
    private Integer id;
    private String name;
    private Integer date; // Year
    private String poster;

    public MovieYearPosterDTO(Integer id, String name, Integer date, String poster) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.poster = poster;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public Integer getDate() { return date; }
    public String getPoster() { return poster; }
}