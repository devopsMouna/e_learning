package tn.teams.lmselearningsecurite.configmail;

import tn.teams.lmselearningsecurite.entites.User;

public interface MailService {

	void sendVerificationToken(String token, User user);
}
