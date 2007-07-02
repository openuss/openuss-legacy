package org.openuss.web.servlets.feedservlets;

import org.openuss.lecture.LectureService;
import org.openuss.web.feeds.InstituteFeed;
import org.openuss.web.feeds.FeedWrapper;
import org.springframework.web.servlet.mvc.Controller;

public class InstituteFeedController extends AbstractFeedServlet implements Controller{

	private InstituteFeed instituteFeed;
	
	private LectureService lectureService;
	
	public InstituteFeed getInstituteFeed() {
		return instituteFeed;
	}

	public void setInstituteFeed(InstituteFeed instituteFeed) {
		this.instituteFeed = instituteFeed;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	@Override
	protected FeedWrapper getFeedWrapper(Long domainId) {
		return instituteFeed.getFeed(domainId);
	}

	@Override
	protected String domainParameterName() {
		return "institute";
	}

}