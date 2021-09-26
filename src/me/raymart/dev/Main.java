package me.raymart.dev;

import me.raymart.dev.util.TicTacToe;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to my tic tac toe game!");
        System.out.println("Made by: Raymart Dev");
        System.out.println("Please choose your symbol: X, O");

        String answer = scanner.nextLine();
        while(notAccepted(answer)) {
            System.out.println("Please choose correct symbol from the list above!");
            answer = scanner.nextLine();
        }

        TicTacToe ticTacToe = new TicTacToe(answer.toCharArray()[0]);

        // start the game
        ticTacToe.start();
    }

    private static boolean notAccepted(String answer) {
        if(!answer.equalsIgnoreCase("X")) {
            return false;
        }
        if(!answer.equalsIgnoreCase("O")) {
            return false;
        }
        return !answer.equals("0");
    }
}
