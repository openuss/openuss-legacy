package org.openuss.web.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.security.SecurityService;
import org.openuss.security.UserCriteria;
import org.openuss.security.UserInfo;
import org.openuss.statistics.SystemStatisticInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * UserBrowser of System-Admin
 * 
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$system$userbrowser", scope=Scope.REQUEST)
@View
public class UserBrowserPage extends BasePage{

	private static final Logger logger = Logger.getLogger(UserBrowserPage.class);

	@Property (value="#{securityService}")
	private SecurityService securityService;
	
	@Property (value="#{systemStatistic}")
	private SystemStatisticInfo systemStatistic;
	
	private LocalDataModel dataModel = new LocalDataModel();
	
	private DataPage<UserInfo> dataPage;
	
	private Set<UserInfo> changedUsers = new HashSet<UserInfo>();
	

	@Prerender
	public void prerender() {
		logger.debug("prerender - refreshing users list");

		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("admin_command_users"));
		newCrumb.setLink(PageLinks.ADMIN_USERSBROWSER);
		
		breadcrumbs.loadAdministrationCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
	
	
	public void changedUserInfo(ValueChangeEvent event) throws LectureException {
		UserInfo userInfo = dataModel.getRowData();
		logger.debug("changed state of " + userInfo.getUsername() + " from " + event.getOldValue() + " to " + event.getNewValue());
		changedUsers.add(userInfo);
	}
	
	/**
	 * Save user changes
	 * @return outcome
	 */
	public String saveUsers() {
		for (UserInfo userInfo : changedUsers) {
			UserInfo user = securityService.getUser(userInfo.getId());
			user.setEnabled(userInfo.isEnabled());
			user.setAccountExpired(userInfo.isAccountExpired());
			user.setAccountLocked(userInfo.isAccountLocked());
			securityService.saveUser(user);
			addMessage(i18n("system_message_changed_user_state",user.getUsername()));
		}
		return Constants.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public DataPage<UserInfo> fetchDataPage(int startRow, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("fetchDataPage(" + startRow + "," + pageSize + ")");
		}

		if (dataPage == null || dataPage.getStartRow() != startRow || dataPage.getData().size() < pageSize) {
			logger.debug("refresh data page");
			UserCriteria criteria = new UserCriteria();
			criteria.setFirstResult(startRow);
			criteria.setMaximumResultSize(pageSize * 4);
			List<UserInfo> users = securityService.getUsers(criteria);
			logger.debug("got "+users.size()+" users");
			// FIXME Total size should be fetched from database instead of guessing
			int size = systemStatistic.getUsers().intValue();
			dataPage = new DataPage<UserInfo>(size,startRow,users);
		}
		return dataPage;
	}
	
	private class LocalDataModel extends AbstractPagedTable<UserInfo> {
		
		private static final long serialVersionUID = -7432989613782632232L;

		@Override
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}

	}
	
	/* ------------ properties ----------------- */
	
	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public LocalDataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(LocalDataModel dataModel) {
		this.dataModel = dataModel;
	}

	public void setSystemStatistic(SystemStatisticInfo systemStatistic) {
		this.systemStatistic = systemStatistic;
	}
}
