package telran.ashkelon2018.forum.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.forum.configuration.AccountConfiguration;
import telran.ashkelon2018.forum.configuration.AccountUserCredentials;
import telran.ashkelon2018.forum.dao.UserAccountRepository;
import telran.ashkelon2018.forum.domain.UserAccount;
import telran.ashkelon2018.forum.dto.UserProfileDto;
import telran.ashkelon2018.forum.dto.UserRegDto;
import telran.ashkelon2018.forum.exceptions.UserConflictException;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	UserAccountRepository userRepository;
	
	@Autowired
	AccountConfiguration accountConfiguration;
	
	@Autowired
	PasswordEncoder encoder;

	@Override
	public UserProfileDto addUser(UserRegDto userRegDto, String token) {
		AccountUserCredentials credentials = accountConfiguration.decodeToken(token);
		if (userRepository.existsById(credentials.getLogin())) {
			throw new UserConflictException();
		}
		String hashPassword = encoder.encode(credentials.getPassword());
		// this line differs from prev project
		UserAccount userAccount = UserAccount.builder()
				.login(credentials.getLogin())
				.password(hashPassword)
				.firstName(userRegDto.getFirstName())
				.lastName(userRegDto.getLastName()).role("ROLE_USER")
				// roles mb writter with capital letters & start with ROLE_				
				.expdate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()))
				.build();
		userRepository.save(userAccount);
		return convertToUserProfileDto(userAccount);
	}
	
	private UserProfileDto convertToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder()
				.firstName(userAccount.getFirstName())
				.lastName(userAccount.getLastName())
				.login(userAccount.getLogin())
				.roles(userAccount.getRoles())
				.build();
	}

	@Override
	public UserProfileDto editUser(UserRegDto userRegDto, String login) {
		UserAccount userAccount = userRepository.findById(login).get();
		if (userRegDto.getFirstName() != null) {
			userAccount.setFirstName(userRegDto.getFirstName());
		}
		if (userRegDto.getLastName() != null) {
			userAccount.setLastName(userRegDto.getLastName());
		}
		userRepository.save(userAccount);
		return convertToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto removeUser(String login) {
		UserAccount userAccount = userRepository.findById(login).get();
		userRepository.delete(userAccount);
		return convertToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto getUser(String login) {
		UserAccount userAccount = userRepository.findById(login).get();
		return convertToUserProfileDto(userAccount);
	}

	@Override
	public Set<String> addRole(String login, String role) {
		UserAccount userAccount = userRepository.findById(login).orElse(null);
		if(userAccount == null) {
			return null;
		}
		userAccount.addRole(role);
		// NB to case in method
		userRepository.save(userAccount);
		return userAccount.getRoles();
	}

	@Override
	public Set<String> removeRole(String login, String role) {
		UserAccount userAccount = userRepository.findById(login).orElse(null);
		if(userAccount == null) {
			return null;
		}
		userAccount.removeRole(role);
		userRepository.save(userAccount);
		return userAccount.getRoles();
	}

	@Override
	public void changePassword(String login, String password) {
		UserAccount userAccount = userRepository.findById(login).get();
		String hashPassword = encoder.encode(password);
		userAccount.setPassword(hashPassword);
		userAccount.setExpdate(LocalDateTime.now()
				.plusDays(accountConfiguration.getExpPeriod()));
		userRepository.save(userAccount);
	}
}
