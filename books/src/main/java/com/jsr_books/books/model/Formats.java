package com.jsr_books.books.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Formats(
        @JsonAlias({"text/html", "html"}) String html,
        @JsonAlias({"text/html; charset=utf-8", "html_utf8"}) String htmlUtf8,
        @JsonAlias({"application/epub+zip", "epub"}) String epub,
        @JsonAlias({"application/x-mobipocket-ebook", "mobipocket"}) String mobipocket,
        @JsonAlias({"text/plain; charset=utf-8", "plain_text_utf8"}) String plainTextUtf8,
        @JsonAlias({"application/rdf+xml", "rdf"}) String rdf,
        @JsonAlias({"image/jpeg", "jpeg"}) String jpeg,
        @JsonAlias({"application/octet-stream", "octet_stream"}) String octetStream,
        @JsonAlias({"text/plain; charset=us-ascii", "plain_text_us_ascii"}) String plainTextUsAscii
) { }