package org.openuss.web.servlets.feedservlets;

import org.openuss.lecture.LectureService;
import org.openuss.web.feeds.FacultyFeed;
import org.openuss.web.feeds.FeedWrapper;
import org.springframework.web.servlet.mvc.Controller;

public class FacultyFeedController extends AbstractFeedServlet implements Controller{

	private FacultyFeed facultyFeed;
	
	private LectureService lectureService;
	
	public FacultyFeed getFacultyFeed() {
		return facultyFeed;
	}

	public void setFacultyFeed(FacultyFeed facultyFeed) {
		this.facultyFeed = facultyFeed;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	@Override
	protected FeedWrapper getFeedWrapper(Long domainId) {
		return facultyFeed.getFeed(domainId);
	}

	@Override
	protected String domainParameterName() {
		return "faculty";
	}

}