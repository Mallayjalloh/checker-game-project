package com.mjcode.model;

import com.mjcode.logic.Move;
import java.util.ArrayList;

public class Game {

    // Declare fields
    private Player player1;
    private Player player2;
    private ArrayList<Move> move;
    private Tile[][] board;

    /**
     * No-args constructor initializes the fields
     */
    public Game() {
        this.player1 = null;
        this.player2 = null;
    }
    public void addPlayer(Player player) {

    }
    public boolean endGame() {
        return false;
    }
    public Tile[][] getTile(int row, int column) {
        return board;
    }
    protected void executeMove(Move move) {

    }
    protected boolean isLegalMove(Move move) {
        return false;
    }
    protected void play() {

    }
    protected void startGame() {

    }
    protected void switchTurns() {

    }

}
