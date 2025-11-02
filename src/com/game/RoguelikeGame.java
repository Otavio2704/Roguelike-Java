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
    private boolean[][] walls;
    private Random rand;
    private int level;
    private Renderer renderer;
    private InputHandler inputHandler;
    private boolean levelCleared;

    public RoguelikeGame(InputHandler inputHandler) {
        this.player = new Player();
        this.enemies = new ArrayList<>();
        this.items = new ArrayList<>();
        this.walls = new boolean[GameConstants.WIDTH][GameConstants.HEIGHT];
        this.rand = new Random();
        this.level = GameConstants.INITIAL_LEVEL;
        this.renderer = new Renderer();
        this.inputHandler = inputHandler;
        this.levelCleared = false;
        generateLevel();
    }

    private void generateLevel() {
        enemies.clear();
        items.clear();
        levelCleared = false;
        
        clearWalls();
        generateBorderWalls();
        generateInnerWalls();
        spawnEnemies();
        spawnItems();
    }

    private void clearWalls() {
        for (int x = 0; x < GameConstants.WIDTH; x++) {
            for (int y = 0; y < GameConstants.HEIGHT; y++) {
                walls[x][y] = false;
            }
        }
    }

    private void generateBorderWalls() {
        for (int x = 0; x < GameConstants.WIDTH; x++) {
            walls[x][0] = true;
            walls[x][GameConstants.HEIGHT - 1] = true;
        }
        for (int y = 0; y < GameConstants.HEIGHT; y++) {
            walls[0][y] = true;
            walls[GameConstants.WIDTH - 1][y] = true;
        }
    }

    private void generateInnerWalls() {
        int wallCount = 5 + level;
        for (int i = 0; i < wallCount; i++) {
            int x = rand.nextInt(GameConstants.WIDTH - 4) + 2;
            int y = rand.nextInt(GameConstants.HEIGHT - 4) + 2;
            if (!isOccupied(x, y)) {
                walls[x][y] = true;
            }
        }
    }

    private void spawnEnemies() {
        int enemyCount = Math.min(2 + level, 8);
        for (int i = 0; i < enemyCount; i++) {
            spawnEntityAtRandomPosition(() -> {
                int x = rand.nextInt(GameConstants.WIDTH - 2) + 1;
                int y = rand.nextInt(GameConstants.HEIGHT - 2) + 1;
                if (!isOccupied(x, y)) {
                    enemies.add(Enemy.createGoblin(x, y, level));
                    return true;
                }
                return false;
            });
        }
    }

    private void spawnItems() {
        int itemCount = 3 + level;
        for (int i = 0; i < itemCount; i++) {
            spawnEntityAtRandomPosition(() -> {
                int x = rand.nextInt(GameConstants.WIDTH - 2) + 1;
                int y = rand.nextInt(GameConstants.HEIGHT - 2) + 1;
                if (!isOccupied(x, y)) {
                    Item item = createRandomItem(x, y);
                    items.add(item);
                    return true;
                }
                return false;
            });
        }
    }

    private Item createRandomItem(int x, int y) {
        int idx = rand.nextInt(3);
        return switch (idx) {
            case 0 -> new Potion(x, y);
            case 1 -> new Gold(x, y);
            case 2 -> new Weapon(x, y);
            default -> new Potion(x, y);
        };
    }

    private void spawnEntityAtRandomPosition(SpawnAttempt spawner) {
        int attempts = 0;
        while (attempts < 50) {
            if (spawner.attempt()) {
                break;
            }
            attempts++;
        }
    }

    @FunctionalInterface
    private interface SpawnAttempt {
        boolean attempt();
    }

    private boolean isOccupied(int x, int y) {
        if (x == player.getX() && y == player.getY()) {
            return true;
        }
        
        if (walls[x][y]) {
            return true;
        }
        
        for (Entity e : enemies) {
            if (e.getX() == x && e.getY() == y) {
                return true;
            }
        }
        
        for (Item item : items) {
            if (item.getX() == x && item.getY() == y) {
                return true;
            }
        }
        
        return false;
    }

    private void movePlayer(int dx, int dy) {
        int nx = player.getX() + dx;
        int ny = player.getY() + dy;

        if (nx < 0 || nx >= GameConstants.WIDTH || ny < 0 || ny >= GameConstants.HEIGHT) {
            return;
        }

        if (walls[nx][ny]) {
            System.out.println("\n✗ Você bateu na parede!");
            inputHandler.sleep(300);
            return;
        }

        for (Entity e : enemies) {
            if (e.isAlive() && e.getX() == nx && e.getY() == ny) {
                System.out.println("\n✗ Há um inimigo no caminho!");
                inputHandler.sleep(300);
                return;
            }
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
        boolean attackedEnemy = false;
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
                        checkLevelCompletion();
                    }
                    attackedEnemy = true;
                    inputHandler.sleep(1000);
                    return;
                }
            }
        }
        
        if (!attackedEnemy) {
            System.out.println("\n✗ Nenhum inimigo perto!");
            inputHandler.sleep(500);
        }
    }

    private void checkLevelCompletion() {
        if (enemies.stream().allMatch(e -> !e.isAlive())) {
            levelCleared = true;
        }
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
                // Calcula nova posição sem modificar o inimigo ainda
                int[] newPos = enemy.getNextPositionTowards(player);
                int newX = newPos[0];
                int newY = newPos[1];
                
                // Só move se a posição é válida
                if (!walls[newX][newY] && !isEnemyAt(newX, newY, enemy)) {
                    enemy.setX(newX);
                    enemy.setY(newY);
                }
            }
        }

        if (!player.isAlive()) {
            renderer.showGameOver(player, level);
            System.exit(0);
        }
    }

    private boolean isEnemyAt(int x, int y, Enemy current) {
        for (Entity e : enemies) {
            if (e != current && e.isAlive() && e.getX() == x && e.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private void advanceToNextLevel() {
        if (levelCleared) {
            level++;
            player.resetPosition();
            player.resetHP();
            generateLevel();
            renderer.showLevelUp(level);
            inputHandler.sleep(2000);
        }
    }

    public boolean[][] getWalls() {
        return walls;
    }

    public void play() {
        while (player.isAlive()) {
            advanceToNextLevel();
            renderer.render(player, enemies, items, walls, level);

            String input = inputHandler.getCommand();

            switch (input) {
                case "w" -> movePlayer(0, -1);
                case "s" -> movePlayer(0, 1);
                case "a" -> movePlayer(-1, 0);
                case "d" -> movePlayer(1, 0);
                case "attack", "" -> attack();
                case "i" -> {
                    renderer.showInfo(player, enemies);
                    inputHandler.sleep(2000);
                }
                case "q" -> {
                    System.out.println("\nAté logo!");
                    return;
                }
                default -> System.out.println("Comando inválido!");
            }
        }
    }
}
