package com.mjcode.model;

import java.awt.*;
import java.util.ArrayList;

public class Game {

    // Declare fields
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private ArrayList<Move> moves;
    private boolean gameStarted;
    private Tile[][] board;


    /**
     * No-args constructor initializes the fields
     */
    public Game() {
        player1 = null;
        player2 = null;
        currentPlayer = null;
        gameStarted = false;
        moves = new ArrayList<>();

        board = new Tile[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Tile(i, j);
            }
        }

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
     * The getTile method returns the Tile on the board at the specified row and column.
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
     * Player's list of Pieces. If the Move results in a Piece occupying its opponent Player's
     * first row*, the Piece is kinged. Finally, adds the specified Move to the history of Moves made thus far.
     * @param move The move
     */
    protected void executeMove(Move move) {
        // Declare and initialize the objects
        Tile fromTile = move.getFromTile();
        Tile toTile = move.getToTile();
        Tile tileJumped = jumpedTile(fromTile, toTile);
        Piece movedPiece = move.getMovedPiece();
        Piece capturedPiece;


        // Declare variable for direction of the player and the destination tile
        int direction = movedPiece.getPlayer().getDirection();
        int row = toTile.getRow();

        // Check if pawn has reached opponent's side to be Kinged
        if ((direction == 1 && row == 7) || (direction == -1 && row == 0)) {
            movedPiece.king();
        }

        // Update the board to reflect the move and set the moved piece to the destination tile
        fromTile.setOccupant(null);
        toTile.setOccupant(movedPiece);

        // Check if jumped tile is not null
        if (tileJumped != null) {
            // Get the occupant from the jump tile
            capturedPiece = tileJumped.getOccupant();

            // The result if the move is a captured
            if (capturedPiece != null) {
                // Update Pieces on the board to reflect the move and remove captured piece directly from the board
                tileJumped.setOccupant(null);

                // Remove the captured piece from the Player's list of pieces
                capturedPiece.getPlayer().removePiece(capturedPiece);
            }
        }

        // Add piece to move list
        moves.add(move);

        // Inform both players about the move being executed
        player1.executeMove(move);
        player2.executeMove(move);
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
        Tile destinationTile = move.getToTile();

        // Check if the Tiles specified is within border bound
        if (initialTile.getRow() < 0 || initialTile.getRow() >= boarderSize ||
                initialTile.getColumn() < 0 || initialTile.getColumn() >= boarderSize ||
                destinationTile.getRow() < 0 || destinationTile.getRow() >= boarderSize ||
                destinationTile.getColumn() < 0 || destinationTile.getColumn() >= boarderSize) {
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

        // The game will loop until the end-game state has been reached
        while (!endGame()) {

            // Current player will be initialized in the startGame method
            // Create a Move object
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
        for (Tile[] tiles : board) {
            for (Tile tile : tiles) {
                // Don't set Tile to null, set occupant of Tile to null
                tile.setOccupant(null);
            }
        }

        // Add 12 pieces to player1 in the positive direction
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1) {
                    // Store new piece from Player.addPiece method and set as occupant of Tile at (i, j)
                    Piece whitePiece = player1.addPiece(getTile(i, j));
                    board[i][j].setOccupant(whitePiece);

                }
            }
        }

        // Add 12 pieces to player 2 in the negative direction
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1) {
                    // Repeat the implementation as the first player
                    Piece blackPiece = player2.addPiece(getTile(i, j));
                    board[i][j].setOccupant(blackPiece);
                }
            }
        }

        // Check if there are currently two non-null player
        if (player1 != null && player2 != null) {

            // Sets the first player as the current player
            currentPlayer = player1;

            // Initialize gameStarted to true
            gameStarted = true;

            // Initialize the game
            play();
        }
    }

    /**
     * The switchTurns method changes the players whose turn it is
     */
    protected void switchTurns() {

        // If currently player is player1, switch to player2
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    // Create a new method called captured piece for capturing a piece on a jumped tile
    /**
     * The jumpedTile method returns the jumped tile of a captured piece
     * @param fromTile Specified tile piece wants to move to
     * @return The jumped tile
     */
    public Tile jumpedTile(Tile fromTile, Tile toTile) {

        // Local variables store the jumped row, jumped column and jumpTile
        int jumpedRow;
        int jumpedCol;
        Tile jumpTile = null;

        // Calculate the row and column differences between the target tile and current tile
        int rowDiff = toTile.getRow() - fromTile.getRow();
        int colDiff = toTile.getColumn() - fromTile.getColumn();

        if (Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 2){

            // Calculate the position of the jumped tile
            jumpedRow = fromTile.getRow() + (rowDiff / 2);
            jumpedCol = fromTile.getColumn() + (colDiff / 2);

            // Get the jumped tile from board
            jumpTile = getTile(jumpedRow, jumpedCol);
        }
        // Return the jumped tile
        return jumpTile;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }
    public boolean isGameStarted() {
        return gameStarted;
    }

}
