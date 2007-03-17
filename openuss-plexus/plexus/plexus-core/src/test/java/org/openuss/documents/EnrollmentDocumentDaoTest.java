// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;


/**
 * JUnit Test for Spring Hibernate EnrollmentDocumentDao class.
 * @see org.openuss.documents.EnrollmentDocumentDao
 */
public class EnrollmentDocumentDaoTest extends EnrollmentDocumentDaoTestBase {
	
	public void testEnrollmentDocumentDaoCreate() {
		EnrollmentDocument enrollmentDocument = new EnrollmentDocumentImpl();
		assertNull(enrollmentDocument.getId());
		enrollmentDocumentDao.create(enrollmentDocument);
		assertNotNull(enrollmentDocument.getId());
	}
}