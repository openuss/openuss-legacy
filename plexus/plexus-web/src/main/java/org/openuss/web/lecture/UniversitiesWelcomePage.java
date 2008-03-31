package org.openuss.web.lecture;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.OrganisationService;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Backing bean for the views universities.xhtml and universitiestable.xhtml in
 * the welcome page.
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$public$university$universitiestablewelcome", scope = Scope.REQUEST)
@View
public class UniversitiesWelcomePage extends BasePage {

	private static final Logger logger = Logger.getLogger(UniversitiesWelcomePage.class);

	private static final long serialVersionUID = 5069930000478432045L;

	private UniversityTableWelcome universitiesWelcome = new UniversityTableWelcome();

	@Property(value = "#{universityService}")
	private UniversityService universityService;

	@Property(value = "#{universityInfo}")
	private UniversityInfo universityInfo;
	
	@Property(value = "#{organisationService}")
	private OrganisationService organisationService;

	@Prerender
	public void prerender() throws Exception {
		breadcrumbs.clear();
		if (universityInfo!=null && universityInfo.getId()!=null){
			universityInfo = universityService.findUniversity(universityInfo.getId());
		}
	}

	/**
	 * Store the selected university into session scope and go to university
	 * main page.
	 * 
	 * @return Outcome
	 */
	public String selectUniversity() {
		logger.debug("Starting method selectUniversity");
		UniversityInfo currentUniversity = currentUniversity();
		logger.debug("Returning to method selectUniversity");
		logger.debug(currentUniversity.getId());
		setBean(Constants.UNIVERSITY_INFO, currentUniversity);

		return Constants.UNIVERSITY_PAGE;
	}

	/**
	 * Store the selected university into session scope and go to university
	 * remove confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectUniversityAndConfirmRemove() {
		logger.debug("Starting method selectUniversityAndConfirmRemove");
		universityInfo = currentUniversity();
		logger.debug("Returning to method selectUniversityAndConfirmRemove");
		setBean(Constants.UNIVERSITY_INFO, universityInfo);

		return Constants.UNIVERSITY_CONFIRM_REMOVE_PAGE;
	}

	/**
	 * Store the selected university into session scope and go to university
	 * disable confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectUniversityAndConfirmDisable() {
		logger.debug("Starting method selectUniversityAndConfirmDisable");
		universityInfo = currentUniversity();
		logger.debug("Returning to method selectUniversityAndConfirmDisable");
		setBean(Constants.UNIVERSITY_INFO, universityInfo);

		return Constants.UNIVERSITY_CONFIRM_DISABLE_PAGE;
	}

	/**
	 * Enables the chosen university. This is just evident for the search
	 * indexing.
	 * 
	 * @return Outcome
	 */
	public String enableUniversity() {
		logger.debug("Starting method enableUniversity");
		universityService.setUniversityStatus(currentUniversity().getId(), true);
		
		addMessage(i18n("message_university_enabled"));
		return Constants.SUCCESS;
	}

	/**
	 * Bookmarks the chosen university and therefore sets a link on the MyUni
	 * Page for the university.
	 * 
	 * @return Outcome
	 */
	public String shortcutUniversity() throws DesktopException {
		logger.debug("Starting method shortcutUniversity");
		if (desktopInfo==null){
			refreshDesktop();
		}
		desktopService2.linkUniversity(desktopInfo.getId(), currentUniversity().getId());

		addMessage(i18n("message_university_shortcut_created"));
		return Constants.SUCCESS;
	}

	private UniversityInfo currentUniversity() {
		logger.debug("Starting method currentUniversity");
		UniversityInfo universityDetails = universitiesWelcome.getRowData();
		UniversityInfo newUniversityInfo = new UniversityInfo();
		newUniversityInfo.setId(universityDetails.getId());
		logger.debug(newUniversityInfo.getId());
		return newUniversityInfo;
	}

	public UniversityTableWelcome getUniversitiesWelcome() {
		return universitiesWelcome;
	}

	public void setUniversitiesWelcome(UniversityTableWelcome universitiesWelcome) {
		this.universitiesWelcome = universitiesWelcome;
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}

	public OrganisationService getOrganisationService() {
		return organisationService;
	}

	public void setOrganisationService(OrganisationService organisationService) {
		this.organisationService = organisationService;
	}

	public String confirmRemoveUniversity() {
		UniversityInfo universityInfo = currentUniversity();
		setBean(Constants.UNIVERSITY_INFO, universityInfo);
		return "removed";
	}

	private class UniversityTableWelcome extends AbstractPagedTable<UniversityInfo> {

		private static final long serialVersionUID = -6072435481349000879L;
		
		private DataPage<UniversityInfo> page; 

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<UniversityInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetch universities data page at " + startRow + ", " + pageSize + " sorted by "
							+ universitiesWelcome.getSortColumn());
				}
				List<UniversityInfo> universities = getUniversityService().findUniversitiesByEnabled(true);
				sort(universities);
				page = new DataPage<UniversityInfo>(universities.size(), 0, universities);
			}
			return page;
		}
		
	}

	public UniversityInfo getUniversityInfo() {
		return universityInfo;
	}

	public void setUniversityInfo(UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}

}