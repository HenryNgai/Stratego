package com.CSE308.Stratego.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("gameDetailRepository")
public interface GameDetailRepository extends JpaRepository<GameDetail, Integer>{
    @Query(value = "SELECT u FROM GameDetail u WHERE u.gameId = :gameId")
    public List<GameDetail> getAllGameDetail(@Param("gameId") int gameId);

    @Modifying
    @Query(value ="insert into PastGame (gameId, piece, whokilledpiece, team) values(:gameId, :piece, :whokilledpiece, team)", nativeQuery = true)
    public void storePastGame(@Param("gameId") int gameId,
                              @Param("piece") String piece,
                              @Param("whokilledpiece") String whokilledpiece,
                              @Param("team") String team);

}
