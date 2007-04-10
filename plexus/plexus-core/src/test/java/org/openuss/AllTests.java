package org.openuss;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openuss.braincontest.BrainContestTests;
import org.openuss.desktop.DesktopTests;
import org.openuss.discussion.DiscussionTests;
import org.openuss.documents.DocumentTests;
import org.openuss.lecture.LectureTests;
import org.openuss.mail.MailTests;
import org.openuss.mailinglist.MailinglistTests;
import org.openuss.news.NewsTests;
import org.openuss.repository.RepositoryTests;
import org.openuss.search.SearchTests;
import org.openuss.security.SecurityTests;
import org.openuss.system.SystemTests;
import org.openuss.viewtracking.ViewTrackingTests;

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
		suite.addTest(MailinglistTests.suite());
		suite.addTest(SearchTests.suite());
		suite.addTest(DocumentTests.suite());
		suite.addTest(ViewTrackingTests.suite());
		suite.addTest(BrainContestTests.suite());
		// $JUnit-END$
		return suite;
	}

	public static void main(String[] args) {
		suite();
	}

}
