package com.roguelike.ui;

import com.roguelike.util.InputHandler;

public class UI {
    private InputHandler inputHandler;

    public UI(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void showMainMenu() {
        clearScreen();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║                                       ║");
        System.out.println("║         🎮 ROGUELIKE GAME 🎮          ║");
        System.out.println("║                                       ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  [1] Novo Jogo                        ║");
        System.out.println("║  [2] Instruções                       ║");
        System.out.println("║  [3] Sair                             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("\nEscolha uma opção: ");
    }

    public void showInstructions() {
        clearScreen();
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                     INSTRUÇÕES DO JOGO                     ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║                                                           ║");
        System.out.println("║ OBJETIVO: Sobreviva aos níveis e derrote todos os inimigos║");
        System.out.println("║                                                           ║");
        System.out.println("║ CONTROLES:                                                ║");
        System.out.println("║   W - Mover para cima                                     ║");
        System.out.println("║   S - Mover para baixo                                    ║");
        System.out.println("║   A - Mover para esquerda                                 ║");
        System.out.println("║   D - Mover para direita                                  ║");
        System.out.println("║   ESPAÇO - Atacar inimigo adjacente                       ║");
        System.out.println("║   I - Mostrar informações                                 ║");
        System.out.println("║   Q - Sair do jogo                                        ║");
        System.out.println("║                                                           ║");
        System.out.println("║ ITENS:                                                    ║");
        System.out.println("║   P - Poção de Vida (restaura 10 HP)                      ║");
        System.out.println("║   $ - Ouro (valor: 25)                                    ║");
        System.out.println("║   † - Adaga (aumenta ATK em 2)                            ║");
        System.out.println("║                                                           ║");
        System.out.println("║ SÍMBOLOS:                                                 ║");
        System.out.println("║   @ - Você (jogador)                                      ║");
        System.out.println("║   G - Goblin (inimigo)                                    ║");
        System.out.println("║                                                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.print("\nPressione ENTER para voltar ao menu...");
        inputHandler.getCommand();
    }

    public void showWelcomeMessage() {
        clearScreen();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║                                       ║");
        System.out.println("║   Bem-vindo ao Roguelike Game! 🎮     ║");
        System.out.println("║                                       ║");
        System.out.println("║  Digite 'i' durante o jogo para       ║");
        System.out.println("║  ver as instruções de controle.       ║");
        System.out.println("║                                       ║");
        System.out.println("║      Boa sorte na sua jornada!        ║");
        System.out.println("║                                       ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("\nPressione ENTER para começar...");
        inputHandler.getCommand();
    }

    public void showPauseMenu() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║         ⏸ JOGO PAUSADO ⏸             ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  [1] Retomar jogo                     ║");
        System.out.println("║  [2] Ver instruções                   ║");
        System.out.println("║  [3] Sair para o menu                 ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("\nEscolha uma opção: ");
    }

    public void showGameOverScreen(int finalLevel, int totalGold) {
        clearScreen();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║                                       ║");
        System.out.println("║          ☠ VOCÊ FOI DERROTADO ☠       ║");
        System.out.println("║                                       ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  Nível alcançado: " + String.format("%-20s", finalLevel) + "║");
        System.out.println("║  Ouro total coletado: " + String.format("%-15s", totalGold) + "║");
        System.out.println("║                                       ║");
        System.out.println("║         Obrigado por jogar! 🎮         ║");
        System.out.println("║                                       ║");
        System.out.println("╚═══════════════════════════════════════╝");
    }

    public void showCommandHelp() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      COMANDOS DISPONÍVEIS              ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  w/a/s/d - Mover                      ║");
        System.out.println("║  ESPAÇO   - Atacar                    ║");
        System.out.println("║  i        - Informações               ║");
        System.out.println("║  h        - Ajuda (este menu)         ║");
        System.out.println("║  q        - Sair                      ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    public void showWarning(String message) {
        System.out.println("\n⚠ AVISO: " + message);
    }

    public void showSuccess(String message) {
        System.out.println("\n✓ " + message);
    }

    public void showError(String message) {
        System.out.println("\n✗ ERRO: " + message);
    }

    public void clearScreen() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}
