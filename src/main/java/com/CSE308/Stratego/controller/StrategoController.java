package com.CSE308.Stratego.controller;
import com.CSE308.Stratego.model.Game;
import com.CSE308.Stratego.model.UserService;
import com.CSE308.Stratego.model.dao.User;
import com.CSE308.Stratego.model.dao.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String userIndex() {
        return "/admin/home";
    }

    @GetMapping("/game")
    public String Game() {
        return "/admin/game";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

    @PostMapping("/validate-move")
    public ResponseEntity validateMove(@RequestParam("piece") String piece,
                                       @RequestParam("previousX") String x1, @RequestParam("previousY") String y1,
                                       @RequestParam("newX") String x2, @RequestParam ("newY") String y2,
                                       @RequestParam("AI") String AI) {
        System.out.println(piece);
        System.out.println(x1);
        System.out.println(y1);
        System.out.println(x2);
        System.out.println(y2);
        System.out.println(AI);
        return new ResponseEntity<>("result successful result",
                HttpStatus.OK);
    }

    @GetMapping("/AIsetup")
    @ResponseBody
    public String setupAI(){

        return "";//String of AI with space delimiter
    }


}