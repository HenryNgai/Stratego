package com.CSE308.Stratego.model;


public class BoardPiece {

    private int posX;
    private int posY;
    private int strength;
    private String name;
    private boolean isUserPiece;
    private BoardPiece killedBy;

    public BoardPiece(String name, int str){
        this.name = name;
        strength = str;
    }


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPoxY(int posY) {
        this.posY = posY;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUserPiece() {
        return isUserPiece;
    }

    public void setUserPiece(boolean userPiece) {
        isUserPiece = userPiece;
    }

    public BoardPiece getKilledBy() {
        return killedBy;
    }

    public void setKilledBy(BoardPiece killedBy) {
        this.killedBy = killedBy;
    }
}
