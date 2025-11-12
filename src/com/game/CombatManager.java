package com.roguelike.game;

import com.roguelike.entity.Enemy;
import com.roguelike.entity.Entity;
import com.roguelike.entity.Player;
import com.roguelike.util.InputHandler;

import java.util.List;
import java.util.Random;

public class CombatManager {
    private Player player;
    private List<Entity> enemies;
    private Random rand;
    private InputHandler inputHandler;
    private boolean[][] walls;

    public CombatManager(Player player, List<Entity> enemies, boolean[][] walls, Random rand, InputHandler inputHandler) {
        this.player = player;
        this.enemies = enemies;
        this.walls = walls;
        this.rand = rand;
        this.inputHandler = inputHandler;
    }

    public void playerAttack(OnLevelClearedCallback callback) {
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
                        checkLevelCompletion(callback);
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

    private void checkLevelCompletion(OnLevelClearedCallback callback) {
        if (enemies.stream().allMatch(e -> !e.isAlive())) {
            callback.onLevelCleared();
        }
    }

    public boolean enemyTurn() {
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
                int[] newPos = enemy.getNextPositionTowards(player);
                int newX = newPos[0];
                int newY = newPos[1];
                
                if (!walls[newX][newY] && !isEnemyAt(newX, newY, enemy)) {
                    enemy.setX(newX);
                    enemy.setY(newY);
                }
            }
        }

        return player.isAlive();
    }

    private boolean isEnemyAt(int x, int y, Enemy current) {
        for (Entity e : enemies) {
            if (e != current && e.isAlive() && e.getX() == x && e.getY() == y) {
                return true;
            }
        }
        return false;
    }

    @FunctionalInterface
    public interface OnLevelClearedCallback {
        void onLevelCleared();
    }
}