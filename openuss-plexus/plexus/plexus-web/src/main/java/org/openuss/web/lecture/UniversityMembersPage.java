package org.openuss.web.lecture;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.InstituteMember;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.OrganisationService;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * Members page to define admin of a university 
 * 
 * @author Tianyu Wang	
 * @author Weijun Chen
 */
@Bean(name = "views$secured$lecture$auth$universitymembers", scope = Scope.REQUEST)
@View
public class UniversityMembersPage extends AbstractUniversityPage{

	private static final Logger logger = Logger.getLogger(MembersPage.class);

	@Property(value = "#{securityService}")
	private SecurityService securityService;
	
	@Property(value = "#{organisationService}")
	private OrganisationService organisationService;
	
	
	private MembersTable members = new MembersTable();




	
	
	private String username;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("institute_command_members"));
		crumb.setHint(i18n("institute_command_members"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}
	

	public String removeMember() throws LectureException {
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("remove a member to university");
		}
		User member = members.getRowData();
		User user = securityService.getUser(member.getId());
		organisationService.removeUserFromGroup(universityInfo.getId(),1L);
		organisationService.removeMember(universityInfo.getId(),user.getId());
		return Constants.SUCCESS;
		*/
	}

	/**
	 * Lookup the user name and add the member
	 * 
	 * @param event
	 * @throws LectureException
	 */
	public void addMember(ActionEvent event) throws LectureException {
		/*
		if (logger.isDebugEnabled()) {
			logger.debug("add a member to university");
		}
		logger.debug(username);
		User user = securityService.getUserByName(username);
		logger.debug(universityInfo.getId());
		logger.debug(user.getId());
		organisationService.addMember(universityInfo.getId(),user.getId());
		organisationService.addUserToGroup(universityInfo.getId(),1L);
		*/
	}

	/**
	 * Show profile of the selected member
	 * 
	 * @return outcome
	 */
	public String showProfile() {
		/*
		User member = members.getRowData();
		User user = securityService.getUser(member.getId());
		setSessionBean(Constants.SHOW_USER_PROFILE, user);
		*/
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	/**
	 * @param startRow
	 *            row to start from
	 * @param pageSize
	 *            number of rows on each page
	 * @return
	 */
	private DataPage<User> fetchDataPage(int startRow, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDataPage(" + startRow + "," + pageSize + ")");
		}
		List<User> members = organisationService.findAllMembers(universityInfo.getId());
		return new DataPage<User>(members.size(), 0, members);
	}

	/**
	 * LocalDataModel of Institute Members
	 */
	private class MembersTable extends AbstractPagedTable<User> {

		private static final long serialVersionUID = 449438749521068451L;

		@Override
		public DataPage<User> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
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
