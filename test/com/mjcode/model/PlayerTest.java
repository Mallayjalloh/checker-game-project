package com.mjcode.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private ArrayList<Piece> pieceList;
    private Player player;
    private Tile initialPosition;

    @BeforeEach
    void setUp() {
        pieceList = new ArrayList<>();
        player = new Player() {
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

        initialPosition = new Tile(3, 4);
    }

    @Test
    void testAddPiece() {
        // Add a piece to the player's list of pieces
        Piece whitePiece = player.addPiece(initialPosition);

        // Retrieve the player's pieces
        pieceList = player.getPieces();

        // Verify if the added piece exist in the player's piece list
        assertTrue(pieceList.contains(whitePiece));
    }

    @Test
    void TestGetPieces() {
        // Create a 2nd tile for the 2nd white piece
        Tile secondPosition = new Tile(3, 2);

        // Create the first piece
        Piece whitePiece1 = player.addPiece(initialPosition);
        // Create the second piece
        Piece whitePiece2 = player.addPiece(secondPosition);

        // Retrieve the players pieces
        pieceList = player.getPieces();

        // Verify if there are two pieces in the player's list
        assertEquals(2, pieceList.size());
    }
    @Test
    void TestGetNumPieces() {
        // Create a 2nd tile for the 2nd white piece
        Tile secondPosition = new Tile(3, 2);

        // Create the first piece
        Piece whitePiece1 = player.addPiece(initialPosition);
        // Create the second piece
        Piece whitePiece2 = player.addPiece(secondPosition);

        // Retrieve the number of pieces in the list
        int numOfPieces = player.getNumPieces();

        // Verify if the number matches with the player's list
        assertEquals(2, numOfPieces);
    }

    @Test
    void TestRemovePiece() {
        // Add a piece to the player's list of pieces
        Piece whitePiece = player.addPiece(initialPosition);
        // Retrieve the player's pieces
        pieceList = player.getPieces();
        // Check if the added piece exist in the player's list
        assertTrue(pieceList.contains(whitePiece));

        // Remove the piece from the player's list
        player.removePiece(whitePiece);
        // Retrieve the player's pieces
        pieceList = player.getPieces();
        // Verify if the piece has been removed from the player's list
        assertFalse(pieceList.contains(whitePiece));
    }

    @Test
    void TestSetColor() {
        // Generate a color
        Color white = Color.WHITE;
        Color black = Color.BLACK;

        // Set the color
        player.setColor(white);

        // Verify if the color match
        assertEquals(white, player.getColor());

        // Check if the color do not match
        assertNotEquals(black, player.getColor());
    }

    @Test
    void TestSetDirection() {
        // Generate the main players direction
        int direction = 1;
        int oppositeDirection = -1;

        // Set the direction
        player.setDirection(direction);

        // Verify if the direction match
        assertEquals(direction, player.getDirection());

        // Check if the direction do not match
        assertNotEquals(oppositeDirection, player.getDirection());
    }

    @Test
    void TestKing() {
        // Create a 2nd tile for the 2nd white piece
        Tile secondPosition = new Tile(3, 2);

        // Create a piece
        Piece kingPiece = new Piece(player, initialPosition, 1);
        Piece whitePiece = new Piece(player, secondPosition, 1);

        // King the piece
        player.king(kingPiece);

        // Verify if the piece is a king
        assertTrue(kingPiece.isKinged());

        // Check if the white piece is not a king
        assertFalse(whitePiece.isKinged());
    }
}