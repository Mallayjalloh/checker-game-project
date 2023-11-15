package com.mjcode;

import com.mjcode.model.*;

public class CheckersDemo {

    public static void main(String[] args) {

        // Declare local variables
        final int ROW = 8, COL = 8;

        // Create a 2D array to represent the checkers board
        Tile[][] board = new Tile[8][8];

        // Create an instance of the tile class to represent a space on the board
        Tile tile = new Tile(2, 5);

        Player player1 = new Player() {
            @Override
            public void executeMove(Move move) {

            }

            @Override
            public Game getGame() {
                return null;
            }

            @Override
            public Move chooseMove(Move move) {
                return null;
            }
        };

        Player player2 = new Player() {
            @Override
            public void executeMove(Move move) {

            }

            @Override
            public Game getGame() {
                return null;
            }

            @Override
            public Move chooseMove(Move move) {
                return null;
            }
        };

        // Create two Piece objects that represents two players
        Piece white = new Piece(player1, tile, 1); // Main player
        Piece black = new Piece(player2, tile, -1); // Opponent player

        // Displaying 8x8 board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" x ");
            }
            System.out.println();
        }

        System.out.println();

        System.out.println(white.isKinged());

        player1.king(white);
        System.out.println(white.isKinged());


    }


}
