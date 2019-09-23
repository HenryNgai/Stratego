package com.CSE308.Stratego.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.CSE308.Stratego.model.dao.BoardPiece;


public interface BoardPieceRepository  extends CrudRepository<BoardPiece, Integer>{
}
