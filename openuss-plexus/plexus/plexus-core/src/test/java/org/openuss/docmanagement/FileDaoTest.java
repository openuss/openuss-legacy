// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.docmanagement;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * JUnit Test for Spring Hibernate LectureService class.
 * 
 * @see org.openuss.lecture.LectureService
 * 
 * @author Ingo Düppe
 */
public class FileDaoTest extends AbstractTransactionalDataSourceSpringContextTests{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FileDaoTest.class);

	public FileDao fileDao;

	public DocTestUtility docTestUtility;
	
	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}
	

	
	public void testSetFileGetFile(){
		docTestUtility.setUp();
		long enrollmentId = System.currentTimeMillis();		
		docTestUtility.addEnrollment(enrollmentId);
		String fileName = (new Long(System.currentTimeMillis())).toString();
		byte[] a = { 1, 2, 3, 4 };		
		BigFile file = new BigFileImpl(
				new Timestamp(1), new Timestamp(2), 1, "testMessage",
				"mimeType", fileName, DocConstants.DISTRIBUTION+"/1", null, 
				1, 1, new java.io.ByteArrayInputStream(a), "testOwner");
		try {
			fileDao.setFile(file);
		} catch (ResourceAlreadyExistsException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		try {
			fileDao.setFile(file);
		} catch (ResourceAlreadyExistsException e) {			
		} catch (DocManagementException e) {
			fail();
		}
		File getFile = new FileImpl();
		try {
			getFile = fileDao.getFile(file.getPath()+"/"+file.getName());
		} catch (NotAFileException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		BigFile bf = new BigFileImpl();
		try {
			bf = fileDao.getFile(getFile);
		} catch (NotAFileException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		if (bf.getDistributionTime().getTime()!=file.getDistributionTime().getTime()) fail();
		if (bf.getLastModification().getTime()!=file.getLastModification().getTime()) fail();
		if (!bf.getName().equals(file.getName()))fail();
		if (!bf.getMessage().equals(file.getMessage())) fail();
		if (!bf.getMimeType().equals(file.getMimeType()))fail();
		if (bf.getVisibility()!=file.getVisibility())fail();		
		if (!bf.getOwner().equals(bf.getOwner())) fail();
		docTestUtility.tearDown();
	}
	
	
	public void testGetFileBig(){
		
	}
	
	public void testSetFile(){
		
	}

	public void testChangeFile(){
		
	}
	
	public void testDelFile(){
		
	}
	
	public void testRemove(){
		
	}
	
	public void testGetOwner(){
		
	}
	
	public void testGetVersions(){
		
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

	public DocTestUtility getDocTestUtility() {
		return docTestUtility;
	}

	public void setDocTestUtility(DocTestUtility docTestUtility) {
		this.docTestUtility = docTestUtility;
	}

}