package org.openuss.web.seminarpool.conditions;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.security.UserInfo;
import org.openuss.seminarpool.SeminarUserConditionValueInfo;
import org.openuss.web.Constants;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;

@Bean(name = "views$secured$seminarpool$conditions$seminarpoolRequirementsByUserStep2", scope = Scope.REQUEST)
@View
public class SeminarpoolRequirementsByUserStep2Page extends
		AbstractSeminarpoolPage {

	@Property(value = "#{" + Constants.SEMINARPOOL_ALLOCATIONS_SELECTED_USER_INFO + "}")
	private UserInfo userInfo;
	
	private List<SeminarUserConditionValueInfo> seminarUserConditionValueList;
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_requirements_breadcrumb_step2") + userInfo.getUsername());
		crumb.setHint(i18n("seminarpool_requirements_breadcrumb_step2") + userInfo.getUsername());
		breadcrumbs.addCrumb(crumb);
		seminarUserConditionValueList = seminarpoolUserRegistrationService.findConditionValuesByUserAndSeminarpool(userInfo.getId(), seminarpoolInfo.getId());
	}

	public List<SeminarUserConditionValueInfo> getSeminarUserConditionValueList() {
		return seminarUserConditionValueList;
	}

	public void setSeminarUserConditionValueList(
			List<SeminarUserConditionValueInfo> seminarUserConditionValueList) {
		this.seminarUserConditionValueList = seminarUserConditionValueList;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
