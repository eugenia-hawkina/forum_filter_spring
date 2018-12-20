package telran.ashkelon2018.forum.service.security;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import telran.ashkelon2018.forum.dao.ForumRepository;
import telran.ashkelon2018.forum.dao.UserAccountRepository;
import telran.ashkelon2018.forum.domain.Post;
import telran.ashkelon2018.forum.exceptions.PostNotFoundException;

@Component
public class WebSecurity {
	@Autowired
	ForumRepository repository;
	
	@Autowired
	UserAccountRepository userRepository;

	public boolean checkAuthBeforeDeletePost(Authentication authentication, String id) {
		Post post = repository.findById(id).orElseThrow(PostNotFoundException::new);
		Set<String> roles = userRepository
				.findById(authentication.getName()).get().getRoles();
		boolean condOwner = authentication.getName().equals(post.getAuthor());
		boolean condAdmMod = roles.contains("ROLE_ADMIN") || roles.contains("ROLE_MODERATOR");		 
		return  (condOwner || condAdmMod);
	}
}
