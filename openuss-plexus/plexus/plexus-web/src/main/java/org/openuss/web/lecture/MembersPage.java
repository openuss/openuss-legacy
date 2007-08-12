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
import org.openuss.lecture.InstituteGroup;
import org.openuss.lecture.InstituteMember;
import org.openuss.lecture.InstituteSecurity;
import org.openuss.lecture.LectureException;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * Members page to define members of a institute and configure group memberships
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$auth$members", scope = Scope.REQUEST)
@View
public class MembersPage extends AbstractLecturePage {

	private static final Logger logger = Logger.getLogger(MembersPage.class);

	@Property(value = "#{securityService}")
	private SecurityService securityService;
	
	private MembersTable members = new MembersTable();

	private InstituteSecurity instituteSecurity;

	private List<?> instituteGroups;
	
	private transient Set<InstituteMember> changedMembers = new HashSet<InstituteMember>();

	private String username;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		// force refreshing data on Render-Response-Phase
		instituteSecurity = null;
		instituteGroups = null;
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
	
	private InstituteSecurity getInstituteSecurity() {
		if (instituteSecurity == null) {
			logger.debug("fetching institute security informations");
			instituteSecurity = getLectureService().getInstituteSecurity(instituteInfo.getId());
			sortMembers(instituteSecurity.getMembers(),members.getSortColumn(),members.isAscending());
		}
		return instituteSecurity;
	}

	public List<?> getInstituteGroups() {
		if (instituteGroups == null) {
			logger.debug("fetching available institute group informatiosn");
			instituteGroups = getInstituteSecurity().getGroups();
			CollectionUtils.transform(instituteGroups, new Transformer() {
				public Object transform(Object input) {
					if (input instanceof InstituteGroup) {
						InstituteGroup group = (InstituteGroup) input;
						return new SelectItem(group, i18n(group.getLabel(),group.getLabel(),null));
					}
					return null;
				}
			});
		}
		return instituteGroups;
	}

	public void changedGroups(ValueChangeEvent event) throws LectureException {
		InstituteMember member = members.getRowData();
		logger.debug("changed " + member.getUsername() + " from " + event.getOldValue() + " to " + event.getNewValue());
		changedMembers.add(member);
	}

	public String save() {
		for(InstituteMember member : changedMembers) {
			try {
				getLectureService().setGroupOfMember(member, instituteInfo.getId());
				addMessage(i18n("institute_auth_message_changed_member", member.getUsername()));
			} catch (LectureException e) {
				addError(i18n(e.getMessage()));
			}
		}
		return Constants.SUCCESS;
	}

	public String removeMember() throws LectureException {
		logger.debug("remove member");
		InstituteMember member = members.getRowData();
		lectureService.removeInstituteMember(member.getId(), instituteInfo.getId());
		addMessage(i18n("institute_auth_message_removed_member",new Object[]{member.getFirstName(),member.getLastName(),member.getUsername()}));
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
			logger.debug("add a member to institute");
		}
		User user = securityService.getUserByName(username);
		lectureService.addInstituteMember(user.getId(), instituteInfo.getId());
		addMessage(i18n("institute_add_member_to_institute", username));
	}

	/**
	 * Show profile of the selected member
	 * 
	 * @return outcome
	 */
	public String showProfile() {
		InstituteMember member = members.getRowData();
		User user = securityService.getUser(member.getId());
		setSessionBean(Constants.SHOW_USER_PROFILE, user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	/**
	 * @param startRow
	 *            row to start from
	 * @param pageSize
	 *            number of rows on each page
	 * @return
	 */
	private DataPage<InstituteMember> fetchDataPage(int startRow, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDataPage(" + startRow + "," + pageSize + ")");
		}
		List<InstituteMember> members = getInstituteSecurity().getMembers();
		return new DataPage<InstituteMember>(members.size(), 0, members);
	}

	/* ------------------------------------  SORTING ------------------- */
	
	private void sortMembers(List<InstituteMember> members, final String column, final boolean ascending) {
		logger.debug("sorting members by "+column+" ascending "+ascending);
		Comparator<InstituteMember> comparator = new Comparator<InstituteMember>() {
			public int compare(InstituteMember m1, InstituteMember m2) {
				if (column == null) {
					return 0;
				} else if (column.equals("username")) {
					return ascending ? m1.getUsername().compareTo(m2.getUsername()) : m2.getUsername().compareTo(
							m1.getUsername());
				} else if (column.equals("firstname")) {
					return ascending ? m1.getFirstName().compareTo(m2.getFirstName()) : m2.getFirstName().compareTo(
							m1.getFirstName());
				} else if (column.equals("lastname")) {
					return ascending ? m1.getLastName().compareTo(m2.getLastName()) : m2.getLastName().compareTo(
							m1.getLastName());
				}
				return 0;
			}
		};
		Collections.sort(members,comparator);
	}
	/* ------------------------------------  SORTING ------------------- */

	/**
	 * LocalDataModel of Institute Members
	 */
	private class MembersTable extends AbstractPagedTable<InstituteMember> {

		private static final long serialVersionUID = 449438749521068451L;

		@Override
		public DataPage<InstituteMember> getDataPage(int startRow, int pageSize) {
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

	public MembersTable getMembers() {
		return members;
	}

	public void setMembers(MembersTable members) {
		this.members = members;
	}
}
