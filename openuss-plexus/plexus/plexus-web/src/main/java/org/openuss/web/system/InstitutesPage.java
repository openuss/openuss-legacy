package org.openuss.web.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.LectureService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$system$institutes", scope=Scope.REQUEST)
@View
public class InstitutesPage extends BasePage{

	private static final Logger logger = Logger.getLogger(InstitutesPage.class);

	@Property (value="#{lectureService}")
	private LectureService lectureService;
	
	private Set<InstituteInfo> changedInstitutes = new HashSet<InstituteInfo>();

	private InstituteDataProvider provider = new InstituteDataProvider();

	@Prerender
	public void prerender() {
		setSessionBean(Constants.BREADCRUMBS, null);
	}
	
	public String selectInstitute() {
		Institute institute = currentInstitute();
		setSessionBean(Constants.INSTITUTE, institute);
		return Constants.INSTITUTE_PAGE;
	}
	
	private Institute currentInstitute() {
		InstituteInfo details = provider.getRowData();
		Institute institute = Institute.Factory.newInstance();
		institute.setId(details.getId());
		return institute;
	}
	
	public void changedInstitute(ValueChangeEvent event) throws LectureException {
		InstituteInfo instituteDetails = provider.getRowData();
		logger.debug("changed state of " + instituteDetails.getName() + " from " + event.getOldValue() + " to " + event.getNewValue());
		changedInstitutes.add(instituteDetails);
	}
	
	/**
	 * Save all changed data
	 * @return outcome
	 */
	public String save() {
		for (InstituteInfo instituteDetails : changedInstitutes) {
			Institute institute = lectureService.getInstitute(instituteDetails.getId());
			institute.setEnabled(instituteDetails.getEnabled());
			lectureService.persist(institute);
			if (institute.getEnabled())
				addMessage(i18n("system_message_institute_enabled", new Object[]{ institute.getName()}));
			else
				addMessage(i18n("system_message_institute_disabled", new Object[]{ institute.getName()}));
		}
		return Constants.SUCCESS;
	}
	
	private class InstituteDataProvider extends AbstractPagedTable<InstituteInfo> {
		
		private static final long serialVersionUID = 8894509911074086603L;
		
		private DataPage<InstituteInfo> page;
		
		@Override
		public DataPage<InstituteInfo> getDataPage(int startRow, int pageSize) {
			if (page == null){
				List<InstituteInfo> institutes = new ArrayList<InstituteInfo>(lectureService.getInstitutes(false));
				sort(institutes);
				page = new DataPage<InstituteInfo>(institutes.size(), 0, institutes);
			}
			return page;
		}

	}
	
	
	/* ------------ properties ----------------- */

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public InstituteDataProvider getProvider() {
		return provider;
	}

	public void setProvider(InstituteDataProvider provider) {
		this.provider = provider;
	}

}
