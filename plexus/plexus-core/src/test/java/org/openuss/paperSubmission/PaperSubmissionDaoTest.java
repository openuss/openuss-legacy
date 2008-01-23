// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.paperSubmission;


/**
 * JUnit Test for Spring Hibernate PaperSubmissionDao class.
 * @see org.openuss.paperSubmission.PaperSubmissionDao
 */
public class PaperSubmissionDaoTest extends PaperSubmissionDaoTestBase {
	
	public void testPaperSubmissionDaoCreate() {
		PaperSubmission paperSubmission = new PaperSubmission.Factory.newInstance();
		paperSubmission.setDeliverDate(" ");
		assertNull(paperSubmission.getId());
		paperSubmissionDao.create(paperSubmission);
		assertNotNull(paperSubmission.getId());
	}
}
