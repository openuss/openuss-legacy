// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.util.Date;


/**
 * JUnit Test for Spring Hibernate PostDao class.
 * @see org.openuss.discussion.PostDao
 */
public class PostDaoTest extends PostDaoTestBase {
	
	public void testPostInfoComparator(){
		PostInfoComparator pic = new PostInfoComparator();
		long time = System.currentTimeMillis();
		Date before = new Date(time);
		Date after = new Date(time + 100000);
		PostInfo first = new PostInfo();
		PostInfo second = new PostInfo();
		first.setCreated(before);
		second.setCreated(after);
		
		assertTrue(pic.compare(first, second)<0);
		assertEquals(pic.compare(first, null), 0);
		assertEquals(pic.compare(null, second), 0);
		assertEquals(pic.compare(null, null), 0);
	}
	
	public void testPostDaoCreate() {
		/*
		Post post = new PostImpl();
		post.setTitle(" ");
		post.setText(" ");
		post.setCreated(" ");
		assertNull(post.getId());
		postDao.create(post);
		assertNotNull(post.getId());*/
	}
}