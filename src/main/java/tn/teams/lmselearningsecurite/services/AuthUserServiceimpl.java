package tn.teams.lmselearningsecurite.services;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import tn.teams.lmselearningsecurite.config.AppConstants;
import tn.teams.lmselearningsecurite.config.JwtService;
import tn.teams.lmselearningsecurite.configmail.MailService;
import tn.teams.lmselearningsecurite.dto.AuthenticationRequest;
import tn.teams.lmselearningsecurite.dto.AuthenticationResponse;
import tn.teams.lmselearningsecurite.dto.RegistrationRequest;
import tn.teams.lmselearningsecurite.entites.Role;
import tn.teams.lmselearningsecurite.entites.Token;
import tn.teams.lmselearningsecurite.entites.TokenType;
import tn.teams.lmselearningsecurite.entites.User;
import tn.teams.lmselearningsecurite.entites.VerificationToken;
import tn.teams.lmselearningsecurite.repository.RoleRepository;
import tn.teams.lmselearningsecurite.repository.TokenRepository;
import tn.teams.lmselearningsecurite.repository.UserRepository;
import tn.teams.lmselearningsecurite.repository.VerificationTokenRepository;
@Service
@RequiredArgsConstructor 
public class AuthUserServiceimpl implements AuthUserService{
	 private final RoleRepository roleRepository;
	  private final UserRepository repository;
	  private final TokenRepository tokenRepository;
	  private final VerificationTokenRepository vtokenRepository;
	  private final PasswordEncoder passwordEncoder;
	  private final JwtService jwtService;
	  private final AuthenticationManager authenticationManager;
	  private final MailService mailService;
	@Override
	public AuthenticationResponse register(RegistrationRequest request) {
		Set<String> strRoles = request.getRole();
	      Set<Role> roles = new HashSet<>();
	      //System.err.println(strRoles[]);
	      if (strRoles == null) {
	          Role userRole = roleRepository.findByName("UTILISATEUR")
	                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	          roles.add(userRole);
	      } else {
	          strRoles.forEach(role -> {

	                  Role adminRole = roleRepository.findByName(role)
	                          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	                  roles.add(adminRole);

	          });
	      }
		  User  user = User.builder()
	        .fullname(request.getFullname())
	        .phonenember(request.getPhonenember())
	        .adress(request.getAdress())
	        .roles(roles)
	        .email(request.getEmail())
	        .password(passwordEncoder.encode(request.getPassword()))
	        .active(false)
	        .build();
		  //user.setRoles(roles);
	    var savedUser = repository.save(user);
	    var jwtToken = jwtService.generateToken(user);
	    var refreshToken = jwtService.generateRefreshToken(user);
	    final String token = UUID.randomUUID().toString();
        createVerificationTokenForUser(user, token);
        mailService.sendVerificationToken(token, user);
	    saveUserToken(savedUser, jwtToken);
	    return AuthenticationResponse.builder()
	        .accessToken(jwtToken)
	            .refreshToken(refreshToken)
	        .build();
	}
	
	/*public boolean resendVerificationToken(final String existingVerificationToken) {
		Token vToken = tokenRepository.findByToken(existingVerificationToken);
		if(vToken != null) {
			vToken.updateToken(UUID.randomUUID().toString());
			vToken = tokenRepository.save(vToken);
			mailService.sendVerificationToken(vToken.getToken(), vToken.getUser());
			return true;
		}
		return false;
	}*/
//***************
	 private void saveUserToken(User user, String jwtToken) {
		    var token = Token.builder()
		        .user(user)
		        .token(jwtToken)
		        .tokenType(TokenType.BEARER)
		        .expired(false)
		        .revoked(false)
		        .build();
		    tokenRepository.save(token);
		  }

		  private void revokeAllUserTokens(User user) {
		    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		    if (validUserTokens.isEmpty())
		      return;
		    validUserTokens.forEach(token -> {
		      token.setExpired(true);
		      token.setRevoked(true);
		    });
		    tokenRepository.saveAll(validUserTokens);
		  }

		  public void refreshToken(
		          HttpServletRequest request,
		          HttpServletResponse response
		  ) throws IOException {
		    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		    final String refreshToken;
		    final String userEmail;
		    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
		      return;
		    }
		    refreshToken = authHeader.substring(7);
		    userEmail = jwtService.extractUsername(refreshToken);
		    if (userEmail != null) {
		      var user = this.repository.findByEmail(userEmail)
		              .orElseThrow();
		      if (jwtService.isTokenValid(refreshToken, user)) {
		        var accessToken = jwtService.generateToken(user);
		        revokeAllUserTokens(user);
		        saveUserToken(user, accessToken);
		        var authResponse = AuthenticationResponse.builder()
		                .accessToken(accessToken)
		                .refreshToken(refreshToken)
		                .build();
		        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
		      }
		    }
		  }
	
	//**********************
	
	
	
	
	
	
	
	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		 authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(
			            request.getEmail(),
			            request.getPassword()
			        )
			    );
			    var user = repository.findByEmail(request.getEmail())
			        .orElseThrow();
			    var jwtToken = jwtService.generateToken(user);
			    var refreshToken = jwtService.generateRefreshToken(user);
			    revokeAllUserTokens(user);
			    saveUserToken(user, jwtToken);
			    return AuthenticationResponse.builder()
			        .accessToken(jwtToken)
			            .refreshToken(refreshToken)
			        .build();
	}

	 @Override
	    public void createVerificationTokenForUser(final User user, final String token) {
	        final VerificationToken myToken = new VerificationToken(token, user);
	        vtokenRepository.save(myToken);
	    }
	 
	    @Override
	    @Transactional
	    public boolean resendVerificationToken(final String existingVerificationToken) {
	        VerificationToken vToken = vtokenRepository.findByToken(existingVerificationToken).get();
	        if(vToken != null) {
	            vToken.updateToken(UUID.randomUUID().toString());
	            vToken = vtokenRepository.save(vToken);
	            mailService.sendVerificationToken(vToken.getToken(), vToken.getUser());
	            return true;
	        }
	        return false;
	    }
	     
	    @Override
	    public String validateVerificationToken(String token) {
	         Optional<VerificationToken> verificationToken = vtokenRepository.findByToken(token);
	        if (verificationToken == null) {
	            return AppConstants.TOKEN_INVALID;
	        }
	 
	        final User user = verificationToken.get().getUser();
	        final Calendar cal = Calendar.getInstance();
	        if ((verificationToken.get().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	            return AppConstants.TOKEN_EXPIRED;
	        }
	 
	        user.setActive(true);
	        tokenRepository.deleteById(verificationToken.get().getId());
	       repository.save(user);
	        return AppConstants.TOKEN_VALID;
	    }

}
