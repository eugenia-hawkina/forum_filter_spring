package telran.ashkelon2018.forum.service;

import java.util.Set;

import telran.ashkelon2018.forum.dto.UserProfileDto;
import telran.ashkelon2018.forum.dto.UserRegDto;

public interface AccountService {
	
	UserProfileDto addUser(UserRegDto userRegDto, String token);
	
	UserProfileDto editUser(UserRegDto userRegDto, String login);
	
	UserProfileDto removeUser(String login);
	
	UserProfileDto getUser(String login);
	
	Set<String> addRole(String login, String role);
	
	Set<String> removeRole(String login, String role);

	void changePassword(String login, String password);
}
