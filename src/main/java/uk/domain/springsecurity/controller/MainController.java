package uk.domain.springsecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.domain.springsecurity.model.AppUser;
import uk.domain.springsecurity.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String main() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/all")
    public String all() {
        return "all";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/user")
    public String user() {
        return "user";
    }

    @RequestMapping("/signup")
    public ModelAndView singUp() {
        return new ModelAndView("registration", "user", new AppUser());
    }

    @RequestMapping("/register")
    public ModelAndView register(AppUser appUser, HttpServletRequest request) {
        userService.saveUserApp(appUser, request);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verify-token")
    public ModelAndView verifyToken(@RequestParam String token) {
        userService.verifyToken(token);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verify-token-owner")
    public ModelAndView verifyTokenOwner(@RequestParam String token) {
        userService.verifyTokenOwner(token);
        return new ModelAndView("redirect:/login");
    }
}
