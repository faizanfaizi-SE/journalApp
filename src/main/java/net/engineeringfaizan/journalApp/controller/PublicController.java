package net.engineeringfaizan.journalApp.controller;

import net.engineeringfaizan.journalApp.entity.User;
import net.engineeringfaizan.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {


    @Autowired
    private UserService userService;

    @GetMapping("/healt-check")
    public String healthCheck(){
        return "Yes it was working";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }
}
