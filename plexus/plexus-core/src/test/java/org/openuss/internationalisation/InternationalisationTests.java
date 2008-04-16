package org.openuss.internationalisation;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Lutz D. Kramer
 */
public class InternationalisationTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.internationalisation");
		//$JUnit-BEGIN$
		suite.addTestSuite(TranslationTextDaoTest.class);
		suite.addTestSuite(LanguageDaoTest.class);
		suite.addTestSuite(TranslationServiceIntegrationTest.class);
		return suite;
	}


}
