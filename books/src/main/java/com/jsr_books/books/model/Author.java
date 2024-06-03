package com.jsr_books.books.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Author(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") int birthYear, // año nacimiento
        @JsonAlias("death_year") int deathYear // año_muerte
) { }