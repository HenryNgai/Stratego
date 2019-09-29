package com.CSE308.Stratego.model.dao;
import lombok.Data;
import javax.persistence.*;
@Data
@Entity
@Table(name = "GameDetail")
public class GameDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int unikey;
    private int gameId;
    private String piece;
    private String whoKilledPiece;
    private String team;

    public int getUnikey() {
        return unikey;
    }

    public void setUnikey(int unikey) {
        this.unikey = unikey;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getWhoKilledPiece() {
        return whoKilledPiece;
    }

    public void setWhoKilledPiece(String whoKilledPiece) {
        this.whoKilledPiece = whoKilledPiece;
    }

}



