package com.mjcode.model;

public class Piece {

    // Declare fields
    private Player player;
    private Tile tile;
    private int direction;
    private boolean isKing;

    // Test fields
    private int row;
    private int col;

    /**
     * Constructor accepts three arguments and initializes the fields
     * @param player The player field
     * @param tile The tile field
     * @param direction The direction field
     */
    public Piece(Player player, Tile tile, int direction, int row, int col) {
        this.player = player;
        this.tile = tile;
        this.direction = direction;
        isKing = false;

        // Test fields
        this.row = row;
        this.col = col;
    }

    /**
     * The canCapture method checks if a piece belongs to a player or the opponent
     * @param other The opponents piece
     * @return true if the supplied piece belongs to a player's piece, else false
     */
    public boolean canCapture(Piece other) {
        return other.getPlayer() != this.player;
    }
    /*
    /**
     * The canMoveTo method determines if a pieces can legally move to a specified tile
     * @param tile The specified tile
     * @return True if piece can legally move to that tile, else false
     */
    /*public boolean canMoveTo(Tile tile) {
        // Check if tile is occupied by the opponent's piece
        if (tile.isOccupied()) {
            return false;
        }
        // If not a King
        if (!isKinged()) {
            // If not a King
            return true;
        } else {
            return false;

        }
    }*/
    // Test method
    public boolean canMoveTo(int newRow, int newCol) {
        // Check if tile is occupied by the opponent's piece
        if (tile.isOccupied()) {
            return false;
        }

        // Calculate the direction of the piece based on the opponent's side
        this.direction = (newRow > row) ? 1 : -1;

        /*  If the piece is not Kinged, it can legally move one space diagonally
            towards the opponent's side
            */
        if (!isKing) {
            // Check if the move is one space diagonally in the specified direction
            if (Math.abs(newRow - this.row) == 1 && newCol - this.col == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            // Piece is a King that can move diagonally in any direction
            if (Math.abs(newRow - this.row) == 1 && Math.abs(newCol - this.col) == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * The getPlayer gets the player
     * @return The player that the piece belongs too
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * The isKinged method determines if a piece is a king
     * @return true if piece has been Kinged, else false
     */
    public boolean isKinged() {
        return this.isKing;
    }

    /**
     * Sets the piece to be kinged so that it can move in all directions
     */
    public void king() {
        this.isKing = true;
    }

}
