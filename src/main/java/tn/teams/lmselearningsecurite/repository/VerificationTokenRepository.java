package tn.teams.lmselearningsecurite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.teams.lmselearningsecurite.entites.Token;
import tn.teams.lmselearningsecurite.entites.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	  Optional<VerificationToken> findByToken(String token);
}
