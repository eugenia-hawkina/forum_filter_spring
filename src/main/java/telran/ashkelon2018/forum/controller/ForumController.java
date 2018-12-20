package telran.ashkelon2018.forum.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.forum.domain.Post;
import telran.ashkelon2018.forum.dto.DatePeriodDto;
import telran.ashkelon2018.forum.dto.NewCommentDto;
import telran.ashkelon2018.forum.dto.NewPostDto;
import telran.ashkelon2018.forum.dto.PostUpdateDto;
import telran.ashkelon2018.forum.service.ForumService;

@RestController
@RequestMapping("/forum")
public class ForumController {
	
	@Autowired
	ForumService forumService;
	
	@PostMapping("/post")
	public Post addNewPost(@RequestBody NewPostDto newPost, Principal principal) {
		return forumService.addNewPost(newPost, principal.getName());
	};
	
	@GetMapping("/post/{id}")
	public Post getPost(@PathVariable String id) {
		return forumService.getPost(id);
	};
	
	@DeleteMapping("/post/{id}")
	public Post removePost(@PathVariable String id, Principal principal) {
		return forumService.removePost(id, principal.getName());
	};
	
	@PutMapping("/post")
	public Post updatePost(@RequestBody PostUpdateDto postUpdate, Principal principal) {
		// id post - in PostUpdateDto
		return forumService.updatePost(postUpdate,principal.getName());
	};
	
	@PutMapping("/post/{id}/like")
	public boolean addLike(@PathVariable String id) {
		return forumService.addLike(id);
	};
	
	@PutMapping("/post/{id}/comment")
	public Post addComment(@PathVariable String id, 
			@RequestBody NewCommentDto newComment, Principal principal) {
		return forumService.addComment(id, newComment, principal.getName());
	};
	
	@GetMapping("/posts/tags")
	public Iterable<Post> findPostsByTags(@RequestBody List<String> tags) {
		return forumService.findPostsByTags(tags);
	};
	
	@GetMapping("/posts/author/{author}")
	public Iterable<Post> findPostsByAuthor(@PathVariable String author){
		return forumService.findPostsByAuthor(author);
	};	
	
	@GetMapping("/posts/dates")
	public Iterable<Post> findPostsByDates(@RequestBody DatePeriodDto datesDto){
		return forumService.findPostsByDates(datesDto);
	};

}
