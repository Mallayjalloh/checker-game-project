package com.mjcode.model;

import java.awt.*;
import java.util.ArrayList;

public abstract class Player {

    // Declare fields
    private Game game;
    private Color color;
    private int direction;
    private ArrayList<Piece> pieceList;

    /**
     * No-args constructor initializes the fields appropriately
     */
    public Player() {
        this.game = null;
        this.color = null;
        this.direction = 0;
        this.pieceList = new ArrayList<>();
    }

    /**
     * The addPiece creates a new piece and adds it to the player's list
     * of pieces
     * @param initialPosition Starting position
     * @return The created piece
     */
    public Piece addPiece(Tile initialPosition) {
        // Create a new piece
        Piece newPiece = new Piece(this, initialPosition, direction);
        // Add piece to the players list of pieces and return it
        pieceList.add(newPiece);
        return newPiece;
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
     * The getPiece method gets all the player's remaining pieces
     * @return An array of pieces
     */
    public ArrayList<Piece> getPiece() {
        return pieceList;
    }

    /**
     * The getNumPieces method gets the number of pieces the player currently has remaining
     * @return The number of pieces this player has remaining
     */
    public int getNumPieces() {
        return pieceList.size();
    }

    /**
     * The removePiece method removes the provided piece from the player's list of pieces
     * @param remove The provided piece from the player's list
     *
     * ?? Not sure if it returns something or if it should be a void method ??
     */
    public void removePiece(Piece remove) {
        pieceList.remove(remove);
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
