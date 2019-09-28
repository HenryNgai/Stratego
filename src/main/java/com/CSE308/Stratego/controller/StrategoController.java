package com.CSE308.Stratego.controller;
import com.CSE308.Stratego.model.Game;
import com.CSE308.Stratego.model.UserService;
import com.CSE308.Stratego.model.dao.User;
import com.CSE308.Stratego.model.dao.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
public class StrategoController {
    
    private Game game;

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        String email ="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        model.addAttribute("results", userService.getAllGameResults(email));
        return "/admin/home";
    }

    @GetMapping("/game")
    public String Game() {
        String email ="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        game = new Game(email,userService.getGameID(email));
        return "/admin/game";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

    @PostMapping("/validate-move")
    @ResponseBody
    public String validateMove(@RequestParam("piece") String piece,
                                       @RequestParam("previousX") String x1, @RequestParam("previousY") String y1,
                                       @RequestParam("newX") String x2, @RequestParam ("newY") String y2,
                                       @RequestParam("AI") String AI, HttpServletResponse response) {

        String result = game.makeMove(piece, Integer.parseInt(x1), Integer.parseInt(y1), Integer.parseInt(x2), Integer.parseInt(y2), Boolean.parseBoolean(AI));
        result = result + " 0 0 0 0";
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return result;
    }

    @GetMapping("/AIsetup")
    @ResponseBody

    public String setupAI(HttpServletResponse response){
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return game.aiSetup();

    }


}