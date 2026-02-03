package com.tweb.movies.movie_service.actors.dtos;

public class ActorNameDTO {
    private Integer id;
    private String name;

    public ActorNameDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
}