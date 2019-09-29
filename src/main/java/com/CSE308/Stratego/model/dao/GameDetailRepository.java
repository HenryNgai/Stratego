package com.CSE308.Stratego.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("gameDetailRepository")
public interface GameDetailRepository extends JpaRepository<GameDetail, Integer>{
}
