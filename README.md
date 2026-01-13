# 🎮 Roguelike Java

Um jogo roguelike clássico desenvolvido em Java puro, jogado direto no terminal. Explore masmorras geradas proceduralmente, colete itens, derrote inimigos e tente sobreviver o máximo de níveis possível!

## 📋 Sobre o Projeto

Este é um roguelike minimalista que captura a essência dos jogos do gênero: morte permanente, níveis gerados proceduralmente e combate tático baseado em turnos. O jogo foi desenvolvido com foco em simplicidade e jogabilidade, ideal para quem quer estudar programação de jogos em Java ou simplesmente se divertir com um roguelike no terminal.

## ✨ Características

- 🗺️ **Geração procedural de níveis** - Cada partida é única
- ⚔️ **Sistema de combate baseado em turnos** - Pense antes de agir
- 🎒 **Sistema de itens** - Poções, ouro e armas para coletar
- 🏪 **Loja de power-ups** - Gaste suas moedas em melhorias permanentes a cada 4 níveis
- 👾 **IA inimiga** - Goblins que perseguem e atacam o jogador
- 📈 **Progressão de dificuldade** - Cada nível fica mais desafiador
- 🎨 **Interface ASCII** - Visual clássico de roguelike

## 🎯 Como Jogar

### Objetivo
Sobreviva aos níveis derrotando todos os inimigos. A cada nível completado, a dificuldade aumenta com mais inimigos e desafios! A cada 4 níveis, uma loja aparecerá para você gastar suas moedas em power-ups.

### Controles
- `W` - Mover para cima
- `S` - Mover para baixo
- `A` - Mover para esquerda
- `D` - Mover para direita
- `ESPAÇO` ou `attack` - Atacar inimigo adjacente
- `I` - Mostrar informações do jogador
- `Q` - Sair do jogo

### Símbolos no Mapa
- `@` - Você (o jogador)
- `G` - Goblin (inimigo)
- `P` - Poção de vida (+10 HP)
- `$` - Ouro (+25 moedas)
- `†` - Adaga (+2 ATK)
- `#` - Parede

## 🚀 Como Executar

### Pré-requisitos
- Java JDK 11 ou superior instalado
- Terminal ou prompt de comando

### Compilação e Execução

1. Clone o repositório:
```bash
git clone https://github.com/Otavio2704/Roguelike-Java.git
cd Roguelike-Java
```

2. Compile o projeto:
```bash
javac -d bin src/com/roguelike/**/*.java
```

3. Execute o jogo:
```bash
java -cp bin com.roguelike.Main
```

### Alternativa com IDE
Você também pode abrir o projeto em qualquer IDE Java (IntelliJ IDEA, Eclipse, VS Code com extensão Java) e executar a classe `Main.java`.

## 🏗️ Estrutura do Projeto

```
Roguelike-Java\src\com
├── Main.java              # Ponto de entrada do jogo
├── entity/
│   ├── Entity.java        # Classe base para entidades
│   ├── Player.java        # Jogador
│   └── Enemy.java         # Inimigos
├── item/
│   ├── Item.java          # Classe base para itens
│   ├── Potion.java        # Poção de vida
│   ├── Gold.java          # Ouro
│   └── Weapon.java        # Arma (Adaga)
├── shop/
│   ├── Shop.java          # Gerenciador da loja
│   ├── ShopUI.java        # Interface visual da loja
│   ├── PowerUp.java       # Classe base para power-ups
│   └── powerups/
│       ├── HealthPotion.java    # Poção grande
│       ├── HealthUpgrade.java   # Aumento de HP máximo
│       └── AttackUpgrade.java   # Aumento de ATK
├── game/
│   ├── RoguelikeGame.java # Lógica principal do jogo
│   ├── GameConstants.java # Constantes do jogo
│   ├── CombatManager.java # Lógica de combate
│   ├── MapGenerator.java  # Gerador de mapas
│   └── PlayerController.java # Lógica do jogador
├── ui/
│   ├── UI.java            # Interface de menus
│   └── Renderer.java      # Renderização do mapa
└── util/
    └── InputHandler.java  # Gerenciamento de entrada
```

## 🎮 Mecânicas do Jogo

### Combate
- O jogador ataca inimigos adjacentes pressionando `ESPAÇO` ou escrevendo `attack`
- Cada ataque causa dano baseado no ATK do jogador + variação aleatória
- Inimigos atacam automaticamente quando estão adjacentes ao jogador
- Inimigos perseguem o jogador quando estão dentro do alcance

### Progressão
- **HP inicial**: 30
- **ATK inicial**: 5
- A cada nível, mais inimigos aparecem
- Inimigos ficam mais fortes a cada nível
- O HP é restaurado ao passar de nível

### Itens
- **Poção (P)**: Restaura 10 pontos de vida
- **Ouro ($)**: Adiciona 25 moedas ao seu total
- **Adaga (†)**: Aumenta permanentemente seu ATK em 2

### Loja de Power-ups 🏪
A cada 4 níveis completados (níveis 4, 8, 12...), uma loja aparece com power-ups especiais:

- **🧪 Poção Grande** (30 ouro): Restaura 20 pontos de vida imediatamente
- **💚 Aumento de HP Máximo** (50 ouro): Aumenta seu HP máximo permanentemente em 10 pontos
- **⚔️ Aumento de Ataque** (75 ouro): Aumenta seu ATK permanentemente em 3 pontos

Gerencie bem suas moedas para comprar os upgrades que mais combinam com seu estilo de jogo!

## 🛠️ Tecnologias Utilizadas

- Java 11+
- Programação Orientada a Objetos
- Padrões de Design (Herança, Polimorfismo, Injeção de Dependências)
- Manipulação de terminal ANSI

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autor

**Otavio2704**

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para:

1. Fazer um fork do projeto
2. Criar uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abrir um Pull Request

## 📧 Contato

Para dúvidas ou sugestões, abra uma issue no GitHub!

---

⭐ Se você gostou do projeto, considere dar uma estrela no repositório!
