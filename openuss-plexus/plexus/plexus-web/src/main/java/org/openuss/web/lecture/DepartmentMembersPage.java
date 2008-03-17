package org.openuss.web.lecture;

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
import org.openuss.web.Constants;

/**
 * Members page to define admin of a department
 * 
 * @author Tianyu Wang
 * @author Weijun Chen
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$auth$departmentMembers", scope = Scope.REQUEST)
@View
public class DepartmentMembersPage extends AbstractDepartmentPage {

	private static final Logger logger = Logger.getLogger(DepartmentMembersPage.class);

	@Property(value = "#{securityService}")
	private SecurityService securityService;

	@Property(value = "#{organisationService}")
	private OrganisationService organisationService;

	private MembersTable members = new MembersTable();

	private String username;
	

	private List<GroupItem> departmentGroups;
	

	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		getdepartmentGroups();
		
	}
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		addPageCrumb();
		username= null;
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("department_command_authorisations"));
		crumb.setHint(i18n("department_command_authorisations"));
		
		breadcrumbs.loadDepartmentCrumbs(departmentInfo);
		breadcrumbs.addCrumb(crumb);
	}

	@SuppressWarnings("unchecked")
	public List<GroupItem> getdepartmentGroups() {
	
		if (departmentGroups == null) {
			logger.debug("fetching available universtiy group informatiosn");
			departmentGroups = organisationService.findGroupsByOrganisation(departmentInfo.getId());
		}
		return departmentGroups;
	}
	



	public String removeMember() throws LectureException {

		  logger.debug("remove member");
		  
		  UserInfo member = members.getRowData(); 
		  logger.debug(member.getUsername());
		  logger.debug("removeUserFromGroup");
		  logger.debug(departmentGroups.get(0).getName());
		  
		  //remove an user from an organisation
		  try{
			  organisationService.removeMember(departmentInfo.getId(), member.getId());
			  }
		  catch(Exception e){
			  		addError(i18n("auth_message_error_removed_member"));
					return Constants.SUCCESS;
			  }
		// remove an user from all his groups  
		  try{
			  organisationService.removeUserFromGroup(member.getId(),departmentGroups.get(0).getId());
			  addMessage(i18n("department_auth_message_removed_member", member.getUsername()));
		  }
		  catch(Exception e){
				addError(i18n(e.getMessage()));
				return Constants.SUCCESS;
		  }
		  		  
		  if(! StringUtils.equals(member.getUsername(),user.getUsername()))
			  return Constants.DEPARTMENT;
		  else
			   return Constants.SUCCESS;
		
	}

	/**
	 * Lookup the user name and add the member
	 * 
	 * @param event
	 * @throws LectureException
	 */
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
	 * LocalDataModel of Universitry Members
	 */
	private class MembersTable extends AbstractPagedTable<UserInfo> {

		private static final long serialVersionUID = 449438749521068451L;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			List<UserInfo> members = organisationService.findAllMembers(departmentInfo.getId());
			sort(members);
			
			return new DataPage<UserInfo>(members.size(),0,members);
		}
	}

	/* --------------------- properties -------------------------- */

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

	public MembersTable getMembers() {
		return members;
	}

	public void setMembers(MembersTable members) {
		this.members = members;
	}


}
