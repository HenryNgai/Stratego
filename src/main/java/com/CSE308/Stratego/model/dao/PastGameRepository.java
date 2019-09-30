package com.CSE308.Stratego.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("pastGameRepository")
public interface PastGameRepository extends JpaRepository<PastGame, Integer>{
    @Query(value = "SELECT u FROM PastGame u WHERE u.username = :email")
    public List<PastGame> GetPastGames(@Param("email") String email);
    @Modifying
    @Transactional
    @Query(value ="insert into PastGame (username, gameId, won) values(:username, :gameId, :won)", nativeQuery = true)
    public void storePastGame(@Param("username") String username, @Param("gameId") int gameId, @Param("won") String won);
}
