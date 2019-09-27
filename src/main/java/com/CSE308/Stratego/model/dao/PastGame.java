package com.CSE308.Stratego.model.dao;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "pastGame")
public class PastGame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String username;
    private int gameId;
    private boolean won;


}

