package com.CSE308.Stratego.model;

public class GameBoard {

    private BoardPiece[][] board;

    public GameBoard(){
        board = new BoardPiece[10][10];
    }


    public BoardPiece[][] getBoard() {
        return board;
    }

    public void setBoard(BoardPiece[][] board) {
        this.board = board;
    }

    public boolean addPiece(BoardPiece piece, int x, int y){
        if(board[x][y] == null){
            board[x][y] = piece;
            piece.setxPos(x);
            piece.setyPos(y);
            return true;
        }
        return false;
    }

    public void removePiece(int x, int y){
        board[x][y] = null;
    }


}
