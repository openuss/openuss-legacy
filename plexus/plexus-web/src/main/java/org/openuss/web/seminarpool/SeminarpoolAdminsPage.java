package org.openuss.web.seminarpool;


import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
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
import org.openuss.web.Constants;

/**
 * Admins page to define admin of a seminarpool
 * 
 * @author PS-Seminarplatzvergabe
 */
@Bean(name = "views$secured$seminarpool$seminarpooladmins", scope = Scope.REQUEST)
@View
public class SeminarpoolAdminsPage extends AbstractSeminarpoolPage {

	private static final Logger logger = Logger.getLogger(SeminarpoolAdminsPage.class);

	private MembersTable members = new MembersTable();

	private String username;

	private List<GroupItem> seminarpoolGroups;
	

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink("");
		newCrumb.setName(i18n("seminarpool_command_options_seminarpooladmins"));
		newCrumb.setHint(i18n("seminarpool_command_options_seminarpooladmins"));		
		breadcrumbs.addCrumb(newCrumb);
		username= null;
	}
	
	public List<GroupItem> getSeminarpoolGroups() {
		if (seminarpoolGroups == null) {
			logger.debug("fetching available university group information");
			seminarpoolGroups = organisationService.findGroupsByOrganisation(seminarpoolInfo.getUniversityId());
		}
		return seminarpoolGroups;
	}
	



	public String removeMember() throws LectureException {
		  logger.debug("remove admin");
		  
		  UserInfo member = members.getRowData(); 
		  logger.debug(member.getUsername());
		  logger.debug("removeUserFromGroup");
		  logger.debug(seminarpoolGroups.get(0).getName());
		  
		  //remove an user from an organisation
		  try{
			  organisationService.removeMember(seminarpoolInfo.getId(), member.getId());
			  }
		  catch(Exception e){
			  		addError(i18n("auth_message_error_removed_member"));
					return Constants.SUCCESS;
			  }
		// remove an user from all his groups  
		  try{
			  organisationService.removeUserFromGroup(member.getId(),seminarpoolGroups.get(0).getId());
			  addMessage(i18n("department_auth_message_removed_member", member.getUsername()));
		  }
		  catch(Exception e){
				addError(i18n(e.getMessage()));
				return Constants.SUCCESS;
		  }
		  		  
		  if(! StringUtils.equals(member.getUsername(),user.getUsername()))
			  return Constants.SEMINARPOOL;
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
			logger.debug("add a admin to seminarpool");
		}
		User newadmin = null;
		try {
			logger.debug(username);
			newadmin = securityService.getUserByName(username);
			seminarpoolAdministrationService.addSeminarpoolAdmin(newadmin.getId(), seminarpoolInfo.getId());
			addMessage(i18n("department_add_member_to_department", username));		
		} catch (OrganisationServiceException e) {
			logger.debug(e.getMessage());
			addError(i18n("organisation_error_apply_member_at_department_already_applied"));
		} catch (Exception e){
			logger.debug(e.getMessage());
			addError(i18n("organisation_error_apply_member_at_department"));
		}
		if(newadmin != null) {
			try {
				DesktopInfo desktopInfo = desktopService2.findDesktopByUser(newadmin.getId());
				desktopService2.linkSeminarpool(desktopInfo.getId(), seminarpoolInfo.getId());
			} catch(Exception e) {
				addError(i18n(e.getMessage()));
			}
		}
	}

	/**
	 * Show profile of the selected member
	 * 
	 * @return outcome
	 */
	public String showProfile() {
		UserInfo member = members.getRowData();
		User user = securityService.getUser(member.getId());
		setSessionBean(Constants.SHOW_USER_PROFILE, user);

		return Constants.USER_PROFILE_VIEW_PAGE;
	}


	/**
	 * LocalDataModel of University Members
	 */
	private class MembersTable extends AbstractPagedTable<UserInfo> {
		private static final long serialVersionUID = 449438749521068451L;

		@Override
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			List<UserInfo> members = organisationService.findAllMembers(seminarpoolInfo.getUniversityId());
			sort(members);
			
			return new DataPage<UserInfo>(members.size(),0,members);
		}
	}


	public MembersTable getMembers() {
		return members;
	}

	public void setMembers(MembersTable members) {
		this.members = members;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSeminarpoolGroups(List<GroupItem> seminarpoolGroups) {
		this.seminarpoolGroups = seminarpoolGroups;
	}
}