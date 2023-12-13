package com.mjcode.model;

import java.awt.*;
import java.util.ArrayList;

public class Game {

    // Declare fields
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private ArrayList<Move> moves;
    private boolean gameStart;
    private Tile[][] board;


    /**
     * No-args constructor initializes the fields
     */
    public Game() {
        player1 = null;
        player2 = null;
        currentPlayer = null;
        gameStart = false;
        moves = new ArrayList<>();
        board = new Tile[8][8];
    }

    /**
     *  If the game has no players, sets the supplied Player to be Player One.
     *  If the game currently has one player, sets the supplied Player to be Player Two,
     *  assigns colors and directions to each Player as appropriate, and starts the game.
     *  If the game currently has two non-null players, does nothing.
     * @param newPlayer To add in the game
     */
    public void addPlayer(Player newPlayer) {
        // If game has no player
        if (player1 == null && player2 == null) {
            player1 = newPlayer;
            player1.setColor(Color.WHITE);
            player1.setDirection(1);
        }

        // If game currently has one player
         else if (player1 != null && player2 == null) {
            player2 = newPlayer;
            player2.setColor(Color.BLACK);
            player2.setDirection(-1);
            gameStart = true;
        }
    }

    /**
     * The endGame method returns true if either Player has no more Pieces
     * @return True if player has no more pieces
     */
    protected boolean endGame() {
        return player1.getPieces().isEmpty() || player2.getPieces().isEmpty();
    }

    /**
     * The getTile method returns the Tile on the board at the specified row and column./font>
     * @param row On the board
     * @param column on the board
     * @return The tile
     */
    public Tile getTile(int row, int column) {
        return board[row][column];
    }

    /**
     * The executeMove method updates the Pieces on the Board to reflect the Move specified, and informs
     * both Players that the Move has been executed via their respective executeMove methods. If the Move
     * results in a capture, removes the captured Piece from the board and from the appropriate
     * Player's list of Pieces. If the Move results in a Piece occupying it's opponent Player's
     * first row*, the Piece is kinged. Finally, adds the specified Move to the history of Moves made thus far.
     * @param move
     */
    protected void executeMove(Move move) {
        // Declare and initialize the objects
        Tile fromTile = move.getFromTile();
        Tile toTile = move.getToTile();
        Piece movedPiece = move.getMovedPiece();
        Piece capturedPiece = move.getCapturedPiece();

        // Declare variable for direction of the player and the destination tile
        int direction = movedPiece.getPlayer().getDirection();
        int row = toTile.getRow();

        // Update Pieces on the board to reflect the move
        fromTile.setOccupant(null);
        toTile.setOccupant(movedPiece);

        // The result if the move is a captured
            // Remove the captured Piece from the board
            // Remove the piece from the board


        // Check if pawn has reached opponent's side to be Kinged
        if ((direction == 1 && row == 7) || (direction == -1 && row == 0)) {
            movedPiece.king();
        }

        // Add piece to move list

        // Inform both players about the move being executed
    }

    /**
     * Returns true if the move specified is legal, false otherwise. Should check to ensure
     * a Piece is present at the initial Tile specified in the move, that the Piece belongs
     * to the Player whose turn it is, that the Piece can legally move in the manner specified,
     * and that both Tiles specified are in the confines of the board.
     * @param move Specified
     * @return true if move specified is legal
     */
    protected boolean isLegalMove(Move move) {

        // Initialize the boarder size
        int boarderSize = 8;

        // Initialize the starting and ending tiles
        Tile initialTile = move.getFromTile();
        Tile destinationTile = move.getFromTile();

        // Check if the Tiles specified is within border bound
        if (initialTile.getRow() < 0 || initialTile.getRow() >= boarderSize ||
                initialTile.getColumn() < 0 && initialTile.getColumn() >= boarderSize ||
                destinationTile.getRow() < 0 && destinationTile.getRow() >= boarderSize ||
                destinationTile.getColumn() < 0 && destinationTile.getColumn() >= boarderSize) {
            return false;
        }

        // Check if there is a piece on the initial tile
        if (!initialTile.isOccupied()) {
            return false;
        }

        // Retrieve the piece that is on the initial tile
        Piece movedPiece = move.getMovedPiece();

        // Check if piece belongs to the player whose turn it is
        if (movedPiece.getPlayer() != currentPlayer) {
            return false;
        }

        // Check if the piece can legally move to the destination tile
        return movedPiece.canMoveTo(destinationTile, board);

    }

    /**
     * While an end-game state is not reached, asks the Player whose turn it is for a move.
     * If that move is not a legal move, keeps asking until a legal move is given. Once a
     * valid move is supplied, the move is executed, and the turn is switched, giving the
     * other Player a chance to move.
     */
    protected void play() {

        // Start the game with player1
        currentPlayer = player1;

        // The game will loop until the end-game state has been reached
        while (!endGame()) {

            // Get current player should already be set to player1 in startGame method
            Move move = null;

            // Ask the current player for a move until a legal move is given
            boolean isLegalMove = false;
            while (!isLegalMove) {

                // Ask player to choose a move
                move = currentPlayer.chooseMove(null);

                // Check if move is legal
                isLegalMove = isLegalMove(move);
            }

            // Execute legal move
            executeMove(move);

            // Switch turn to other player
            switchTurns();
        }

    }

    /**
     * The startGame method Resets the board, removing any Pieces that are present from the board and
     * either Player, adds 12 pieces to each Player in the correct configuration, sets the first Player added
     * as the Player whose turn it is, and then initializes game play with the play method.
     */
    protected void startGame() {

        // Reset the board by removing any existing pieces
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = null;
            }
        }

        // Add 12 pieces to player1
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1) {
                    player1.addPiece(getTile(i, j));
                }
            }
        }

        // Add 12 pieces to player 2
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1) {
                    player2.addPiece(getTile(i, j));
                }
            }
        }

        // Check if there are currently two non-null player
        if (player1 != null && player2 != null) {

            // Sets the first player as the current player
            currentPlayer = player1;

            // Initialize the game
            play();
        }

    }

    /**
     * The switchTurns method changes the players whose turn it is
     */
    protected void switchTurns() {

        // If currently player is player1, switch to player2
        if (currentPlayer.equals(player1)) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

}
