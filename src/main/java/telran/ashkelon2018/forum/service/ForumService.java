package telran.ashkelon2018.forum.service;

import java.util.List;

import org.springframework.stereotype.Service;

import telran.ashkelon2018.forum.domain.Post;
import telran.ashkelon2018.forum.dto.DatePeriodDto;
import telran.ashkelon2018.forum.dto.NewCommentDto;
import telran.ashkelon2018.forum.dto.NewPostDto;
import telran.ashkelon2018.forum.dto.PostUpdateDto;

@Service
public interface ForumService {
	
	Post addNewPost(NewPostDto newPost, String login);
	
	Post getPost(String id);
	
	Post removePost(String id, String login);
	
	Post updatePost(PostUpdateDto postUpdate, String login);
	
	boolean addLike(String id);
	
	Post addComment(String id, NewCommentDto newComment, String login);
	
	Iterable<Post> findPostsByTags(List<String> tags);
	
	Iterable<Post> findPostsByAuthor(String author);	
	
	Iterable<Post> findPostsByDates(DatePeriodDto datesDto);

}
