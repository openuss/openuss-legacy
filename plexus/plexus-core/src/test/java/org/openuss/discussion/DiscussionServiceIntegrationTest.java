// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.util.Date;
import java.util.List;

/**
 * JUnit Test for Spring Hibernate DiscussionService class.
 * @see org.openuss.discussion.DiscussionService
 */
@SuppressWarnings("unchecked")
public class DiscussionServiceIntegrationTest extends DiscussionServiceIntegrationTestBase {
	
	private PostInfo generatePost(){
		PostInfo postInfo = new PostInfo();
		postInfo.setCreated(new Date(System.currentTimeMillis()));
		postInfo.setLastModification(postInfo.getCreated());
		postInfo.setSubmitter("user");
		postInfo.setSubmitterId(new Long(1));
		postInfo.setText("testText");
		postInfo.setTitle("testTitle");	
		return postInfo;
	}

	private Long generateDomainObject(){
		return new Long(System.currentTimeMillis());
	}
	
	public void testCreateDeleteTopic(){
		//test correct creation of a example topic
		Long domainObject = generateDomainObject();
		PostInfo firstPost = generatePost();
		discussionService.createTopic(firstPost, domainObject);
		List<TopicInfo> topics = discussionService.getTopics(domainObject);
		assertNotNull(topics);
		assertEquals(1, topics.size());
		TopicInfo addedTopic = topics.get(0); 
		assertEquals(0, addedTopic.getAnswerCount().intValue());
		assertEquals(addedTopic.getTitle(), firstPost.getTitle());
		assertEquals(addedTopic.getSubmitter(), firstPost.getSubmitter());
		assertEquals(addedTopic.getCreated(), firstPost.getCreated());
		List<PostInfo> posts =discussionService.getPosts(addedTopic); 
		assertNotNull(posts);
		assertEquals(1, posts.size());
		//test correct delete of that topic
		discussionService.deleteTopic(addedTopic);
		topics = discussionService.getTopics(domainObject);
		assertNotNull(topics);
		assertEquals(0, topics.size());
		
		posts = discussionService.getPosts(addedTopic);
		assertNotNull(posts);
		assertEquals(0, posts.size());		
	}
	
	public void testAddPost(){
		//test correct creation of a example topic
		Long domainObject = generateDomainObject();
		PostInfo firstPost = generatePost();
		discussionService.createTopic(firstPost, domainObject);
		List<TopicInfo> topics = discussionService.getTopics(domainObject);
		assertNotNull(topics);
		assertEquals(1, topics.size());
		TopicInfo addedTopic = topics.get(0); 
		assertEquals(0, addedTopic.getAnswerCount().intValue());
		assertEquals(addedTopic.getTitle(), firstPost.getTitle());
		assertEquals(addedTopic.getSubmitter(), firstPost.getSubmitter());
		assertEquals(addedTopic.getCreated(), firstPost.getCreated());
		List<PostInfo> posts =discussionService.getPosts(addedTopic); 
		assertNotNull(posts);
		assertEquals(1, posts.size());
		//test correct adding of a post
		//Post
	}
}