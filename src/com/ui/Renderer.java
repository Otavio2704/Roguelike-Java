package com.roguelike.ui;

import com.roguelike.entity.Entity;
import com.roguelike.entity.Player;
import com.roguelike.game.GameConstants;
import com.roguelike.item.Item;
import java.util.List;

public class Renderer {
    private UI ui;

    public Renderer() {
        this.ui = null;
    }

    public Renderer(UI ui) {
        this.ui = ui;
    }

    public void render(Player player, List<Entity> enemies, List<Item> items, boolean[][] walls, int level) {
        clearScreen();
        drawHeader(player, level);
        drawMap(player, enemies, items, walls);
        drawFooter();
    }

    private void drawHeader(Player player, int level) {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  Roguelike - Nível: " + level + 
                         " | Ouro: " + player.getGold() + 
                         " | HP: " + player.getHp() + "/" + player.getMaxHp() +
                         " | ATK: " + player.getAtk());
        System.out.println("═══════════════════════════════════════════");
    }

    private void drawMap(Player player, List<Entity> enemies, List<Item> items, boolean[][] walls) {
        for (int y = 0; y < GameConstants.HEIGHT; y++) {
            System.out.print("║");
            for (int x = 0; x < GameConstants.WIDTH; x++) {
                char c = ' ';

                if (walls[x][y]) {
                    c = '#';
                } else if (player.getX() == x && player.getY() == y) {
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
        
        String hpLine = "║ HP: " + player.getHp() + "/" + player.getMaxHp();
        System.out.println(padLine(hpLine, 40));
        
        String atkLine = "║ ATK: " + player.getAtk();
        System.out.println(padLine(atkLine, 40));
        
        String goldLine = "║ Ouro: " + player.getGold();
        System.out.println(padLine(goldLine, 40));
        
        long aliveEnemies = enemies.stream().filter(Entity::isAlive).count();
        String enemiesLine = "║ Inimigos vivos: " + aliveEnemies;
        System.out.println(padLine(enemiesLine, 40));
        
        System.out.println("╚══════════════════════════════════════╝");
    }

    private String padLine(String line, int totalWidth) {
        int currentLength = line.length();
        int spacesNeeded = totalWidth - currentLength - 1;
        return line + " ".repeat(Math.max(0, spacesNeeded)) + "║";
    }

    public void showGameOver(Player player, int level) {
        if (ui == null) {
            ui = new UI(null);
        }
        ui.showGameOverScreen(level, player.getGold());
    }

    public void showLevelUp(int level) {
        System.out.println("\n🏆 Próximo nível! Nível: " + level);
    }

    // ===== NOVO MÉTODO =====
    public void showShopNotification(int level) {
        clearScreen();
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║                                                       ║");
        System.out.println("║           * LOJA DISPONÍVEL NO NÍVEL " + String.format("%-2d", level) + "! *          ║");
        System.out.println("║                                                       ║");
        System.out.println("║         Prepare-se para gastar suas moedas!           ║");
        System.out.println("║                                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }
}
