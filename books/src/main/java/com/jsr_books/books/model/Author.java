package com.jsr_books.books.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Author(
        @JsonAlias({"name", "nombre"}) String name,
        @JsonAlias({"birth_year", "año_nacimiento"}) int birthYear,
        @JsonAlias({"death_year", "año_muerte"}) int deathYear
) { }