package org.openuss.web.seminarpool;


import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.OrganisationServiceException;
import org.openuss.security.GroupItem;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.seminarpool.ConditionType;
import org.openuss.seminarpool.SeminarConditionInfo;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.web.Constants;

/**
 * Admins page to specify the seminarpool requirements
 * 
 * @author PS-Seminarplatzvergabe
 */
@Bean(name = "views$secured$seminarpool$conditions$seminarpoolrequirements", scope = Scope.REQUEST)
@View
public class SeminarpoolRequirementsPage extends AbstractSeminarpoolPage {

	private static final long serialVersionUID = -202776319652385870L;
	private static final Logger logger = Logger.getLogger(SeminarpoolRequirementsPage.class);
	
	@Property(value = "#{seminarConditionInfo}")
	protected SeminarConditionInfo seminarConditionInfo;
	
	@Property(value="#{"+ Constants.SEMINARPOOL_CONDITIONS_LIST + "}")
	private List<SeminarConditionInfo> conditionsList;

	private ConditionsTable conditionsTable = new ConditionsTable();

	
	/**
	 * Refreshing seminarpool and condition entity
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing seminarpool session object");
		if (seminarpoolInfo != null) {
			if(seminarpoolInfo.getId() != null) {
				seminarpoolInfo = seminarpoolAdministrationService.findSeminarpool(seminarpoolInfo.getId());
			}
		} else {
			seminarpoolInfo = (SeminarpoolInfo) getSessionBean(Constants.SEMINARPOOL_INFO);
		}
		if(seminarConditionInfo != null) {
			seminarConditionInfo = (SeminarConditionInfo) getSessionBean(Constants.SEMINARCONDITION_INFO);
		}
		setSessionBean(Constants.SEMINARPOOL_INFO, seminarpoolInfo);
		setSessionBean(Constants.SEMINARCONDITION_INFO, seminarConditionInfo);
	}
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink("");
		newCrumb.setName(i18n("seminarpool_command_options_requirements"));
		newCrumb.setHint(i18n("seminarpool_command_options_requirements"));		
		breadcrumbs.addCrumb(newCrumb);
	}
	
	
	
	/**
	 * Local DataModel of seminarpool conditions
	 */
	private class ConditionsTable extends AbstractPagedTable<SeminarConditionInfo> {

		private static final long serialVersionUID = 449438749521068451L;

		@Override
		public DataPage<SeminarConditionInfo> getDataPage(int startRow, int pageSize) {
			if(conditionsList == null)
				conditionsList = seminarpoolAdministrationService.findConditionBySeminarpool(seminarpoolInfo.getId());
//FIXME 			sort(conditionsList);
			return new DataPage<SeminarConditionInfo>(conditionsList.size(), 0, conditionsList);
		}
	}
	
	
	/**
	 * add the new condition to the list
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void addCondition(ActionEvent event) throws Exception {
		logger.debug("add seminarpool condition");
		seminarConditionInfo.setSeminarpoolId(seminarpoolInfo.getId());
		seminarpoolAdministrationService.addConditionToSeminarpool(seminarConditionInfo);
		seminarConditionInfo = new SeminarConditionInfo();
		setSessionBean(Constants.SEMINARCONDITION_INFO, seminarConditionInfo);
	}
	
	public String removeCondition() {
		logger.debug("remove seminarpool condition");
		  
		SeminarConditionInfo condition = conditionsTable.getRowData(); 
		seminarpoolAdministrationService.removeConditionFromSeminarpool(condition);
		
		return "";	  
	}
	
	public String showCondition() {
		SeminarConditionInfo condition = conditionsTable.getRowData();
		setSessionBean(Constants.SEMINARCONDITION_INFO, condition);
		return "seminarpool_edit_requirement";
	}


	/**
	 * processing the selection of an entry in the conditiontype pulldown menu
	 * @param event ValueChangeEvent
	 */
	public void processConditionTypeChanged(ValueChangeEvent event) {
		Object conditionTypeGroup = event.getNewValue();
		seminarConditionInfo.setFieldType((ConditionType) conditionTypeGroup);
	}

	public SeminarConditionInfo getSeminarConditionInfo() {
		return seminarConditionInfo;
	}

	public void setSeminarConditionInfo(SeminarConditionInfo seminarConditionInfo) {
		this.seminarConditionInfo = seminarConditionInfo;
	}

	public ConditionsTable getConditionsTable() {
		return conditionsTable;
	}

	public void setConditionsTable(ConditionsTable conditionsTable) {
		this.conditionsTable = conditionsTable;
	}

	public List<SeminarConditionInfo> getConditionsList() {
		return conditionsList;
	}

	public void setConditionsList(List<SeminarConditionInfo> conditionsList) {
		this.conditionsList = conditionsList;
	}

}
