package com.roguelike.game;

import com.roguelike.entity.Enemy;
import com.roguelike.entity.Entity;
import com.roguelike.entity.Player;
import com.roguelike.item.Gold;
import com.roguelike.item.Item;
import com.roguelike.item.Potion;
import com.roguelike.item.Weapon;
import com.roguelike.ui.Renderer;
import com.roguelike.util.InputHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RoguelikeGame {
    private Player player;
    private List<Entity> enemies;
    private List<Item> items;
    private Random rand;
    private int level;
    private Renderer renderer;
    private InputHandler inputHandler;

    public RoguelikeGame() {
        this.player = new Player();
        this.enemies = new ArrayList<>();
        this.items = new ArrayList<>();
        this.rand = new Random();
        this.level = GameConstants.INITIAL_LEVEL;
        this.renderer = new Renderer();
        this.inputHandler = new InputHandler();
        generateLevel();
    }

    private void generateLevel() {
        enemies.clear();
        items.clear();

        // Gera inimigos
        int enemyCount = 2 + level;
        for (int i = 0; i < enemyCount; i++) {
            int x = rand.nextInt(GameConstants.WIDTH - 2) + 1;
            int y = rand.nextInt(GameConstants.HEIGHT - 2) + 1;
            if (!(x == player.getX() && y == player.getY())) {
                enemies.add(Enemy.createGoblin(x, y, level));
            }
        }

        // Gera itens
        int itemCount = 2 + level;
        for (int i = 0; i < itemCount; i++) {
            int x = rand.nextInt(GameConstants.WIDTH - 2) + 1;
            int y = rand.nextInt(GameConstants.HEIGHT - 2) + 1;
            if (!(x == player.getX() && y == player.getY())) {
                int idx = rand.nextInt(3);
                Item item = switch (idx) {
                    case 0 -> new Potion(x, y);
                    case 1 -> new Gold(x, y);
                    case 2 -> new Weapon(x, y);
                    default -> new Potion(x, y);
                };
                items.add(item);
            }
        }
    }

    private void movePlayer(int dx, int dy) {
        int nx = player.getX() + dx;
        int ny = player.getY() + dy;

        if (nx < 0 || nx >= GameConstants.WIDTH || ny < 0 || ny >= GameConstants.HEIGHT) {
            return;
        }

        player.setX(nx);
        player.setY(ny);

        checkCollisions();
        enemyAction();
    }

    private void checkCollisions() {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getX() == player.getX() && item.getY() == player.getY()) {
                item.apply(player);
                it.remove();
                inputHandler.sleep(1000);
            }
        }
    }

    private void attack() {
        for (Entity e : enemies) {
            if (e.isAlive()) {
                int dist = Math.abs(e.getX() - player.getX()) + Math.abs(e.getY() - player.getY());
                if (dist == GameConstants.ATTACK_RANGE) {
                    int dmg = player.getAtk() + rand.nextInt(3);
                    e.takeDamage(dmg);
                    System.out.println("\n⚔ Você atacou " + e.getName() + " por " + dmg + " dano! HP inimigo: " + Math.max(0, e.getHp()));
                    if (!e.isAlive()) {
                        System.out.println("💀 " + e.getName() + " foi derrotado!");
                        player.addGold(GameConstants.GOLD_REWARD);
                    }
                    inputHandler.sleep(1000);
                    return;
                }
            }
        }
        System.out.println("\n✗ Nenhum inimigo perto!");
        inputHandler.sleep(500);
    }

    private void enemyAction() {
        for (Entity e : enemies) {
            if (!e.isAlive()) continue;

            Enemy enemy = (Enemy) e;
            int dist = enemy.distanceTo(player);

            if (dist <= GameConstants.ENEMY_ATTACK_RANGE && dist > 0) {
                int dmg = Math.max(1, enemy.getAtk() + rand.nextInt(2) - 1);
                player.takeDamage(dmg);
                System.out.println("\n⚔ " + enemy.getName() + " atacou você por " + dmg + " dano! Seu HP: " + player.getHp() + "/" + player.getMaxHp());
                inputHandler.sleep(1000);
            } else if (dist > GameConstants.ENEMY_ATTACK_RANGE && dist <= GameConstants.ENEMY_PURSUIT_RANGE) {
                enemy.moveTowards(player);
            }
        }

        if (!player.isAlive()) {
            renderer.showGameOver(player, level);
            System.exit(0);
        }
    }

    private void nextLevel() {
        if (enemies.stream().allMatch(e -> !e.isAlive())) {
            level++;
            player.resetPosition();
            player.resetHP();
            generateLevel();
            renderer.showLevelUp(level);
            inputHandler.sleep(2000);
        }
    }

    public void play() {
        while (player.isAlive()) {
            nextLevel();
            renderer.render(player, enemies, items, level);

            String input = inputHandler.getCommand();

            switch (input) {
                case "w" -> movePlayer(0, -1);
                case "s" -> movePlayer(0, 1);
                case "a" -> movePlayer(-1, 0);
                case "d" -> movePlayer(1, 0);
                case " " -> attack();
                case "i" -> {
                    renderer.showInfo(player, enemies);
                    inputHandler.sleep(2000);
                }
                case "q" -> {
                    System.out.println("Até logo!");
                    inputHandler.close();
                    return;
                }
                default -> System.out.println("Comando inválido!");
            }
        }

        inputHandler.close();
    }
}
