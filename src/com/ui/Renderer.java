package com.roguelike.ui;

import com.roguelike.entity.Entity;
import com.roguelike.entity.Player;
import com.roguelike.game.GameConstants;
import com.roguelike.item.Item;
import java.util.List;

public class Renderer {

    public void render(Player player, List<Entity> enemies, List<Item> items, int level) {
        clearScreen();
        drawHeader(player, level);
        drawMap(player, enemies, items);
        drawFooter();
    }

    private void drawHeader(Player player, int level) {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  Roguelike - Nível: " + level + 
                         " | Ouro: " + player.getGold() + 
                         " | HP: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println("═══════════════════════════════════════════");
    }

    private void drawMap(Player player, List<Entity> enemies, List<Item> items) {
        for (int y = 0; y < GameConstants.HEIGHT; y++) {
            System.out.print("║");
            for (int x = 0; x < GameConstants.WIDTH; x++) {
                char c = ' ';

                if (player.getX() == x && player.getY() == y) {
                    c = player.getSymbol();
                } else {
                    boolean found = false;
                    for (Entity e : enemies) {
                        if (e.getX() == x && e.getY() == y && e.isAlive()) {
                            c = e.getSymbol();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        for (Item item : items) {
                            if (item.getX() == x && item.getY() == y) {
                                c = item.getSymbol();
                                found = true;
                                break;
                            }
                        }
                    }
                }
                System.out.print(c);
            }
            System.out.println("║");
        }
    }

    private void drawFooter() {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("Comandos: w/a/s/d (mover) | espaço (atacar) | i (info) | q (sair)");
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void showInfo(Player player, List<Entity> enemies) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         INFORMAÇÕES DO JOGADOR       ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ HP: " + player.getHp() + "/" + player.getMaxHp() + 
                         (player.getHp() < 10 ? "                          ║" : "                         ║"));
        System.out.println("║ ATK: " + player.getAtk() + 
                         (player.getAtk() < 10 ? "                             ║" : "                            ║"));
        System.out.println("║ Ouro: " + player.getGold() + 
                         (player.getGold() < 10 ? "                           ║" : 
                          (player.getGold() < 100 ? "                          ║" : "                         ║")));
        long aliveEnemies = enemies.stream().filter(Entity::isAlive).count();
        System.out.println("║ Inimigos vivos: " + aliveEnemies + 
                         (aliveEnemies < 10 ? "                   ║" : "                  ║"));
        System.out.println("╚══════════════════════════════════════╝");
    }

    public void showGameOver(Player player, int level) {
        System.out.println("\n☠ GAME OVER! Você foi derrotado no nível " + level + "!");
        System.out.println("Ouro total: " + player.getGold());
    }

    public void showLevelUp(int level) {
        System.out.println("\n🏆 Próximo nível! Nível: " + level);
    }
}
