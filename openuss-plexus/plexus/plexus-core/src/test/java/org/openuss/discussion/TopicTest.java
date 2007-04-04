package org.openuss.discussion;

import junit.framework.TestCase;

public class TopicTest extends TestCase {

	public final void testAddPostPost() {
		Post post = Post.Factory.newInstance();
		Topic topic = Topic.Factory.newInstance();
		assertNull(topic.getFirst());
		assertNull(topic.getLast());
		assertEquals(0, topic.getAnswerCount().intValue());
		assertEquals(0, topic.getHits().intValue());
		
		topic.addPost(post);
		assertEquals(post, topic.getFirst());
		assertEquals(post, topic.getLast());
		
		Post answer = Post.Factory.newInstance();
		topic.addPost(answer);
		assertEquals(post, topic.getFirst());
		assertEquals(answer, topic.getLast());
		assertEquals(1, topic.getAnswerCount().intValue());
		
		Post answer2 = Post.Factory.newInstance();
		topic.addPost(answer2);
		assertEquals(post, topic.getFirst());
		assertEquals(answer2, topic.getLast());
		assertEquals(2, topic.getAnswerCount().intValue());
		
		topic.removePost(post);
		assertEquals(answer, topic.getFirst());
		assertEquals(answer2, topic.getLast());
		assertEquals(1, topic.getAnswerCount().intValue());
		
		topic.removePost(answer2);
		assertEquals(answer, topic.getFirst());
		assertEquals(answer, topic.getLast());
		assertEquals(0, topic.getAnswerCount().intValue());
	}
}
