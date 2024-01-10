package com.mjcode.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
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
    Tile positiveFromTile;
    Tile positiveToTile;
    Tile negativeFromTile;
    Tile negativeToTile;

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

        // Create tiles for player1
        positiveFromTile = new Tile(2, 3);
        positiveToTile = new Tile(3, 4);

        // Create tiles for player 2
        negativeFromTile = new Tile(7, 2);
        negativeToTile = new Tile(6, 3);

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
    void addPlayer_GameHasNoPlayers() {
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

    // ** Test if game already has two players, should do nothing ** //
    @Test
    void addPlayer_GameHasTwoPlayers_NoPlayersAdded() {
        // Add player 1
        game.addPlayer(player1);
        // Validate player 1
        assertEquals(white, player1.getColor());
        assertEquals(1, player1.getDirection());

        // Add player 2
        game.addPlayer(player2);
        // Validate player 2
        assertEquals(black, player2.getColor());
        assertEquals(-1, player2.getDirection());

        // Create player 3
        Player extraPlayer = new Player() {
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
        // Add player 3
        game.addPlayer(extraPlayer);

        // Compare player 1 to player 3, and ensure no extra player has been added
        assertNotEquals(white, extraPlayer.getColor());
        assertNotEquals(1, extraPlayer.getDirection());

        // Compare player 1 to player 3, and ensure no extra player has been added
        assertNotEquals(black, extraPlayer.getColor());
        assertNotEquals(-1, extraPlayer.getDirection());

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
        // Create pieces
        Piece whitePiece = player1.addPiece(game.getTile(2, 3));
        Piece blackPiece = player2.addPiece(game.getTile(7, 2));

        // Set the current tile
        game.setCurrentTile(positiveFromTile);

        // Set the occupant and create a move from tile (2, 3) to (3, 4)
        positiveFromTile.setOccupant(whitePiece);
        Move move = new Move(positiveFromTile, positiveToTile);

        // Add the players
        game.addPlayer(player1);
        game.addPlayer(player2);

        // Set the current player
        game.setCurrentPlayer(player1);

        // Execute the move
        game.executeMove(move);

        // ** Assert that the fromTile.occupant is null ** //
        assertNull(positiveFromTile.getOccupant());
        assertEquals(whitePiece, positiveToTile.getOccupant());

        // Set the occupant and create a move
        negativeFromTile.setOccupant(blackPiece);
        Move secondMove = new Move(negativeFromTile, negativeToTile);

        // Execute the second move
        game.executeMove(secondMove);
        assertEquals(blackPiece, negativeToTile.getOccupant());
    }

    @Test
    void executeMove_pieceIsKinged() {

        // Tile before last row
        Tile currentTile = new Tile(6,1);
        Tile targetTile = new Tile(7, 0);

        // Set the current tile
        game.setCurrentTile(currentTile);

        // Create pieces
        Piece whitePiece = player1.addPiece(game.getTile(6, 1));

        // Set the occupant the tile before the last row and check if it occupies that tile
        currentTile.setOccupant(whitePiece);
        assertTrue(currentTile.isOccupied());
        assertEquals(whitePiece, currentTile.getOccupant());

        // Check if the white piece is a king
        assertFalse(whitePiece.isKinged());

        Move move = new Move(currentTile, targetTile);

        // Add the players
        game.addPlayer(player1);
        game.addPlayer(player2);

        // Set the current player
        game.setCurrentPlayer(player1);

        // Execute the move
        game.executeMove(move);

        // Check if the current tile is no longer occupied, and if the target tile is
        // occupied with the white piece
        assertFalse(currentTile.isOccupied());
        assertTrue(targetTile.isOccupied());
        assertEquals(whitePiece, targetTile.getOccupant());

        // Check if piece is now a king
        assertTrue(whitePiece.isKinged());
    }

    @Test
    void isLegalMove() {
        // Create a Piece object
        Piece whitePiece = new Piece(player1, positiveFromTile, 1);
        Piece blackPiece = new Piece(player2, negativeFromTile, -1);
        // Set the piece on the tile
        positiveFromTile.setOccupant(whitePiece);
        negativeFromTile.setOccupant(blackPiece);

        // Create a move object
        Move move = new Move(positiveFromTile, positiveToTile);

        // Set the current player of the game
        game.setCurrentPlayer(player1);

        boolean result = game.isLegalMove(move);
        assertTrue(result);

        // Create a new move
        Move move2 = new Move(negativeFromTile, negativeToTile);

        // Check if the does not belong to the current player
        assertNotEquals(blackPiece.getPlayer(), game.getCurrentPlayer());

        // Check if it is a legal move
        result = game.isLegalMove(move2);
        assertFalse(result);
    }
    @Test
    void isNotLegalMove() {
        // No piece will occupy the positiveFromTile

        // Create move object
        Move move = new Move(positiveFromTile, positiveToTile);

        // Set the current player of the game
        game.setCurrentPlayer(player1);

        boolean result = game.isLegalMove(move);
        assertFalse(result);
    }

    @Test
    void isNotLegalMove_OutOfBound() {
        Tile fromTile = new Tile(8, 1);
        Tile toTile = new Tile(7, 2);

        // Create move object
        Move move = new Move(fromTile, toTile);

        // Set the current player of the game
        game.setCurrentPlayer(player1);

        boolean result = game.isLegalMove(move);
        assertFalse(result);
    }

    @Test
    void play() {
        // Create a spy
        Game testGame = spy(game);

        Player player1Test = mock(Player.class);
        Player player2Test = mock(Player.class);

        // Add both players to the game
        testGame.addPlayer(player1Test);
        testGame.addPlayer(player2Test);

        // Set player1 to current player
        testGame.setCurrentPlayer(player1Test);
        assertEquals(player1Test, testGame.getCurrentPlayer());

        // Move
        Move move1 = new Move(positiveFromTile, positiveToTile);
        Move move2 = new Move(negativeFromTile, negativeToTile);

        // Stubbing player moves for testing
        when(player1Test.chooseMove(null)).thenReturn(move1);
        when(player2Test.chooseMove(null)).thenReturn(move2);
        //doReturn(move1).when(player1Test).chooseMove(null);

        // Simulate a legal move being chosen by returning true if move is legal
        doReturn(true).when(testGame).isLegalMove(any());

        // Execute move for player 1
        player1Test.executeMove(move1);

        // Switch current player and execute player 2 move
        testGame.switchTurns();
        player2Test.executeMove(move2);

        // Call the play method
        testGame.play();

        // Check if the current player is player 2
        assertEquals(player2Test, testGame.getCurrentPlayer());

        //verify(player1Test, atLeastOnce()).chooseMove(null);
    }

    @Test
    void startGame() {
        // Create a spy
        Game mockGame = spy(game);

        // Mock the two players
        Player mockPlayer1 = mock(Player.class);
        Player mockPlayer2 = mock(Player.class);

        // Ensure that the game flag is set to false
        assertFalse(mockGame.isGameStarted());

        // Add the players
        mockGame.addPlayer(mockPlayer1);
        mockGame.addPlayer(mockPlayer2);

        // Start the game
        mockGame.startGame();

        assertNotNull(mockGame.getCurrentPlayer()); // Ensure that both players are not null
        assertEquals(mockPlayer1, mockGame.getCurrentPlayer()); // Ensure player 1 is the current player
        assertTrue(mockGame.isGameStarted()); // Ensure the game flag is set to true, indicating the game has started

        // Checking if player 2 is now the current player
        mockGame.switchTurns();
        assertEquals(mockPlayer2, mockGame.getCurrentPlayer());

        // Check that the addPiece method has been called 12 times for both players
        verify(mockPlayer1, times(12)).addPiece(any());
        verify(mockPlayer2, times(12)).addPiece(any());
    }
    @Test
    void jumpedTile() {
        // Create the rows and columns
        int row = 2, col = 3;

        // Create the tiles
        Tile currentTile = new Tile(1, 2);
        Tile targetTile = new Tile(3,4);

        // Set up the current tile
        game.setCurrentTile(currentTile);

        // Actual jumped tile
        Tile actualTile = new Tile(row, col);
        board[row][col] = actualTile;

        // Call the jumpedTile method
        Tile jumpTile = game.jumpedTile(targetTile, board);

        // Ensure the jump tile matches the actual tile
        assertNotNull(actualTile);
        assertNotNull(jumpTile);
        assertEquals(actualTile, jumpTile);
    }
    @Test
    void jumpedTile_GetPiece() {
        // Create the rows and columns
        int row = 2, col = 3;

        // Create the tiles
        Tile currentTile = new Tile(1, 2);
        Tile targetTile = new Tile(3,4);

        // Set up the current tile
        game.setCurrentTile(currentTile);

        // Actual jumped tile
        Tile actualTile = new Tile(row, col);

        // Create an opponent's piece and set the piece to occupy the actual tile
        Piece blackPiece = new Piece(player2, actualTile, -1);
        actualTile.setOccupant(blackPiece);

        // Create white piece for testing purposes
        Piece whitePiece = new Piece(player1, actualTile, 1);

        board[row][col] = actualTile; // Assigned the tile to the board
        assertTrue(actualTile.isOccupied()); // Ensure that the tile is occupied by a piece

        // Call the jumpedTile method
        Tile jumpTile = game.jumpedTile(targetTile, board);

        // Ensure the jump tile matches the actual tile
        assertEquals(actualTile, jumpTile);

        // Ensure the occupant of that tile matches the black piece
        assertEquals(blackPiece, jumpTile.getOccupant());
        assertNotEquals(whitePiece, jumpTile.getOccupant());
    }

    @Test
    void switchTurns() {
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

    @Test
    void switchTurns_Player2IsCurrentPlayer() {
        // Add players to the game
        game.addPlayer(player1);
        game.addPlayer(player2);

        // Set player2 as current player
        game.setCurrentPlayer(player2);

        // Validate player1 is the current player
        assertEquals(player2, game.getCurrentPlayer());

        // Switch players
        game.switchTurns();

        // Validate that player 2 is not the current player
        assertNotEquals(player2, game.getCurrentPlayer());
        // Validate current player is player 1
        assertEquals(player1, game.getCurrentPlayer());
    }

}