package org.openuss.web.seminarpool.allocation;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.seminarpool.SeminarpoolStatus;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;

@Bean(name = "views$secured$seminarpool$allocation$userAllocationOverview", scope = Scope.REQUEST)
@View
public class UserAllocationOverviewPage extends AbstractSeminarpoolPage {
	
	public static final Logger logger = Logger.getLogger(UserAllocationOverviewPage.class);
	
	private boolean status;
	private boolean status2;
	private boolean status3;
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_allocation_breadcrumb_overview"));
		crumb.setHint(i18n("seminarpool_allocation_breadcrumb_overview"));
		breadcrumbs.addCrumb(crumb);
	}
	
	public String generateAllocation(ActionEvent event){
		this.seminarpoolAdministrationService.generateAllocation(seminarpoolInfo.getId());
		addMessage(i18n("seminarpool_allocation_sucessfull"));
		return "";
	}
	public String completeAllocation(ActionEvent event){
		seminarpoolInfo.setSeminarpoolStatus(SeminarpoolStatus.CONFIRMEDPHASE);
		this.getSeminarpoolAdministrationService().updateSeminarpool(seminarpoolInfo);
		//Send Email
		this.getSeminarpoolUserRegistrationService().informParticipantsByMail(seminarpoolInfo.getId());
		this.getSeminarpoolUserRegistrationService().setBookmarksOnMyUniPage(seminarpoolInfo.getId());
		addMessage(i18n("seminarpool_allocation_complete_sucessfull"));
		return "";
	}
	
	public boolean getStatus(){
		return (seminarpoolInfo.getSeminarpoolStatus() == SeminarpoolStatus.REGISTRATIONCOMPLETEPHASE);
	}
	
	public void setStatus(boolean status){
		this.status=status;
	}

	public boolean getStatus2() {
		return (seminarpoolInfo.getSeminarpoolStatus().getValue() > 2);
	}

	public void setStatus2(boolean status2) {
		this.status2 = status2;
	}
	
	public boolean getStatus3() {
		return (seminarpoolInfo.getSeminarpoolStatus().getValue() == 3);
	}

	public void setStatus3(boolean status3) {
		this.status3 = status3;
	}

}
