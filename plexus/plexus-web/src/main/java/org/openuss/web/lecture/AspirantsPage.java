package org.openuss.web.lecture;

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
import org.openuss.lecture.OrganisationService;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

/**
 * Aspirant page to manage user application for membership 
 *  
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$auth$aspirants", scope = Scope.REQUEST)
@View
public class AspirantsPage extends AbstractLecturePage {
	private static final Logger logger = Logger.getLogger(AspirantsPage.class);

	@Property(value = "#{organisationService}")
	private OrganisationService organisationService;
	
	private static final long serialVersionUID = 3577437988777775136L;

	private AspirantDataProvider data = new AspirantDataProvider();
	
	private transient Set<UserInfo> acceptAspirants = new HashSet<UserInfo>();
	private transient Set<UserInfo> rejectAspirants = new HashSet<UserInfo>();

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}

	private void addPageCrumb() {
		breadcrumbs.loadInstituteCrumbs(instituteInfo);
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("institute_command_aspirants"));
		crumb.setHint(i18n("institute_command_aspirants"));
		breadcrumbs.addCrumb(crumb);
	}
	
	public String save() {
		acceptAspirants();
		rejectAspirants();
		
		return Constants.SUCCESS;
	}

	private void rejectAspirants() {
		for (UserInfo userInfo : rejectAspirants) {
			try {
				
				organisationService.rejectAspirant(instituteInfo.getId(), userInfo.getId());
				addMessage(i18n("institute_reject_aspirants",userInfo.getUsername()));
			} catch (Exception e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}

	private void acceptAspirants() {
		for (UserInfo userInfo : acceptAspirants) {
			try {
				organisationService.acceptAspirant(instituteInfo.getId(), userInfo.getId());
				addMessage(i18n("institute_add_member_to_institute", userInfo.getUsername()));
			} catch (Exception e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}
	
	public void changedAspirant(ValueChangeEvent event) throws LectureException {
		UserInfo aspirant = data.getRowData();
		if (logger.isDebugEnabled()) {
			logger.debug("changed " + aspirant.getUsername() + " from " + event.getOldValue() + " to " + event.getNewValue());
		}
		
		if ("accept".equals(event.getNewValue())) {
			rejectAspirants.remove(aspirant);
			acceptAspirants.add(aspirant);
		} else if ("reject".equals(event.getNewValue())) {
			rejectAspirants.add(aspirant);
			acceptAspirants.remove(aspirant);
		}
	}
	
	private class AspirantDataProvider extends AbstractPagedTable<UserInfo> {

		private static final long serialVersionUID = -7717775162072514379L;
		
		private DataPage<UserInfo> page; 
		
		@Override 
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<UserInfo> aspirants = organisationService.findAllAspirants(instituteInfo.getId());
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

	public OrganisationService getOrganisationService() {
		return organisationService;
	}

	public void setOrganisationService(OrganisationService organisationService) {
		this.organisationService = organisationService;
	}
	

}
