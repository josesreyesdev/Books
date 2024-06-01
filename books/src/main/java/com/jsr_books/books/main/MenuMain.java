package com.jsr_books.books.main;

import com.jsr_books.books.model.Book;
import com.jsr_books.books.model.SearchResult;
import com.jsr_books.books.service.ApiService;
import com.jsr_books.books.service.ConvertData;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuMain {

    private final ApiService apiService = new ApiService();
    private final ConvertData convertData = new ConvertData();

    private final String BASE_URL = "https://gutendex.com/";
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        try {
            while (true) {

                // get search result data
                SearchResult searchResultData = fetchResultData();
                System.out.println();
                System.out.println("All Data: " + searchResultData);

                // get specific books for user
                Integer limitNumber = getUserInput(Integer.class, "Ingresa el total de libros que deseas solicitar");
                getBookData(searchResultData, limitNumber);


                String exit = getUserInput(String.class, "Ingresa 'exit' para terminar ó ingrese cualquier otra letra para hacer nueva solicitud");
                if (exit.equalsIgnoreCase("Exit")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBookData(SearchResult searchResult, Integer limitNumber) {
        System.out.println();
        AtomicInteger ind = new AtomicInteger(1);
        searchResult.results().stream()
                .sorted(Comparator.comparingInt(Book::downloadCount).reversed())
                .limit(limitNumber)
                .forEach(book -> System.out.println(ind.getAndIncrement() +"->"+ book));
    }

    private SearchResult fetchResultData() {
        String url = BASE_URL + "books/";
        String json = apiService.getData(url);
        return convertData.getData(json, SearchResult.class);
    }

    private String encodeAndResultBookName(String bookName) {
        String encodedSeriesName = URLEncoder.encode(bookName, StandardCharsets.UTF_8);
        return encodedSeriesName.replace("+", "%20");
    }

    private <T> T getUserInput(Class<T> dataType, String message) {

        System.out.println();
        System.out.println(message);

        if (dataType.equals(Integer.class)) {
            return dataType.cast(scanner.nextInt());
        } else if (dataType.equals(String.class)) {
            scanner.nextLine(); // Limpiar el buffer antes de leer la línea
            return dataType.cast(scanner.nextLine());
        }
        throw new IllegalArgumentException("Tipo de datos no soportado: " + dataType.getSimpleName());
    }
}
