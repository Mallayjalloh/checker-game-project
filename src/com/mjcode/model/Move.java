package com.mjcode.model;

public final class Move {

    // Declare fields
    final Tile fromTile;
    final Tile toTile;
    final Piece movedPiece;
    final Piece capturedPiece;

    public Move(Tile fromTile, Tile toTile) {
        this.fromTile = fromTile;
        this.toTile = toTile;
        this.movedPiece = fromTile.getOccupant();
        this.capturedPiece = null;
    }

    public Tile getFromTile() {
        return this.fromTile;
    }

    public Tile getToTile() {
        return this.toTile;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public Piece getCapturedPiece() {
        return this.capturedPiece;
    }

}
