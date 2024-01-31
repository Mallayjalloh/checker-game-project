package com.mjcode.ui;

import com.mjcode.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CheckersGUITest {
    PieceComponent[][] pieceComponents;
    CheckersGUI checkersGUI;
    Color white;
    Player player;
    int tileSize;
    int diameter;
    Tile fromTile;
    int positiveDirection;
    Piece piece;

    @BeforeEach
    void setUp() {
        pieceComponents = new PieceComponent[8][8];
        tileSize = 60;
        diameter = 50;
        white = Color.WHITE;
        checkersGUI = new CheckersGUI(tileSize, diameter) {
            @Override
            public PieceComponent getPieceComponentAt(int row, int column) {
                return pieceComponents[row][column];
            }
        };
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
        fromTile = new Tile(2,3);
        positiveDirection = 1;
        piece = new Piece(player, fromTile, positiveDirection);
    }

    @Test
    void addPiece() {
        // Get the row and column of the piece
        int row = piece.getCurrentTile().getRow();
        int column = piece.getCurrentTile().getColumn();

        // Set the color of the player
        player.setColor(white);
        Color color = piece.getPlayer().getColor();

        // Call the addPiece method that accepts the color and piece
        checkersGUI.addPiece(color, piece);

        // Create an instance for testing
        PieceComponent expectedPieceComponent = new PieceComponent(color, diameter, piece.isKinged());
        // Assign expectedPieceComponent to the specified location on the board
        pieceComponents[row][column] = expectedPieceComponent;

        // Get the addedPieceComponent at the specified location
        PieceComponent addedPieceComponent = checkersGUI.getPieceComponentAt(row, column);

        // Ensure the addedPieceComponent is not null
        assertNotNull(addedPieceComponent);
        assertEquals(color, addedPieceComponent.color);
        assertEquals(50, addedPieceComponent.diameter);
        assertEquals(piece.isKinged(), addedPieceComponent.kinged);
    }

    @Test
    void movePiece() {
        // Get the row and column
        // piece will move from (2, 3) to (3, 4)
        int fromRow = fromTile.getRow(); // 2
        int fromColumn = fromTile.getColumn(); // 3
        int toRow = 3;
        int toColumn = 4;

        // Set the color of the player
        player.setColor(white);
        Color color = player.getColor();

        // Create a movingPiece for testing
        PieceComponent movingPiece = new PieceComponent(color, diameter, piece.isKinged());
        // Assign movingPiece to the specified location on the board
        pieceComponents[fromRow][fromColumn] = movingPiece;

        // Ensure movingPiece is not null
        assertNotNull(movingPiece);
        // Ensure movingPiece and piece on the board are equal
        assertEquals(checkersGUI.getPieceComponentAt(fromRow, fromColumn), movingPiece);

        // Call the method
        checkersGUI.movePiece(fromRow, fromColumn, toRow, toColumn);

        assertNull(checkersGUI.getPieceComponentAt(fromRow, fromColumn));
        //assertNotNull(checkersGUI.getPieceComponentAt(toRow, toColumn));
    }

    @Test
    void getTileSize() {
        CheckersGUI checkers = new CheckersGUI();

        // Create a variable to store the actual size of the tile
        int actualSize = checkers.getTileSize();

        // Ensure the tileSize matches the actualSize
        assertEquals(tileSize, actualSize);
    }
    @Test
    void getTileSize_ReturnNotEqual() {
        CheckersGUI checkers = new CheckersGUI();

        // Create a tile size for testing
        int expectedTileSize = 80;

        // Create variable to store the actual size of the tile
        int actualSize = checkers.getTileSize();

        // Ensure the expectedTileSize is not equal to actualSize
        assertNotEquals(expectedTileSize, actualSize);
    }
    @Test
    void getPieceComponentAt_ReturnsCorrectPieceComponent(){
        // Declare row and column
        int row = piece.getCurrentTile().getRow();
        int column = piece.getCurrentTile().getColumn();

        // Create an instance of testing
        PieceComponent expectedPieceComponent = new PieceComponent(white, diameter, piece.isKinged());
        // Assign expectedPieceComponent on the specified location on the board
        pieceComponents[row][column] = expectedPieceComponent;

        // Create an instance of the actualPieceComponent
        PieceComponent actualPieceComponent = checkersGUI.getPieceComponentAt(row, column);

        assertNotNull(expectedPieceComponent);
        assertNotNull(actualPieceComponent);
        assertEquals(expectedPieceComponent, actualPieceComponent);
    }

    @Test
    void getPieceComponentAt_ReturnsNullForEmptyLocation(){
        // Declare row and column
        int row = piece.getCurrentTile().getRow();
        int column = piece.getCurrentTile().getColumn();

        // Create an instance for testing
        PieceComponent expectedPieceComponent = new PieceComponent(white, diameter, piece.isKinged());
        // The instance will NOT be placed on the specified location on the board

        // Create an instance of the actualPieceComponent
        PieceComponent actualPieceComponent = checkersGUI.getPieceComponentAt(row, column);

        // Ensure the both instances of pieceComponent are not equal
        assertNotEquals(expectedPieceComponent, actualPieceComponent);
        // Ensure actualPieceComponent returns null for an empty location
        assertNull(actualPieceComponent);
    }
}