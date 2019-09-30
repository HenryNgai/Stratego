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

    public void writeGameToDatabase(Game game, boolean userWon){
        if(userWon) {
            PastGame p = new PastGame();
            p.setGameId(game.getGameId());
            p.setUsername(game.getUser().getName());
            p.setWon(true);
            pastGameRepository.save(p);
        }else{
            PastGame p = new PastGame();
            p.setGameId(game.getGameId());
            p.setUsername(game.getUser().getName());
            p.setWon(false);
            pastGameRepository.save(p);
        }
        for(BoardPiece p: game.getUserGraveyard()){
            GameDetail g = new GameDetail();
            g.setGameId(game.getGameId());
            g.setPiece(p.getName());
            g.setWhoKilledPiece(p.getKilledBy().getName());
            g.setTeam(p.getSomeplayer().getName());
            gameDetailRepository.save(g);
        }
        for(BoardPiece p: game.getAiGraveyard()){
            GameDetail g = new GameDetail();
            g.setGameId(game.getGameId());
            g.setPiece(p.getName());
            g.setWhoKilledPiece(p.getKilledBy().getName());
            g.setTeam(p.getSomeplayer().getName());
            gameDetailRepository.save(g);
        }
        User u = userRepository.findByEmail(game.getUserName());
        u.setNumberofGamesPlayed(game.getGameId());
        userRepository.save(u);
    }


}