package org.openuss.web.seminarpool;


import java.util.List;

import javax.faces.event.ActionEvent;

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
import org.openuss.lecture.OrganisationService;
import org.openuss.lecture.OrganisationServiceException;
import org.openuss.security.GroupItem;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.security.User;
import org.openuss.seminarpool.SeminarConditionInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Members page to define admin of a department
 * 
 * @author Tianyu Wang
 * @author Weijun Chen
 * @author Kai Stettner
 *//*
@Bean(name = "views$secured$seminarpool$seminarpoolConditions", scope = Scope.REQUEST)
@View 
public class SeminarpoolConditionsPage extends BasePage {

	private static final Logger logger = Logger.getLogger(SeminarpoolConditionsPage.class);

	@Property(value = "#{securityService}")
	private SecurityService securityService;

	@Property(value = "#{organisationService}")
	private OrganisationService organisationService;
	
	@Property(value = "#{seminarpoolAdministrationService}")
	private SeminarpoolAdministrationService seminarpoolAdministrationService;
	
	@Property(value = "#{seminarpoolInfo}")
	private SeminarpoolInfo	seminarpoolInfo;

	private ConditionsTable conditions = new ConditionsTable();
	private List<SeminarConditionInfo> conditionsList;
	private String username;
		

	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		getdepartmentGroups();
		
	}
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
		username= null;
		
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("department_command_authorisations"));
		crumb.setHint(i18n("department_command_authorisations"));
		
		breadcrumbs.loadSeminarpoolCrumbs(seminarpoolInfo);
		breadcrumbs.addCrumb(crumb);
	}

	public List<GroupItem> getConditionsList() {
	
		if (departmentGroups == null) {
			logger.debug("fetching available universtiy group informatiosn");
			departmentGroups = organisationService.findGroupsByOrganisation(departmentInfo.getId());
		}
		return departmentGroups;
	}
	



	public String removeCondition() throws Exception {

		  logger.debug("remove condition");
		  
		  SeminarConditionInfo condition = conditions.getRowData(); 

		  //remove a condition from a seminarpool
		  try{
			  seminarpoolAdministrationService.removeConditionFromSeminarpool(condition);
			  }
		  catch(Exception e){
			  		addError(i18n("auth_message_error_removed_member"));
					return Constants.SUCCESS;
			  }
	}

	/**
	 * Lookup the user name and add the member
	 * 
	 * @param event
	 * @throws LectureException
	 *
	public void addMember(ActionEvent event) throws LectureException {

		if (logger.isDebugEnabled()) {
			logger.debug("add a member to department");
		}
		try{
		logger.debug(username);
		user = securityService.getUserByName(username);
		logger.debug(departmentInfo.getId());
		logger.debug(user.getId());
		organisationService.addMember(departmentInfo.getId(), user.getId());
		logger.debug(departmentGroups.size());
		logger.debug(departmentGroups.get(0).getId());
		logger.debug(departmentGroups.get(0).getName());
		organisationService.addUserToGroup(user.getId(), departmentGroups.get(0).getId());
		addMessage(i18n("department_add_member_to_department", username));		
		} catch (OrganisationServiceException e) {
			logger.debug(e.getMessage());
			addError(i18n("organisation_error_apply_member_at_department_already_applied"));
		} catch (Exception e){
			logger.debug(e.getMessage());
			addError(i18n("organisation_error_apply_member_at_department"));
		} try{
			DesktopInfo desktopInfo = desktopService2.findDesktopByUser(user.getId());
			desktopService2.linkDepartment(desktopInfo.getId(), departmentInfo.getId());}
			catch(Exception e){
				addError(i18n(e.getMessage()));}

	}

	/**
	 * Show details of the selected condition
	 * 
	 * @return outcome
	 *
	public String showCondition() {
		SeminarConditionInfo condition = conditions.getRowData();
		setSessionBean(Constants.SHOW_SEMINARPOOL_CONDITION, condition);

		return Constants.SEMINARPOOL_CONDITION_VIEW_PAGE;
	}


	/**
	 * LocalDataModel of seminarpool conditions
	 *
	private class ConditionsTable extends AbstractPagedTable<SeminarConditionInfo> {

		private static final long serialVersionUID = 449438749521068451L;

		@Override
		public DataPage<SeminarConditionInfo> getDataPage(int startRow, int pageSize) {
			List<SeminarConditionInfo> conditions = seminarpoolAdministrationService.findAllMembers(departmentInfo.getId());
			sort(conditions);
			return new DataPage<SeminarConditionInfo>(conditions.size(),0,conditions);
		}
	}

	/* --------------------- properties -------------------------- *

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
		return seminarpoolAdministrationService;
	}
	public void setSeminarpoolAdministrationService(
			SeminarpoolAdministrationService seminarpoolAdministrationService) {
		this.seminarpoolAdministrationService = seminarpoolAdministrationService;
	}
	public SeminarpoolInfo getSeminarpoolInfo() {
		return seminarpoolInfo;
	}
	public void setSeminarpoolInfo(SeminarpoolInfo seminarpoolInfo) {
		this.seminarpoolInfo = seminarpoolInfo;
	}
	public ConditionsTable getConditions() {
		return conditions;
	}
	public void setConditions(ConditionsTable conditions) {
		this.conditions = conditions;
	}


}
*/