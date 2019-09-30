package com.CSE308.Stratego.model.dao;


import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor

public class PastGameId  implements Serializable {

    private String username;
    private int gameId;


    public PastGameId(String username, int gameId) {
        this.username = username;
        this.gameId = gameId;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}