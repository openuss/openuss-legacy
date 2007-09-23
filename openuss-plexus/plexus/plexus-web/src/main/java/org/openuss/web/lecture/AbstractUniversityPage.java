package org.openuss.web.lecture;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.lecture.UniversityServiceException;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract Organisation Page
 * 
 * @author Tianyu Wang
 * @author Weijun Chen
 * @author Kai Stettner
 */
public abstract class AbstractUniversityPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractUniversityPage.class);

	@Property(value = "#{universityInfo}")
	protected UniversityInfo universityInfo;

	@Property(value = "#{universityService}")
	protected UniversityService universityService;

	/**
	 * Refreshing universityInfo
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing university session object");
		if (universityInfo != null) {
			universityInfo = universityService.findUniversity(universityInfo.getId());
		} else {
			universityInfo = (UniversityInfo) getSessionBean(Constants.UNIVERSITY_INFO);
		}
		setSessionBean(Constants.UNIVERSITY_INFO, universityInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		try {
			logger.debug("prerender - refreshing university session object");
			refreshUniversity();
			if (universityInfo == null) {
				addError(i18n("message_error_no_university_selected"));
				redirect(Constants.DESKTOP);
			} 
		} catch (Exception e) {
			logger.debug(e.getStackTrace());
			universityInfo = null;
			addError(i18n("university_not_found"));
			redirect(Constants.HOME);
		}
		
	}

	private void refreshUniversity() {
		logger.debug("Starting method refresh university");
		if (universityInfo != null) {
			logger.debug(universityInfo.getId());
			universityInfo = universityService.findUniversity(universityInfo.getId());
			logger.debug(universityInfo.getId());
			setSessionBean(Constants.UNIVERSITY_INFO, universityInfo);
		}
	}

	public UniversityInfo getUniversityInfo() {
		return universityInfo;
	}

	public void setUniversityInfo(UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}
	


}
