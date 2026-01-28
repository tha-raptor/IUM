package com.tweb.movies.movie_service.dtos;

public interface MovieByIdDTO {
    Integer getId();
    String getName();
    Integer getDate();
    Double getRating();
    String getDescription();
    Integer getMinute();
    String getTagline();
}