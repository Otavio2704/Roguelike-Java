package com.roguelike.entity;

import com.roguelike.game.GameConstants;

public class Player extends Entity {
    private int gold;

    public Player() {
        super(GameConstants.PLAYER_INITIAL_X, GameConstants.PLAYER_INITIAL_Y,
                GameConstants.PLAYER_INITIAL_HP, GameConstants.PLAYER_INITIAL_ATK,
                GameConstants.PLAYER_SYMBOL, "Você");
        this.gold = 0;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    public int getGold() {
        return gold;
    }

    public void resetPosition() {
        this.x = GameConstants.PLAYER_INITIAL_X;
        this.y = GameConstants.PLAYER_INITIAL_Y;
    }

    public void resetHP() {
        this.hp = maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
}
