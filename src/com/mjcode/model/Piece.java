package com.mjcode.model;

public class Piece {

    // Declare fields
    private Player player;
    private Tile tile;
    private int direction;
    private boolean isKing;

    /**
     * Constructor accepts three arguments and initializes the fields
     * @param player The player field
     * @param tile The tile field
     * @param direction The direction field
     */
    public Piece(Player player, Tile tile, int direction) {
        this.player = player;
        this.tile = tile;
        this.direction = direction;
        isKing = false;
    }

    /**
     * The canCapture method checks if a piece belongs to a player or the opponent
     * @param other The opponents piece
     * @return true if the supplied piece belongs to a player's piece, else false
     */
    public boolean canCapture(Piece other) {
        return false;
    }

    /**
     * The canMoveTo method determines if a pieces can legally move to a specified tile
     * @param tile The specified tile
     * @return True if piece can legally move to that tile, else false
     */
    public boolean canMoveTo(Tile tile) {
        return false;
    }

    /**
     * The getPlayer gets the player
     * @return The player that the piece belongs too
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * The isKing method determines if a piece is a king
     * @return true if piece has been Kinged, else false
     */
    public boolean isKing() {
        return false;
    }

    /**
     * Sets the piece to be kinged so that it can move in all directions
     */
    public void king() {

    }


}
