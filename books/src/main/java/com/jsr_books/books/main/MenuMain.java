package com.jsr_books.books.main;

import com.jsr_books.books.model.Book;
import com.jsr_books.books.model.SearchResult;
import com.jsr_books.books.service.ApiService;
import com.jsr_books.books.service.ConvertData;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MenuMain {

    private static final String URL_BASE = "https://gutendex.com/";
    private final ApiService apiService = new ApiService();
    private final ConvertData convertData = new ConvertData();

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        try {
            while (true) {

                // get search result data
                SearchResult searchResultData = fetchResultData();
                System.out.println("\nAll Data: " + searchResultData);

                // get specific books for user
                try {
                    Integer limitNumber = getUserInput(Integer.class, "Enter the total number of books you wish to view:");
                    getBookData(searchResultData, limitNumber);

                    // get top {10} books con mas decargas
                    Integer topBooks = getUserInput(Integer.class, "Enter the TOP books with the most downloads you want to view:");
                    getTopBookData(searchResultData, topBooks);

                    // search book by title
                    String bookName = getUserInput(String.class, "Enter the name of the book you wish to view:");
                    getBookByTitle(bookName);

                    // get statistics
                    getStatistics(searchResultData);

                } catch (Exception ex) {
                    System.out.println("Data type is not valid, try again");
                }

                String exit = getUserInput(String.class, "Enter 'exit' to finish or enter new letter to continue");
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

        System.out.println("\nAll Statistics: " + est);
        System.out.println("Book with the highest download: " + est.getMax());
        System.out.println("Book with lower download: " + est.getMin());
        System.out.println("Average of books downloaded: " + est.getAverage());
        System.out.println("Total count: " + est.getCount());
    }

    private void getBookByTitle(String bookName) {

        String bookNameResult = encodeAndFormatSeriesName(bookName);
        String url = URL_BASE + "books/?search=" + bookNameResult;

        String json = apiService.getData(url);
        var searchData = convertData.getData(json, SearchResult.class);

        Optional<Book> searchBookByTitle = searchData.results().stream()
                .filter(b -> b.title().toUpperCase().contains(bookName.toUpperCase()))
                .findFirst();

        if (searchBookByTitle.isPresent()) {
            System.out.println("\nBook found: ");
            System.out.println("All book data: " + searchBookByTitle.get());
            System.out.println("Title book: " + searchBookByTitle.get().title());
        } else {
            System.out.println("\nBook not found");
        }
    }

    private void getTopBookData(SearchResult resultData, Integer topBooks) {
        System.out.println();
        AtomicInteger ind = new AtomicInteger(1);
        resultData.results().stream()
                .sorted(Comparator.comparingInt(Book::downloadCount).reversed())
                .limit(topBooks)
                .forEach(book -> {
                    System.out.println(ind.getAndIncrement() + "-> Title: " + book.title().toUpperCase());
                    System.out.println("--- Total downloads: " + book.downloadCount());
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
        String url = URL_BASE + "books/";
        String json = apiService.getData(url);
        return convertData.getData(json, SearchResult.class);
    }

    private <T> T getUserInput(Class<T> dataType, String message) {

        System.out.println("\n" + message);

        if (dataType.equals(Integer.class)) {
            return dataType.cast(scanner.nextInt());
        } else if (dataType.equals(String.class)) {
            scanner.nextLine();
            return dataType.cast(scanner.nextLine());
        }
        throw new IllegalArgumentException("Data type not supported: " + dataType.getSimpleName());
    }

    private String encodeAndFormatSeriesName(String seriesName) {
        String encodedSeriesName = URLEncoder.encode(seriesName, StandardCharsets.UTF_8);
        return encodedSeriesName.replace("+", "%20");
    }
}