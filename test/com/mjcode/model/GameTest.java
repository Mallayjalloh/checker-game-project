package com.mjcode.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {
    Tile[][] board;
    Game game;
    Player player1;
    Player player2;
    ArrayList<Move> moves;
    Color white;
    Color black;

    @BeforeEach
    void setUp() {
        board = new Tile[8][8]; // Initializing the board

        game = new Game() {
            @Override
            public Tile getTile(int row, int col) {
                return board[row][col];
            }
        };

        white = Color.WHITE;
        black = Color.BLACK;

        moves = new ArrayList<>();
        player1 = new Player() {
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

        player2 = new Player() {
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

    }
    @Test
    void addPlayer_GameHasNoPayer() {
        // Add player1
        game.addPlayer(player1);

        // Validate player1 has been assigned to WHITE and is in the positive direction
        assertEquals(white, player1.getColor());
        assertEquals(1, player1.getDirection());

    }
    @Test
    void addPlayer_GameHasOnePlayer() {
        // Add player 1
        game.addPlayer(player1);

        // Validate player1 has been added
        assertEquals(white, player1.getColor());
        assertEquals(1, player1.getDirection());

        // Add player 2
        game.addPlayer(player2);

        // Validate player2 has been added
        assertEquals(black, player2.getColor());
        assertEquals(-1, player2.getDirection());
    }
    @Test
    void endGame() {
        // Add the players
        game.addPlayer(player1);
        game.addPlayer(player2);

        Piece whitePiece = player1.addPiece(new Tile(3, 2));
        Piece blackPiece = player2.addPiece(new Tile(6, 1));

        // validate both players has 1 piece in there list
        assertEquals(1, player1.getNumPieces());
        assertEquals(1, player2.getNumPieces());

        // Confirm game has not ended since neither player has an empty list
        assertEquals(1, player1.getNumPieces());
        assertEquals(1, player2.getNumPieces());
        assertFalse(game.endGame());

        // Remove piece from player1
        player1.removePiece(whitePiece);

        // Validate piece has been removed from player1
        assertEquals(0, player1.getNumPieces());
        assertTrue(game.endGame());
    }

    @Test
    void getTile() {
        int row = 3;
        int col = 4;

        Tile expectedTile = new Tile(row, col);
        board[row][col] = expectedTile;

        Tile actualTile = game.getTile(row, col);

        assertNotNull(expectedTile);
        assertNotNull(actualTile);
        assertEquals(expectedTile, actualTile);
    }

    @Test
    void executeMove() {

        // Create the tile
        Tile fromTile = new Tile(2,3);
        Tile toTile = new Tile(3, 4);

        // Create pieces
        Piece whitePiece = player1.addPiece(game.getTile(2, 3));
        Piece blackPiece = player2.addPiece(game.getTile(7, 2));

        // Set the occupant and create a move from tile (2, 3) to (3, 4)
        fromTile.setOccupant(whitePiece);
        Move move = new Move(fromTile, toTile);

        // Add the players
        game.addPlayer(player1);
        game.addPlayer(player2);
        // Set the current player
        game.setCurrentPlayer(player1);

        // Execute the move
        game.executeMove(move);
        assertEquals(whitePiece, toTile.getOccupant());

        // Create a new tile from and to tile for the black piece to move
        Tile secondFromTile = new Tile(7, 2);
        Tile secondToTile = new Tile(6, 3);

        // Set the occupant and create a move
        secondFromTile.setOccupant(blackPiece);
        Move secondMove = new Move(secondFromTile, secondToTile);

        // Execute the second move
        game.executeMove(secondMove);
        assertEquals(blackPiece, secondToTile.getOccupant());

    }

    @Test
    void isLegalMove() {
        // Create fromTile and toTile object
        Tile fromTile = new Tile(2, 3);
        Tile toTile = new Tile(3, 4);

        // Create a Piece object
        Piece whitePiece = new Piece(player1, fromTile, 1);
        // Set the piece on the tile
        fromTile.setOccupant(whitePiece);

        // Creat a move object
        Move move = new Move(fromTile, toTile);

        // Set the current player of the game
        game.setCurrentPlayer(player1);

        boolean result = game.isLegalMove(move);
        assertTrue(result);
    }
    @Test
    void isNotLegalMove() {

        Tile fromTile = new Tile(2, 3);
        Tile toTile = new Tile(3, 4);

        // No piece will occupy the fromTile

        // Create move object
        Move move = new Move(fromTile, toTile);

        // Set the current player of the game
        game.setCurrentPlayer(player1);

        boolean result = game.isLegalMove(move);
        assertFalse(result);
    }

    @Test
    void play() {
        // Requires Mockito
        Game testGame = spy(game);

        // Create tiles for player1
        Tile positiveFromTile = new Tile(2, 3);
        Tile positiveToTile = new Tile(3, 4);

        // Game will return false initially and then true to indicate game is over
        doReturn(false, true).when(testGame).endGame();

        // Add both players to the game
        testGame.addPlayer(player1);
        testGame.addPlayer(player2);

        // Set player1 to current player
        testGame.setCurrentPlayer(player1);

        // creat a move for player1
        Move move1 = new Move(positiveFromTile, positiveToTile);

        // Simulate a legal move being chosen by returning true if move is legal
        doReturn(true).when(testGame).isLegalMove(any());
        when(testGame.getCurrentPlayer().chooseMove(null)).thenReturn(move1);
    }

    @Test
    void startGame() {
        // Add the players
        game.addPlayer(player1);
        game.addPlayer(player2);

        //game.startGame();
    }

    @Test
    void switchTurns() {
        // Create a current player object
        Player currentPlayer;

        // Add players to the game
        game.addPlayer(player1);
        game.addPlayer(player2);

        // Set player1 as current player
        //currentPlayer = player1;
        game.setCurrentPlayer(player1);

        // Validate player1 is the current player
        assertEquals(player1, game.getCurrentPlayer());

        // Switch players
        game.switchTurns();

        // Validate that player 1 is not the current player
        assertNotEquals(player1, game.getCurrentPlayer());
        // Validate current player is player 2
        assertEquals(player2, game.getCurrentPlayer());
    }

}