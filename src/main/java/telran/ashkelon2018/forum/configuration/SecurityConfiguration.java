package telran.ashkelon2018.forum.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static telran.ashkelon2018.forum.api.ForumEndpoints.*;
// static import -> don't need to write class name every time when we use constant
// * ForumEndpoints.FORUM_POST_ID -> FORUM_POST_ID

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.POST, ACCOUNT);
		// don't need auth for this endpoint and POST
		// endpoints in interface Endpoints, package api
		
	
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		// works with basic auth (Base64)
		http.csrf().disable();
		// cross-site request forgery - doesn't allow methods put, post, delete
//		http.authorizeRequests().anyRequest().authenticated();
//		// only registered users have access to all endpoints
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// every inquiry asks for new tokes
		// session psw is not saved
		
	
		http.authorizeRequests()
			.antMatchers(FORUM_POSTS + "/**")
			.permitAll();
		http.authorizeRequests()
			.antMatchers(ACCOUNT_ROLE+"/**")
			.hasRole("ADMIN");
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, FORUM_POST)
			.hasAnyRole("USER", "ADMIN", "MODERATOR");
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, ACCOUNT, FORUM_POST + "/")
			.hasAnyRole("USER", "ADMIN", "MODERATOR");
		http.authorizeRequests()
			.antMatchers(HttpMethod.PUT, ACCOUNT_PASSWORD, ACCOUNT, 
					FORUM_POST+"/*/like", FORUM_POST+"/*/comment")
			.hasAnyRole("USER", "ADMIN", "MODERATOR");				
		http.authorizeRequests()
			.antMatchers(HttpMethod.PUT, ACCOUNT_PASSWORD)
			.authenticated();
		
	// my variant	
//		http.authorizeRequests()
//			.antMatchers(HttpMethod.DELETE, FORUM_POST+"/")
//			.authenticated();
		
		// variant 2
		// https://docs.spring.io/spring-security/site/docs/current/reference/html/authorization.html#el-common-built-in
		http.authorizeRequests()
		.antMatchers(HttpMethod.PUT, ACCOUNT_PASSWORD)
		.access("@webSecurity.check(authentication,request)");
	
		http.authorizeRequests()
		.antMatchers("/actuator/**")
		.hasRole("ADMIN");
	}
}

