package me.raymart.dev.util;

import java.util.*;

public class Player {

    private final PlayerType type;
    private final char symbol;
    private final int hash;
    private final Set<Integer> turns;
    public Player(PlayerType type, char symbol) {
        this.type = type;
        this.symbol = symbol;
        this.hash = type.hashCode();
        this.turns = new HashSet<>();
    }

    public PlayerType getType() {
        return type;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Player)) {
            return false;
        }
        return getType() == ((Player)o).getType();
    }

    public int makeTurn() {
        if(getType() == PlayerType.CPU) {
            return roll();
        }
        return -1;
    }

    public int makeTurn(Scanner scanner) {
        if(getType() == PlayerType.CPU) {
            return roll();
        }
        int playerTurn = scanner.nextInt();
        if(playerTurn <= 0) {
            return -1;
        }
        if(playerTurn > 9) {
            return -1;
        }
        int turn = playerTurn - 1;
        if(getTurns().contains(turn)) {
            return -1;
        }
        return turn;
    }

    public Set<Integer> getTurns() {
        return turns;
    }

    public int roll() {
        return new Random().nextInt(9);
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
