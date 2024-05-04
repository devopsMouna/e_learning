package tn.teams.lmselearningsecurite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.teams.lmselearningsecurite.entites.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	  Optional<Role> findByName(String role);
}
