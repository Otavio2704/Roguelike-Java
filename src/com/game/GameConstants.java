package com.roguelike.game;

public class GameConstants {
    // Dimensões do mapa
    public static final int WIDTH = 20;
    public static final int HEIGHT = 10;

    // Configurações do jogador
    public static final int PLAYER_INITIAL_HP = 30;
    public static final int PLAYER_INITIAL_ATK = 5;
    public static final int PLAYER_INITIAL_X = 2;
    public static final int PLAYER_INITIAL_Y = 2;
    public static final char PLAYER_SYMBOL = '@';

    // Configurações do jogo
    public static final int INITIAL_LEVEL = 1;

    // Configurações de inimigos
    public static final int ENEMY_BASE_HP = 10;
    public static final int ENEMY_BASE_ATK = 2;
    public static final char ENEMY_SYMBOL = 'G';
    public static final String ENEMY_NAME = "Goblin";

    // Configurações de itens
    public static final String[] ITEM_NAMES = {"Poção", "Ouro", "Adaga"};
    public static final char[] ITEM_SYMBOLS = {'P', '$', '†'};
    public static final int[] ITEM_VALUES = {10, 25, 5};

    // Valores de efeito
    public static final int POTION_HEAL = 10;
    public static final int GOLD_REWARD = 10;
    public static final int DAGGER_ATK_BONUS = 2;

    // Configurações de combate
    public static final int ATTACK_RANGE = 1;
    public static final int ENEMY_PURSUIT_RANGE = 5;
    public static final int ENEMY_ATTACK_RANGE = 1;
}
