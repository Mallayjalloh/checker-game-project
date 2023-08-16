package com.mjcode.model;

public class Tile {

    // Declare fields
    private int column;
    private int row;
    private Piece piece;

    /**
     * Constructor accepts two arguments and initializes fields
     * @param column The number of given columns
     * @param row The number of given rows
     */
    public Tile(int column, int row) {
        this.column = column;
        this.row = row;
        this.piece = null;
    }

    /**
     * The setOccupant method sets the occupant of this tile
     * @param newOccupant The piece that's specified
     */
    public void setOccupant(Piece newOccupant) {
        this.piece = newOccupant;
    }

    /**
     * The getOccupant method determines if a tile has an occupants
     * @return The piece that is occupying this tile or null if tile has no occupant
     */
    public Piece getOccupant() {
        return this.piece;
    }

    /**
     * The isOccupied method determines if a tile has a piece
     * @return True if tile has a piece, else return false
     */
    public boolean isOccupied() {
        return false;
    }
}
