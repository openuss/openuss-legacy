package org.openuss.news;

import junit.framework.Test;
import junit.framework.TestSuite;

public class NewsTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.news");
		//$JUnit-BEGIN$
		suite.addTestSuite(NewsItemDaoTest.class);
		suite.addTestSuite(NewsCategoryDaoTest.class);
		suite.addTestSuite(NewsPublisherDaoTest.class);
		suite.addTestSuite(NewsServiceInputValidationTest.class);
		suite.addTestSuite(NewsServiceTest.class);
		suite.addTestSuite(NewsServiceIntegrationTest.class);
		//$JUnit-END$
		return suite;
	}

}
