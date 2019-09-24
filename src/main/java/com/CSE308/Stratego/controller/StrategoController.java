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
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

//    @GetMapping("/home")
//    public String home(){
//        return "home";
//    }

    @GetMapping("game")
    public String game(){
        return "game";
    }

    @GetMapping("success")
    public String homePage(Model model) {
        model.addAttribute("results", services.getAllGameResults());
        return "home";
    }

    @PostMapping("/validate_login")
    public String validate_login(@RequestParam String username, @RequestParam String password){
        if (services.validate_login_function(username, password)) {
            return "redirect:success";
        }
        else{
            return "Invalid login please refresh the page and try again try again";
        }
    }



}