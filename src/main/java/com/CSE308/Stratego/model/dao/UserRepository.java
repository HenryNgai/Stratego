package com.CSE308.Stratego.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query(value = "SELECT u.NumberofGamesPlayed FROM User u WHERE u.email = :email")
    public int Getgameid(@Param("email") String email);
//    @Modifying
//    @Query(value = "UPDATE User u SET u.NumberofGamesPlayed = :NumberofGamesPlayed WHERE u.email = :email", nativeQuery = true)
//    public void UpdateGameId(@Param("email") String email, @Param("NumberofGamesPlayed")int NumberofGamesPlayed);
}