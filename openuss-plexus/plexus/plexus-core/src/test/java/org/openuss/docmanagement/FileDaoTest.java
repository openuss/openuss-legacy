// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.docmanagement;

import java.sql.Timestamp;

import javax.jcr.PathNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

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
		BigFile file = startUp();
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

	private BigFile startUp() {
		docTestUtility.setUp();
		long enrollmentId = System.currentTimeMillis();		
		String enrollment = (new Long(enrollmentId)).toString();
		docTestUtility.addEnrollment(enrollmentId);
		String fileName = (new Long(System.currentTimeMillis())).toString();
		byte[] a = { 1, 2, 3, 4 };		
		BigFile file = new BigFileImpl(
				new Timestamp(1), new Timestamp(2), 1, "testMessage",
				"mimeType", fileName, DocConstants.DISTRIBUTION+"/"+enrollment, null, 
				1, 1, new java.io.ByteArrayInputStream(a), "testOwner");
		return file;
	}
	
	
	public void testSetSystemFile(){		
			docTestUtility.setUp();
			long enrollmentId = System.currentTimeMillis();	
			String enrollment = (new Long(enrollmentId)).toString();			
			docTestUtility.addEnrollment(enrollmentId);
			String fileName = (new Long(System.currentTimeMillis())).toString();
			byte[] a = { 1, 2, 3, 4 };		
			BigFile file = new BigFileImpl(
					new Timestamp(1), new Timestamp(2), 1, "testMessage",
					"mimeType", fileName, DocConstants.DISTRIBUTION, null, 
					1, 1, new java.io.ByteArrayInputStream(a), "testOwner");
			try {
				fileDao.setFile(file);
			} catch (SystemFolderException e) {				
			} catch (DocManagementException e) {
				fail();
			}
			file.setPath(DocConstants.DISTRIBUTION+"/"+enrollment+"/"+DocConstants.TRASH_NAME);
			try {
				fileDao.setFile(file);
			} catch (SystemFolderException e) {				
			} catch (DocManagementException e) {
				fail();
			}
			file.setPath(DocConstants.DISTRIBUTION+"/"+enrollment+"/"+DocConstants.TRASH_NAME+"/xyz");
			try {
				fileDao.setFile(file);
			} catch (SystemFolderException e) {				
			} catch (DocManagementException e) {
				fail();
			}
	}
	

	public void testGetOwner(){
		BigFile file = startUp();
		try {
			fileDao.setFile(file);
		} catch (SystemFolderException e) {				
		} catch (DocManagementException e) {
			fail();
		}		
		file.setPath(file.getPath()+"/"+file.getName());
		try {
			if (!file.getOwner().equals(fileDao.getOwner(file))) fail();
		} catch (PathNotFoundException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		
	}
	
	public void testDelete(){
		docTestUtility.setUp();
		long enrollmentId = System.currentTimeMillis();	
		String enrollment = (new Long(enrollmentId)).toString();			
		docTestUtility.addEnrollment(enrollmentId);
		String fileName = (new Long(System.currentTimeMillis())).toString();
		byte[] a = { 1, 2, 3, 4 };		
		BigFile file = new BigFileImpl(
				new Timestamp(1), new Timestamp(2), 1, "testMessage",
				"mimeType", fileName, DocConstants.DISTRIBUTION+"/"+enrollment, null, 
				1, 1, new java.io.ByteArrayInputStream(a), "testOwner");
		try {
			fileDao.setFile(file);
		} catch (SystemFolderException e) {				
		} catch (DocManagementException e) {
			fail();
		}
		file.setPath(file.getPath()+"/"+file.getName());
		try {
			fileDao.delFile(file, true);
		} catch (NotAFileException e) {
			fail();
		} catch (ResourceAlreadyExistsException e) {
			fail();
		} catch (SystemFolderException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		try {
			File file2 = fileDao.getFile(file);
			fail();
		} catch (DocManagementException e) {			
		}
		file.setPath(DocConstants.DISTRIBUTION+"/"+enrollment+"/"+DocConstants.TRASH_NAME+"/"+file.getName());
		try {
			File file3 = fileDao.getFile(file);
		} catch (DocManagementException e) {
			fail();
		}
		docTestUtility.tearDown();
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