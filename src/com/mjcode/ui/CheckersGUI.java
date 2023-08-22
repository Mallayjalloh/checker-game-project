package com.mjcode.ui;

import com.mjcode.model.Piece;

import java.awt.*;

public class CheckersGUI {

    // Declare fields
    private int tileSize;

    /**
     * The addPiece method creates a PieceComponent
     * @param color Of a new piece
     * @param piece Location on the board
     */
    public void addPiece(Color color, Piece piece) {

    }

    /**
     * The movePiece method moves the PieceComponent on the board from on space to another.
     * If is two spaces away, it will remove the PieceComponent on the intermediary space.
     * If not Piece is present, it does nothing
     * @param fromRow From the row on the board
     * @param fromColumn From the column on the board
     * @param toRow To the row on the board
     * @param toColumn To the column on the board
     */
    public void movePiece(int fromRow, int fromColumn, int toRow, int toColumn) {

    }

    /**
     * The getTileSize method gets the tile size
     * @return The size of the tile on the CheckersGUI in pixel
     */
    public int getTileSize() {
        return tileSize;
    }
}
