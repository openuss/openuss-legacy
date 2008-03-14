package org.openuss.web.seminarpool.allocation;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.security.User;
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.SeminarPlaceAllocationInfo;
import org.openuss.seminarpool.SeminarpoolAllocationService;
import org.openuss.seminarpool.SeminarpoolStatus;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;

@Bean(name = "views$secured$seminarpool$allocation$userAllocationOverview", scope = Scope.REQUEST)
@View
public class UserAllocationOverviewPage extends AbstractSeminarpoolPage {

	public static final Logger logger = Logger
			.getLogger(UserAllocationOverviewPage.class);
	
	@Property(value = "#{seminarpoolAllocationService}")
	protected SeminarpoolAllocationService seminarpoolAllocationService;


	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_allocation_breadcrumb_overview"));
		crumb.setHint(i18n("seminarpool_allocation_breadcrumb_overview"));
		breadcrumbs.addCrumb(crumb);
	}

	public String generateAllocation(ActionEvent event) {
		this.seminarpoolAllocationService.generateAllocation(seminarpoolInfo.getId());
		addMessage(i18n("seminarpool_allocation_sucessfull"));
		return "";
	}

	public String completeAllocation(ActionEvent event) {
		seminarpoolInfo.setSeminarpoolStatus(SeminarpoolStatus.CONFIRMEDPHASE);
		this.getSeminarpoolAdministrationService().updateSeminarpool(
				seminarpoolInfo);
		// Send Email
		this.getSeminarpoolUserRegistrationService().informParticipantsByMail(
				seminarpoolInfo.getId());
		this.getSeminarpoolUserRegistrationService().informLecturerByMail(
				seminarpoolInfo.getId());
		this.getSeminarpoolUserRegistrationService().setBookmarksOnMyUniPage(
				seminarpoolInfo.getId());
		addMessage(i18n("seminarpool_allocation_complete_sucessfull"));
		return "";
	}

	public String exportAllocation(ActionEvent event) {
		try {
			this.generateCVSfile();
		} catch (Exception e) {
			logger.error(e);
		}
		return "";
	}

	private void generateCVSfile() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();
		String fileName = seminarpoolInfo.getName() + ".xls";
		response.setContentType("application/txt");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ fileName + "\"");
		response.getWriter().println(generateCVSstring());
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	private String generateCVSstring(){
		StringBuffer cvs = new StringBuffer();
		List<CourseSeminarpoolAllocationInfo> csaiList = new ArrayList<CourseSeminarpoolAllocationInfo>(seminarpoolAdministrationService.findCoursesInSeminarpool(seminarpoolInfo.getId()));
		for(CourseSeminarpoolAllocationInfo csai : csaiList){
			cvs.append(csai.getCourseName());
			cvs.append("\t\t\t\r\n");
			List<SeminarPlaceAllocationInfo> spaiList = new ArrayList<SeminarPlaceAllocationInfo>(seminarpoolAdministrationService.getAllocationsByCourse(csai.getCourseId(), csai.getSeminarpoolId()));
			for(SeminarPlaceAllocationInfo spai : spaiList){
				User user = this.securityService.getUser(spai.getUserId());
				cvs.append(user.getFirstName());
				cvs.append('\t');
				cvs.append(user.getLastName());
				cvs.append('\t');
				cvs.append(user.getEmail());
				cvs.append('\t');
				cvs.append(spai.getGroupName());
				cvs.append("\r\n");
			}
			cvs.append("\r\n");
		}
		return cvs.toString();
	}


	public SeminarpoolAllocationService getSeminarpoolAllocationService() {
		return seminarpoolAllocationService;
	}

	public void setSeminarpoolAllocationService(
			SeminarpoolAllocationService seminarpoolAllocationService) {
		this.seminarpoolAllocationService = seminarpoolAllocationService;
	}

}
