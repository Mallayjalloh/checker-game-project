package com.mjcode.model;

public class Piece {

    // Declare fields
    private Player player;
    private Tile tile;
    private int direction;
    private boolean isKing;

    // Test fields
    private int currentRow;
    private int currentCol;

    /**
     * Constructor accepts three arguments and initializes the fields
     * @param player The player field
     * @param tile The tile field
     * @param direction The direction field
     */
    public Piece(Player player, Tile tile, int direction, int currentRow, int currentCol) {
        this.player = player;
        this.tile = tile;
        this.direction = direction;
        isKing = false;

        // Test fields
        this.currentRow = currentRow;
        this.currentCol = currentCol;
    }

    /**
     * The canCapture method checks if a piece belongs to a player or the opponent
     * @param other The opponents piece
     * @return true if the supplied piece belongs to a player's piece, else false
     */
    public boolean canCapture(Piece other) {
        return other.getPlayer() != this.player;
    }

    /**
     * The canMoveTo method determines if a pieces can legally move to a specified tile
     * @param tile The specified tile the piece wants to move to
     * @return True if piece can legally move to that tile, else false
     */
    public boolean canMoveTo(Tile tile) {
        // Local variables to store the squares that lies between the current tile and the target tile
        int jumpedRow;
        int jumpedCol;

        // Calculate the row and column differences between the current tile and target tile
        int rowDiff = tile.getRow() - this.currentRow;
        int colDiff = tile.getColumn() - this.currentCol;

        // Check if tile is occupied by another piece
        if (tile.isOccupied()) {
            return false;
        }

        // If not a King
        if (!isKinged()) {
            // If not a King, check if the move is one space diagonally in the specified direction
            if (rowDiff == this.direction &&
                    Math.abs(colDiff) == this.direction) {
                return true;

            } else if (rowDiff == 2 && Math.abs(colDiff) == 2){
                // Calculate the position of the jumped tile
                jumpedRow = (this.currentRow + rowDiff) / 2;
                jumpedCol = (this.currentCol + colDiff) / 2;

                // Check if there is an opponent's piece on the jumped tile
                if (tile.isOccupied() && tile.getOccupant().getPlayer() != this.player) {
                    return true;
                }
            }
            return false;

        } else {
            // Piece is a King that can move diagonally in any direction
            if (Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 1) {
                return true;
            } else if (Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 2){
                // Calculate the position of the jumped tile
                jumpedRow = (this.currentRow + rowDiff) / 2;
                jumpedCol = (this.currentCol + colDiff) / 2;

                // Check if there is an opponent's piece on the jumped tile
                if (tile.isOccupied() && tile.getOccupant().getPlayer() != this.player) {
                    return true;
                }
            }

            return false;
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
