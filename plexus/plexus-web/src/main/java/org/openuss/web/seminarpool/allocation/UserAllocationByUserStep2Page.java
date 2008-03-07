package org.openuss.web.seminarpool.allocation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.UserInfo;
import org.openuss.seminarpool.SeminarPlaceAllocationInfo;
import org.openuss.web.Constants;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;

@Bean(name = "views$secured$seminarpool$allocation$userAllocationByUserStep2", scope = Scope.REQUEST)
@View
public class UserAllocationByUserStep2Page extends AbstractSeminarpoolPage {

	
	public static final Logger logger = Logger.getLogger(UserAllocationByUserPage.class);
	
	@Property(value = "#{" + Constants.SEMINARPOOL_ALLOCATIONS_SELECTED_USER_INFO + "}")
	private UserInfo userInfo;
	
	private SeminarpoolUserRegistrationsTable dataCourseTypes = new SeminarpoolUserRegistrationsTable();
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_allocation_breadcrumb_user_step1"));
		crumb.setHint(i18n("seminarpool_allocation_breadcrumb_user_step1"));
		breadcrumbs.addCrumb(crumb);
	}
	
	public String removeAllocation(){
		
		return Constants.SEMINARPOOL_ALLOCATIONS_BY_USER_STEP2;
	}
	
	private class SeminarpoolUserRegistrationsTable extends AbstractPagedTable<SeminarPlaceAllocationInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<SeminarPlaceAllocationInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<SeminarPlaceAllocationInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<SeminarPlaceAllocationInfo> courseTypes = new ArrayList<SeminarPlaceAllocationInfo>(seminarpoolAdministrationService.getAllocationsByUserAndSeminarpool(userInfo.getId(), seminarpoolInfo.getId()));
				page = new DataPage<SeminarPlaceAllocationInfo>(courseTypes.size(), 0, courseTypes);
			}
			return page;
		}

	}

	public SeminarpoolUserRegistrationsTable getDataCourseTypes() {
		return dataCourseTypes;
	}

	public void setDataCourseTypes(SeminarpoolUserRegistrationsTable dataCourseTypes) {
		this.dataCourseTypes = dataCourseTypes;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
