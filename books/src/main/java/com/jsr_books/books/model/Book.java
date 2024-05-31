package com.jsr_books.books.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record Book(
        @JsonAlias("id") Integer id,
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<Author> authors,
        @JsonAlias("subjects") List<String> subjects, //temas, materia
        @JsonAlias("bookshelves") List<String> bookshelves, // estanterias
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("copyright") Boolean copyright, // derechos
        @JsonAlias("media_type") String mediaType,
        @JsonAlias("formats") Formats formats,
        @JsonAlias("download_count") Integer downloadCount
) { }