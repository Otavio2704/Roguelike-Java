package com.roguelike.item;

public abstract class Item {
    protected int x;
    protected int y;
    protected char symbol;
    protected String name;
    protected int value;

    public Item(int x, int y, char symbol, String name, int value) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.name = name;
        this.value = value;
    }

    public abstract void apply(Object target);

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public char getSymbol() { return symbol; }

    public String getName() { return name; }

    public int getValue() { return value; }
}
