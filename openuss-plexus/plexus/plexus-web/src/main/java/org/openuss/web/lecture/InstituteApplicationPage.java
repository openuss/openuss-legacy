package org.openuss.web.lecture;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.ApplicationInfo;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;


/**
 * This page should be moved to the "Stammdaten view"
 * @author Tianyu Wang
 * @author Weijun Chen
 */
@Bean(name = "views$secured$lecture$instituteDepartments", scope = Scope.REQUEST)
@View
public class InstituteApplicationPage extends AbstractLecturePage{
	private static final long serialVersionUID = 20278675452385870L;
	

	
	@Property(value = "#{applicationInfo}")
	protected ApplicationInfo applicationInfo;
	
	private Long departmentId;
	
	private String appStatusDescription;
	
	private boolean appStatus;
	

	private  ApplicationInfo appAnDepartment;
		
	private List<SelectItem> departmentItems;

	private List<DepartmentInfo> allDepartments;
	

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("department_command_institutes"));
		crumb.setHint(i18n("department_command_institutes"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}	
	


	private static ResourceBundle getResourceBundle(){
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle rb = ResourceBundle.getBundle(
				context.getApplication().getMessageBundle(), 
				context.getViewRoot().getLocale());
		return rb;
	}
	private Long getUniversityId(){
		Long departmentId = instituteService.findInstitute(instituteInfo.getId()).getDepartmentId();
		Long  universityId= departmentService.findDepartment(departmentId).getUniversityId();
		UniversityInfo universityInfo = universityService.findUniversity(universityId); 
		return universityInfo.getId();
	}
	public List<SelectItem> getAllDepartments(){
		departmentItems = new ArrayList<SelectItem>();
		
			
		logger.info("universityId:"+getUniversityId());
		allDepartments = departmentService.findDepartmentsByUniversity(getUniversityId());
		Iterator<DepartmentInfo> iter = allDepartments.iterator();
		DepartmentInfo department;
		
		while (iter.hasNext()){
			department = iter.next();
			SelectItem item = new SelectItem(department.getId(), department.getName());
			departmentItems.add(item);
		}
		
		logger.info("DepartmentId:" + allDepartments.get(0).getId());
		return departmentItems;
	
	}
	
	public String apply(){
		signoffInstitute();
		logger.debug("Debug apply");
	
		
		
		logger.debug("DepartmentId"+ departmentId);
		
		logger.debug("Descriptiony"+applicationInfo.getDescription());
	    
		
		logger.debug("InstituteI"+instituteInfo.getId());
		
		UserInfo userInfo = new UserInfo();
		userInfo.setId(user.getId());
		applicationInfo.setApplyingUserInfo(userInfo);
		
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setId(applicationInfo.getId());
		applicationInfo.setDepartmentInfo(departmentInfo);
		
		applicationInfo.setInstituteInfo(instituteInfo);
		applicationInfo.setId(null);
		
		try{
		Long appId = instituteService.applyAtDepartment(applicationInfo);
			}
		catch(Exception e){;}
		
		
		return Constants.SUCCESS;
	}
	
  
    	
    public String signoffInstitute(){
    	try{
    	departmentService.signoffInstitute(instituteInfo.getId());
    	Long departmentId = instituteService.findInstitute(instituteInfo.getId()).getDepartmentId();
    	DepartmentInfo departmentInfo = departmentService.findDepartment(departmentId);
    	setSessionBean(Constants.DEPARTMENT_INFO,departmentInfo);}
    	catch(Exception e){;}
    	
    	  	
    	return Constants.SUCCESS;
    }
    

	public ApplicationInfo getApplicationInfo() {
		return applicationInfo;
	}

	public void setApplicationInfo(ApplicationInfo applicationInfo) {
		this.applicationInfo = applicationInfo;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getAppStatusDescription() {
		String status;
		ResourceBundle rb = getResourceBundle();
		ApplicationInfo app = instituteService.findApplicationByInstitute(instituteInfo.getId());
		if (app == null)
			appStatus = false;
		else{
		appStatusDescription = app.getDepartmentInfo().getName();
		appStatus = true;
			if (app.isConfirmed())
				appStatusDescription  = appStatusDescription + rb.getString("application_accept_info");
			else
				appStatusDescription  = appStatusDescription + rb.getString("application_working_info");
		}
		return appStatusDescription;
	}

	public void setAppStatusDescription(String appStatusDescription) {
		this.appStatusDescription = appStatusDescription;
	}

	public boolean isAppStatus() {
		return appStatus;
	}

	public void setAppStatus(boolean appStatus) {
		this.appStatus = appStatus;
	}



}
