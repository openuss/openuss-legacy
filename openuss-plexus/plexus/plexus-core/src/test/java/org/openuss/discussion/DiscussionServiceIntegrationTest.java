// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.util.Date;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.security.SecurityService;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate DiscussionService class.
 * @see org.openuss.discussion.DiscussionService
 */
@SuppressWarnings("unchecked")
public class DiscussionServiceIntegrationTest extends DiscussionServiceIntegrationTestBase {
	
	public SecurityService securityService;
	
	private PostInfo generatePost(){
		User user = testUtility.createUserSecureContext();
		PostInfo postInfo = new PostInfo();
		postInfo.setCreated(new Date(System.currentTimeMillis()));
		postInfo.setLastModification(postInfo.getCreated());
		postInfo.setSubmitter(user.getUsername());
		postInfo.setSubmitterId(1L);
		postInfo.setText("testText");
		postInfo.setTitle("testTitle");	
		postInfo.setIp("001.002.003.004");
		return postInfo;
	}

	private DomainObject generateDomainObject(){
		DomainObject domainObject = new DefaultDomainObject(TestUtility.unique());
		securityService.createObjectIdentity(domainObject, null);
		//securityService.setPermissions(user, domainId, LectureAclEntry.ASSIST);		
		return domainObject;
	}
	
	public void testCreateDeleteTopic(){
		//test correct creation of a example topic
		ForumInfo fi = new ForumInfo();
		DomainObject domainObject = generateDomainObject();		
		fi.setDomainIdentifier(domainObject.getId());
		fi.setReadOnly(false);
		discussionService.addForum(fi);
		commit();
		ForumInfo loadedForum = discussionService.getForum(domainObject);
		assertNotNull(loadedForum);
		assertEquals(domainObject.getId(), loadedForum.getDomainIdentifier());
		PostInfo firstPost = generatePost();
		commit();
		discussionService.createTopic(firstPost, loadedForum);
		commit();		
		List<TopicInfo> topics = discussionService.getTopics(loadedForum);
		assertNotNull(topics);
		assertEquals(1, topics.size());
		TopicInfo addedTopic = topics.get(0); 
		assertEquals(0, addedTopic.getAnswerCount().intValue());
		assertEquals(addedTopic.getTitle(), firstPost.getTitle());
		assertEquals(addedTopic.getCreated().getTime(), firstPost.getCreated().getTime());
		List<PostInfo> posts =discussionService.getPosts(addedTopic); 
		assertNotNull(posts);
		assertEquals(1, posts.size());
		commit();
		//test correct delete of that topic		
		discussionService.deleteTopic(addedTopic);
		commit();
		topics = discussionService.getTopics(discussionService.getForum(domainObject));
		assertNotNull(topics);
		assertEquals(0, topics.size());
		commit();
		posts = discussionService.getPosts(addedTopic);
		assertNotNull(posts);
		assertEquals(0, posts.size());		
	}
	
	public void testAddPost(){
		ForumInfo fi = new ForumInfo();
		DomainObject domainObject = generateDomainObject();
		fi.setDomainIdentifier(domainObject.getId());
		fi.setReadOnly(false);
		discussionService.addForum(fi);
		ForumInfo loadedForum = discussionService.getForum(domainObject);
		assertNotNull(loadedForum);
		assertEquals(domainObject.getId(), loadedForum.getDomainIdentifier());

		//test correct creation of a example topic
		PostInfo firstPost = generatePost();
		discussionService.createTopic(firstPost, loadedForum);
		List<TopicInfo> topics = discussionService.getTopics(discussionService.getForum(domainObject));
		assertNotNull(topics);
		assertEquals(1, topics.size());
		TopicInfo addedTopic = topics.get(0); 
		assertEquals(0, addedTopic.getAnswerCount().intValue());
		assertEquals(addedTopic.getTitle(), firstPost.getTitle());
		assertEquals(addedTopic.getCreated().getTime(), firstPost.getCreated().getTime());
		List<PostInfo> posts =discussionService.getPosts(addedTopic); 
		assertNotNull(posts);
		assertEquals(1, posts.size());
		//test correct adding of a post
		PostInfo newPost = generatePost();
		discussionService.addPost(newPost, addedTopic);
		topics = discussionService.getTopics(discussionService.getForum(domainObject));
		assertNotNull(topics);
		assertEquals(1, topics.size());
		addedTopic = topics.get(0); 
		assertEquals(1, addedTopic.getAnswerCount().intValue());
	}
	
	public void testDeletePost(){
		ForumInfo fi = new ForumInfo();
		DomainObject domainObject = generateDomainObject();
		fi.setDomainIdentifier(domainObject.getId());
		fi.setReadOnly(false);
		discussionService.addForum(fi);
		ForumInfo loadedForum = discussionService.getForum(domainObject);
		assertNotNull(loadedForum);
		assertEquals(domainObject.getId(), loadedForum.getDomainIdentifier());
		//test delete 
		//test correct creation of a example topic
		flush();
		PostInfo firstPost = generatePost();
		discussionService.createTopic(firstPost, loadedForum);
		List<TopicInfo> topics = discussionService.getTopics(discussionService.getForum(domainObject));
		assertNotNull(topics);
		assertEquals(1, topics.size());
		TopicInfo addedTopic = topics.get(0); 
		assertEquals(0, addedTopic.getAnswerCount().intValue());
		assertEquals(addedTopic.getTitle(), firstPost.getTitle());
		assertEquals(addedTopic.getCreated().getTime(), firstPost.getCreated().getTime());
		List<PostInfo> posts =discussionService.getPosts(addedTopic); 
		assertNotNull(posts);
		assertEquals(1, posts.size());
		//test correct adding of a post
		PostInfo newPost = generatePost();
		discussionService.addPost(newPost, addedTopic);
		flush();
		topics = discussionService.getTopics(discussionService.getForum(domainObject));
		assertNotNull(topics);
		assertEquals(1, topics.size());
		addedTopic = topics.get(0); 
		assertEquals(1, addedTopic.getAnswerCount().intValue());
		PostInfo addedPost = discussionService.getPost(newPost);
		flush();
		discussionService.deletePost(addedPost);
		flush();
		topics = discussionService.getTopics(loadedForum);
		assertNotNull(topics);
		assertEquals(1, topics.size());
		addedTopic = topics.get(0); 
		assertEquals(0, addedTopic.getAnswerCount().intValue());			
	}
	
	public void testAddGetForum(){		
		ForumInfo fi = new ForumInfo();
		DomainObject domainObject = generateDomainObject();
		fi.setDomainIdentifier(domainObject.getId());
		fi.setReadOnly(false);
		discussionService.addForum(fi);
		ForumInfo loadedForum = discussionService.getForum(domainObject);
		assertNotNull(loadedForum);
		assertEquals(domainObject.getId(), loadedForum.getDomainIdentifier());
		
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
}
