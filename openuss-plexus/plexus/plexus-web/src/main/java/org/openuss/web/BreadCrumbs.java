package org.openuss.web;

import java.io.Serializable;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.PageLinks;
import java.util.List;
import java.util.ArrayList;
import org.openuss.lecture.UniversityService;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.InstituteInfo;
import javax.faces.el.ValueBinding;


/**
 * Utility Bean for hierarchical bread crumb generation
 * 
 * @author Julian Reimann
 */
public class BreadCrumbs extends BaseBean implements Serializable {
	private static final long serialVersionUID = 7747345698483117871L;
	private boolean isRendered;
	private List<BreadCrumb> myCrumbs;
	
	public BreadCrumbs()
	{
		super();
		isRendered = true;
		myCrumbs = getBaseCrumbs();
		
	}
	
	public List<BreadCrumb> getCrumbs()
	{
		return myCrumbs;
	}
	
	public void setCrumbs(List<BreadCrumb> newCrumbs)
	{
		myCrumbs = newCrumbs;
	}
	
	public boolean getIsRendered()
	{
		return isRendered;
	}
	
	public void setIsRendered(boolean newIsRendered)
	{
		isRendered = newIsRendered;
	}
	
	public List<BreadCrumb> getBaseCrumbs()
	{
		List<BreadCrumb> crumbs = new ArrayList<BreadCrumb>();
		
		BreadCrumb baseCrumb = new BreadCrumb();
		baseCrumb.setName("Startseite");
		baseCrumb.setHint("Die Openuss Startseite");
		baseCrumb.setLink(PageLinks.START_PAGE);
		
		crumbs.add(baseCrumb);
		return crumbs;
	}
	
	public List<BreadCrumb> getUniversityCrumbs(Long universityId)
	{
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{universityService}");
		if(binding == null)
			return null;
		
		UniversityService universityService = (UniversityService)binding.getValue(getFacesContext());
		if(universityService == null)
			return null;
		
		UniversityInfo info = universityService.findUniversity(universityId);
		if(info == null)
			return null;
		
		List<BreadCrumb> crumbs = getBaseCrumbs();
		
		BreadCrumb universityCrumb = new BreadCrumb();
		universityCrumb.setName(info.getName());
		universityCrumb.setLink(PageLinks.UNIVERSITY_PAGE);
		universityCrumb.addParameter("university", info.getId());
		
		crumbs.add(universityCrumb);
		return crumbs;
	}
	
	public List<BreadCrumb> getDepartmentCrumbs(Long departmentId)
	{
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{departmentService}");
		if(binding == null)
			return null;
		
		DepartmentService departmentService = (DepartmentService)binding.getValue(getFacesContext());
		if(departmentService == null)
			return null;
		
		DepartmentInfo info = departmentService.findDepartment(departmentId);
		if(info == null)
			return null;
		
		List<BreadCrumb> crumbs = getUniversityCrumbs(info.getUniversityId());
		
		BreadCrumb departmentCrumb = new BreadCrumb();
		departmentCrumb.setName(info.getName());
		departmentCrumb.setLink(PageLinks.DEPARTMENT_PAGE);
		departmentCrumb.addParameter("department", info.getId());
		
		crumbs.add(departmentCrumb);
		return crumbs;
	}
	
	public List<BreadCrumb> getInstituteCrumbs(Long instituteId)
	{
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{instituteService}");
		if(binding == null)
			return null;
		
		InstituteService instituteService = (InstituteService)binding.getValue(getFacesContext());
		if(instituteService == null)
			return null;
		
		InstituteInfo info = instituteService.findInstitute(instituteId);
		if(info == null)
			return null;
		
		List<BreadCrumb> crumbs = getDepartmentCrumbs(info.getDepartmentId());
		
		BreadCrumb instituteCrumb = new BreadCrumb();
		instituteCrumb.setName(info.getName());
		instituteCrumb.setLink(PageLinks.INSTITUTE_PAGE);
		instituteCrumb.addParameter("institute", info.getId());
		
		if(crumbs == null)
			crumbs = new ArrayList<BreadCrumb>();
		
		crumbs.add(instituteCrumb);
		return crumbs;
	}
	
	public List<BreadCrumb> getMyUniCrumbs()
	{
		List<BreadCrumb> crumbs = getBaseCrumbs();
		
		BreadCrumb myUniCrumb = new BreadCrumb();
		myUniCrumb.setName("MyUni");
		myUniCrumb.setLink(PageLinks.MYUNI_PAGE);
		
		crumbs.add(myUniCrumb);
		return crumbs;
	}
	
	public void loadUniversityCrumbs(Long universityId)
	{
		this.setCrumbs(this.getUniversityCrumbs(universityId));
	}
	
	public void loadDepartmentCrumbs(Long departmentId)
	{
		this.setCrumbs(this.getDepartmentCrumbs(departmentId));
	}
	
	public void loadInstituteCrumbs(Long instituteId)
	{
		this.setCrumbs(this.getInstituteCrumbs(instituteId));
	}
	
	public void addCrumb(BreadCrumb newCrumb)
	{
		if(newCrumb != null)
			myCrumbs.add(newCrumb);
	}
	
}
