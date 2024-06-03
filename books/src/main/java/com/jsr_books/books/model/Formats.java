package com.jsr_books.books.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Formats(
        @JsonAlias("html") String html,
        @JsonAlias("html_utf8") String htmlUtf8,
        @JsonAlias("epub") String epub,
        @JsonAlias("mobipocket") String mobipocket,
        @JsonAlias("plain_text_utf8") String plainTextUtf8,
        @JsonAlias("rdf") String rdf,
        @JsonAlias("jpeg") String jpeg,
        @JsonAlias("octet_stream") String octetStream,
        @JsonAlias("plain_text_us_ascii") String plainTextUsAscii
){ }