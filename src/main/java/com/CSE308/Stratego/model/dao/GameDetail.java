package com.CSE308.Stratego.model.dao;
import lombok.Data;
import javax.persistence.*;
@Data
@Entity
@Table(name = "GameDetail")
public class GameDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int gameId;
    private String piece;
    private String whoKilledPiece;
}



