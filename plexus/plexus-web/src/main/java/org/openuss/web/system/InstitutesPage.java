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
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.DepartmentServiceException;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.InstituteServiceException;
import org.openuss.lecture.LectureException;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * 
 * @author Ingo Dueppe
 * @author Malte Stockmann
 * @author Kai Stettner
 * @author Sebastian Roekens
 */
@Bean(name="views$secured$system$institutes", scope=Scope.REQUEST)
@View
public class InstitutesPage extends BasePage{

	private static final Logger logger = Logger.getLogger(InstitutesPage.class);

	@Property (value="#{instituteService}")
	private InstituteService instituteService;
	
	@Property (value="#{departmentService}")
	private DepartmentService departmentService;
	
	private Set<InstituteInfo> changedInstitutes = new HashSet<InstituteInfo>();

	private InstituteDataProvider provider = new InstituteDataProvider();

	@Prerender
	public void prerender() {
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("admin_command_institutes"));
		newCrumb.setHint(i18n("admin_command_institutes"));
		newCrumb.setLink(PageLinks.ADMIN_INSTITUTES);
		
		breadcrumbs.loadAdministrationCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
	
	public String selectInstitute() {

		logger.debug("Starting method selectInstitute");
		InstituteInfo currentInstitute = currentInstitute();
		logger.debug("Returning to method selectInstitute");
		logger.debug(currentInstitute.getId());	
	
		setBean(Constants.INSTITUTE_INFO, currentInstitute);
		
		return Constants.INSTITUTE_PAGE;
		
		
	}

	private InstituteInfo currentInstitute() {
		logger.debug("Starting method currentInstitute");
		InstituteInfo instituteDetails = provider.getRowData();
		logger.debug(instituteDetails.getName());
		logger.debug(instituteDetails.getOwnerName());
		logger.debug(instituteDetails.getId());

		InstituteInfo newInstituteInfo = new InstituteInfo();
	
		newInstituteInfo.setId(instituteDetails.getId());
		return newInstituteInfo;
	}
	
	/**
	 * Store the selected institute into session scope and go to institute remove confirmation page.
	 * @return Outcome
	 */
	public String selectInstituteAndConfirmRemove() {
		logger.debug("Starting method selectInstituteAndConfirmRemove");
		InstituteInfo currentInstitute = currentInstitute();
		logger.debug("Returning to method selectInstituteAndConfirmRemove");
		logger.debug(currentInstitute.getId());	
		setBean(Constants.INSTITUTE_INFO, currentInstitute);
		
		return Constants.INSTITUTE_CONFIRM_REMOVE_PAGE;
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
		for (InstituteInfo instituteInfo : changedInstitutes) {
			// activate institute - perform additional error checking because 
			// depending on the activation state of the super-ordinate department 
			// the activation might fail
			if(instituteInfo.isEnabled()){
				try {
					instituteService.setInstituteStatus(instituteInfo.getId(), true);
					addMessage(i18n("system_message_institute_enabled", new Object[]{ instituteInfo.getName()}));
				} catch(InstituteServiceException ise) {
					String departmentName;
					try{
						departmentName = departmentService.findDepartment(instituteInfo.getDepartmentId()).getName();
					} catch(DepartmentServiceException dse){
						departmentName = "";
					}
					addError(i18n("message_institute_enabled_failed_department_disabled_detailed", new Object[]{ instituteInfo.getName(), departmentName }));
				}
			// disable institute
			} else {
				instituteService.setInstituteStatus(instituteInfo.getId(), false);
				addMessage(i18n("system_message_institute_disabled", new Object[]{ instituteInfo.getName() }));
			}			
		}
		return Constants.SUCCESS;
	}
	
	private class InstituteDataProvider extends AbstractPagedTable<InstituteInfo> {
		
		private static final long serialVersionUID = 8894509911074086603L;
		
		private DataPage<InstituteInfo> page;
		
		@SuppressWarnings("unchecked")
		@Override
		public DataPage<InstituteInfo> getDataPage(int startRow, int pageSize) {
			if (page == null){
				List<InstituteInfo> institutes = new ArrayList<InstituteInfo>(instituteService.findAllInstitutes(false));
				sort(institutes);
				page = new DataPage<InstituteInfo>(institutes.size(), 0, institutes);
			}
			return page;
		}

	}
	
	
	/* ------------ properties ----------------- */

	public InstituteDataProvider getProvider() {
		return provider;
	}

	public void setProvider(InstituteDataProvider provider) {
		this.provider = provider;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

}
