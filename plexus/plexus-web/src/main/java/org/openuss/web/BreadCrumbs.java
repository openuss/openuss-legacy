package org.openuss.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.CourseTypeService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.OrganisationHierarchy;
import org.openuss.lecture.OrganisationService;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;

/**
 * Utility Bean for hierarchical bread crumb generation This bean knows how to
 * create hierarchical breadcrumbs for the domain model of openuss. View-beans
 * can call the BreadCrumbs bean to load the breadcrumbs for a given domain
 * object and add their own view-specific crumbs afterwards.
 * 
 * @author Julian Reimann
 * @author Ingo Düppe
 */
@Bean(name = "breadcrumbs", scope = Scope.REQUEST)
public class BreadCrumbs extends BaseBean {

	private boolean rendered;

	private List<BreadCrumb> breadcrumbs;

	@Property(value = "#{courseService}")
	private CourseService courseService;

	@Property(value = "#{courseTypeService}")
	private CourseTypeService courseTypeService;

	@Property(value = "#{instituteService}")
	private InstituteService instituteService;

	@Property(value = "#{departmentService}")
	private DepartmentService departmentService;

	@Property(value = "#{universityService}")
	private UniversityService universityService;

	@Property(value = "#{organisationService}")
	private OrganisationService organisationService;

	private OrganisationHierarchy organisationHierarchy;

	public BreadCrumbs() {
		super();
		organisationHierarchy = new OrganisationHierarchy();
		rendered = true;
		breadcrumbs = null;
	}

	/*
	 * Clears the crumbs and hides them
	 */
	public void clear() {
		rendered = false;
		breadcrumbs = null;
	}

	/*
	 * Sets the crumbs to point to the Startpage
	 */
	public void init() {
		rendered = true;
		breadcrumbs = null;
	}

	/*
	 * Returns the current list of bread crumbs
	 */
	public List<BreadCrumb> getCrumbs() {
		if (breadcrumbs == null) {
			breadcrumbs = newBaseCrumbs();
		}
		return breadcrumbs;
	}

	public void setCrumbs(List<BreadCrumb> breadcrumbs) {
		this.breadcrumbs = breadcrumbs;
	}

	/*
	 * Defines if the crumbs should be rendered or not
	 */
	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	/*
	 * Returns an empty list of bread crumbs
	 */
	private List<BreadCrumb> getEmptyList() {
		return new ArrayList<BreadCrumb>();
	}

	// Startpage Crumb Generation

	private List<BreadCrumb> newBaseCrumbs() {
		List<BreadCrumb> crumbs = new ArrayList<BreadCrumb>();

		BreadCrumb baseCrumb = new BreadCrumb();
		baseCrumb.setName(i18n("home"));
		baseCrumb.setHint(i18n("home"));
		baseCrumb.setLink(PageLinks.START_PAGE);

		crumbs.add(baseCrumb);
		return crumbs;
	}

	// University Crumb Generation

	private List<BreadCrumb> getUniversityCrumbs(Long universityId) {
		/*
		 * ValueBinding binding =
		 * getFacesContext().getApplication().createValueBinding("#{universityService}");
		 * if(binding == null) return getEmptyList();
		 * 
		 * UniversityService universityService =
		 * (UniversityService)binding.getValue(getFacesContext());
		 */

		if (universityService == null) {
			return getEmptyList();
		}

		UniversityInfo info = universityService.findUniversity(universityId);
		if (info == null) {
			return getEmptyList();
		}

		List<BreadCrumb> crumbs = newBaseCrumbs();
		crumbs.add(getUniversityCrumb(info));
		return crumbs;
	}

	private List<BreadCrumb> getUniversityCrumbs(UniversityInfo info) {
		if (info == null) {
			return getEmptyList();
		}

		List<BreadCrumb> crumbs = newBaseCrumbs();
		crumbs.add(getUniversityCrumb(info));
		return crumbs;
	}

	private BreadCrumb getUniversityCrumb(UniversityInfo info) {
		assert info != null;
		BreadCrumb universityCrumb = new BreadCrumb();
		universityCrumb.setName(info.getShortcut());
		universityCrumb.setHint(info.getName());
		universityCrumb.setLink(PageLinks.UNIVERSITY_PAGE);
		universityCrumb.addParameter("university", info.getId());

		return universityCrumb;
	}

