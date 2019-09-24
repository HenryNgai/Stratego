package com.CSE308.Stratego.model.dao;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "GameResult", schema = "Stratego")
public class GameResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Timestamp timestamp;
    private String WinnerName;
    private String PlayerPiecesLost;
    private String CPUPiecesLost;

    public void setTimestamp(Timestamp timestamp)
    {
        this.timestamp = timestamp;
    }
    public Timestamp getTimestamp()
    {
        return timestamp;
    }

    public String getCPUPiecesLost() {
        return CPUPiecesLost;
    }

    public void setCPUPiecesLost(String CPUPiecesLost) {
        this.CPUPiecesLost = CPUPiecesLost;
    }

    public String getPlayerPiecesLost() {
        return PlayerPiecesLost;
    }

    public void setPlayerPiecesLost(String playerPiecesLost) {
        PlayerPiecesLost = playerPiecesLost;
    }

    public String getWinnerName() {
        return WinnerName;
    }

    public void setWinnerName(String winnerName) {
        WinnerName = winnerName;
    }
}
