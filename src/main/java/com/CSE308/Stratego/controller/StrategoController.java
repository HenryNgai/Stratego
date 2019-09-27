package com.CSE308.Stratego.controller;
import com.CSE308.Stratego.model.Services;
import com.CSE308.Stratego.model.dao.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class StrategoController {
    @Autowired LoginRepository loginRepository;
    @Autowired Services services;

    @GetMapping("/")
    public String root() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/home")
    public String userIndex() {
        return "user/home";
    }

    @GetMapping("/game")
    public String Game() {
        return "user/game";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }


}