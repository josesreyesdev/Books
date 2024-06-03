package com.jsr_books.books.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record SearchResult(
        @JsonAlias("count") Integer count,
        @JsonAlias("next") String next,
        @JsonAlias("previous") String previous,
        @JsonAlias("results")List<Book> results
) { }