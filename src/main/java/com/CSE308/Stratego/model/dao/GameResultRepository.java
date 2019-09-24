package com.CSE308.Stratego.model.dao;

import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface GameResultRepository extends CrudRepository<GameResult, Integer> {}
