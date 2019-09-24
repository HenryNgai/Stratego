package com.CSE308.Stratego.model;

import com.CSE308.Stratego.model.dao.GameResult;
import com.CSE308.Stratego.model.dao.GameResultRepository;
import com.CSE308.Stratego.model.dao.Login;
import com.CSE308.Stratego.model.dao.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Services {

    @Autowired LoginRepository loginRepository;

    @Autowired GameResultRepository gameResultRepository;

    public boolean validate_login_function(String username, String password){
        if (loginRepository.validate_login(username,password).size() > 0){
            return true;
        }
        return false;
    }

    public GameResult createGameResult(GameResult result) {
        return gameResultRepository.save(result);
    }

    public List<GameResult> getAllGameResults() {
        return (List<GameResult>) gameResultRepository.findAll();
    }

}
