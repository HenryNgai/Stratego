package com.CSE308.Stratego.model;

import com.CSE308.Stratego.model.dao.BoardPieceDao;


public class BoardCell {
    private boolean occupied;

    private boolean selected;

    private BoardPieceDao currentPiece;

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public BoardPieceDao getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(BoardPieceDao currentPiece) {
        this.currentPiece = currentPiece;
    }
}
