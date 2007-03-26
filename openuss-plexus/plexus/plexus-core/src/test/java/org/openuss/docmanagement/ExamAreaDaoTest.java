// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.docmanagement;

import java.sql.Timestamp;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class ExamAreaDaoTest extends AbstractTransactionalDataSourceSpringContextTests{
	
	public FolderDao folderDao;
	
	public DocTestUtility docTestUtility;
	
	public ExamAreaDao examAreaDao;
	
	public void testGetExamArea(){
		docTestUtility.setUp();
		String id = (new Long(System.currentTimeMillis())).toString();
		try {
			folderDao.buildSystemStructure(DocConstants.EXAMAREA, id , "testName", false);
		} catch (ResourceAlreadyExistsException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		try {
			ExamArea ea = examAreaDao.getExamArea("/"+DocConstants.EXAMAREA+"/"+id);
		} catch (PathNotFoundException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		try {
			ExamArea ea2 = examAreaDao.getExamArea("/"+DocConstants.EXAMAREA+"/"+id+"1");
		} catch (PathNotFoundException e) {
		} catch (DocManagementException e) {
			fail();
		}
		docTestUtility.tearDown();
	}
	
	public void testSetDeadline(){
		docTestUtility.setUp();
		String id = (new Long(System.currentTimeMillis())).toString();
		try {
			folderDao.buildSystemStructure(DocConstants.EXAMAREA, id , "testName", false);
		} catch (ResourceAlreadyExistsException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		try {
			ExamArea ea = examAreaDao.getExamArea("/"+DocConstants.EXAMAREA+"/"+id);
			examAreaDao.setDeadline(new Timestamp(1111111), ea.getPath());
			ea = examAreaDao.getExamArea("/"+DocConstants.EXAMAREA+"/"+id);
			if (ea.getDeadline().getTime()!=1111111) fail();
		} catch (PathNotFoundException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-beans.xml",
			"classpath*:applicationContext-entities.xml",
			"classpath*:applicationContext-tests.xml",
			"classpath*:applicationContext-docmanagement-tests.xml",
			"classpath*:testSecurity.xml",
			"classpath*:testDataSource.xml", 
			"classpath*:mocks.xml" };
	}

	public FolderDao getFolderDao() {
		return folderDao;
	}

	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
	}

	public DocTestUtility getDocTestUtility() {
		return docTestUtility;
	}

	public void setDocTestUtility(DocTestUtility docTestUtility) {
		this.docTestUtility = docTestUtility;
	}

	public ExamAreaDao getExamAreaDao() {
		return examAreaDao;
	}

	public void setExamAreaDao(ExamAreaDao examAreaDao) {
		this.examAreaDao = examAreaDao;
	}
}