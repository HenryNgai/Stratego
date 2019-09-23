package com.CSE308.Stratego.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepository extends CrudRepository<Login, Integer> {
    @Query(value = "SELECT u FROM Login u WHERE u.username = :username AND u.password = :password")
    List<Login>validate_login(@Param("username")String username, @Param("password")String password);
}
