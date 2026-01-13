package com.roguelike.shop;

import com.roguelike.entity.Player;
import com.roguelike.util.InputHandler;

import java.util.List;

public class ShopUI {
    private InputHandler inputHandler;

    public ShopUI(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void displayShop(List<PowerUp> powerUps, Player player) {
        clearScreen();
        
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║                 🏪 LOJA DE POWER-UPS 🏪               ║");
        System.out.println("╠═══════════════════════════════════════════════════════╣");
        System.out.println("║  Seu ouro: " + String.format("%-41s", player.getGold() + " moedas") + "║");
        System.out.println("╠═══════════════════════════════════════════════════════╣");

        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);
            String line = String.format("║  [%d] %c %-20s - %3d ouro",
                    (i + 1),
                    powerUp.getSymbol(),
                    powerUp.getName(),
                    powerUp.getPrice());
            
            int padding = 55 - line.length();
            System.out.println(line + " ".repeat(Math.max(0, padding)) + "║");
            
            String descLine = "║      " + powerUp.getDescription();
            padding = 55 - descLine.length();
            System.out.println(descLine + " ".repeat(Math.max(0, padding)) + "║");
            
            if (i < powerUps.size() - 1) {
                System.out.println("║                                                       ║");
            }
        }

        System.out.println("╠═══════════════════════════════════════════════════════╣");
        System.out.println("║  [Q] Sair da loja                                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.print("\nEscolha um item para comprar: ");
    }

    public void showShopAnnouncement() {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║                                                       ║");
        System.out.println("║           🏪 LOJA DISPONÍVEL NO PRÓXIMO NÍVEL! 🏪     ║");
        System.out.println("║                                                       ║");
        System.out.println("║         Prepare-se para gastar suas moedas!           ║");
        System.out.println("║                                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}