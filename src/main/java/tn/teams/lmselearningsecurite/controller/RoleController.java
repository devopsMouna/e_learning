package tn.teams.lmselearningsecurite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.teams.lmselearningsecurite.entites.Role;
import tn.teams.lmselearningsecurite.repository.RoleRepository;







@RestController
@RequestMapping("/role")
public class RoleController {
  @Autowired
 private  RoleRepository roleRepository;
  @GetMapping("/getallroles")
  public List<Role> getallrole(){
	
	  return roleRepository.findAll() ;
	  
  }
  @PostMapping("/saveroles")
  public Role saverole(@RequestBody Role role){
	
	  return roleRepository.save(role) ;
  }
}
