package com.roguelike.entity;

import com.roguelike.game.GameConstants;

public class Enemy extends Entity {

    public Enemy(int x, int y, int hp, int atk) {
        super(x, y, hp, atk, GameConstants.ENEMY_SYMBOL, GameConstants.ENEMY_NAME);
    }

    public static Enemy createGoblin(int x, int y, int level) {
        int hp = GameConstants.ENEMY_BASE_HP + level * 2;
        int atk = GameConstants.ENEMY_BASE_ATK + level;
        return new Enemy(x, y, hp, atk);
    }

    public int distanceTo(Entity other) {
        return Math.abs(this.x - other.getX()) + Math.abs(this.y - other.getY());
    }

    // Retorna nova posição sem modificar o estado
    public int[] getNextPositionTowards(Entity target) {
        int dx = Integer.compare(target.getX(), this.x);
        int dy = Integer.compare(target.getY(), this.y);
        
        int newX = this.x;
        int newY = this.y;
        
        // Move preferencialmente na direção de maior distância
        if (Math.abs(target.getX() - this.x) > Math.abs(target.getY() - this.y)) {
            newX += dx;
        } else {
            newY += dy;
        }
        
        return new int[]{newX, newY};
    }
}
