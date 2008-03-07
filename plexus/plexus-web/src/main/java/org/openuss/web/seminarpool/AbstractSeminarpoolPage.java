package org.openuss.web.seminarpool;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.OrganisationService;
import org.openuss.security.SecurityService;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

public abstract class AbstractSeminarpoolPage extends BasePage {

	private static final Logger logger = Logger.getLogger(SeminarpoolParticipantsPage.class);

	@Property(value = "#{securityService}")
	protected SecurityService securityService;

	@Property(value = "#{organisationService}")
	protected OrganisationService organisationService;
	
	@Property(value = "#{seminarpoolInfo}")
	protected SeminarpoolInfo seminarpoolInfo;
	
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService;

	
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing department session object");
		if (seminarpoolInfo != null) {
			if (seminarpoolInfo.getId() != null) {
				seminarpoolInfo = seminarpoolAdministrationService.findSeminarpool(seminarpoolInfo.getId());
			} else {
				seminarpoolInfo = (SeminarpoolInfo) getSessionBean(Constants.SEMINARPOOL_INFO);
			}
		}

		setSessionBean(Constants.SEMINARPOOL_INFO, seminarpoolInfo);
	}
	@Prerender
	public void prerender() throws Exception {
		logger.debug("prerender - refreshing seminarpool session object");
		refreshSeminarpool();
		if (seminarpoolInfo == null || seminarpoolInfo.getId() == null) {
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
		}
		breadcrumbs.loadSeminarpoolCrumbs(seminarpoolInfo);
	}

	private void refreshSeminarpool() {
		logger.debug("Starting method refresh seminarpool");
		if (seminarpoolInfo != null) {
			if (seminarpoolInfo.getId() != null) {
				seminarpoolInfo = seminarpoolAdministrationService.findSeminarpool(seminarpoolInfo.getId());
				setSessionBean(Constants.SEMINARPOOL_INFO, seminarpoolInfo);
			}
		}
	}

	public SecurityService getSecurityService() {
		return securityService;
	}
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	public OrganisationService getOrganisationService() {
		return organisationService;
	}
	public void setOrganisationService(OrganisationService organisationService) {
		this.organisationService = organisationService;
	}
	public SeminarpoolInfo getSeminarpoolInfo() {
		return seminarpoolInfo;
	}
	public void setSeminarpoolInfo(SeminarpoolInfo seminarpoolInfo) {
		this.seminarpoolInfo = seminarpoolInfo;
	}
	public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
		return seminarpoolAdministrationService;
	}
	public void setSeminarpoolAdministrationService(
			SeminarpoolAdministrationService seminarpoolAdministrationService) {
		this.seminarpoolAdministrationService = seminarpoolAdministrationService;
	}

	
}
