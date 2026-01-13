package com.roguelike.shop;

import com.roguelike.entity.Player;
import com.roguelike.shop.powerups.AttackUpgrade;
import com.roguelike.shop.powerups.HealthPotion;
import com.roguelike.shop.powerups.HealthUpgrade;
import com.roguelike.util.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<PowerUp> powerUps;
    private ShopUI shopUI;
    private InputHandler inputHandler;

    public Shop(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        this.shopUI = new ShopUI(inputHandler);
        this.powerUps = new ArrayList<>();
        initializePowerUps();
    }

    private void initializePowerUps() {
        powerUps.add(new HealthPotion());
        powerUps.add(new HealthUpgrade());
        powerUps.add(new AttackUpgrade());
    }

    public void openShop(Player player) {
        boolean shopping = true;

        while (shopping) {
            shopUI.displayShop(powerUps, player);
            String choice = inputHandler.getCommand();

            if (choice.equals("q") || choice.equals("sair")) {
                shopping = false;
                System.out.println("\n👋 Volte sempre!");
                inputHandler.sleep(1000);
            } else {
                try {
                    int index = Integer.parseInt(choice) - 1;
                    
                    if (index >= 0 && index < powerUps.size()) {
                        PowerUp powerUp = powerUps.get(index);
                        purchasePowerUp(player, powerUp);
                    } else {
                        System.out.println("\n✗ Opção inválida!");
                        inputHandler.sleep(800);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\n✗ Digite um número válido ou 'q' para sair!");
                    inputHandler.sleep(800);
                }
            }
        }
    }

    private void purchasePowerUp(Player player, PowerUp powerUp) {
        if (player.getGold() >= powerUp.getPrice()) {
            player.addGold(-powerUp.getPrice());
            powerUp.apply(player);
            System.out.println("\n✓ Você comprou: " + powerUp.getName() + "!");
            inputHandler.sleep(1500);
        } else {
            int needed = powerUp.getPrice() - player.getGold();
            System.out.println("\n✗ Ouro insuficiente! Faltam " + needed + " moedas.");
            inputHandler.sleep(1200);
        }
    }

    public void addPowerUp(PowerUp powerUp) {
        this.powerUps.add(powerUp);
    }
}