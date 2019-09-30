package com.CSE308.Stratego.model;

import com.CSE308.Stratego.model.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserService {


    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private PastGameRepository pastGameRepository;
    @Autowired private GameDetailRepository gameDetailRepository;


    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        user.setNumberofGamesPlayed(0);
        userRepository.save(user);
    }

    public int getGameID(String email) {
        return userRepository.Getgameid(email);
    }

    public List<PastGame> getAllGameResults(String email) {
        return pastGameRepository.GetPastGames(email);
    }

    public List<GameDetail> getGameDetail(int gameId) {
        return gameDetailRepository.getAllGameDetail(gameId);
    }

//    public void writeGameToDatabase(Game game, boolean userWon){
//        if(userWon) {
//            pastGameRepository.storePastGame(game.getUser().getName(), game.getGameId(), game.getUser().getName());
//        }else{
//            pastGameRepository.storePastGame(game.getUser().getName(), game.getGameId(), game.getAi().getName());
//        }
//        for(BoardPiece p: game.getUserGraveyard()){
//            gameDetailRepository.storePastGame(game.getGameId(), p.getName(), p.getKilledBy().getName(), p.getSomeplayer().getName());
//        }
//        for(BoardPiece p: game.getAiGraveyard()){
//            gameDetailRepository.storePastGame(game.getGameId(), p.getName(), p.getKilledBy().getName(), p.getSomeplayer().getName());
//        }
//        userRepository.UpdateGameId(game.getUserName(), game.getGameId()+1);
//    }


}