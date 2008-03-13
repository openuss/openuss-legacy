package org.openuss.web.seminarpool.userRegistration;

import java.util.ArrayList;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.seminarpool.SeminarPriorityDetailInfo;
import org.openuss.seminarpool.SeminarUserRegistrationInfo;
import org.openuss.web.Constants;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;


@Bean(name = "views$secured$seminarpool$userRegistration$seminarpoolUserRegistrationEditStep1", scope = Scope.REQUEST)
@View
public class SeminarpoolUserRegistrationEditStep1Page extends
		AbstractSeminarpoolPage {

	@Property(value = "#{" + Constants.SEMINARPOOL_USER_REGISTRATION_INFO + "}")
	protected SeminarUserRegistrationInfo seminarUserRegistrationInfo;
	
	private SeminarPriorityInfoTable seminarPriorityInfoTable = new SeminarPriorityInfoTable(); 
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_user_registration_edit_breadcrumb_step1"));
		crumb.setHint(i18n("seminarpool_user_registration_edit_breadcrumb_step1"));
		breadcrumbs.addCrumb(crumb);
		
		
	}
	
	
	
	
	
	
	
	
	private class SeminarPriorityInfoTable extends AbstractPagedTable<SeminarPriorityDetailInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<SeminarPriorityDetailInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<SeminarPriorityDetailInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<SeminarPriorityDetailInfo> courseTypes = new ArrayList<SeminarPriorityDetailInfo>(seminarpoolUserRegistrationService.getDetailUserRegistrationById(seminarUserRegistrationInfo.getId()));
				sort(courseTypes);
				page = new DataPage<SeminarPriorityDetailInfo>(courseTypes.size(), 0, courseTypes);
			}
			return page;
		}
		
//		public void sort(List<SeminarPrioritiesInfo> courseSeminarpoolAllocation) {
//			Collections.sort(courseSeminarpoolAllocation, new CourseSeminarpoolAllocationCourseNameComparator(dataCourseTypes.isAscending()));
//		}

	}

	public SeminarUserRegistrationInfo getSeminarUserRegistrationInfo() {
		return seminarUserRegistrationInfo;
	}

	public void setSeminarUserRegistrationInfo(
			SeminarUserRegistrationInfo seminarUserRegistrationInfo) {
		this.seminarUserRegistrationInfo = seminarUserRegistrationInfo;
	}

	public SeminarPriorityInfoTable getSeminarPriorityInfoTable() {
		return seminarPriorityInfoTable;
	}

	public void setSeminarPriorityInfoTable(
			SeminarPriorityInfoTable seminarPriorityInfoTable) {
		this.seminarPriorityInfoTable = seminarPriorityInfoTable;
	}

}
