package me.raymart.dev.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;

public class TicTacToe {

    private final char[] boardContents;
    private final Player player;
    private final Player cpu;
    private boolean isEnded = false;
    private final Player[] players;

    public TicTacToe(char playerSymbol) {

        char cpuSymbol;
        if(Character.toUpperCase(playerSymbol) == 'X') {
            cpuSymbol = 'O';
        } else {
            cpuSymbol = 'X';
        }
        this.player = new Player(PlayerType.PLAYER, Character.toUpperCase(playerSymbol));
        this.cpu = new Player(PlayerType.CPU, cpuSymbol);
        this.players = new Player[] {player, cpu};

        this.boardContents = new char[] {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        // print start message
        // game has commenced
        System.out.println("Game has commenced");
        System.out.println("Your symbol is: " + getPlayerHuman().getSymbol());
        System.out.println("CPU symbol is: " + getPlayerCPU().getSymbol() + "\n\n");

        int roll = new Random().nextInt(2);

        Player firstPlayer;
        if(roll == 1) {
            firstPlayer = player;
        } else {
            firstPlayer = cpu;
        }
        game(getNextPlayer(firstPlayer), scanner);
    }

    private void game(Player prevPlayer, Scanner scanner) {
        Player nextPlayer = prevPlayer;
        while(!isEnded) {
            if (nextPlayer.getType() == PlayerType.PLAYER) {
                System.out.println("Type from 1-9 to take your turn, ");

                int turn = nextPlayer.makeTurn(scanner);
                while (getPlayerCPU().getTurns().contains(turn) || turn == -1) {
                    System.out.println("That spot is already taken or out of range, please try again. ");
                    turn = nextPlayer.makeTurn(scanner);
                }
                nextPlayer.getTurns().add(turn);
                boardContents[turn] = nextPlayer.getSymbol();
                printBoard();
                nextPlayer = cpu;
            } else {

                System.out.println("CPU turn. ");

                int turn = nextPlayer.makeTurn();
                while (getPlayerHuman().getTurns().contains(turn) || turn == -1) {
                    turn = nextPlayer.makeTurn();
                }

                nextPlayer.getTurns().add(turn);
                boardContents[turn] = nextPlayer.getSymbol();
                printBoard();

                nextPlayer = player;
            }

            checkForWinner(p -> {
                if (p != null) {
                    isEnded = true;
                    System.out.println("Game ended.");
                    System.out.println("Winner is: " + p.getType().getName());
                }
            });
        }
    }

    private Player getNextPlayer(Player player) {
        if(player.getType() == PlayerType.PLAYER) {
            return getPlayerCPU();
        }
        return getPlayerHuman();
    }

    private void printBoard() {

        // first row
        System.out.println(boardContents[0] + "|" + boardContents[1] + "|" + boardContents[2]);
        printDivider();

        // second row
        System.out.println(boardContents[3] + "|" + boardContents[4] + "|" + boardContents[5]);
        printDivider();

        // third row
        System.out.println(boardContents[6] + "|" + boardContents[7] + "|" + boardContents[8]);
        System.out.println("\n");
    }

    private void printDivider() {
        System.out.println("-+-+-");
    }

    private void checkForWinner(Consumer<Player> winner) {
        Optional<Player> winnerPlayer = Arrays.stream(players)
                .filter(player -> {
                    for(int[] winningNum : possibleWins()) {
                        if(player.getTurns().contains(winningNum[0]) && player.getTurns().contains(winningNum[1]) && player.getTurns().contains(winningNum[2])) {
                            return true;
                        }
                    }
                    return false;
                }).findAny();
        if(winnerPlayer.isPresent()) {
            winner.accept(winnerPlayer.get());
        } else {
            winner.accept(null);
        }
    }

    private int[][] possibleWins() {
            return new int[][] {{0, 1, 2}, {0, 3, 6}, {0,4,8}, {1,4,7}, {2,5,8}, {3,4,5}, {6,7,8}, {6,4,2}};
    }

    private Player getPlayerHuman() {
        return Arrays.stream(players).filter(p -> p.getType() == PlayerType.PLAYER).findAny().get();
    }

    private Player getPlayerCPU() {
        return Arrays.stream(players).filter(p -> p.getType() == PlayerType.CPU).findAny().get();
    }

    private Player getRandomPlayer() {
        return players[new Random().nextInt(players.length)];
    }



}
