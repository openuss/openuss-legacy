package org.openuss.web;

import java.io.Serializable;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
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
import org.openuss.lecture.CourseTypeService;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.CourseInfo;
import javax.faces.el.ValueBinding;


/**
 * Utility Bean for hierarchical bread crumb generation
 * This bean knows how to create hierarchical breadcrumbs for the domain model of openuss.
 * View-beans can call the BreadCrumbs bean to load the breadcrumbs for a given domain object
 * and add their own view-specific crumbs afterwards.
 * 
 * @author Julian Reimann
 */
@Bean(name = "breadcrumbs", scope = Scope.REQUEST)
public class BreadCrumbs extends BaseBean implements Serializable {
	private static final long serialVersionUID = 7747345698483117871L;
	private boolean rendered;
	private List<BreadCrumb> myCrumbs;
	
	public BreadCrumbs()
	{
		super();
		rendered = true;
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
	
	public boolean isRendered()
	{
		return rendered;
	}
	
	public void setRendered(boolean rendered)
	{
		this.rendered = rendered;
	}
	
	private List<BreadCrumb> getEmptyList()
	{
		return new ArrayList<BreadCrumb>();
	}
	
	// Startpage Crumb Generation
	
	private List<BreadCrumb> getBaseCrumbs()
	{
		List<BreadCrumb> crumbs = new ArrayList<BreadCrumb>();
		
		BreadCrumb baseCrumb = new BreadCrumb();
		baseCrumb.setName("Startseite");
		baseCrumb.setHint("Die Openuss Startseite");
		baseCrumb.setLink(PageLinks.START_PAGE);
		
		crumbs.add(baseCrumb);
		return crumbs;
	}

	
	// University Crumb Generation
	
	private List<BreadCrumb> getUniversityCrumbs(Long universityId)
	{
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{universityService}");
		if(binding == null)
			return getEmptyList();
		
		UniversityService universityService = (UniversityService)binding.getValue(getFacesContext());
		if(universityService == null)
			return getEmptyList();
		
		UniversityInfo info = universityService.findUniversity(universityId);
		if(info == null)
			return getEmptyList();
		
		List<BreadCrumb> crumbs = getBaseCrumbs();
		BreadCrumb universityCrumb = getUniversityCrumb(info);
		
		
		assert crumbs != null;
		crumbs.add(universityCrumb);
		return crumbs;
	}
	
	private BreadCrumb getUniversityCrumb(UniversityInfo info)
	{
		assert info != null;
		BreadCrumb universityCrumb = new BreadCrumb();
		universityCrumb.setName(info.getName());
		universityCrumb.setLink(PageLinks.UNIVERSITY_PAGE);
		universityCrumb.addParameter("university", info.getId());
		
		return universityCrumb;
	}
	
	
	// Department Crumb Generation
	
	private List<BreadCrumb> getDepartmentCrumbs(Long departmentId)
	{
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{departmentService}");
		if(binding == null)
			return getEmptyList();
		
		DepartmentService departmentService = (DepartmentService)binding.getValue(getFacesContext());
		if(departmentService == null)
			return getEmptyList();
		
		DepartmentInfo info = departmentService.findDepartment(departmentId);
		if(info == null)
			return getEmptyList();
		
		List<BreadCrumb> crumbs = getUniversityCrumbs(info.getUniversityId());
		BreadCrumb departmentCrumb = getDepartmentCrumb(info);
		
		assert crumbs != null;
		crumbs.add(departmentCrumb);
		return crumbs;
	}
	
	private List<BreadCrumb> getDepartmentCrumbs(DepartmentInfo info)
	{
		if(info == null)
			return getEmptyList();
		
		List<BreadCrumb> crumbs = getUniversityCrumbs(info.getUniversityId());
		BreadCrumb departmentCrumb = getDepartmentCrumb(info);
		
		assert crumbs != null;
		crumbs.add(departmentCrumb);
		return crumbs;
	}
	
	private BreadCrumb getDepartmentCrumb(DepartmentInfo info)
	{
		assert info != null;
		BreadCrumb departmentCrumb = new BreadCrumb();
		departmentCrumb.setName(info.getName());
		departmentCrumb.setLink(PageLinks.DEPARTMENT_PAGE);
		departmentCrumb.addParameter("department", info.getId());
		
		return departmentCrumb;
	}
	
	
	// Institute Crumb Generation
	
	private List<BreadCrumb> getInstituteCrumbs(Long instituteId)
	{
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{instituteService}");
		if(binding == null)
			return getEmptyList();
		
		InstituteService instituteService = (InstituteService)binding.getValue(getFacesContext());
		if(instituteService == null)
			return getEmptyList();
		
		InstituteInfo info = instituteService.findInstitute(instituteId);
		if(info == null)
			return getEmptyList();
		
		List<BreadCrumb> crumbs = getDepartmentCrumbs(info.getDepartmentId());
		BreadCrumb instituteCrumb = getInstituteCrumb(info);
		
		assert crumbs != null;
		crumbs.add(instituteCrumb);
		return crumbs;
	}
	
	private BreadCrumb getInstituteCrumb(InstituteInfo info)
	{
		assert info != null;
		BreadCrumb instituteCrumb = new BreadCrumb();
		instituteCrumb.setName(info.getName());
		instituteCrumb.setLink(PageLinks.INSTITUTE_PAGE);
		instituteCrumb.addParameter("institute", info.getId());
		
		return instituteCrumb;
	}
	
	// Course Crumb Generation
	
	private List<BreadCrumb> getCourseCrumbs(Long courseId)
	{
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{instituteService}");
		if(binding == null)
			return getEmptyList();
		
		CourseService courseService = (CourseService)binding.getValue(getFacesContext());
		if(courseService == null)
			return getEmptyList();
		
		CourseInfo info = courseService.findCourse(courseId);
		if(info == null)
			return getEmptyList();
		
		List<BreadCrumb> crumbs = getInstituteCrumbs(info.getInstituteId());
		BreadCrumb courseCrumb = getCourseCrumb(info);
		
		assert crumbs != null;
		crumbs.add(courseCrumb);
		return crumbs;
	}
	
	private List<BreadCrumb> getCourseCrumbs(CourseInfo info)
	{
		if(info == null)
			return getEmptyList();
		
		List<BreadCrumb> crumbs = getInstituteCrumbs(info.getInstituteId());
		BreadCrumb courseCrumb = getCourseCrumb(info);
		
		assert crumbs != null;
		crumbs.add(courseCrumb);
		return crumbs;
	}
	
	private BreadCrumb getCourseCrumb(CourseInfo info)
	{
		assert info != null;
		BreadCrumb courseCrumb = new BreadCrumb();
		courseCrumb.setName(info.getName());
		courseCrumb.setLink(PageLinks.COURSE_PAGE);
		courseCrumb.addParameter("course", info.getId());
		
		return courseCrumb;
	}
	
	
	// MyUni Crumb Generation
	
	private List<BreadCrumb> getMyUniCrumbs()
	{
		List<BreadCrumb> crumbs = getBaseCrumbs();
		assert crumbs != null;
		
		BreadCrumb myUniCrumb = new BreadCrumb();
		myUniCrumb.setName("MyUni");
		myUniCrumb.setLink(PageLinks.MYUNI_PAGE);
		
		crumbs.add(myUniCrumb);
		return crumbs;
	}
	
	// Course Type Crumb Generation
	
	
	private List<BreadCrumb> getCourseTypeCrumbs(CourseTypeInfo info)
	{
		if(info == null)
			return getEmptyList();
		
		List<BreadCrumb> crumbs = getInstituteCrumbs(info.getInstituteId());
		BreadCrumb courseTypeCrumb = getCourseTypeCrumb(info);
		
		assert crumbs != null;
		crumbs.add(courseTypeCrumb);
		return crumbs;
	}
	
	private BreadCrumb getCourseTypeCrumb(CourseTypeInfo info)
	{
		assert info != null;
		BreadCrumb courseTypeCrumb = new BreadCrumb();
		courseTypeCrumb.setName(info.getName());
		courseTypeCrumb.setLink(PageLinks.COURSE_PAGE);
		courseTypeCrumb.addParameter("course", info.getId());
		
		return courseTypeCrumb;
	}
	
	
	// Public loader methods to generate crumbs for the domain object types
	
	public void loadUniversityCrumbs(Long universityId)
	{
		setCrumbs(getUniversityCrumbs(universityId));
	}
	
	public void loadDepartmentCrumbs(Long departmentId)
	{
		setCrumbs(getDepartmentCrumbs(departmentId));
	}
	
	public void loadDepartmentCrumbs(DepartmentInfo info)
	{
		setCrumbs(getDepartmentCrumbs(info));
	}
	
	public void loadInstituteCrumbs(Long instituteId)
	{
		setCrumbs(getInstituteCrumbs(instituteId));
	}
	
	public void loadCourseCrumbs(CourseInfo courseInfo)
	{
		setCrumbs(getCourseCrumbs(courseInfo));
	}
	
	public void loadCourseTypeCrumbs(CourseTypeInfo courseTypeInfo)
	{
		setCrumbs(getCourseTypeCrumbs(courseTypeInfo));
	}
	
	public void loadMyUniCrumbs()
	{
		setCrumbs(getMyUniCrumbs());
	}
	
	public void addCrumb(BreadCrumb newCrumb)
	{
		if(newCrumb != null)
			myCrumbs.add(newCrumb);
	}
	
}
