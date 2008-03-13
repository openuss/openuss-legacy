package org.openuss.web.seminarpool;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.OrganisationService;
import org.openuss.security.GroupItem;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Admins page to define admin of a seminarpool
 * 
 * @author PS-Seminarplatzvergabe
 */
@Bean(name = "views$secured$seminarpool$seminarpoolparticipants", scope = Scope.REQUEST)
@View
public class SeminarpoolParticipantsPage extends BasePage {

	private static final Logger logger = Logger.getLogger(SeminarpoolParticipantsPage.class);

	@Property(value = "#{securityService}")
	private SecurityService securityService;

	@Property(value = "#{organisationService}")
	private OrganisationService organisationService;
	
	@Property(value = "#{seminarpoolInfo}")
	protected SeminarpoolInfo seminarpoolInfo;
	
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService;

	private MembersTable members = new MembersTable();

	private String username;
	

	private List<GroupItem> seminarpoolGroups;
	

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
//FIXME		getdepartmentGroups();
		
	}
	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing seminarpool session object");
		refreshSeminarpool();
		if (seminarpoolInfo == null || seminarpoolInfo.getId() == null) {
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
		}
		addPageCrumb();
		username= null;
	}

	private void refreshSeminarpool() {
		logger.debug("Starting method refresh seminarpool");
		if (seminarpoolInfo != null && seminarpoolInfo.getId() != null) {
				seminarpoolInfo = seminarpoolAdministrationService.findSeminarpool(seminarpoolInfo.getId());
				setSessionBean(Constants.SEMINARPOOL_INFO, seminarpoolInfo);
		}
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_participants"));
		crumb.setHint(i18n("seminarpool_participants"));
		
		breadcrumbs.loadSeminarpoolCrumbs(seminarpoolInfo);
		breadcrumbs.addCrumb(crumb);
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
		  		  
		  if(! StringUtils.equals(member.getUsername(),user.getUsername())){
			  return Constants.SEMINARPOOL;
		  }
		  else{
			   return Constants.SUCCESS;
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
			List<UserInfo> members = seminarpoolAdministrationService.getRegistrationsAsUserInfo(seminarpoolInfo.getId());
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
