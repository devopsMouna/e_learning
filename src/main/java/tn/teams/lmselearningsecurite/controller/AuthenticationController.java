package tn.teams.lmselearningsecurite.controller;


import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import tn.teams.lmselearningsecurite.config.AppConstants;
import tn.teams.lmselearningsecurite.dto.AuthenticationRequest;
import tn.teams.lmselearningsecurite.dto.AuthenticationResponse;
import tn.teams.lmselearningsecurite.dto.RegistrationRequest;
import tn.teams.lmselearningsecurite.entites.User;
import tn.teams.lmselearningsecurite.repository.UserRepository;
import tn.teams.lmselearningsecurite.services.AuthUserService;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthUserService service;
    private  final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegistrationRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

   /* @GetMapping("/listerUser")
    public List<User> getUsers(){
        return userRepository.findAll();
    }*/
    
    @GetMapping("/token/verify")
    public ResponseEntity<?> confirmRegistration(@NotEmpty @RequestParam String token) {
        final String result = service.validateVerificationToken(token);
        return ResponseEntity.ok(result);
    }
 
    // user activation - verification
    @PostMapping("/token/resend")
    @ResponseBody
    public ResponseEntity<?> resendRegistrationToken(@NotEmpty @RequestBody String expiredToken) {
        if (!service.resendVerificationToken(expiredToken)) {
            return ResponseEntity.badRequest().body("Token not found!");
        }
        return ResponseEntity.ok().body("Valid Token");
    }	
}