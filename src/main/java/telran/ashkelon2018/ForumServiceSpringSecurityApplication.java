package telran.ashkelon2018;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.ashkelon2018.forum.dao.UserAccountRepository;
import telran.ashkelon2018.forum.domain.UserAccount;

@SpringBootApplication
public class ForumServiceSpringSecurityApplication implements CommandLineRunner{

	@Autowired
	UserAccountRepository repository;
	
	@Autowired
	PasswordEncoder encoder;
	
	public static void main(String[] args) {
		SpringApplication.run(ForumServiceSpringSecurityApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		if(!repository.existsById("admin")) {
			// create first admin
			String hashPassword = encoder.encode("admin");
			UserAccount userAccount = UserAccount.builder()
					.login("admin")
					.password(hashPassword)
					.firstName("Super")
					.lastName("Admin")
					.expdate(LocalDateTime.now().plusYears(70))
					.role("ROLE_ADMIN")
					.build();
			repository.save(userAccount);
		}		
	}
}

