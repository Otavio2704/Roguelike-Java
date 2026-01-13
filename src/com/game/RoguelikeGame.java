package com.roguelike.game;

import com.roguelike.entity.Entity;
import com.roguelike.entity.Player;
import com.roguelike.item.Item;
import com.roguelike.shop.Shop;
import com.roguelike.ui.Renderer;
import com.roguelike.util.InputHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoguelikeGame {
    private Player player;
    private List<Entity> enemies;
    private List<Item> items;
    private boolean[][] walls;
    private Random rand;
    private int level;
    private Renderer renderer;
    private InputHandler inputHandler;
    private boolean levelCleared;

    
    private MapGenerator mapGenerator;
    private CombatManager combatManager;
    private PlayerController playerController;
    private Shop shop;

    public RoguelikeGame(InputHandler inputHandler) {
        this.player = new Player();
        this.enemies = new ArrayList<>();
        this.items = new ArrayList<>();
        this.walls = new boolean[GameConstants.WIDTH][GameConstants.HEIGHT];
        this.rand = new Random();
        this.level = GameConstants.INITIAL_LEVEL;
        this.renderer = new Renderer();
        this.inputHandler = inputHandler;
        this.levelCleared = false;
        
        // Inicializa componentes
        this.mapGenerator = new MapGenerator(walls, rand, player);
        this.combatManager = new CombatManager(player, enemies, walls, rand, inputHandler);
        this.playerController = new PlayerController(player, enemies, items, walls, inputHandler);
        this.shop = new Shop(inputHandler); // NOVA INICIALIZAÇÃO
        
        generateLevel();
    }

    private void generateLevel() {
        levelCleared = false;
        mapGenerator.generateLevel(level, enemies, items);
    }

    private void movePlayer(int dx, int dy) {
        if (playerController.movePlayer(dx, dy)) {
            combatManager.enemyTurn();
            checkGameOver();
        }
    }

    private void attack() {
        combatManager.playerAttack(() -> levelCleared = true);
    }

    private void checkGameOver() {
        if (!player.isAlive()) {
            renderer.showGameOver(player, level);
            System.exit(0);
        }
    }

    
    private void advanceToNextLevel() {
        if (levelCleared) {
            level++;
            
            // Verifica se deve abrir a loja
            if (level % GameConstants.SHOP_INTERVAL == 0) {
                renderer.showShopNotification(level);
                inputHandler.sleep(2000);
                openShop();
            }
            
            player.resetPosition();
            player.resetHP();
            generateLevel();
            renderer.showLevelUp(level);
            inputHandler.sleep(2000);
        }
    }

    
    private void openShop() {
        shop.openShop(player);
    }

    public boolean[][] getWalls() {
        return walls;
    }

    public void play() {
        while (player.isAlive()) {
            advanceToNextLevel();
            renderer.render(player, enemies, items, walls, level);

            String input = inputHandler.getCommand();

            switch (input) {
                case "w" -> movePlayer(0, -1);
                case "s" -> movePlayer(0, 1);
                case "a" -> movePlayer(-1, 0);
                case "d" -> movePlayer(1, 0);
                case "attack", "" -> attack();
                case "i" -> {
                    renderer.showInfo(player, enemies);
                    inputHandler.sleep(2000);
                }
                case "q" -> {
                    System.out.println("\nAté logo!");
                    return;
                }
                default -> System.out.println("Comando inválido!");
            }
        }
    }
}
