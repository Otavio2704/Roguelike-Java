package com.roguelike.game;

import com.roguelike.entity.Entity;
import com.roguelike.entity.Player;
import com.roguelike.item.Item;
import com.roguelike.util.InputHandler;

import java.util.Iterator;
import java.util.List;

public class PlayerController {
    private Player player;
    private List<Entity> enemies;
    private List<Item> items;
    private boolean[][] walls;
    private InputHandler inputHandler;

    public PlayerController(Player player, List<Entity> enemies, List<Item> items, boolean[][] walls, InputHandler inputHandler) {
        this.player = player;
        this.enemies = enemies;
        this.items = items;
        this.walls = walls;
        this.inputHandler = inputHandler;
    }

    public boolean movePlayer(int dx, int dy) {
        int nx = player.getX() + dx;
        int ny = player.getY() + dy;

        if (nx < 0 || nx >= GameConstants.WIDTH || ny < 0 || ny >= GameConstants.HEIGHT) {
            return false;
        }

        if (walls[nx][ny]) {
            System.out.println("\n✗ Você bateu na parede!");
            inputHandler.sleep(300);
            return false;
        }

        for (Entity e : enemies) {
            if (e.isAlive() && e.getX() == nx && e.getY() == ny) {
                System.out.println("\n✗ Há um inimigo no caminho!");
                inputHandler.sleep(300);
                return false;
            }
        }

        player.setX(nx);
        player.setY(ny);

        checkCollisions();
        return true;
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
}