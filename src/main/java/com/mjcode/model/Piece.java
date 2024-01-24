package com.mjcode.model;

public class Piece {

    // Declare fields
    private Player player;
    private Tile currentTile;
    private int direction;
    private boolean isKing;

    /**
     * Constructor accepts three arguments and initializes the fields
     * @param player The player field
     * @param currentTile The tile field
     * @param direction The direction field
     */
    public Piece(Player player, Tile currentTile, int direction) {
        this.player = player;
        this.currentTile = currentTile;
        this.direction = direction;
        isKing = false;
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
     * @param targetTile The specified tile the piece wants to move to
     * @return True if piece can legally move to that tile, else false
     */
    public boolean canMoveTo(Tile targetTile,Tile[][] board) {

        // Local variables to store the squares that lies between the current tile and the target tile
        int jumpedRow;
        int jumpedCol;

        // Calculate the row and column differences between the target tile and current tile
        int rowDiff = targetTile.getRow() - this.currentTile.getRow();
        int colDiff = targetTile.getColumn() - this.currentTile.getColumn();

        // Check if tile is occupied by another piece
        if (targetTile.isOccupied()) {
            return false;
        }

        // If not a King
        if (!isKinged()) {

            // If not a King, check if the move is one space diagonally in the specified direction
            if (rowDiff == this.direction &&
                    Math.abs(colDiff) == 1) {
                return true;

            } else if (rowDiff == this.direction * 2 && Math.abs(colDiff) == 2) {

                // Check if there's a jump move and calculate the position of the jumped tile
                jumpedRow = this.currentTile.getRow() + (rowDiff / 2);
                jumpedCol = this.currentTile.getColumn() + (colDiff / 2);

                // Get the current jumped tile from the board
                Tile jumpTile = board[jumpedRow][jumpedCol];

                // Check if there is an opponent's piece on the jumped tile
                return jumpTile.isOccupied() && canCapture(jumpTile.getOccupant());
            }

            return false;

        } else {

            // Piece is a King that can move diagonally in any direction
            if (Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 1) {
                return true;
            } else if (Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 2){

                // Calculate the position of the jumped tile
                jumpedRow = this.currentTile.getRow() + (rowDiff / 2);
                jumpedCol = this.currentTile.getColumn() + (colDiff / 2);

                // Get the current jumped tile from board
                Tile jumpTile = board[jumpedRow][jumpedCol];

                // Check if there is an opponent's piece on the jumped tile
                return jumpTile.isOccupied() && canCapture(jumpTile.getOccupant());
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

    public Tile getCurrentTile() {
        return currentTile;
    }

}
