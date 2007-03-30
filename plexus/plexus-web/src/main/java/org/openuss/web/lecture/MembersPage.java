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
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.FacultyGroup;
import org.openuss.lecture.FacultyMember;
import org.openuss.lecture.FacultySecurity;
import org.openuss.lecture.LectureException;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * Members page to define members of a faculty and configure group memberships
 * 
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$auth$members", scope = Scope.REQUEST)
@View
public class MembersPage extends AbstractLecturePage {

	private static final Logger logger = Logger.getLogger(MembersPage.class);

	private static final long serialVersionUID = 3577437988777775136L;

	@Property(value = "#{securityService}")
	private SecurityService securityService;
	
	private MembersTable members = new MembersTable();

	private FacultySecurity facultySecurity;

	private List facultyGroups;
	
	private transient Set<FacultyMember> changedMembers = new HashSet<FacultyMember>();

	private String username;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		// force refreshing data on Render-Response-Phase
		facultySecurity = null;
		facultyGroups = null;
	}

	private FacultySecurity getFacultySecurity() {
		if (facultySecurity == null) {
			logger.debug("fetching faculty security informations");
			facultySecurity = getLectureService().getFacultySecurity(faculty.getId());
			sortMembers(facultySecurity.getMembers(),members.getSortColumn(),members.isAscending());
		}
		return facultySecurity;
	}

	public List getFacultyGroups() {
		if (facultyGroups == null) {
			logger.debug("fetching available faculty group informatiosn");
			facultyGroups = getFacultySecurity().getGroups();
			CollectionUtils.transform(facultyGroups, new Transformer() {
				public Object transform(Object input) {
					if (input instanceof FacultyGroup) {
						FacultyGroup group = (FacultyGroup) input;
						//FIXME Add translation of grouptype here
						return new SelectItem(group, group.getLabel());
					}
					return null;
				}
			});
		}
		return facultyGroups;
	}

	public void changedGroups(ValueChangeEvent event) throws LectureException {
		FacultyMember member = members.getRowData();
		logger.debug("changed " + member.getUsername() + " from " + event.getOldValue() + " to " + event.getNewValue());
		changedMembers.add(member);
	}

	public String save() {
		for(FacultyMember member : changedMembers) {
			try {
				getLectureService().setGroupOfMember(member, faculty.getId());
				addMessage(i18n("faculty_auth_message_changed_member", member.getUsername()));
			} catch (LectureException e) {
				addError(i18n(e.getMessage()));
			}
		}
		return Constants.SUCCESS;
	}

	public String removeMember() throws LectureException {
		logger.debug("remove member");
		FacultyMember member = members.getRowData();
		lectureService.removeFacultyMember(member.getId(), faculty.getId());
		addMessage(i18n("faculty_auth_message_removed_member",new Object[]{member.getFirstName(),member.getLastName(),member.getUsername()}));
		return Constants.SUCCESS;
	}

	/**
	 * Lockup the username and add the member
	 * 
	 * @param event
	 * @throws LectureException
	 */
	public void addMember(ActionEvent event) throws LectureException {
		if (logger.isDebugEnabled()) {
			logger.debug("add a member to faculty");
		}
		User user = securityService.getUserByName(username);
		lectureService.addFacultyMember(user.getId(), faculty.getId());
		addMessage(i18n("faculty_add_member_to_faculty", username));
	}

	/**
	 * Show profile of the selected member
	 * 
	 * @return outcome
	 */
	public String showProfile() {
		FacultyMember member = members.getRowData();
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
	private DataPage<FacultyMember> fetchDataPage(int startRow, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDataPage(" + startRow + "," + pageSize + ")");
		}
		List<FacultyMember> members = getFacultySecurity().getMembers();
		return new DataPage<FacultyMember>(members.size(), 0, members);
	}

	/* ------------------------------------  SORTING ------------------- */
	
	private void sortMembers(List<FacultyMember> members, final String column, final boolean ascending) {
		logger.debug("sorting members by "+column+" ascending "+ascending);
		Comparator comparator = new Comparator<FacultyMember>() {
			public int compare(FacultyMember m1, FacultyMember m2) {
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
	 * LocalDataModel of Faculty Members
	 */
	private class MembersTable extends AbstractPagedTable<FacultyMember> {

		@Override
		public DataPage<FacultyMember> getDataPage(int startRow, int pageSize) {
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
