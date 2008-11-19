package org.openuss.documents;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DocumentTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.documents");
		//$JUnit-BEGIN$
		suite.addTestSuite(DocumentServiceIntegrationTest.class);
		suite.addTestSuite(DocumentServiceIntegrationNonTransactionalTest.class);
		suite.addTestSuite(FileEntryDaoTest.class);
		suite.addTestSuite(FolderDaoTest.class);
		suite.addTestSuite(FolderEntryDaoTest.class);
		suite.addTestSuite(FolderTest.class);
		//$JUnit-END$
		return suite;
	}

}
