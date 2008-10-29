// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.paperSubmission;

import java.util.Date;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;


/**
 * JUnit Test for Spring Hibernate ExamDao class.
 * @see org.openuss.paperSubmission.ExamDao
 */
public class ExamDaoTest extends ExamDaoTestBase {
	
	public void testExamDao() {
		DefaultDomainObject domain = new DefaultDomainObject(TestUtility.unique());
		Exam exam = new ExamImpl();
		exam.setName("test_exam");
		exam.setDescription("description");
		exam.setDomainId(domain.getId());
		Long futureDate = new Date().getTime()+100000000;
		exam.setDeadline(new Date(futureDate));
		assertNull(exam.getId());
		examDao.create(exam);
		assertNotNull(exam.getId());
		Exam exam2 = examDao.load(exam.getId());
		assertEquals(exam2.getId(), exam.getId());
		assertEquals(exam2.getName(), exam.getName());
		assertEquals(exam2.getDomainId(), exam.getDomainId());
		exam.setDescription("description2");
		examDao.update(exam);
		exam2 = examDao.load(exam.getId());
		assertEquals(exam2.getDescription(), "description2");
		List<Exam> examList = examDao.findByDomainId(domain.getId());
		assertNotNull(examList);
		assertEquals(1, examList.size());
		examDao.remove(exam2);
		assertNull(examDao.load(exam2.getId()));
	}
}
