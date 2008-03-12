package org.openuss.web.seminarpool.allocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.UserInfo;
import org.openuss.seminarpool.util.UserInfoFirstNameComparator;
import org.openuss.web.Constants;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;


@Bean(name = "views$secured$seminarpool$allocation$userAllocationByCourseStep3", scope = Scope.REQUEST)
@View
public class UserAllocationByCourseStep3Page extends AbstractSeminarpoolPage {

	public static final Logger logger = Logger.getLogger(UserAllocationByCourseStep3Page.class);
	
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
	
	public String selectUser(){
		setSessionBean(Constants.SEMINARPOOL_ALLOCATIONS_SELECTED_USER_INFO, dataCourseTypes.getRowData());
		return "seminarpool_options_allocations_course_step4";
	}
	
	private class SeminarpoolUserRegistrationsTable extends AbstractPagedTable<UserInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<UserInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<UserInfo> courseTypes = new ArrayList<UserInfo>(seminarpoolAdministrationService.getRegistrationsAsUserInfo(seminarpoolInfo.getId()));
				page = new DataPage<UserInfo>(courseTypes.size(), 0, courseTypes);
			}
			return page;
		}
		
		public void sort(List<UserInfo> page) {
			Collections.sort(page, new UserInfoFirstNameComparator(dataCourseTypes.isAscending()));
		}

	}

	public SeminarpoolUserRegistrationsTable getDataCourseTypes() {
		return dataCourseTypes;
	}

	public void setDataCourseTypes(SeminarpoolUserRegistrationsTable dataCourseTypes) {
		this.dataCourseTypes = dataCourseTypes;
	}
}
