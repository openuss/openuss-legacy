package org.openuss.web.lecture;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
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
import org.openuss.lecture.OrganisationService;
import org.openuss.lecture.OrganisationServiceException;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

/**
 * Members page to define members of a institute and configure group memberships
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 * @author Tianyu Wang
 */
@Bean(name = "views$secured$lecture$auth$institutemembers", scope = Scope.REQUEST)
@View
public class InstituteMembersPage extends AbstractLecturePage {

	private static final Logger logger = Logger.getLogger(InstituteMembersPage.class);

	@Property(value = "#{securityService}")
	private SecurityService securityService;

	@Property(value = "#{organisationService}")
	private OrganisationService organisationService;

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

		breadcrumbs.loadInstituteCrumbs(instituteInfo);
		breadcrumbs.addCrumb(crumb);
	}

	private InstituteSecurity getInstituteSecurity() {
		if (instituteSecurity == null) {
			logger.debug("fetching institute security informations");
			instituteSecurity = instituteService.getInstituteSecurity(instituteInfo.getId());
			sortMembers(instituteSecurity.getMembers(), members.getSortColumn(), members.isAscending());
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
						return new SelectItem(group, i18n(group.getLabel(), group.getLabel(), null));
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
		for (InstituteMember member : changedMembers) {
			try {
				instituteService.setGroupOfMember(member, instituteInfo.getId());
				addMessage(i18n("institute_auth_message_changed_member", member.getUsername()));
			} catch (Exception e) {
				addError(i18n(e.getMessage()));
			}
		}
		return Constants.SUCCESS;
	}

	public String removeMember() throws LectureException {
		logger.debug("remove member");
		InstituteMember member = members.getRowData();

		try {
			organisationService.removeMember(instituteInfo.getId(), member.getId());
		} catch (Exception iae) {
			addError(i18n("auth_message_error_removed_member"));
			return Constants.SUCCESS;
		}

		// FIXME Below code should be handled within the business layer
		
		Iterator<InstituteGroup> iter = member.getGroups().iterator();
		InstituteGroup group;
		while (iter.hasNext()) {
			group = iter.next();
			try {
				organisationService.removeUserFromGroup(member.getId(), group.getId());
			} catch (Exception e) {
				addError(i18n(e.getMessage()));
			}
		}

		addMessage(i18n("institute_auth_message_removed_member", new Object[] { 
				member.getFirstName(),
				member.getLastName(), 
				member.getUsername() }));
		if (StringUtils.equals(member.getUsername(), user.getUsername())) {
			return Constants.INSTITUTE;
		} else {
			return Constants.SUCCESS;
		}

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
		UserInfo user = securityService.getUserByName(username);
		try {
			organisationService.addMember(instituteInfo.getId(), user.getId());
			addMessage(i18n("organisation_add_member_to_institute", username));
		} catch (OrganisationServiceException e) {
			logger.debug(e.getMessage());
			addError(i18n("organisation_error_apply_member_at_institute_already_applied"));
		} catch (Exception e) {
			logger.debug(e.getMessage());
			addError(i18n("organisation_error_apply_member_at_institute"));
		}
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

	/* ------------------------------------ SORTING ------------------- */

	private void sortMembers(List<InstituteMember> members, final String column, final boolean ascending) {
		logger.debug("sorting members by " + column + " ascending " + ascending);
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
		Collections.sort(members, comparator);
	}

	/* ------------------------------------ SORTING ------------------- */

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

	public OrganisationService getOrganisationService() {
		return organisationService;
	}

	public void setOrganisationService(OrganisationService organisationService) {
		this.organisationService = organisationService;
	}
}
