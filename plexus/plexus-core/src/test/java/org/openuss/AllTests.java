package org.openuss;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openuss.aop.AopTests;
import org.openuss.braincontest.BrainContestTests;
import org.openuss.calendar.CalendarTests;
import org.openuss.chat.ChatTests;
import org.openuss.commands.CommandTests;
import org.openuss.course.newsletter.CourseNewsletterTests;
import org.openuss.desktop.DesktopTests;
import org.openuss.discussion.DiscussionTests;
import org.openuss.documents.DocumentTests;
import org.openuss.groups.UserGroupTests;
import org.openuss.internationalisation.InternationalisationTests;
import org.openuss.lecture.LectureTests;
import org.openuss.messaging.MessagingTests;
import org.openuss.news.NewsTests;
import org.openuss.newsletter.NewsletterTests;
import org.openuss.registration.RegistrationTests;
import org.openuss.repository.RepositoryTests;
import org.openuss.search.SearchTests;
import org.openuss.security.SecurityTests;
import org.openuss.statistics.StatisticsTests;
import org.openuss.system.SystemTests;
import org.openuss.viewtracking.ViewTrackingTests;
import org.openuss.wiki.WikiTests;

/**
 * JUnit Testsuite for all core tests. 
 * @author Ingo Dueppe
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss");
		// $JUnit-BEGIN$
		suite.addTest(AopTests.suite());
		suite.addTest(BrainContestTests.suite());
		suite.addTest(CommandTests.suite());
		suite.addTest(DesktopTests.suite());
		suite.addTest(DiscussionTests.suite());
		suite.addTest(DocumentTests.suite());
		suite.addTest(CourseNewsletterTests.suite());
		suite.addTest(LectureTests.suite());
		suite.addTest(NewsletterTests.suite());
		suite.addTest(MessagingTests.suite());
		suite.addTest(NewsTests.suite());
		suite.addTest(RegistrationTests.suite());
		suite.addTest(RepositoryTests.suite());
		suite.addTest(SearchTests.suite());
		suite.addTest(SecurityTests.suite());
		suite.addTest(SystemTests.suite());
		suite.addTest(ViewTrackingTests.suite());
		suite.addTest(WikiTests.suite());
		suite.addTest(StatisticsTests.suite());
		suite.addTest(ChatTests.suite());
		suite.addTest(UserGroupTests.suite());
		suite.addTest(CalendarTests.suite());
		suite.addTest(InternationalisationTests.suite());
		// $JUnit-END$
		return suite;
	}

	public static void main(String[] args) {
		suite();
	}

}
