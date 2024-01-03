package com.mjcode.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    private Tile fromTile;
    private Tile toTile;
    private Player player;


    @BeforeEach
    void setUp() {
        fromTile = new Tile(3, 4);
        toTile = new Tile(4, 5);
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
    }


    @Test
    void TestGetFromTile() {
        Move move = new Move(fromTile, toTile);
        // Retrieve "fromTile" from the move object

        Tile retrievedFromTile = move.getFromTile();
        // Check if retrieved tile is the same
        assertEquals(fromTile, retrievedFromTile);

        // Check if retrieved tile is not the same
        assertNotEquals(toTile, retrievedFromTile);
    }

    @Test
    void TestGetToTile() {
        Move move = new Move(fromTile, toTile);
        // Retrieve "toTile" from the move object
        Tile retrievedToTile = move.getToTile();

        // Check if retrieved tile matches
        assertEquals(toTile, retrievedToTile);

        // Check if retrieved tile do not match
        assertNotEquals(fromTile, retrievedToTile);
    }

    @Test
    void TestGetMovedPiece() {
       Piece whitePiece = new Piece(player, fromTile, 1);
       // Set the piece on the tile
       fromTile.setOccupant(whitePiece);

        // Test if the tile is occupied
       assertTrue(fromTile.isOccupied());

       // Create a move object
       Move move = new Move(fromTile, toTile);

       // Get the piece from the move
       Piece retrievedPiece = move.getMovedPiece();

       // Check if retrieved piece matches white piece
       assertEquals(whitePiece, retrievedPiece);
    }
}