	// Department Crumb Generation

	private List<BreadCrumb> getDepartmentCrumbs(Long departmentId) {
		/*
		 * ValueBinding binding =
		 * getFacesContext().getApplication().createValueBinding("#{departmentService}");
		 * if(binding == null) return getEmptyList();
		 * 
		 * DepartmentService departmentService =
		 * (DepartmentService)binding.getValue(getFacesContext());
		 */
		if (departmentService == null) {
			return getEmptyList();
		}

		DepartmentInfo info = departmentService.findDepartment(departmentId);
		if (info == null) {
			return getEmptyList();
		}

		List<BreadCrumb> crumbs;

		if (organisationHierarchy.getUniversityInfo() != null) {
			crumbs = getUniversityCrumbs(organisationHierarchy.getUniversityInfo());
		} else {
			crumbs = getUniversityCrumbs(info.getUniversityId());
		}
		assert crumbs != null;
		crumbs.add(getDepartmentCrumb(info));
		return crumbs;
	}

	private List<BreadCrumb> getDepartmentCrumbs(DepartmentInfo info) {
		if (info == null) {
			return getEmptyList();
		}

		List<BreadCrumb> crumbs;

		if (organisationHierarchy.getUniversityInfo() != null) {
			crumbs = getUniversityCrumbs(organisationHierarchy.getUniversityInfo());
		} else {
			crumbs = getUniversityCrumbs(info.getUniversityId());
		}

		assert crumbs != null;
		crumbs.add(getDepartmentCrumb(info));
		return crumbs;
	}

	private BreadCrumb getDepartmentCrumb(DepartmentInfo info) {
		assert info != null;

		BreadCrumb departmentCrumb = new BreadCrumb();
		departmentCrumb.setName(info.getShortcut());
		departmentCrumb.setHint(info.getName());
		departmentCrumb.setLink(PageLinks.DEPARTMENT_PAGE);
		departmentCrumb.addParameter("department", info.getId());

		return departmentCrumb;
	}

	// Institute Crumb Generation

	private List<BreadCrumb> getInstituteCrumbs(Long instituteId) {
		/*
		 * ValueBinding binding =
		 * getFacesContext().getApplication().createValueBinding("#{instituteService}");
		 * if(binding == null) return getEmptyList();
		 * 
		 * InstituteService instituteService =
		 * (InstituteService)binding.getValue(getFacesContext());
		 */

		if (instituteService == null) {
			return getEmptyList();
		}

		InstituteInfo info = instituteService.findInstitute(instituteId);
		if (info == null) {
			return getEmptyList();
		}

		List<BreadCrumb> crumbs;

		if (organisationHierarchy.getDepartmentInfo() != null) {
			crumbs = getDepartmentCrumbs(organisationHierarchy.getDepartmentInfo());
		} else {
			crumbs = getDepartmentCrumbs(info.getDepartmentId());
		}

		assert crumbs != null;
		crumbs.add(getInstituteCrumb(info));
		return crumbs;
	}

	private List<BreadCrumb> getInstituteCrumbs(InstituteInfo info) {
		if (info == null) {
			return getEmptyList();
		}

		List<BreadCrumb> crumbs;

		if (organisationHierarchy.getDepartmentInfo() != null) {
			crumbs = getDepartmentCrumbs(organisationHierarchy.getDepartmentInfo());
		} else {
			crumbs = getDepartmentCrumbs(info.getDepartmentId());
		}

		BreadCrumb instituteCrumb = getInstituteCrumb(info);

		assert crumbs != null;
		crumbs.add(instituteCrumb);
		return crumbs;
	}

	private BreadCrumb getInstituteCrumb(InstituteInfo info) {
		assert info != null;
		BreadCrumb instituteCrumb = new BreadCrumb();
		instituteCrumb.setName(info.getShortcut());
		instituteCrumb.setHint(info.getName());
		instituteCrumb.setLink(PageLinks.INSTITUTE_PAGE);
		instituteCrumb.addParameter("institute", info.getId());

		return instituteCrumb;
	}

