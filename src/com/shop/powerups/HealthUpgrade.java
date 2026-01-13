package com.roguelike.shop.powerups;

import com.roguelike.entity.Player;
import com.roguelike.shop.PowerUp;

public class HealthUpgrade extends PowerUp {
    private static final String NAME = "Aumento de HP Máximo";
    private static final String DESCRIPTION = "Aumenta seu HP máximo em 10 pontos";
    private static final int PRICE = 50;
    private static final char SYMBOL = '=>';
    private static final int HP_BONUS = 10;

    public HealthUpgrade() {
        super(NAME, DESCRIPTION, PRICE, SYMBOL);
    }

    @Override
    public void apply(Player player) {
        int oldMaxHp = player.getMaxHp();
        player.setMaxHp(player.getMaxHp() + HP_BONUS);
        player.heal(HP_BONUS); // Também cura o HP adicionado
        
        System.out.println("\n✦ HP Máximo aumentado de " + oldMaxHp + " para " + player.getMaxHp() + "!");
        System.out.println("✦ HP atual: " + player.getHp() + "/" + player.getMaxHp());
    }
}