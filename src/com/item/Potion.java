package com.roguelike.item;

import com.roguelike.entity.Player;
import com.roguelike.game.GameConstants;

public class Potion extends Item {

    public Potion(int x, int y) {
        super(x, y, 'P', "Poção", GameConstants.POTION_HEAL);
    }

    @Override
    public void apply(Object target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.heal(value);
            System.out.println("\n✦ Você bebeu uma poção! HP: " + player.getHp() + "/" + player.getMaxHp());
        }
    }
}
