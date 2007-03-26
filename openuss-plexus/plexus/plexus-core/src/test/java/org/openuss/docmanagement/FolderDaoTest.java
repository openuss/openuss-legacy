// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.docmanagement;

import org.apache.log4j.Logger;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class FolderDaoTest extends AbstractTransactionalDataSourceSpringContextTests{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FolderDaoTest.class);
	
	public DocTestUtility docTestUtility;
	
	public FolderDao folderDao;
	
	public void testBuildSystemStructureGetFolder(){
		docTestUtility.setUp();
		String id = (new Long(System.currentTimeMillis())).toString();
		try {
			folderDao.buildSystemStructure(DocConstants.DISTRIBUTION, id, "testMessage", true);
		} catch (ResourceAlreadyExistsException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		try {
			folderDao.buildSystemStructure(DocConstants.DISTRIBUTION, id, "testMessage", true);
		} catch (ResourceAlreadyExistsException e) {
		} catch (DocManagementException e) {
			fail();
		}
		try {
			Folder folder = folderDao.getFolder(DocConstants.DISTRIBUTION+"/"+id);
			if (!folder.getMessage().equals("testMessage")) fail();
			if (!folder.getName().equals(id)) fail();
			if (!folder.getPath().equals("/"+DocConstants.DISTRIBUTION + "/" + id)) fail();
		} catch (PathNotFoundException e) {
			fail();
		} catch (NotAFolderException e) {
			fail();
		} catch (NotAFileException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		docTestUtility.tearDown();
	}
	public void testGetFolder(){
		try {
			Folder folder = folderDao.getFolder(null);
		} catch (PathNotFoundException e) {
			fail();
		} catch (NotAFolderException e) {
		} catch (NotAFileException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		try {
			Folder folder2 = folderDao.getFolder("YX");
		} catch (PathNotFoundException e) {
		} catch (NotAFolderException e) {
			fail();
		} catch (NotAFileException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}

		docTestUtility.tearDown();
	}

	public void testSetFolderGetFolder(){
		docTestUtility.setUp();
		String folderName = (new Long(System.currentTimeMillis())).toString();		
		String id = (new Long(System.currentTimeMillis())).toString();
		Folder folder = new FolderImpl("testMessage", folderName, DocConstants.DISTRIBUTION+"/"+id,null, 1);
		try {
			folderDao.buildSystemStructure(DocConstants.DISTRIBUTION, id, "testMessage", true);
			folderDao.setFolder(folder);
		} catch (SystemFolderException e) {
			fail();
		} catch (ResourceAlreadyExistsException e) {
			fail();
		} catch (DocManagementException e) {
			fail();
		}
		try {
			folderDao.setFolder(folder);
		} catch (SystemFolderException e) {
			fail();
		} catch (ResourceAlreadyExistsException e) {
		} catch (DocManagementException e) {
			fail();
		}
		folder.setPath("/"+folder.getPath()+"/"+folder.getName());
		try {
			Folder folder2 = folderDao.getFolder(folder.getPath());
			if (!folder.getMessage().equals(folder2.getMessage())) fail();
			if (!folder.getName().equals(folder2.getName())) fail();
			if (!folder.getPath().equals(folder2.getPath())) fail();			
		} catch (PathNotFoundException e) {
			fail();
		} catch (NotAFolderException e) {
			fail();
		} catch (NotAFileException e) {
			fail();
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

	public FolderDao getFolderDao() {
		return folderDao;
	}

	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
	}
	
	
}
