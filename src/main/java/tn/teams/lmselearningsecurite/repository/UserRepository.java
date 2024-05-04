package tn.teams.lmselearningsecurite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.teams.lmselearningsecurite.entites.User;



public interface UserRepository extends JpaRepository<User,Long> {
Optional<User> findByEmail(String email);
}