	/**
	 * Generates a CourceCrumb structure.
	 * @param info course object
	 * @return List&gt;BreadCrumb&lt;
	 */
	private List<BreadCrumb> getCourseCrumbs(final CourseInfo info) {
		if (info == null) {
			return getEmptyList();
		}

		List<BreadCrumb> crumbs;

		if (organisationHierarchy.getInstituteInfo() != null) {
			crumbs = getInstituteCrumbs(organisationHierarchy.getInstituteInfo());
		} else {
			crumbs = getInstituteCrumbs(info.getInstituteId());
		}

		BreadCrumb courseCrumb = getCourseCrumb(info);

		assert crumbs != null;
		crumbs.add(courseCrumb);
		return crumbs;
	}

	private BreadCrumb getCourseCrumb(CourseInfo info) {
		assert info != null;
		BreadCrumb courseCrumb = new BreadCrumb();
		courseCrumb.setName(info.getShortcut());
		courseCrumb.setHint(info.getName());
		courseCrumb.setLink(PageLinks.COURSE_PAGE);
		courseCrumb.addParameter("course", info.getId());

		return courseCrumb;
	}

	// Course Type Crumb Generation

	private List<BreadCrumb> getCourseTypeCrumbs(CourseTypeInfo info) {
		if (info == null) {
			return getEmptyList();
		}

		List<BreadCrumb> crumbs;

		if (organisationHierarchy.getInstituteInfo() != null) {
			crumbs = getInstituteCrumbs(organisationHierarchy.getInstituteInfo());
		} else {
			crumbs = getInstituteCrumbs(info.getInstituteId());
		}

		BreadCrumb courseTypeCrumb = getCourseTypeCrumb(info);

		assert crumbs != null;
		crumbs.add(courseTypeCrumb);
		return crumbs;
	}

	private BreadCrumb getCourseTypeCrumb(CourseTypeInfo info) {
		assert info != null;
		BreadCrumb courseTypeCrumb = new BreadCrumb();
		courseTypeCrumb.setName(info.getShortcut());
		courseTypeCrumb.setHint(info.getName());
		courseTypeCrumb.setLink(PageLinks.COURSE_PAGE);
		courseTypeCrumb.addParameter("course", info.getId());

		return courseTypeCrumb;
	}

	// MyUni Crumb Generation

	private List<BreadCrumb> getMyUniCrumbs() {
		List<BreadCrumb> crumbs = newBaseCrumbs();
		assert (crumbs != null);

		BreadCrumb myUniCrumb = new BreadCrumb();
		myUniCrumb.setName(i18n("desktop_command_myuni"));
		myUniCrumb.setHint(i18n("desktop_command_myuni"));
		myUniCrumb.setLink(PageLinks.MYUNI_PAGE);

		crumbs.add(myUniCrumb);
		return crumbs;
	}

	// Edit Profile Crumb Generation

	private List<BreadCrumb> getProfileCrumbs() {
		List<BreadCrumb> crumbs = newBaseCrumbs();
		assert crumbs != null;

		BreadCrumb profileCrumb = new BreadCrumb();
		profileCrumb.setName(i18n("desktop_command_userprofile"));
		profileCrumb.setHint(i18n("desktop_command_userprofile"));

		crumbs.add(profileCrumb);
		return crumbs;
	}

	// Extended Search Crumb Generation

	private List<BreadCrumb> getExtendedSearchCrumbs() {
		List<BreadCrumb> crumbs = newBaseCrumbs();
		assert crumbs != null;

		BreadCrumb extendedSearchCrumb = new BreadCrumb();
		extendedSearchCrumb.setName(i18n("extended_search_headline"));
		extendedSearchCrumb.setHint(i18n("extended_search_headline"));

		crumbs.add(extendedSearchCrumb);
		return crumbs;
	}

	// Search Crumb Generation

	private List<BreadCrumb> getSearchCrumbs() {
		List<BreadCrumb> crumbs = newBaseCrumbs();
		assert crumbs != null;

		BreadCrumb searchCrumb = new BreadCrumb();
		searchCrumb.setName(i18n("header_search"));
		searchCrumb.setHint(i18n("header_search"));

		crumbs.add(searchCrumb);
		return crumbs;
	}

