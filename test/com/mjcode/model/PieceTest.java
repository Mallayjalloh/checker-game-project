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

        //whiteCurrentTile = new Tile(3,5);
        whiteCurrentTile = new Tile(3,4);

        //blackCurrentTile = new Tile(6,5);
        blackCurrentTile = new Tile(5,4);

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
        Piece blackPiece = new Piece(player2, blackCurrentTile, -1);

        // Testing when pieces belong to different players
        assertTrue(whitePiece.canCapture(blackPiece));

        // Test when pieces belong to the same player
        assertFalse(whitePiece.canCapture(whitePiece));
    }

    @Test
    void testCanMoveToOccupiedTile() {

        Tile targetTile = new Tile(4,5);
        Piece whitePiece = new Piece(player1, whiteCurrentTile, 1);
        Piece blackPiece = new Piece(player2, targetTile, -1);

        // Add piece to the target tile
        targetTile.setOccupant(blackPiece);

        // Test when the target tile is occupied by a checkers piece
        assertTrue(targetTile.isOccupied());
        assertFalse(whitePiece.canMoveTo(targetTile, board));
    }

    @Test
    void testCanMoveToLegalMove() {

        Tile targetTile = new Tile(4,5);
        Piece whitePiece = new Piece(player1, whiteCurrentTile, 1);

        assertTrue(whitePiece.canMoveTo(targetTile, board));
    }
    @Test
    void testCanMoveToIllegalMove() {

        Tile backwardTile = new Tile(2,3);
        Piece whitePiece = new Piece(player1, whiteCurrentTile, 1);

        assertFalse(whitePiece.canMoveTo(backwardTile, board));

    }
    @Test
    void testPawnCanMoveToCanJump() {

        Tile targetTile = new Tile(5,6);
        Piece whitePiece = new Piece(player1, whiteCurrentTile,1);

        Tile jumpedTile = new Tile(4,5);
        Piece blackPiece = new Piece(player2, jumpedTile, -1);

        jumpedTile.setOccupant(blackPiece);
        assertTrue(jumpedTile.isOccupied());

        board[4][5] = jumpedTile;

        // test white pawn can make the jump
        assertTrue(whitePiece.canMoveTo(targetTile, board));

    }
    @Test
    void testKingCanMoveToCanJump() {

        // King Piece jumping forward
        Tile forwardTile = new Tile(5, 6);
        Tile backwardTile = new Tile(1,2);

        Piece kingPiece = new Piece(player1, whiteCurrentTile,1);
        kingPiece.king();
        assertTrue(kingPiece.isKinged());

        Tile forwardJumpedTile = new Tile(4, 5);
        Piece blackPiece = new Piece(player2, forwardJumpedTile,-1);

        // Set the black piece to occupy the jumped tile
        forwardJumpedTile.setOccupant(blackPiece);
        assertTrue(forwardJumpedTile.isOccupied());
        
        // Set up the board with the jumped tile
        board[4][5] = forwardJumpedTile;
        assertTrue(kingPiece.canMoveTo(forwardTile, board));

        // King Piece jumping backward
        Tile backwardJumpedTile = new Tile(2,3);
        Piece backwardBlackPiece = new Piece(player2, backwardJumpedTile, -1);

        // Set the piece to the tile
        backwardJumpedTile.setOccupant(backwardBlackPiece);

        // Set up the board with jumped piece
        board[2][3] = backwardJumpedTile;
        assertTrue(kingPiece.canMoveTo(backwardTile, board));
    }
    @Test
    void testKingCanMoveToAnyDirection() {

        Tile forwardTile = new Tile(4,5);
        Tile backwardTile = new Tile(2,3);
        Piece kingPiece = new Piece(player1, whiteCurrentTile, 1);

        // King the white piece
        kingPiece.king();
        // test that white piece is now a king
        assertTrue(kingPiece.isKinged());

        // Test the king piece can move forward
        assertTrue(kingPiece.canMoveTo(forwardTile, board));

        // Test the king piece can move backward
        assertTrue(kingPiece.canMoveTo(backwardTile, board));
    }

    @Test
    void testGetPlayer() {

        Piece whitePiece = new Piece(player1, whiteCurrentTile, 1);
        Piece blackPiece = new Piece(player2, blackCurrentTile, -1);

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