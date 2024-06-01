package com.jsr_books.books.main;

import com.jsr_books.books.model.SearchResult;
import com.jsr_books.books.service.ApiService;
import com.jsr_books.books.service.ConvertData;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MenuMain {

    private final ApiService apiService = new ApiService();
    private final ConvertData convertData = new ConvertData();

    private final String BASE_URL = "https://gutendex.com/";
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        try {
            while (true) {
                //var bookName = getUserInput("ingresa el nombre del libro a consultar: ");
                //String encodeResultName = encodeAndResultBookName(bookName);

                // get search result
                SearchResult searchResultData = fetchResultData();
                System.out.println("All Data: " + searchResultData);


                System.out.println();
                System.out.println("Ingresa 'exit' para terminar ó ingrese cualquier otra letra para hacer nueva solicitud");
                String exit = scanner.nextLine();
                if (exit.equalsIgnoreCase("Exit")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUserInput(String message) {
        System.out.println();
        System.out.println(message);
        return scanner.nextLine();
    }

    private String encodeAndResultBookName(String bookName) {
        String encodedSeriesName = URLEncoder.encode(bookName, StandardCharsets.UTF_8);
        return encodedSeriesName.replace("+", "%20");
    }

    private SearchResult fetchResultData() {
        String url = BASE_URL + "books/";
        System.out.println(url);
        String json = apiService.getData(url);
        return convertData.getData(json, SearchResult.class);
    }
}
