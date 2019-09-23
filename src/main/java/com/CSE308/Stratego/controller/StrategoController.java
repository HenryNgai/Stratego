package com.CSE308.Stratego.controller;
import com.CSE308.Stratego.model.dao.BoardPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class StrategoController {

    @Autowired
    private BoardPieceRepository boardPieceRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("game")
    public String game(){
        return "game";
    }



}