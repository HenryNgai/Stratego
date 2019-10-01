package com.CSE308.Stratego.controller;
import com.CSE308.Stratego.model.Game;
import com.CSE308.Stratego.model.UserService;
import com.CSE308.Stratego.model.dao.GameDetail;
import com.CSE308.Stratego.model.dao.User;
import com.CSE308.Stratego.model.dao.UserRepository;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import java.io.IOException;
import java.util.List;


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
    public ModelAndView homePage(Model model) {
        ModelAndView modelandview= new ModelAndView();
        String email ="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        model.addAttribute("results", userService.getAllGameResults(email));
        modelandview.setViewName("admin/home");
        return modelandview;
    }

    @GetMapping("/game")
    public ModelAndView Game() {
        ModelAndView modelandview= new ModelAndView();
        String email ="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        game = new Game(email,userService.getGameID(email));
        modelandview.setViewName("admin/game");
        return modelandview;
    }

    @GetMapping("/access-denied")
    public ModelAndView accessDenied() {
        ModelAndView modelandview = new ModelAndView();
        modelandview.setViewName("error/access-denied");
        return modelandview;
    }

    @PostMapping("/validate-move")
    @ResponseBody
    public ResponseEntity<String> validateMove(@RequestParam("piece") String piece,
                                       @RequestParam("previousX") String x1, @RequestParam("previousY") String y1,
                                       @RequestParam("newX") String x2, @RequestParam ("newY") String y2,
                                       @RequestParam("AI") String AI, HttpServletResponse response) throws IOException {

        String result = game.makeMove(piece, Integer.parseInt(x1), Integer.parseInt(y1), Integer.parseInt(x2), Integer.parseInt(y2), Boolean.parseBoolean(AI));

        HttpHeaders headers = new HttpHeaders();


        if(game.isGamelost()){
            headers.add("endgame", "lost");
        }
        if(game.isGamewon()){
            headers.add("endgame", "won");
        }

        return new ResponseEntity<>(result,headers, HttpStatus.OK);

    }

    @GetMapping("/AIsetup")
    @ResponseBody
    public String setupAI(HttpServletResponse response){
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return game.aiSetup();

    }

    @PostMapping("/getGameDetail")
    @ResponseBody
    public String getGameDetail(@RequestParam ("gameId") String gameId,  HttpServletResponse response){
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        List<GameDetail> queryResult = userService.getGameDetail(Integer.parseInt(gameId));
        String temp="";
        for (int i =0; i < queryResult.size() ; i++){
            temp = temp + queryResult.get(i).getTeam() + " ";
            temp = temp + queryResult.get(i).getGameId() + " ";
            temp = temp + queryResult.get(i).getPiece() + " ";
            temp = temp + queryResult.get(i).getWhoKilledPiece() + ",";
        }
        return temp;
    }

    @GetMapping("/lost")
    public ModelAndView lost(Model model){
        ModelAndView modelandview = new ModelAndView();
        userService.writeGameToDatabase(game, false);
        model.addAttribute("message", "You Lost!");
        modelandview.setViewName("admin/end");
        return modelandview;
    }

    @GetMapping("/won")
    public ModelAndView won(Model model){
        ModelAndView modelandview = new ModelAndView();
        userService.writeGameToDatabase(game, true);
        model.addAttribute("message", "You Won!");
        modelandview.setViewName("admin/end");
        return modelandview;
    }
    
    @PostMapping("/autoMove")
    @ResponseBody
    public ResponseEntity<String> autoMove(HttpServletResponse response){
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String result = "";
        result = result + game.aiMovePiece(game.getUser());
        result = result + " " + game.aiMovePiece(game.getAi());
        HttpHeaders headers = new HttpHeaders();
        if(game.isGamelost()){
            headers.add("endgame", "lost");
        }
        if(game.isGamewon()){
            headers.add("endgame", "won");
        }
        return new ResponseEntity<>(result,headers, HttpStatus.OK);
    }

    @GetMapping("/autoSetup")
    @ResponseBody
    public String autoSetup(HttpServletResponse response){
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String result = game.autoSetup();
        return result;
    }


}