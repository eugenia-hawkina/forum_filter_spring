package telran.ashkelon2018.forum.api;

public interface ForumEndpoints {

	String ACCOUNT = "/account";  // PUT -owner, GET - auth
	String ACCOUNT_ID = "/account/{id}";  // delete - owner, admin, moder
	String ACCOUNT_ROLE = "/account/role/*/";	// delete - admin, put - admin
	String ACCOUNT_PASSWORD = "/account/password";		// put - owner
	String FORUM_POST = "/forum/post";		// post - auth
//	String FORUM_POST_ID = "/forum/post/{id}";	// get - auth, del - owner, admin, moder
//	String FORUM_POST_LIKE = "/forum/post/{id}/like"; // put - auth
//	String FORUM_POST_COMMENT = "/forum/post/{id}/comment"; // put - auth
	String FORUM_POSTS = "/forum/posts"; // all 
	
	
}
