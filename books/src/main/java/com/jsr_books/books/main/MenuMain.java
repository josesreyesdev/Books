package com.jsr_books.books.main;

import java.util.Scanner;

public class MenuMain {

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        try {
            while (true) {


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
}
