package com.CSE308.Stratego.model.dao;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor

@Entity
@Table(name = "pastGame")
@IdClass(PastGameId.class)
public class PastGame {
    @Id
    @Column(name = "username")
    private String username;
    @Id
    @Column(name = "gameId")
    private int gameId;

    @Column(name = "won")
    private boolean won;

}

