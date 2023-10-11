package com.mjcode.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    Tile[][] board;

    Tile whiteCurrentTile;
    Tile blackCurrentTile;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {

        board = new Tile[8][8];

        whiteCurrentTile = new Tile(6,4);

        blackCurrentTile = new Tile(2,4);

        // Since Player is an abstract class, we are creating a mock instance for player 1 for testing
        player1 = new Player() {
            @Override
            public Move chooseMove(Move move) {
                return null;
            }
        };
        // Mock instance for player 2
        player2 = new Player() {
            @Override
            public Move chooseMove(Move move) {
                return null;
            }
        };

    }

    @Test
    void testCanCapture() {

        Piece whitePiece = new Piece(player1, whiteCurrentTile, 1);
        Piece blackPiece = new Piece(player2, blackCurrentTile, 1);

        // Testing when pieces belong to different players
        assertTrue(whitePiece.canCapture(blackPiece));

        // Test when pieces belong to the same player
        assertFalse(whitePiece.canCapture(whitePiece));
    }

    @Test
    void testCanMoveToOccupiedTile() {
        Tile targetTile = new Tile(5,5);
        Piece whitePiece = new Piece(player1, whiteCurrentTile, 1);

        assertFalse(targetTile.isOccupied());
        assertFalse(whitePiece.canMoveTo(targetTile, board));
    }

    @Test
    @Disabled
    void testCanMoveToLegalMove() {

        Tile targetTile = new Tile(5,5);
        Piece whitePiece = new Piece(player1, whiteCurrentTile, 1);
        targetTile.isOccupied();

        assertTrue(whitePiece.canMoveTo(targetTile, board));

    }
    @Test
    void testCanMoveToIllegalMove() {

    }

    @Test
    void testGetPlayer() {

        Piece whitePiece = new Piece(player1, whiteCurrentTile, 1);
        Piece blackPiece = new Piece(player2, blackCurrentTile, 1);

        // Test if the piece belongs to the same player
        assertEquals(player1, whitePiece.getPlayer());

        // Test that piece does not belong to the opponent player.
        assertNotEquals(player1, blackPiece.getPlayer());
    }

    @Test
    void testIsKinged() {

        // Piece is on the opponent's end of the board
        Tile currentTile = new Tile(0,2);

        // Create a Piece instance that is a non-king piece
        Piece nonKingPiece = new Piece(player1, whiteCurrentTile, 1);

        // Create a Piece instance that is a king piece
        Piece kingPiece = new Piece(player1, currentTile, 1);
        kingPiece.king(); // King the piece

        // Assert that isKinged returns false for non-king piece
        assertFalse(nonKingPiece.isKinged());

        // Assert that isKinged returns true for a King piece
        assertTrue(kingPiece.isKinged());
    }

    @Test
    void testKing() {

        Piece whitePiece = new Piece(player1, whiteCurrentTile, 1);
        whitePiece.king();
        assertTrue(whitePiece.isKinged());

    }
}