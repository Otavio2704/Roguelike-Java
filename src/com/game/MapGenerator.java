package com.roguelike.game;

import com.roguelike.entity.Enemy;
import com.roguelike.entity.Entity;
import com.roguelike.entity.Player;
import com.roguelike.item.Gold;
import com.roguelike.item.Item;
import com.roguelike.item.Potion;
import com.roguelike.item.Weapon;

import java.util.List;
import java.util.Random;

public class MapGenerator {
    private boolean[][] walls;
    private Random rand;
    private Player player;

    public MapGenerator(boolean[][] walls, Random rand, Player player) {
        this.walls = walls;
        this.rand = rand;
        this.player = player;
    }

    public void generateLevel(int level, List<Entity> enemies, List<Item> items) {
        enemies.clear();
        items.clear();
        
        clearWalls();
        generateBorderWalls();
        generateInnerWalls(level);
        spawnEnemies(level, enemies);
        spawnItems(level, items);
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

    private void generateInnerWalls(int level) {
        int wallCount = 5 + level;
        for (int i = 0; i < wallCount; i++) {
            int x = rand.nextInt(GameConstants.WIDTH - 4) + 2;
            int y = rand.nextInt(GameConstants.HEIGHT - 4) + 2;
            if (!isOccupied(x, y, null, null)) {
                walls[x][y] = true;
            }
        }
    }

    private void spawnEnemies(int level, List<Entity> enemies) {
        int enemyCount = Math.min(2 + level, 8);
        for (int i = 0; i < enemyCount; i++) {
            spawnEntityAtRandomPosition(() -> {
                int x = rand.nextInt(GameConstants.WIDTH - 2) + 1;
                int y = rand.nextInt(GameConstants.HEIGHT - 2) + 1;
                if (!isOccupied(x, y, enemies, null)) {
                    enemies.add(Enemy.createGoblin(x, y, level));
                    return true;
                }
                return false;
            });
        }
    }

    private void spawnItems(int level, List<Item> items) {
        int itemCount = 3 + level;
        for (int i = 0; i < itemCount; i++) {
            spawnEntityAtRandomPosition(() -> {
                int x = rand.nextInt(GameConstants.WIDTH - 2) + 1;
                int y = rand.nextInt(GameConstants.HEIGHT - 2) + 1;
                if (!isOccupied(x, y, null, items)) {
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

    public boolean isOccupied(int x, int y, List<Entity> enemies, List<Item> items) {
        if (x == player.getX() && y == player.getY()) {
            return true;
        }
        
        if (walls[x][y]) {
            return true;
        }
        
        if (enemies != null) {
            for (Entity e : enemies) {
                if (e.getX() == x && e.getY() == y) {
                    return true;
                }
            }
        }
        
        if (items != null) {
            for (Item item : items) {
                if (item.getX() == x && item.getY() == y) {
                    return true;
                }
            }
        }
        
        return false;
    }
}