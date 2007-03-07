// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;


/**
 * JUnit Test for Spring Hibernate AnswerDao class.
 * @see org.openuss.braincontest.AnswerDao
 */
public class AnswerDaoTest extends AnswerDaoTestBase {
	
	public void testAnswerDaoCreate() {
		Answer answer = new AnswerImpl();
		answer.setAnswer(" ");
		assertNull(answer.getId());
		answerDao.create(answer);
		assertNotNull(answer.getId());
	}
}