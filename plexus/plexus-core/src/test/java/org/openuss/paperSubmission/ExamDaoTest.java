// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.paperSubmission;


/**
 * JUnit Test for Spring Hibernate ExamDao class.
 * @see org.openuss.paperSubmission.ExamDao
 */
public class ExamDaoTest extends ExamDaoTestBase {
	
	public void testExamDaoCreate() {
		Exam exam = new Exam.Factory.newInstance();
		exam.setName(" ");
		exam.setDeadline(" ");
		assertNull(exam.getId());
		examDao.create(exam);
		assertNotNull(exam.getId());
	}
}
