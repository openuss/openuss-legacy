package org.openuss.web.servlets.feedservlets;

import java.text.DateFormat;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.LectureService;
import org.openuss.web.feeds.FacultyFeed;
import org.openuss.web.feeds.FeedWrapper;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class FacultyFeedController extends AbstractFeedServlet implements Controller{

	private static Logger logger = Logger.getLogger(FacultyFeedController.class);
	
	private FacultyFeed facultyFeed;
	
	private LectureService lectureService;
	
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Long facultyId = Long.parseLong(req.getParameter("faculty"));
		String modifiedSince = req.getParameter(IF_MODIFIED_SINCE);
		
		if (facultyId!=null) {
			Faculty faculty = getLectureService().getFaculty(facultyId);
			if (faculty == null){
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
			FeedWrapper  feedWrapper = facultyFeed.getFeed(facultyId);
			if (feedWrapper==null){
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
			
			if (modifiedSince!=null&&modifiedSince!=""){
				try {
					if (DateFormat.getDateTimeInstance().parse(modifiedSince).getTime()<feedWrapper.getLastModified().getTime()){
						res.sendError(HttpServletResponse.SC_NOT_MODIFIED);
						return null;
					}
				} catch (ParseException e) {
					logger.debug("Malformed header information");
				}
				String lastModified = DateFormatUtils.format(feedWrapper.getLastModified(), DATE_FORMAT);
				res.setHeader(LAST_MODIFIED, lastModified);
			}
			res.setContentType(APPLICATION_RSS_XML);
			res.getWriter().write(feedWrapper.getWriter().toString());
			return null;
		}
		res.sendError(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

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

}