package com.roguelike.item;

import com.roguelike.entity.Player;
import com.roguelike.game.GameConstants;

public class Weapon extends Item {

    public Weapon(int x, int y) {
        super(x, y, '†', "Adaga", GameConstants.DAGGER_ATK_BONUS);
    }

    @Override
    public void apply(Object target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.setAtk(player.getAtk() + value);
            System.out.println("\n✦ Você equipou uma adaga! ATK: " + player.getAtk());
        }
    }
}
