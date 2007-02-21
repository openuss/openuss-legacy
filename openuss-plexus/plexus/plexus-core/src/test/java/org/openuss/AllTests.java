package org.openuss;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openuss.desktop.DesktopTests;
import org.openuss.discussion.DiscussionTests;
import org.openuss.docmanagement.DocManagementTests;
import org.openuss.lecture.LectureTests;
import org.openuss.migration.MigrationTests;
import org.openuss.news.NewsTests;
import org.openuss.repository.RepositoryTests;
import org.openuss.security.SecurityTests;
import org.openuss.system.SystemTests;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss");
		// $JUnit-BEGIN$
		suite.addTest(SystemTests.suite());
		suite.addTest(SecurityTests.suite());
		suite.addTest(RepositoryTests.suite());
		suite.addTest(NewsTests.suite());
		suite.addTest(DesktopTests.suite());
		suite.addTest(LectureTests.suite());
		suite.addTest(DiscussionTests.suite());
		suite.addTest(DocManagementTests.suite());
		suite.addTest(MigrationTests.suite());
		// $JUnit-END$
		return suite;
	}

	public static void main(String[] args) {
		suite();
	}

}
