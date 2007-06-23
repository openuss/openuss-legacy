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
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDetails;
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
	
	private Set<InstituteDetails> changedInstitutes = new HashSet<InstituteDetails>();

	private InstituteDataProvider provider = new InstituteDataProvider();

	public String selectInstitute() {
		Institute institute = currentInstitute();
		setSessionBean(Constants.INSTITUTE, institute);
		return Constants.INSTITUTE_PAGE;
	}
	
	private Institute currentInstitute() {
		InstituteDetails details = provider.getRowData();
		Institute institute = Institute.Factory.newInstance();
		institute.setId(details.getId());
		return institute;
	}
	
	public void changedInstitute(ValueChangeEvent event) throws LectureException {
		InstituteDetails instituteDetails = provider.getRowData();
		logger.debug("changed state of " + instituteDetails.getName() + " from " + event.getOldValue() + " to " + event.getNewValue());
		changedInstitutes.add(instituteDetails);
	}
	
	/**
	 * Save all changed data
	 * @return outcome
	 */
	public String save() {
		for (InstituteDetails instituteDetails : changedInstitutes) {
			Institute institute = lectureService.getInstitute(instituteDetails.getId());
			institute.setEnabled(instituteDetails.isEnabled());
			lectureService.persist(institute);
			if (institute.isEnabled())
				addMessage(i18n("system_message_institute_enabled", new Object[]{ institute.getName()}));
			else
				addMessage(i18n("system_message_institute_disabled", new Object[]{ institute.getName()}));
		}
		return Constants.SUCCESS;
	}
	
	private class InstituteDataProvider extends AbstractPagedTable<InstituteDetails> {
		
		private static final long serialVersionUID = 8894509911074086603L;
		
		private DataPage<InstituteDetails> page;
		
		@Override
		public DataPage<InstituteDetails> getDataPage(int startRow, int pageSize) {
			if (page == null){
				List<InstituteDetails> institutes = new ArrayList(lectureService.getInstitutes(false));
				sort(institutes);
				page = new DataPage(institutes.size(), 0, institutes);
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