	// Administration Crumb Generation

	private List<BreadCrumb> getAdministrationCrumbs() {
		List<BreadCrumb> crumbs = newBaseCrumbs();
		assert crumbs != null;

		BreadCrumb administrationCrumb = new BreadCrumb();
		administrationCrumb.setName(i18n("mainmenu_command_administration"));
		administrationCrumb.setHint(i18n("mainmenu_command_administration"));

		crumbs.add(administrationCrumb);
		return crumbs;
	}

	// Public loader methods to generate crumbs for the domain object types

	public void loadUniversityCrumbs(final Long universityId) {
		setCrumbs(getUniversityCrumbs(universityId));
	}

	public void loadUniversityCrumbs(final UniversityInfo universityInfo) {
		setCrumbs(getUniversityCrumbs(universityInfo));
	}

	public void loadDepartmentCrumbs(final Long departmentId) {
		setCrumbs(getDepartmentCrumbs(departmentId));
	}

	public void loadDepartmentCrumbs(final DepartmentInfo departmentInfo) {
		setCrumbs(getDepartmentCrumbs(departmentInfo));
	}

	public void loadInstituteCrumbs(final Long instituteId) {
		setOrganisationHierarchy(organisationService.findInstituteHierarchy(instituteId));
		setCrumbs(getInstituteCrumbs(instituteId));
	}

	public void loadInstituteCrumbs(final InstituteInfo instituteInfo) {
		setOrganisationHierarchy(organisationService.findInstituteHierarchy(instituteInfo.getId()));
		setCrumbs(getInstituteCrumbs(instituteInfo));
	}

	public void loadCourseCrumbs(final CourseInfo courseInfo) {
		setOrganisationHierarchy(organisationService.findCourseHierarchy(courseInfo.getId()));
		setCrumbs(getCourseCrumbs(courseInfo));
	}

	public void loadCourseTypeCrumbs(final CourseTypeInfo courseTypeInfo) {
		// setOrganisationHierarchy(organisationService.findCourseTypeHierarchy(courseTypeInfo.getId()));
		setCrumbs(getCourseTypeCrumbs(courseTypeInfo));
	}

	public void loadMyUniCrumbs() {
		setCrumbs(getMyUniCrumbs());
	}

	public void loadProfileCrumbs() {
		setCrumbs(getProfileCrumbs());
	}

	public void loadExtendedSearchCrumbs() {
		setCrumbs(getExtendedSearchCrumbs());
	}

	public void loadSearchCrumbs() {
		setCrumbs(getSearchCrumbs());
	}

	public void loadAdministrationCrumbs() {
		setCrumbs(getAdministrationCrumbs());
	}

	public void addCrumb(final BreadCrumb newCrumb) {
		if (newCrumb != null) {
			getCrumbs().add(newCrumb);
		}
	}

	// Getters and Setters

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(final CourseService courseService) {
		this.courseService = courseService;
	}

	public CourseTypeService getCourseTypeService() {
		return courseTypeService;
	}

	public void setCourseTypeService(final CourseTypeService courseTypeService) {
		this.courseTypeService = courseTypeService;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(final InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(final DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(final UniversityService universityService) {
		this.universityService = universityService;
	}

	public OrganisationService getOrganisationService() {
		return organisationService;
	}

	public void setOrganisationService(final OrganisationService organisationService) {
		this.organisationService = organisationService;
	}

	private void setOrganisationHierarchy(final OrganisationHierarchy organisationHierarchy) {
		if (organisationHierarchy != null) {
			this.organisationHierarchy = organisationHierarchy;
		} else {
			this.organisationHierarchy = new OrganisationHierarchy();
		}
	}

	/**
	 * @return ResoureBundle
	 */
	@Override
	public ResourceBundle getBundle() {
		Visit visit = (Visit) getBean("visit");
		if (visit != null) {
			return ResourceBundle.getBundle(getBundleName(), new Locale(visit.getLocale()));
		} else if (getFacesContext().getViewRoot() == null) {
			return ResourceBundle.getBundle(getBundleName(), getRequest().getLocale());
		} else {
			return ResourceBundle.getBundle(getBundleName(), getFacesContext().getViewRoot().getLocale());
		}
	}

}
