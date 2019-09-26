package com.CSE308.Stratego.model;


import com.CSE308.Stratego.model.dao.Player;

public class BoardPiece {

    private String name;
    private int strength;
    private int xPos;
    private int yPos;
    private boolean moveMultiple;
    private boolean moveable;
    private Player someplayer;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Player getPlayer() { return someplayer; }

    public boolean isMoveMultiple() {
        return moveMultiple;
    }

    public void setMoveMultiple(boolean moveMultiple) {
        this.moveMultiple = moveMultiple;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public BoardPiece(String piecename, Player pieceplayer){

        name = piecename;
        someplayer = pieceplayer;
        xPos = -1;
        yPos = -1;

        if(name.equals("Marshall")){
            moveable = true;
            strength = 10;
            moveMultiple = false;
        }

        if(name.equals("General")){
            moveable = true;
            strength = 9;
            moveMultiple = false;
        }

        if(name.equals("Colonel")){
            moveable = true;
            strength = 8;
            moveMultiple = false;
        }

        if(name.equals("Major")){
            moveable = true;
            strength = 7;
            moveMultiple = false;
        }

        if(name.equals("Captain")){
            moveable = true;
            strength = 6;
            moveMultiple = false;
        }

        if(name.equals("Lieutenant")){
            moveable = true;
            strength = 5;
            moveMultiple = false;
        }

        if(name.equals("Sergeant")){
            moveable = true;
            strength = 4;
            moveMultiple = false;
        }

        if(name.equals("Miner")){
            moveable = true;
            strength = 3;
            moveMultiple = false;
        }

        if(name.equals("Scout")){
            moveable = true;
            strength = 2;
            moveMultiple = true;
        }

        if(name.equals("Spy")){
            moveable = true;
            strength = 1;
            moveMultiple = false;
        }

        if(name.equals("Bomb")){
            moveable = false;
            moveMultiple = false;
        }

        if(name.equals("Flag")){
            moveable = false;
            moveMultiple = false;
        }





    }








}
