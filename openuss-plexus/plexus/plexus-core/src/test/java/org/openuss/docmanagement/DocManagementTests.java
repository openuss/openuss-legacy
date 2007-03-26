package org.openuss.docmanagement;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DocManagementTests{
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.docmanagement");
		//$JUnit-BEGIN$
		suite.addTestSuite(CollaborationServiceIntegrationTest.class);
		suite.addTestSuite(DistributionServiceIntegrationTest.class);
		suite.addTestSuite(ExaminationServiceIntegrationTest.class);
		suite.addTestSuite(FileDaoTest.class);
		suite.addTestSuite(FolderDaoTest.class);		
		suite.addTestSuite(ExamAreaDaoTest.class);		
		//$JUnit-END$
		return suite;
	}
}