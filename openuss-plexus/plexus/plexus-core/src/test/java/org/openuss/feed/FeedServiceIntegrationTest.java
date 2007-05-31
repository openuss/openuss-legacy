// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.EnrollmentService;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.Period;
import org.openuss.lecture.Subject;
import org.openuss.news.NewsCategory;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserPreferences;
/**
 * JUnit Test for Spring Hibernate FeedService class.
 * @see org.openuss.feed.FeedService
 */
public class FeedServiceIntegrationTest extends FeedServiceIntegrationTestBase {
	
	TestUtility testUtility;
	
	NewsService newsService;
	
	SecurityService securityService;
	
	LectureService lectureService;
	
	EnrollmentService enrollmentService;
	
	private Date past = new Date(System.currentTimeMillis() - 1000000);
	private Date future = new Date(System.currentTimeMillis() + 1000000);
	
	private NewsItemInfo createNewsItem(Enrollment enrollment) {
		NewsItemInfo vo = new NewsItemInfo();
		vo.setCategory(NewsCategory.ENROLLMENT);
		vo.setPublisherIdentifier(enrollment.getId());
		vo.setPublisherName("publishername of newsitem can be 250 character long");
		vo.setTitle("titel of newsitem can be 250 character long");
		vo.setText("text of the newsitem");
		vo.setAuthor("author of the newsitem");
		vo.setPublishDate(past);
		vo.setExpireDate(future);
		return vo;	
	}
	
	@Override
	protected void onTearDownInTransaction() throws Exception {
		//testUtility.removePersistFacultyAndDefaultUser();
	}
	
	public void testEnrollmentRss(){
/*
		Faculty faculty = testUtility.createPersistFacultyWithDefaultUser();

		Subject subject = Subject.Factory.newInstance();
		subject.setDescription("subjectDescription");
		subject.setFaculty(faculty);
		subject.setName("subjectName");
		subject.setShortcut("subjectShortcut"+testUtility.unique());
		
		Period period = Period.Factory.newInstance();
		period.setDescription("periodDescription");
		period.setName("periodName");
		period.setFaculty(faculty);
				
		getLectureService().persist(subject);
		getLectureService().persist(period);
		
		commit(); 
		
		getSecurityService().createObjectIdentity(faculty, null);
		commit();
		getSecurityService().createObjectIdentity(period, faculty);
		getSecurityService().createObjectIdentity(subject, faculty);
		
		Enrollment enrollment = getLectureService().createEnrollment(subject.getId(), period.getId());
		
		commit();
		
		NewsItemInfo newsInfo = createNewsItem(enrollment);
		newsInfo.setId(enrollment.getId());		
		newsService.saveNewsItem(newsInfo);
		
		commit();
		
		String xml = getFeedService().getRssFeedForEnrollment(getEnrollmentService().getEnrollmentInfo(enrollment));
		*/		
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public EnrollmentService getEnrollmentService() {
		return enrollmentService;
	}

	public void setEnrollmentService(EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}


}