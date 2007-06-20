// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.List;

import org.openuss.TestUtility;
import org.springframework.dao.DataAccessException;

/**
 * JUnit Test for Spring Hibernate InstituteDao class.
 * @see org.openuss.lecture.InstituteDao
 */
public class InstituteDaoTest extends InstituteDaoTestBase {
	
	private TestUtility testUtility;
	
	public void testInstituteDaoCreate() {
		Institute institute = createTestInstitute();
		assertNull(institute.getId());
		instituteDao.create(institute);
		assertNotNull(institute.getId());
	}
	
	public void testUniqueShortcut() {
		Institute institute = createTestInstitute();
		institute.setShortcut("xxxx");
		assertNull(institute.getId());
		instituteDao.create(institute);
		assertNotNull(institute.getId());
		
		Institute institute2 = createTestInstitute();
		institute2.setShortcut("xxxx");
		assertNull(institute2.getId());
		instituteDao.create(institute2);
		assertNotNull(institute2.getId());

		try {
			setComplete();
			endTransaction();
			fail();
		} catch (DataAccessException e) {
			// success - remove the first one
		}
	}
	
	public void testLoadAllEnabled() {
		Institute institute1 = createTestInstitute();
		institute1.setEnabled(false);
		instituteDao.create(institute1);

		Institute institute2 = createTestInstitute();
		institute2.setEnabled(true);
		instituteDao.create(institute2);
		
		setComplete();
		endTransaction();
		
		List institutes = instituteDao.loadAllEnabled();
		assertTrue(institutes.contains(institute2));
		institutes.contains(institute1);
		assertFalse(institutes.contains(institute1));
		
	}

	private Institute createTestInstitute() {
		LectureBuilder builder = new LectureBuilder().createInstitute(testUtility.createUserInDB());
		return builder.getInstitute();
	}
	

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
}