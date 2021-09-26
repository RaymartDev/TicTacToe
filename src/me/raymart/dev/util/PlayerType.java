package me.raymart.dev.util;

public enum PlayerType {

    PLAYER("Player"), CPU("CPU");

    private final String name;
    PlayerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
