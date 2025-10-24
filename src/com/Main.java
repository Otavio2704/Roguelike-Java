package com.roguelike;

import com.roguelike.game.RoguelikeGame;
import com.roguelike.ui.UI;
import com.roguelike.util.InputHandler;

public class Main {
    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        UI ui = new UI(inputHandler);

        boolean running = true;
        while (running) {
            ui.showMainMenu();
            String choice = inputHandler.getCommand();

            switch (choice) {
                case "1" -> {
                    ui.showWelcomeMessage();
                    RoguelikeGame game = new RoguelikeGame(inputHandler);
                    game.play();
                }
                case "2" -> ui.showInstructions();
                case "3" -> {
                    System.out.println("\nAté logo! :)");
                    running = false;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }

        inputHandler.close();
    }
}
