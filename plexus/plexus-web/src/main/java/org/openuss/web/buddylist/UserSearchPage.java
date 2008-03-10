package org.openuss.web.buddylist;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserCriteria;
import org.openuss.security.UserInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.groups.GroupsMainPage;

@Bean(name = "views$secured$buddylist$usersearch", scope = Scope.REQUEST)
@View
public class UserSearchPage extends BasePage {

	private static final Logger logger = Logger.getLogger(GroupsMainPage.class);

	@Property(value = "#{securityService}")
	private SecurityService securityService;

	private UserDataProvider data = new UserDataProvider();
	private DataPage<UserInfo> page;
	private List<UserInfo> users;
	private String username = null;
	private String firstname = null;
	private String lastname = null;

	/* ----- private classes ----- */

	private class UserDataProvider extends AbstractPagedTable<UserInfo> {

		private static final long serialVersionUID = -5342817757466323535L;

		@Override
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				logger.debug("fetching user list");
				page = new DataPage<UserInfo>(users.size(), 0, users);
				sort(users);
			}
			return page;
		}
	}

	/* ----- business logic ----- */

	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		users = new ArrayList<UserInfo>();
	}

	public String linkProfile() {
		User profile = User.Factory.newInstance();
		profile.setId(this.data.getRowData().getId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	public String findUser() {
		getUsers(username, firstname, lastname);
		if (users.size() == 0) {
			users = null;
			page = null;
			addError(i18n("user_not_found_error"));
			return Constants.OPENUSS4US_USER_SEARCH;
		}
		if (users.size() == 1) {
			User profile = User.Factory.newInstance();
			profile.setId(users.get(0).getId());
			setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
			return Constants.USER_PROFILE_VIEW_PAGE;
		}
		if (users.size() > 1) {
			page = null;
			page = new DataPage<UserInfo>(users.size(), 0, users);
			addMessage(i18n("user_more_found_error"));
		}
		return Constants.OPENUSS4US_USER_SEARCH;
	}

	public String writeMessage() {
		return null;
	}

	private void getUsers(String uname, String fname, String lname) {
		UserCriteria criteria = new UserCriteria();
		if (uname != null && !uname.isEmpty()) {
			criteria.setUsername(uname.toLowerCase());
		}
		if (!fname.isEmpty()) {
			criteria.setFirstName(fname);
		}
		if (!lname.isEmpty()) {
			criteria.setLastName(lname);
		}
		users = securityService.getUsers(criteria);
	}

	/* ----- getter and setter ----- */

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public UserDataProvider getData() {
		return data;
	}

	public void setData(UserDataProvider data) {
		this.data = data;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public List<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}

}
