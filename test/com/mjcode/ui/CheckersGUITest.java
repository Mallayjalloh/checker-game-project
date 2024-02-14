package com.mjcode.ui;

import com.mjcode.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CheckersGUITest {

    CheckersGUI checkersGUI;
    Color white;
    Player player1;
    int tileSize;
    int diameter;
    Tile fromTile;
    int positiveDirection;
    Piece piece;


    @BeforeEach
    void setUp() {
        tileSize = 60;
        diameter = 50;
        white = Color.WHITE;

        checkersGUI = new CheckersGUI(tileSize, diameter);
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

        fromTile = new Tile(2,3);
        positiveDirection = 1;
        piece = new Piece(player1, fromTile, positiveDirection);
    }

    @Test
    void addPiece() {
        // Get the row and column of the piece
        int row = piece.getCurrentTile().getRow();
        int column = piece.getCurrentTile().getColumn();

        // Set the color of the player
        player1.setColor(white);
        Color color = piece.getPlayer().getColor();

        // Call the addPiece method that accepts the color and piece
        checkersGUI.addPiece(color, piece);

        // Create an instance for testing
        PieceComponent expectedPieceComponent = new PieceComponent(color, diameter, piece.isKinged());
        // Assign expectedPieceComponent to the specified location on the board
        checkersGUI.setPieceComponents(row, column, expectedPieceComponent);

        // Get the addedPieceComponent at the specified location
        PieceComponent addedPieceComponent = checkersGUI.getPieceComponentAt(row, column);

        // Ensure the addedPieceComponent is not null
        assertNotNull(addedPieceComponent);
        assertEquals(color, addedPieceComponent.color);
        assertEquals(50, addedPieceComponent.diameter);
        assertFalse(addedPieceComponent.kinged);
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
        player1.setColor(white);
        Color color = player1.getColor();

        // Create a movingPiece for testing
        PieceComponent movingPiece = new PieceComponent(color, diameter, piece.isKinged());
        // Assign movingPiece to the specified location on the board
        checkersGUI.setPieceComponents(fromRow, fromColumn, movingPiece);

        PieceComponent actualComponent = checkersGUI.getPieceComponentAt(fromRow, fromColumn);

        // Ensure movingPiece is not null
        assertNotNull(actualComponent);
        // Ensure movingPiece and piece on the board are equal
        assertEquals(actualComponent, movingPiece);

        // Call the method
        checkersGUI.movePiece(fromRow, fromColumn, toRow, toColumn);

        // Ensure that PieceComponent has moved to the new location
        assertNull(checkersGUI.getPieceComponentAt(fromRow, fromColumn));
        assertNotNull(checkersGUI.getPieceComponentAt(toRow, toColumn));

        // Get the X and Y location
        int yTile = toRow * checkersGUI.getTileSize();
        int xTile = toColumn * checkersGUI.getTileSize();
        PieceComponent tileSize = checkersGUI.getPieceComponentAt(toRow, toColumn);

        // Ensure that PieceComponent has moved to the correct location
        assertEquals(yTile, tileSize.getY());
        assertEquals(xTile, tileSize.getX());
    }
    @Test
    void movePiece_RemoveJumpedPiece() {
        // Get the row and column
        // piece will move from (2, 3) to (4, 5)
        int fromRow = fromTile.getRow(); // 2
        int fromColumn = fromTile.getColumn(); // 3
        int toRow = 4;
        int toColumn = 5;

        // Set the color of the player
        player1.setColor(white);
        Color color = player1.getColor();

        // Create a movingWhitePiece for testing
        PieceComponent movingWhitePiece = new PieceComponent(color, diameter, piece.isKinged());
        // Assign movingWhitePiece to the specified location on the board
        checkersGUI.setPieceComponents(fromRow, fromColumn, movingWhitePiece);

        // Create an actualComponent to test the movingWhitePiece
        PieceComponent actualComponent = checkersGUI.getPieceComponentAt(fromRow, fromColumn);

        // Ensure movingWhitePiece location is not null
        assertNotNull(actualComponent);
        // Ensure actualComponent and movingWhitePiece on the board are equal
        assertEquals(actualComponent, movingWhitePiece);

        // Create an opponents piece on the jumped tile. Opponent piece will set on (3, 4)
        PieceComponent movingBlackPiece = new PieceComponent(Color.BLACK, diameter, false);
        checkersGUI.setPieceComponents(3, 4 , movingBlackPiece);

        PieceComponent jumpedPiece = checkersGUI.getPieceComponentAt(3, 4);

        // Ensure location of movingBlackPiece is not null
        assertNotNull(jumpedPiece);
        assertEquals(jumpedPiece, movingBlackPiece);

        // Call the method
        checkersGUI.movePiece(fromRow, fromColumn, toRow, toColumn);

        // Ensure that PieceComponent has moved to the new location
        assertNull(checkersGUI.getPieceComponentAt(fromRow, fromColumn));
        assertNotNull(checkersGUI.getPieceComponentAt(toRow, toColumn));

        // Calculate row and column difference for jumped spaces
        int rowDiff = toRow - fromRow;
        int colDiff = toColumn - fromColumn;

        // Check removal of PieceComponent at the intermediary space if applicable
        if (Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 2) {
            int jumpedRow = fromRow + (rowDiff / 2);
            int jumpedCol = fromColumn + (colDiff / 2);
            jumpedPiece = checkersGUI.getPieceComponentAt(jumpedRow, jumpedCol);
            assertNull(jumpedPiece);
        }

        // Get the X and Y location
        int yTile = toRow * checkersGUI.getTileSize();
        int xTile = toColumn * checkersGUI.getTileSize();
        PieceComponent tileSize = checkersGUI.getPieceComponentAt(toRow, toColumn);

        // Ensure that PieceComponent has moved to the correct location
        assertEquals(yTile, tileSize.getY());
        assertEquals(xTile, tileSize.getX());
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
        checkersGUI.setPieceComponents(row, column, expectedPieceComponent);

        // Create an instance of the actualPieceComponent
        PieceComponent actualPieceComponent = checkersGUI.getPieceComponentAt(row, column);

        // Ensure the actualPieceComponent is not null and is equal to expectedPieceComponent
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