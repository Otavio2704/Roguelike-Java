package com.roguelike.shop.powerups;

import com.roguelike.entity.Player;
import com.roguelike.shop.PowerUp;

public class AttackUpgrade extends PowerUp {
    private static final String NAME = "Aumento de Ataque";
    private static final String DESCRIPTION = "Aumenta seu ATK permanentemente em 3 pontos";
    private static final int PRICE = 75;
    private static final char SYMBOL = '|';
    private static final int ATK_BONUS = 3;

    public AttackUpgrade() {
        super(NAME, DESCRIPTION, PRICE, SYMBOL);
    }

    @Override
    public void apply(Player player) {
        int oldAtk = player.getAtk();
        player.setAtk(player.getAtk() + ATK_BONUS);
        
        System.out.println("\n✦ Ataque aumentado de " + oldAtk + " para " + player.getAtk() + "!");
    }
}
