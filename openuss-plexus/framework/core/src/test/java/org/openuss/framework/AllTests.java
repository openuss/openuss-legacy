package org.openuss.framework;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openuss.framework.web.jsf.pages.PageTest;
import org.openuss.framework.web.jsf.pages.PagesTest;
import org.openuss.framework.web.jsf.pages.ParameterTest;
import org.openuss.framework.web.jsf.util.FacesUtilsTest;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.framework.web.jsf.pages");
		//$JUnit-BEGIN$
		suite.addTestSuite(ParameterTest.class);
		suite.addTestSuite(PagesTest.class);
		suite.addTestSuite(FacesUtilsTest.class);
		suite.addTestSuite(PageTest.class);
		//$JUnit-END$
		return suite;
	}

}
