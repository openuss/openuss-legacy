package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


public abstract class AbstractDepartmentRegistrationPage extends BasePage {

	@Property(value = "#{universityService}")
	protected UniversityService universityService;

	@Property(value = "#{universityInfo}")
	protected UniversityInfo universityInfo;

	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		if (universityInfo != null && universityInfo.getId()!=null) {
			universityInfo = universityService.findUniversity(universityInfo.getId());
		}
		setBean(Constants.UNIVERSITY_INFO, universityInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		if (universityInfo != null && universityInfo.getId()!=null) {
			universityInfo = universityService.findUniversity(universityInfo.getId());
		}
		setBean(Constants.UNIVERSITY_INFO, universityInfo);
		if (universityInfo==null||universityInfo.getId()==null){
			addError(i18n("university_not_found"));
			redirect(Constants.DESKTOP);
			return;
		}
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}

	public UniversityInfo getUniversityInfo() {
		return universityInfo;
	}

	public void setUniversityInfo(UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}
	
}