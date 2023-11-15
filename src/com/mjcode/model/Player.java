package com.mjcode.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    // Declare fields
    private Game game;
    private Piece[] pieces;
    private Color color;
    private int direction;
    private ArrayList<Piece> playerList;

    /**
     * No-args constructor initializes the fields appropriately
     */
    public Player() {
        this.game = null;
        this.color = null;
        this.direction = 0;
        this.playerList = new ArrayList<>();
    }

    /**
     * The addPiece creates a new piece and adds it to the player's list
     * of pieces
     * @param initialPosition initial position
     * @return The created piece
     */
    public Piece addPiece(Tile initialPosition) {
        // Implement - LIST
        if (initialPosition.getOccupant() != null) {
            Piece newPiece = new Piece(initialPosition.getOccupant().getPlayer(), initialPosition, direction); // Create a new piece
            playerList.add(newPiece); // Add the piece to the player's list
            return newPiece;
        } else {
            return null;
        }
    }

    /**
     * The executeMove method reflects the move specified
     * @param move The move to be executed
     */
    public abstract void executeMove(Move move);

    /**
     * Starts the game of checkers
     */
    public abstract Game getGame();

    /**
     * The setGame method sets the game of checkers
     * @param game The game field
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * The getPiece method returns all the player's remaining pieces
     * @return An array of pieces
     */
    public Piece[] getPieces() {
        // Implement - ARRAY
        // Convert arrayList to an array
        pieces = new Piece[playerList.size()];
        pieces = playerList.toArray(pieces);
        return pieces;
    }

    /**
     * The getNumPieces method gets the number of pieces the player currently has remaining
     * @return The number of pieces this player has remaining
     */
    public int getNumPieces() {
        // Implement - INTEGER
        return 0;
    }

    /**
     * The removePiece method removes the provided piece from the player's list of pieces
     * @param remove The provided piece from the player's list
     */
    public void removePiece(Piece remove) {
        // Implement - LIST
        playerList.remove(remove);
    }

    /**
     * The setColor method sets the color player's color to the color specified
     * @param color The value in the color field
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * The setDirection method sets the direction in  which the Player's un-kinged piece can move legally
     * @param direction The value in the direction field
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * The chooseMove will be implemented in the subclass
     * @param move That represents the chosen move
     * @return A move object
     */
    public abstract Move chooseMove(Move move);

    /**
     * The king method kings the supplied piece
     * @param piece The piece to be set as king
     */
    public void king(Piece piece) {
        piece.king();
    }

}
