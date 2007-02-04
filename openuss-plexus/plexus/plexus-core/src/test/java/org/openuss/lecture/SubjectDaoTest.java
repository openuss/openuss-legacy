// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate SubjectDao class.
 * @see org.openuss.lecture.SubjectDao
 */
public class SubjectDaoTest extends SubjectDaoTestBase {
	
	private TestUtility testUtility;
	private Faculty faculty;
	
	private FacultyDao facultyDao;
	
	public SubjectDaoTest () {
		setDefaultRollback(false);
	}
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		faculty = testUtility.createPersistFacultyWithDefaultUser();
		commit();
	}
	
	@Override
	protected void onTearDownInTransaction() throws Exception {
		testUtility.removePersistFacultyAndDefaultUser();
	}
	
	private void commit() {
		endTransaction();
		startNewTransaction();
	}
	
	
	public void testSubjectDao() {
		// create a subject
		Subject subject = new SubjectImpl();
		subject.setName("subjectname"+System.currentTimeMillis());
		subject.setShortcut("shortcut"+System.currentTimeMillis());
		
		// associate subject with faculty
		faculty.add(subject);
		subject.setFaculty(faculty);
		
		// persist
		assertNull(subject.getId());
		facultyDao.update(faculty);
		assertNotNull(subject.getId());
		commit();
		
		// load 
		Subject s = subjectDao.load(subject.getId());
		assertEquals(subject.getId(),s.getId());
		
		// update
		s.setDescription("description");
		subjectDao.update(s);
		commit();

		// remove subject from faculty
		faculty.remove(s);
		facultyDao.update(faculty);
		commit();
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}



}