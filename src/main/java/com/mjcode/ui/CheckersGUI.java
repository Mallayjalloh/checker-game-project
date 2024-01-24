package com.mjcode.ui;

import com.mjcode.model.Piece;

import javax.swing.*;
import java.awt.*;

public class CheckersGUI {

    // Declare fields
    private final int tileSize; // The size of each tile on the board
    private final int diameter; // Represent the diameter of a checkers piece
    private PieceComponent[][] pieceComponents; // Represent the game state using a 2D array of PieceComponent objects

    public CheckersGUI() {
        this.tileSize = 60;
        this.diameter = 50;
        pieceComponents = new PieceComponent[8][8];
    }

    /**
     * Creates a PieceComponent with the provided color of a new piece
     * and adds it to the location on the board specified by the Piece's Tile.
     * @param color Provided color of a new piece
     * @param piece Location on the board
     */
    public void addPiece(Color color, Piece piece) {
        // Create a Piece component
        PieceComponent pieceComponent = new PieceComponent(color, diameter, piece.isKinged());

        // Determine the position on the board based on the Piece's tile
        int row = piece.getCurrentTile().getRow();
        int column = piece.getCurrentTile().getColumn();

        // Calculate the x and y coordinates based on the row and column
        int x = column * tileSize;
        int y = row * tileSize;

        // Set the location of the PieceComponent on the board
        pieceComponent.setBounds(x, y, diameter, diameter);

        // Add the PieceComponent to the CheckersGUI
        pieceComponent.add(pieceComponent);

        // Ensure the GUI updates
        pieceComponent.revalidate();
        pieceComponent.repaint();
    }

    /**
     * Moves the PieceComponent (representing the Piece at (fromRow, fromCol)
     * on the board to (toRow, toCol) on the board. (Hint: remove the PieceComponent
     * from the old location and add it to the new location). If (fromRow, fromCol)
     * is two spaces away from (toRow, toCol), removes the PieceComponent on the
     * intermediary space. If no Piece is present at (fromRow, fromCol), does nothing.
     * @param fromRow The fromRow on the board
     * @param fromColumn The fromColumn on the board
     * @param toRow The toRow on the board
     * @param toColumn The toColumn on the board
     */
    public void movePiece(int fromRow, int fromColumn, int toRow, int toColumn) {
        // Get the PieceComponent at the specified location
        PieceComponent movingPiece = getPieceComponentAt(fromRow, fromColumn);

        // Calculate the difference between the toRow and the fromRow
        int rowDiff = toRow - fromRow;
        int colDiff = toColumn - fromColumn;

        // Check if there is a PieceComponent at the  specified location
        if (movingPiece != null) {
            // Move the PieceComponent to the new location and remove it from the old location
            pieceComponents[toRow][toColumn] = movingPiece;
            pieceComponents[fromRow][fromColumn] = null;

            // Calculate the x and y coordinates for the new location
            int x = toColumn * tileSize;
            int y = toRow * tileSize;

            // Set the location of the PieceComponent to the new location
            movingPiece.setBounds(x, y, movingPiece.getPreferredSize().width, movingPiece.getPreferredSize().height);

            // If the move is two spaces away, remove the PieceComponent at the intermediary space
            if (Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 2) {
                int jumpedRow = fromRow + (rowDiff / 2);
                int jumpedCol = fromColumn + (colDiff / 2);
                PieceComponent jumpedPiece = getPieceComponentAt(jumpedRow, jumpedCol);

                // Check if there is a PieceComponent at the intermediary space
                if (jumpedPiece != null) {
                    pieceComponents[jumpedRow][jumpedCol] = null;
                    //jumpedPiece.remove(jumpedPiece);
                }
            }

            // Ensure the GUI updates
            movingPiece.revalidate();
            movingPiece.repaint();

        }
    }

    /**
     * Gets the size (in pixels) of a tile on the CheckersGUI.
     * Since tiles should be square*, getTileSize can return a single integer.
     * @return The size of the tile on the CheckersGUI in pixel
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Gets the PieceComponent at the specified row and column on the board
     * @param row The row on the board
     * @param column The column on the board
     * @return The PieceComponent at the specified location,
     * or null if no PieceComponent is present.
     */
    private PieceComponent getPieceComponentAt(int row, int column) {
        return pieceComponents[row][column];
    }
}
