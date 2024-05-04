package tn.teams.lmselearningsecurite.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.teams.lmselearningsecurite.entites.Role;
import tn.teams.lmselearningsecurite.entites.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RegistrationRequest {
	private String fullname;
	private String password;
	private String email;
	private String adress;
	private String phonenember;
	 private Set<String> role;
	 
	
	public static User toEntity(RegistrationRequest request)
	{
	return User.builder()
			.fullname(request.getFullname())
			.email(request.getEmail())
			.password(request.getPassword())
			.adress(request.getAdress())
			.phonenember(request.getPhonenember())
			.build();
		
		
		
	}
	
	
	
	
	/* public static User toEntity(RegistrationRequest request) {
	        return User.builder()
	                .fullname(request.getFullname())
	                .email(request.getEmail())
	                .password(request.getPassword())
	                .build();*/
	   // };
}
