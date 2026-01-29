package com.tweb.movies.movie_service.dtos;

public class ActorDTO {
    private Integer id;
    private String name;
    private String role;

    public ActorDTO(Integer id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
}