package telran.ashkelon2018.forum.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.forum.dto.UserProfileDto;
import telran.ashkelon2018.forum.dto.UserRegDto;
import telran.ashkelon2018.forum.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@PostMapping
	public UserProfileDto register(@RequestBody UserRegDto userRegDto, 
			@RequestHeader("Authorization") String token) {
		return accountService.addUser(userRegDto, token);
	}

	@PutMapping
	public UserProfileDto update(@RequestBody UserRegDto userRegDto, Principal principal) {
		return accountService.editUser(userRegDto, principal.getName());
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("#id == aunthenication.name or hasAnyRole('ADMIN', 'MODERATOR')")
	// this is owner of account / admin / moder
	public UserProfileDto remove(@PathVariable String id) {
		return accountService.removeUser(id);
	}
	
	@GetMapping
	public UserProfileDto login(Principal principal) {
		return accountService.getUser(principal.getName());
	}
	
	@PutMapping("/role/{id}/{role}")
	public Set<String> addRole(@PathVariable String id, @PathVariable String role){
		return accountService.addRole(id, role);
	}
		
	@DeleteMapping("/role/{id}/{role}")
	public Set<String> removeRole(@PathVariable String id, @PathVariable String role){
		return accountService.removeRole(id, role);
	}
	
	@PutMapping("/password")
	public void changePassword(Principal principal, 
			@RequestHeader("X-Authorization") String password) {
		accountService.changePassword(principal.getName(), password);
	}	
}
