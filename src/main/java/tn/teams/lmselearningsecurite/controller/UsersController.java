package tn.teams.lmselearningsecurite.controller;


import lombok.RequiredArgsConstructor;
import tn.teams.lmselearningsecurite.entites.User;
import tn.teams.lmselearningsecurite.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Users")
@RequiredArgsConstructor
public class UsersController {
    private  final UserRepository userRepository;
    @GetMapping("/listerUser")
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    
}
