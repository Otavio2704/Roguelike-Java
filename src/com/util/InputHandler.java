package com.roguelike.util;

import java.util.Scanner;

public class InputHandler {
    private Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String getCommand() {
        System.out.print("Comando: ");
        String input = scanner.nextLine().toLowerCase();
        
        // Se for só espaço, retorna "attack" para facilitar
        if (input.trim().isEmpty() && input.length() > 0) {
            return "attack";
        }
        
        return input.trim();
    }

    public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void close() {
        scanner.close();
    }
}
