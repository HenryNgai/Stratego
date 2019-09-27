package com.CSE308.Stratego.model;


public class Player{
    private String name;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Player (String playername, String pcolor){
        name = playername;
        color = pcolor;
    }
}