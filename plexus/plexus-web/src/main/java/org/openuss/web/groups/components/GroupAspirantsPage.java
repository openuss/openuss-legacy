package org.openuss.web.groups.components;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;


/**
 * 
 * @author Lutz D. Kramer
 * Aspirant page to manage user application for membership 
 * 
 */
@Bean(name = "views$secured$groups$groupaspirants", scope = Scope.REQUEST)
@View
public class GroupAspirantsPage extends AbstractGroupPage {
	
	private static final Logger logger = Logger.getLogger(GroupAspirantsPage.class);
	
	private AspirantDataProvider data = new AspirantDataProvider();
	
	private transient Set<UserInfo> acceptAspirants = new HashSet<UserInfo>();
	private transient Set<UserInfo> rejectAspirants = new HashSet<UserInfo>();


	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}
	
	// TODO - Lutz: Properties anpassen
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("groups_command_options_aspirants"));
		crumb.setHint(i18n("groups_command_options_aspirants"));
		breadcrumbs.addCrumb(crumb);
	}	
	
	public String save() {
		acceptAspirants();
		rejectAspirants();
		return Constants.SUCCESS;
	}

	private void rejectAspirants() {
		for (UserInfo aspirants: rejectAspirants) {
			try {
				groupService.rejectAspirant(groupInfo, aspirants.getId());
				// TODO - Lutz: Properties anpassen
				addMessage(i18n("groups_aspirant_reject", aspirants.getUsername()));
			} catch (Exception e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}

	private void acceptAspirants() {
		for (UserInfo aspirants: acceptAspirants) {
			try {
				groupService.acceptAspirant(groupInfo, aspirants.getId());
				// TODO - Lutz: Properties anpassen
				addMessage(i18n("groups_aspirant_accepted", aspirants.getUsername()));
			} catch (Exception e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}
	
	public String showProfile() {
		UserInfo aspirant = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(aspirant.getId());		
		setSessionBean(Constants.SHOW_USER_PROFILE, user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
	
	public void changedAspirant(ValueChangeEvent event){
		logger.debug("groups: changed aspirant");
		UserInfo aspirant = data.getRowData();
		if (logger.isDebugEnabled()) {
			logger.debug("changed "+aspirant.getUsername()+ " from " + event.getOldValue() + " to " + event.getNewValue());
		}
		
		if ("accept".equals(event.getNewValue())) {
			acceptAspirants.add(aspirant);
		} else if ("reject".equals(event.getNewValue())) {
			rejectAspirants.add(aspirant);
		}
	}
	
	private class AspirantDataProvider extends AbstractPagedTable<UserInfo> {

		private static final long serialVersionUID = 2219795204824844857L;
		
		private DataPage<UserInfo> page; 
		
		@Override 
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<UserInfo> aspirants = groupService.getAspirants(groupInfo);
				page = new DataPage<UserInfo>(aspirants.size(),0,aspirants);
				sort(aspirants);
			}
			return page;
		}
	}
	
	/* --------------------- properties --------------------------*/
	public AspirantDataProvider getData() {
		return data;
	}
	
	public void setData(AspirantDataProvider data) {
		this.data = data;
	}

}