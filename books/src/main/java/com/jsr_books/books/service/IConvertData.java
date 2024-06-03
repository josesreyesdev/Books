package com.jsr_books.books.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> genericClass);
}
