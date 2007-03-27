package org.openuss;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openuss.desktop.DesktopTests;
import org.openuss.discussion.DiscussionTests;
import org.openuss.documents.DocumentTests;
import org.openuss.lecture.LectureTests;
import org.openuss.news.NewsTests;
import org.openuss.repository.RepositoryTests;
import org.openuss.security.SecurityTests;
import org.openuss.system.SystemTests;
import org.openuss.viewtracking.ViewTrackingTests;
import org.openuss.mail.MailTests;

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
		suite.addTest(MailTests.suite());
		suite.addTest(DocumentTests.suite());
		suite.addTest(ViewTrackingTests.suite());
		// $JUnit-END$
		return suite;
	}

	public static void main(String[] args) {
		suite();
	}

}
