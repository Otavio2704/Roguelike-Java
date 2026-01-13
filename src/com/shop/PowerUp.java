package com.roguelike.shop;

import com.roguelike.entity.Player;

public abstract class PowerUp {
    protected String name;
    protected String description;
    protected int price;
    protected char symbol;

    public PowerUp(String name, String description, int price, char symbol) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.symbol = symbol;
    }

    public abstract void apply(Player player);

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public char getSymbol() { return symbol; }
}
