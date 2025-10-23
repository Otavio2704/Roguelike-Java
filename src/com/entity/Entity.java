package com.roguelike.entity;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int hp;
    protected int maxHp;
    protected int atk;
    protected char symbol;
    protected String name;

    public Entity(int x, int y, int hp, int atk, char symbol, String name) {
        this.x = x;
        this.y = y;
        this.hp = this.maxHp = hp;
        this.atk = atk;
        this.symbol = symbol;
        this.name = name;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public void heal(int amount) {
        this.hp = Math.min(hp + amount, maxHp);
    }

    // Getters e Setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getMaxHp() { return maxHp; }

    public int getAtk() { return atk; }
    public void setAtk(int atk) { this.atk = atk; }

    public char getSymbol() { return symbol; }

    public String getName() { return name; }
}
