package tn.teams.lmselearningsecurite.services;

import tn.teams.lmselearningsecurite.dto.AuthenticationRequest;
import tn.teams.lmselearningsecurite.dto.AuthenticationResponse;
import tn.teams.lmselearningsecurite.dto.RegistrationRequest;
import tn.teams.lmselearningsecurite.entites.User;

public interface AuthUserService {
	 AuthenticationResponse register(RegistrationRequest request);

	  AuthenticationResponse authenticate(AuthenticationRequest request);
	  void createVerificationTokenForUser(User user, String token);
	  
	    boolean resendVerificationToken(String token);
	 
	    String validateVerificationToken(String token);
}
