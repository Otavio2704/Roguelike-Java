package com.roguelike.item;

import com.roguelike.entity.Player;

public class Gold extends Item {
    private static final int GOLD_VALUE = 25;

    public Gold(int x, int y) {
        super(x, y, '$', "Ouro", GOLD_VALUE);
    }

    @Override
    public void apply(Object target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.addGold(value);
            System.out.println("\n✦ Você coletou " + value + " de ouro!");
        }
    }
}
