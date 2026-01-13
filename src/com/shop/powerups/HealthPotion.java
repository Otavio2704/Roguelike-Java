package com.roguelike.shop.powerups;

import com.roguelike.entity.Player;
import com.roguelike.shop.PowerUp;

public class HealthPotion extends PowerUp {
    private static final String NAME = "Poção Grande";
    private static final String DESCRIPTION = "Restaura 20 pontos de vida imediatamente";
    private static final int PRICE = 30;
    private static final char SYMBOL = '^';
    private static final int HEAL_AMOUNT = 20;

    public HealthPotion() {
        super(NAME, DESCRIPTION, PRICE, SYMBOL);
    }

    @Override
    public void apply(Player player) {
        int oldHp = player.getHp();
        player.heal(HEAL_AMOUNT);
        int healedAmount = player.getHp() - oldHp;
        
        System.out.println("\n✦ Você recuperou " + healedAmount + " de HP!");
        System.out.println("✦ HP atual: " + player.getHp() + "/" + player.getMaxHp());
    }
}
