package com.jsr_books.books.main;

import com.jsr_books.books.model.Book;
import com.jsr_books.books.model.SearchResult;
import com.jsr_books.books.service.ApiService;
import com.jsr_books.books.service.ConvertData;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MenuMain {

    private final ApiService apiService = new ApiService();
    private final ConvertData convertData = new ConvertData();

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        try {
            while (true) {

                // get search result data
                SearchResult searchResultData = fetchResultData();
                System.out.println();
                System.out.println("All Data: " + searchResultData);

                // get specific books for user
                try {
                    Integer limitNumber = getUserInput(Integer.class, "Ingresa el total de libros que deseas solicitar");
                    getBookData(searchResultData, limitNumber);

                    // get top {10} books con mas decargas
                    Integer topBooks = getUserInput(Integer.class, "Ingresa el TOP de libros con mas descargas que deseas ver");
                    getTopBookData(searchResultData, topBooks);

                    // search book by title
                    String bookName = getUserInput(String.class, "Ingresa el nombre del libro que deseas ver: ");
                    getBookByTitle(searchResultData, bookName);

                    // get statistics
                    getStatistics(searchResultData);

                } catch (Exception ex) {
                    System.out.println("Tipo de dato no valido, intenta de nuevo");
                }

                String exit = getUserInput(String.class, "Ingresa 'exit' para terminar ó ingrese cualquier otra letra para hacer nueva solicitud");
                if (exit.equalsIgnoreCase("Exit")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getStatistics(SearchResult resultData) {
        IntSummaryStatistics est = resultData.results().stream()
                .filter(b -> b.downloadCount() > 0)
                .collect(Collectors.summarizingInt(Book::downloadCount));

        System.out.println();
        System.out.println("Todas las estadisiticas: " + est);
        System.out.println("Libro con mayor descarga: " + est.getMax());
        System.out.println("Libro con menor descarga: " + est.getMin());
        System.out.println("Media de libros descargadas: " + est.getAverage());
    }

    private void getBookByTitle(SearchResult resultData, String bookName) {
        Optional<Book> searchBookByTitle = resultData.results().stream()
                .filter(b -> b.title().toUpperCase().contains(bookName.toUpperCase()))
                .findFirst();

        System.out.println();
        if (searchBookByTitle.isPresent()) {
            System.out.println("Libro encontrado: ");
            System.out.println("All data book: " + searchBookByTitle.get());
            System.out.println("Title of my book: " + searchBookByTitle.get().title());
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void getTopBookData(SearchResult resultData, Integer topBooks) {
        System.out.println();
        AtomicInteger ind = new AtomicInteger(1);
        resultData.results().stream()
                .sorted(Comparator.comparingInt(Book::downloadCount).reversed())
                .limit(topBooks)
                .forEach(book -> {
                    System.out.println(ind.getAndIncrement() + "-> Title: " + book.title());
                    System.out.println("--- Total descargas: " + book.downloadCount());
                });
    }

    private void getBookData(SearchResult resultData, Integer limitNumber) {
        System.out.println();
        AtomicInteger ind = new AtomicInteger(1);
        resultData.results().stream()
                .limit(limitNumber)
                .forEach(book -> System.out.println(ind.getAndIncrement() + "->" + book));
    }

    private SearchResult fetchResultData() {
        String BASE_URL = "https://gutendex.com/";
        String url = BASE_URL + "books/";
        String json = apiService.getData(url);
        return convertData.getData(json, SearchResult.class);
    }

    private <T> T getUserInput(Class<T> dataType, String message) {

        System.out.println();
        System.out.println(message);

        if (dataType.equals(Integer.class)) {
            return dataType.cast(scanner.nextInt());
        } else if (dataType.equals(String.class)) {
            scanner.nextLine();
            return dataType.cast(scanner.nextLine());
        }
        throw new IllegalArgumentException("Tipo de datos no soportado: " + dataType.getSimpleName());
    }


}
