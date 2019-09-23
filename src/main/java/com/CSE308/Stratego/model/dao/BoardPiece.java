package com.CSE308.Stratego.model.dao;

import javax.persistence.*;


@Entity
public class BoardPiece {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String name;
    private Integer strength;
    private Integer xPos;
    private Integer yPos;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getxPos() {
        return xPos;
    }

    public void setxPos(Integer xPos) {
        this.xPos = xPos;
    }

    public Integer getyPos() {
        return yPos;
    }

    public void setyPos(Integer yPos) {
        this.yPos = yPos;
    }





}
