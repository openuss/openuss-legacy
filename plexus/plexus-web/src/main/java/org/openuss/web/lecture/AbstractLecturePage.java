package org.openuss.web.lecture;


import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.Institute;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.CourseType;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * Abstract Lecture Page
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 */
public abstract class AbstractLecturePage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLecturePage.class);

	@Property(value = "#{institute}")
	protected Institute institute;
	
	@Property(value = "#{lectureService}")
	protected LectureService lectureService;

	@Property(value = "#{sessionScope.courseType}")
	protected CourseType courseType;
	
	@Property(value = "#{crumbs}")
	protected ArrayList<BreadCrumb> crumbs;

	@Property(value = "#{systemService}")
	protected SystemService systemService;
	
	/**
	 * Refreshing institute entity 
	 * @throws Exception 
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing institute session object");
		if (institute != null) {
			institute = lectureService.getInstitute(institute.getId());
		} else {
			institute = (Institute) getSessionBean(Constants.INSTITUTE);
		}
		setSessionBean(Constants.INSTITUTE, institute);
	}
	
	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing institute session object");
		setCrumbs(null);
		if (institute == null) {
			institute = (Institute) getSessionBean(Constants.INSTITUTE);
		} 
		if (institute == null) {
			addError(i18n("message_error_no_institute_selected"));
			redirect(Constants.DESKTOP);
		} else {
			institute = lectureService.getInstitute(institute.getId());
			setSessionBean(Constants.INSTITUTE, institute);
		}
		generateCrumbs();
	}

	private void generateCrumbs(){
		crumbs = new ArrayList<BreadCrumb>();
		BreadCrumb instituteCrumb = new BreadCrumb();
		
		instituteCrumb.setName(institute.getShortcut());
		instituteCrumb.setLink(getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+PageLinks.INSTITUTE_PAGE+"?institute="+institute.getId());
		instituteCrumb.setHint(institute.getName());
		
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}
	
	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public CourseType getCourseType() {
		return courseType;
	}

	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}

	public ArrayList getCrumbs() {
		return crumbs;
	}

	public void setCrumbs(ArrayList<BreadCrumb> crumbs) {
		this.crumbs = crumbs;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
}
