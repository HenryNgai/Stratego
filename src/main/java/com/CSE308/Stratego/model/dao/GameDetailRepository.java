package com.CSE308.Stratego.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("gameDetailRepository")
public interface GameDetailRepository extends JpaRepository<GameDetail, Integer>{
    @Query(value = "SELECT u FROM GameDetail u WHERE u.gameId = :gameId AND u.team = :email ")
    public List<GameDetail> getAllGameDetail(@Param("email") String email, @Param("gameId") int gameId);
}
