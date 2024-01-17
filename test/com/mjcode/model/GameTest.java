package com.mjcode.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {
    Tile[][] board;
    Game game;
    Player player1;
    Player player2;
    Color white;
    Color black;
    Tile positiveFromTile;
    Tile positiveToTile;
    Tile negativeFromTile;
    Tile negativeToTile;

    @BeforeEach
    void setUp() {
        board = new Tile[8][8];
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

        // Compare player 2 to player 3, and ensure no extra player has been added
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

        assertNull(positiveFromTile.getOccupant());
        assertEquals(whitePiece, positiveToTile.getOccupant());

        // Set the occupant and create a move for the opponent
        negativeFromTile.setOccupant(blackPiece);
        Move secondMove = new Move(negativeFromTile, negativeToTile);

        // Execute the second move
        game.executeMove(secondMove);
        assertNull(negativeFromTile.getOccupant());
        assertEquals(blackPiece, negativeToTile.getOccupant());
    }

    @Test
    void executeMove_pieceIsKinged() {
        // Tile before last row
        Tile currentTile = new Tile(6,1);
        Tile targetTile = new Tile(7, 0);

        // Create pieces
        Piece whitePiece = player1.addPiece(game.getTile(6, 1));

        // Set the white piece to occupy the current tile
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
    void executeMove_CapturedPiece() {
        // Create a new tile for piece to move
        Tile toTile = new Tile(4, 5);

        // Create white piece for player 1 and set piece to occupy that tile
        Piece whitePiece = player1.addPiece(game.getTile(2, 3));
        positiveFromTile.setOccupant(whitePiece);

        // Ensure that white piece occupies the positiveFromTile and not the toTile
        assertFalse(toTile.isOccupied());
        assertTrue(positiveFromTile.isOccupied());
        assertEquals(whitePiece, positiveFromTile.getOccupant());

        // Create tile to represent the tile being jumped
        Tile actualTile = new Tile(3, 4);
        board[3][4] = actualTile;

        // Create black piece for player 2 and set the piece to occupy the jumped tile
        Piece blackPiece = player2.addPiece(game.getTile(3, 4));
        actualTile.setOccupant(blackPiece);

        // Ensure the black piece is occupying jumped tile
        assertTrue(actualTile.isOccupied());
        assertEquals(blackPiece, actualTile.getOccupant());

        // Create a move that will jump from tile (2, 3) to (4, 5)
        Move move = new Move(positiveFromTile, toTile);
        // Create a jumpTile for testing. Tile is (3, 4)
        Tile jumpTile = game.jumpedTile(positiveFromTile, toTile);

        // Ensure the jump tile and actual tile matches
        assertEquals(actualTile, jumpTile);

        // Add the players
        game.addPlayer(player1);
        game.addPlayer(player2);

        // Check how many pieces each player has
        assertEquals(1, player1.getNumPieces());
        assertEquals(1, player2.getNumPieces());

        // Set the current player
        game.setCurrentPlayer(player1);

        // Create a captured piece and ensure the black piece matches the captured piece
        Piece capturedPiece = jumpTile.getOccupant();

        // Ensure that the captured piece is not null
        assertNotNull(capturedPiece);
        assertEquals(blackPiece, capturedPiece);

        // Call the execute method
        game.executeMove(move);

        // Ensure the occupant of the jump tile is null
        assertNull(jumpTile.getOccupant());

        // Ensure the toTile is occupied by the white piece and
        // that no piece is occupying the positive from tile
        assertFalse(positiveFromTile.isOccupied());
        assertTrue(toTile.isOccupied());
        assertEquals(whitePiece, toTile.getOccupant());

        // Ensure the black piece was removed from player2
        assertEquals(1, player1.getNumPieces());
        assertEquals(0, player2.getNumPieces());
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

        // Ensure the black piece does not the current player
        assertNotEquals(blackPiece.getPlayer(), game.getCurrentPlayer());
        assertEquals(whitePiece.getPlayer(), game.getCurrentPlayer());

        // Check if the second move is a legal move
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
        Game mockGame = spy(game);

        // Mock the two players
        Player mockPlayer1 = mock(Player.class);
        Player mockPlayer2 = mock(Player.class);

        Piece whitePiece = mockPlayer1.addPiece(positiveFromTile);
        Piece blackPiece = mockPlayer2.addPiece(negativeFromTile);

        positiveFromTile.setOccupant(whitePiece);
        negativeFromTile.setOccupant(blackPiece);

        // Verify that mock players has added a piece to their list
        verify(mockPlayer1).addPiece(positiveFromTile);
        verify(mockPlayer2).addPiece(negativeFromTile);

        // Verify how many times the method was called
        verify(mockPlayer1, times(1)).addPiece(any());
        verify(mockPlayer2, times(1)).addPiece(any());

        // Add the mock players to the game
        mockGame.addPlayer(mockPlayer1);
        mockGame.addPlayer(mockPlayer2);

        when(mockGame.endGame()).thenReturn(false).thenReturn(true);
        assertFalse(mockGame.endGame());

        // Set player1 to current player
        mockGame.setCurrentPlayer(mockPlayer1);
        assertEquals(mockPlayer1, mockGame.getCurrentPlayer());

        // Move
        Move move1 = new Move(positiveFromTile, positiveToTile);
        Move move2 = new Move(negativeFromTile, negativeToTile);

        // Stubbing player 1 for move
        when(mockPlayer1.chooseMove(null)).thenReturn(move1);
        // Ensure that player 1 move matches the actual move
        Move player1ActualMove = mockPlayer1.chooseMove(null);
        assertEquals(move1, player1ActualMove);

        // player 1 legal moves
        when(mockGame.isLegalMove(move1)).thenReturn(true);
        boolean resultPlayer1 = mockGame.isLegalMove(move1);
        assertTrue(resultPlayer1);

        // Stubbing player 2 for move
        when(mockPlayer2.chooseMove(null)).thenReturn(move2);
        Move player2ActualMove = mockPlayer2.chooseMove(null);
        assertEquals(move2, player2ActualMove);

        // player 2 legal moves
        when(mockGame.isLegalMove(move2)).thenReturn(true);
        boolean resultPlayer2 = mockGame.isLegalMove(player2ActualMove);
        assertTrue(resultPlayer2);

        // Call the play method
        mockGame.play();

        // Insight on the methods the mocked objects are interacting with
        System.out.println("Interaction: " + mockingDetails(mockPlayer1).getInvocations());
        System.out.println("\nInteraction: " + mockingDetails(mockPlayer2).getInvocations());

        // Verify that both players at least made one move
        verify(mockPlayer1, atLeastOnce()).chooseMove(null);
        verify(mockPlayer2, atLeastOnce()).chooseMove(null);

        // Ensure the game has ended
        assertTrue(mockGame.endGame());
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

        assertNotNull(mockGame.getCurrentPlayer()); // Ensure that current players is not null
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
        Tile fromTile = new Tile(1, 2);
        Tile toTile = new Tile(3,4);

        // Actual jumped tile
        Tile actualTile = new Tile(row, col);
        board[row][col] = actualTile;

        // Call the jumpedTile method
        Tile jumpTile = game.jumpedTile(fromTile, toTile); //

        // Ensure both tiles are not null and the jump tile matches the actual tile
        assertNotNull(actualTile);
        assertNotNull(jumpTile);
        assertEquals(actualTile, jumpTile);
    }
    @Test
    void jumpedTile_GetPiece() {
        // Create the rows and columns
        int row = 2, col = 3;

        // Create the tiles
        Tile fromTile = new Tile(1, 2);
        Tile toTile = new Tile(3,4);

        // Actual jumped tile
        Tile actualTile = new Tile(row, col);

        // Create an opponent's piece and set the piece to occupy the actual tile
        Piece blackPiece = new Piece(player2, actualTile, -1);
        actualTile.setOccupant(blackPiece);

        // Create white piece for testing purposes
        Piece whitePiece = new Piece(player1, fromTile, 1);
        fromTile.setOccupant(whitePiece);

        board[row][col] = actualTile; // Assigned the tile to the board
        assertTrue(actualTile.isOccupied()); // Ensure that the tile is occupied by a piece
        assertEquals(blackPiece, actualTile.getOccupant()); // Ensure black piece is occupying tile

        // Call the jumpedTile method
        Tile jumpTile = game.jumpedTile(fromTile, toTile);

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

        // Validate player2 is the current player
        assertEquals(player2, game.getCurrentPlayer());

        // Switch players
        game.switchTurns();

        // Validate that player 2 is not the current player
        assertNotEquals(player2, game.getCurrentPlayer());
        // Validate current player is player 1
        assertEquals(player1, game.getCurrentPlayer());
    }

}