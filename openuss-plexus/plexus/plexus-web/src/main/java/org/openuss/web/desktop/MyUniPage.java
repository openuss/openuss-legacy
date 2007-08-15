package org.openuss.web.desktop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.faces.el.ValueBinding;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Institute;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.framework.jsfcontrols.components.flexlist.ListItemDAO;
import org.openuss.framework.jsfcontrols.components.flexlist.UIFlexList;
import org.openuss.framework.jsfcontrols.components.flexlist.UITabs;

import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.lecture.LectureService;
import org.openuss.security.User;

/**
 * Display of the Startpage, after the user logged in.
 * 
 * @author Peter Grosskopf
 * @author Julian Reimann
 */
@Bean(name = "views$secured$myuni$myuni", scope = Scope.REQUEST)
@View
public class MyUniPage extends BasePage {
	private static final Logger logger = Logger.getLogger(DesktopPage.class);

	private String	currentUniversity;
	private Long	longCurrentUniversity;
//	private DepartmentsFlexlistController	departmentsController;
	private UIFlexList departmentsList;
	private UIFlexList institutesList;
	private UIFlexList coursesList;
	private UITabs tabs;
	
	private MyUniDataSet myUniDataSet;

	
	
	@Prerender
	public void prerender() {
//		logger.debug("prerender desktop");
		refreshDesktop();
		prepareData();
		loadParams();
		loadValuesForDepartmentList();
		loadValuesForInstituteList();
		loadValuesForCourseList();
		loadValuesForTabs();
//		crumbs.clear();
	}
	
	ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
	String locale = (String)binding.getValue(getFacesContext());
	ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));

	
	private void refreshDesktop() {
		if (user != null) {
			try {
				if (desktop == null) {
					logger.error("No desktop found for user " + user.getUsername() + ". Create new one.");
					desktop = desktopService.getDesktopByUser(user);
				} else {
					logger.debug("refreshing desktop data");
					desktop = desktopService.getDesktop(desktop);
				}
				setSessionBean(Constants.DESKTOP, desktop);
				
				
				
				
			} catch (DesktopException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}
	
	private void loadParams()
	{
		Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String stringUniversity = (String)params.get("university");
		setCurrentUniversity(stringUniversity);
		
		
	}
	
	private void loadValuesForDepartmentList()
	{
		prepareData();
		Long universityId = getLongCurrentUniversity();
		
		
		if(myUniDataSet != null && departmentsList != null)
			departmentsList.getAttributes().put("visibleItems", myUniDataSet.getDepartments(universityId));
	}
	
	private void loadValuesForInstituteList()
	{
		prepareData();
		Long universityId = getLongCurrentUniversity();
		
		
		if(myUniDataSet != null && institutesList != null)
		{
			institutesList.getAttributes().put("visibleItems", myUniDataSet.getInstitutes(universityId));
			institutesList.getAttributes().put("hiddenItems", myUniDataSet.getAdditionalInstitutes(universityId));
		}
	}
	
	private void loadValuesForCourseList()
	{
		prepareData();
		Long universityId = getLongCurrentUniversity();
		
		
		if(myUniDataSet != null && coursesList != null)
		{
			coursesList.getAttributes().put("visibleItems", myUniDataSet.getCourses(universityId));
			coursesList.getAttributes().put("hiddenItems", myUniDataSet.getAdditionalCourses(universityId));
		}
	}
	
	private void loadValuesForTabs()
	{
		prepareData();
		Long universityId = getLongCurrentUniversity();
		
		
		if(myUniDataSet != null && tabs != null)
		{
			ListItemDAO newItem;
			ListItemDAO currentItem = null;
			List<ListItemDAO> items = new ArrayList<ListItemDAO>();
			Iterator<UniversityInfo> i = myUniDataSet.getUniversities().iterator();
			
			
			while(i.hasNext())
			{
				UniversityInfo currentUni = i.next();
				newItem = new ListItemDAO();
				newItem.setTitle(currentUni.getName());
				newItem.setUrl("myuni.faces?university=" + currentUni.getId().toString());
				
				if(universityId != null && universityId.longValue() == currentUni.getId().longValue())
					currentItem = newItem;
				else
					items.add(newItem);
			}
			
			tabs.getAttributes().put("currentItem", currentItem);
			tabs.getAttributes().put("items", items);
		}
	}
	

	public void setCurrentUniversity(String id)
	{
		boolean useDefault = false;
		
		if(id == null)
			useDefault = true;
		else
		{
			try {
				if(myUniDataSet != null && myUniDataSet.getUniversityDataSets().containsKey(Long.valueOf(id)))
				{
					currentUniversity = id;
					longCurrentUniversity = Long.valueOf(id);
				}	
				else
					useDefault = true;
				
			} catch (NumberFormatException e) {
				useDefault = true;
			}
		}
		
		if(useDefault == true)
		{
			longCurrentUniversity = getDefaultUniversity();
			currentUniversity = longCurrentUniversity.toString();
		}
	}
	
	
	public String getCurrentUniversity()
	{
		if(currentUniversity == null)
			setCurrentUniversity(null);
		
		return currentUniversity;
	}
	
	public Long getLongCurrentUniversity()
	{
		if(longCurrentUniversity == null)
			setCurrentUniversity(null);
		
		return longCurrentUniversity;
	}
	
	public Long getDefaultUniversity()
	{
		if(myUniDataSet != null)
		{
			List<UniversityInfo> uniList = myUniDataSet.getUniversities();
			
			if(uniList.size() > 0)
				return uniList.get(0).getId();
		}
		
		return null;
	}
	


			
	public UIFlexList getDepartmentsList()
	{
		return departmentsList;
	}
	
	public void setDepartmentsList(UIFlexList departmentsList)
	{
		this.departmentsList = departmentsList;
		departmentsList.getAttributes().put("title", bundle.getString("flexlist_departments"));
		departmentsList.getAttributes().put("showButtonTitle", bundle.getString("flexlist_more_departments"));
		departmentsList.getAttributes().put("hideButtonTitle", bundle.getString("flexlist_less_departments"));
		
		loadValuesForDepartmentList();
	}
	
	public UIFlexList getInstitutesList()
	{
		return institutesList;
	}
	
	public void setInstitutesList(UIFlexList institutesList)
	{
		this.institutesList = institutesList;
		institutesList.getAttributes().put("title", bundle.getString("flexlist_institutes"));
		institutesList.getAttributes().put("showButtonTitle", bundle.getString("flexlist_more_institutes"));
		institutesList.getAttributes().put("hideButtonTitle", bundle.getString("flexlist_less_institutes"));
		
		loadValuesForInstituteList();
	}
	
	public UIFlexList getCoursesList()
	{
		return coursesList;
	}
	
	public void setCoursesList(UIFlexList coursesList)
	{
		this.coursesList = coursesList;
		coursesList.getAttributes().put("title", bundle.getString("flexlist_courses"));
		coursesList.getAttributes().put("showButtonTitle", bundle.getString("flexlist_more_courses"));
		coursesList.getAttributes().put("hideButtonTitle", bundle.getString("flexlist_less_courses"));
		
		loadValuesForCourseList();
	}
	
	public UITabs getTabs() {
		return tabs;
	}

	public void setTabs(UITabs tabs) {
		this.tabs = tabs;
		
		loadValuesForTabs();
	}
	
	
	public void prepareData()
	{
		if(myUniDataSet == null)
		{
			myUniDataSet = new MyUniDataSet();
			myUniDataSet.loadTestData();
			setCurrentUniversity(null);
		}
		
/*		Iterator iterator;
		
		
		List<Course> courses = desktop.getCourses();
		List<Institute> instituteBookmarks = desktop.getInstitutes();
		List<Department> departmentBookmarks = desktop.getDepartments();
		
		
		iterator = courses.iterator();
		while (iterator.hasNext()) {
			Course course = (Course)iterator.next();
			myUniDataSet.processCourse(course);
		}
		
		iterator = instituteBookmarks.iterator();
		while (iterator.hasNext()) {
			Institute institute = (Institute)iterator.next();
			myUniDataSet.processInstituteBookmark(institute);
		}
		
		iterator = departmentBookmarks.iterator();
		while (iterator.hasNext()) {
			Department department = (Department)iterator.next();
			myUniDataSet.processDepartmentBookmark(department);
		}
*/	
	}

	
	public class MyUniDataSet {
		
		@Property(value="#{universityService}")
		UniversityService universityService;
		
		@Property(value="#{departmentDao}")
		DepartmentDao departmentDao;
		
		@Property(value="#{courseDao}")
		CourseDao courseDao;
		
		@Property(value="#{instituteDao}")
		InstituteDao instituteDao;
		
		@Property(value="#{universityDao}")
		UniversityDao universityDao;
		
		private Map<Long, UniversityDataSet> uniDataSets;
		
		public MyUniDataSet() {
			uniDataSets = new HashMap<Long, UniversityDataSet>();
		}
		
		public void setUniversityService(UniversityService universityService)
		{
			this.universityService = universityService;
		}
		
		public void setDepartmentDao(DepartmentDao departmentDao)
		{
			this.departmentDao = departmentDao;
		}
		
		public void setInstituteDao(InstituteDao instituteDao)
		{
			this.instituteDao = instituteDao;
		}
		
		public void setCourseDao(CourseDao courseDao)
		{
			this.courseDao = courseDao;
		}
		
		public void setUniversityDao(UniversityDao universityDao)
		{
			this.universityDao = universityDao;
		}
		
		public Map<Long, UniversityDataSet> getUniversityDataSets()
		{
			return uniDataSets;
		}
		
		
		
		
		public Long processDepartment(Department department)
		{
			University uni = department.getUniversity();
			Long uniID = uni.getId();
			
			if(!uniDataSets.containsKey(uniID))
			{
				uniDataSets.put(uniID, new UniversityDataSet(universityDao.toUniversityInfo(uni)));
			}
			
			uniDataSets.get(uniID).addDepartment(departmentDao.toDepartmentInfo(department));
			
			return uniID;
		}
		
		public Long processDepartmentBookmark(Department department)
		{
			University uni = department.getUniversity();
			Long uniID = uni.getId();
			
			if(!uniDataSets.containsKey(uniID))
			{
				uniDataSets.put(uniID, new UniversityDataSet(universityDao.toUniversityInfo(uni)));
			}
			
			uniDataSets.get(uniID).addDepartmentBookmark(departmentDao.toDepartmentInfo(department));
			
			return uniID;
		}
		
		public Long processInstitute(Institute institute, boolean isCurrent)
		{	
			Long uniID = processDepartment(institute.getDepartment());
			uniDataSets.get(uniID).addInstitute(instituteDao.toInstituteInfo(institute), isCurrent);
			
			return uniID;
		}
		
		public Long processInstituteBookmark(Institute institute)
		{	
			Long uniID = processDepartment(institute.getDepartment());
			uniDataSets.get(uniID).addInstituteBookmark(instituteDao.toInstituteInfo(institute));
			
			return uniID;
		}
		
		public Long processCourse(Course course)
		{
			boolean isCurrent = course.getPeriod().isActive();
			Long uniID = processInstitute(course.getCourseType().getInstitute(), isCurrent);
			uniDataSets.get(uniID).addCourse(courseDao.toCourseInfo(course), isCurrent);
			return uniID;
		}
		
		public void loadTestData()
		{
			UniversityInfo uniInfo;
			DepartmentInfo departmentInfo;
			CourseInfo courseInfo;
			UniversityDataSet uniDataSet;
			
			
			// Create Uni 1 and Subitems
			uniInfo = new UniversityInfo();
			uniInfo.setId(1L);
			uniInfo.setName("Uni Münster");
			uniDataSet = new UniversityDataSet(uniInfo);
			
			departmentInfo = new DepartmentInfo();
			departmentInfo.setId(1L);
			departmentInfo.setName("Fachbereich 4");
			departmentInfo.setUniversityId(1L);
			uniDataSet.addDepartment(departmentInfo);
			
			departmentInfo = new DepartmentInfo();
			departmentInfo.setId(2L);
			departmentInfo.setName("Fachbereich 5");
			departmentInfo.setUniversityId(1L);
			uniDataSet.addDepartment(departmentInfo);
			
			departmentInfo = new DepartmentInfo();
			departmentInfo.setId(3L);
			departmentInfo.setName("Fachbereich 6");
			departmentInfo.setUniversityId(1L);
			uniDataSet.addDepartment(departmentInfo);
			
			courseInfo = new CourseInfo();
			courseInfo.setId(1L);
			courseInfo.setName("KLR");
			uniDataSet.addCourse(courseInfo, true);
			
			courseInfo = new CourseInfo();
			courseInfo.setId(2L);
			courseInfo.setName("BWL1");
			uniDataSet.addCourse(courseInfo, true);
			
			courseInfo = new CourseInfo();
			courseInfo.setId(3L);
			courseInfo.setName("BWL2");
			uniDataSet.addCourse(courseInfo, false);
			
			courseInfo = new CourseInfo();
			courseInfo.setId(4L);
			courseInfo.setName("BWL3");
			uniDataSet.addCourse(courseInfo, false);

			uniDataSets.put(1L, uniDataSet);
		
			// Create Uni 2 and subitems
			uniInfo = new UniversityInfo();
			uniInfo.setId(2L);
			uniInfo.setName("Uni Bonn");
			uniDataSet = new UniversityDataSet(uniInfo);
			
			
			departmentInfo = new DepartmentInfo();
			departmentInfo.setId(4L);
			departmentInfo.setName("Fachbereich 4");
			departmentInfo.setUniversityId(2L);
			uniDataSet.addDepartment(departmentInfo);
			
			departmentInfo = new DepartmentInfo();
			departmentInfo.setId(5L);
			departmentInfo.setName("Fachbereich 8");
			departmentInfo.setUniversityId(2L);
			uniDataSet.addDepartment(departmentInfo);
			
			
			courseInfo = new CourseInfo();
			courseInfo.setId(1L);
			courseInfo.setName("Kosten- und Leistungsrechnung");
			uniDataSet.addCourse(courseInfo, true);
			
			courseInfo = new CourseInfo();
			courseInfo.setId(2L);
			courseInfo.setName("Informatik 1");
			uniDataSet.addCourse(courseInfo, true);
			
			courseInfo = new CourseInfo();
			courseInfo.setId(3L);
			courseInfo.setName("Informatik 2");
			uniDataSet.addCourse(courseInfo, true);
			
			courseInfo = new CourseInfo();
			courseInfo.setId(4L);
			courseInfo.setName("Unternehmensgründung Märkte und Branchen");
			uniDataSet.addCourse(courseInfo, false);
			
			uniDataSets.put(2L, uniDataSet);
			
			// Create Uni 3 and subitems
			uniInfo = new UniversityInfo();
			uniInfo.setId(3L);
			uniInfo.setName("Uni Köln");
			uniDataSet = new UniversityDataSet(uniInfo);
			
			departmentInfo = new DepartmentInfo();
			departmentInfo.setId(6L);
			departmentInfo.setName("Fachbereich 1");
			departmentInfo.setUniversityId(3L);
			uniDataSet.addDepartment(departmentInfo);
			
			departmentInfo = new DepartmentInfo();
			departmentInfo.setId(7L);
			departmentInfo.setName("Fachbereich 7");
			departmentInfo.setUniversityId(3L);
			uniDataSet.addDepartment(departmentInfo);
			
			departmentInfo = new DepartmentInfo();
			departmentInfo.setId(8L);
			departmentInfo.setName("Fachbereich 8");
			departmentInfo.setUniversityId(8L);
			uniDataSet.addDepartment(departmentInfo);
			
			
			courseInfo = new CourseInfo();
			courseInfo.setId(1L);
			courseInfo.setName("Einführung in die WI");
			uniDataSet.addCourse(courseInfo, true);
			
			courseInfo = new CourseInfo();
			courseInfo.setId(2L);
			courseInfo.setName("Datenbanken");
			uniDataSet.addCourse(courseInfo, false);
			
			courseInfo = new CourseInfo();
			courseInfo.setId(3L);
			courseInfo.setName("Einführung in die Java Framework-Theorie");
			uniDataSet.addCourse(courseInfo, false);
			
			courseInfo = new CourseInfo();
			courseInfo.setId(4L);
			courseInfo.setName("OpenUSS Projektseminar");
			uniDataSet.addCourse(courseInfo, false);
			
			
			uniDataSets.put(3L, uniDataSet);
			
		}
		
		public List<ListItemDAO> getDepartments(Long universityId)
		{
			List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
			Collection<DepartmentInfo> departments = uniDataSets.get(universityId).departments.values();
			Map<Long, Boolean> departmentBookmarks = uniDataSets.get(universityId).departmentBookmarks;
			Iterator<DepartmentInfo> i = departments.iterator();
			
			while(i.hasNext())
			{
				DepartmentInfo currentDepartment = i.next();
				ListItemDAO newListItem = new ListItemDAO();
				newListItem.setTitle(currentDepartment.getName());
				newListItem.setIsBookmark(instituteBookmarks.get(currentInstitute.getId()) == true);
				listItems.add(newListItem);
			}
			
			return listItems;
		}
		
		public List<ListItemDAO> getInstitutes(Long universityId)
		{
			List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
			
			if(universityId != null && uniDataSets.containsKey(universityId))
			{
				Collection<InstituteInfo> institutes = uniDataSets.get(universityId).currentInstitutes.values();
				Map<Long, Boolean> instituteBookmarks = uniDataSets.get(universityId).instituteBookmarks;
				Iterator<InstituteInfo> i = institutes.iterator();
				
				while(i.hasNext())
				{
					InstituteInfo currentInstitute = i.next();
					ListItemDAO newListItem = new ListItemDAO();
					newListItem.setTitle(currentInstitute.getName());
					newListItem.setIsBookmark(instituteBookmarks.get(currentInstitute.getId()) == true);
					
					listItems.add(newListItem);
				}
			}
			
			return listItems;
		}
		
		public List<ListItemDAO> getAdditionalInstitutes(Long universityId)
		{
			List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
			
			if(universityId != null && uniDataSets.containsKey(universityId))
			{
				Collection<InstituteInfo> institutes = uniDataSets.get(universityId).pastInstitutes.values();
				Iterator<InstituteInfo> i = institutes.iterator();
				
				while(i.hasNext())
				{
					InstituteInfo currentInstitute = i.next();
					ListItemDAO newListItem = new ListItemDAO();
					newListItem.setTitle(currentInstitute.getName());
					listItems.add(newListItem);
				}
			}
			
			return listItems;
		}
		
		public List<ListItemDAO> getCourses(Long universityId)
		{
			List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
			
			if(universityId != null && uniDataSets.containsKey(universityId))
			{
				Collection<CourseInfo> institutes = uniDataSets.get(universityId).currentCourses.values();
				Iterator<CourseInfo> i = institutes.iterator();
				
				while(i.hasNext())
				{
					CourseInfo currentCourse = i.next();
					ListItemDAO newListItem = new ListItemDAO();
					newListItem.setTitle(currentCourse.getName());
					// Set bookmark flag as courses are always bookmarked
					newListItem.setIsBookmark(true);
					listItems.add(newListItem);
				}
			}
			
			return listItems;
		}
		
		public List<ListItemDAO> getAdditionalCourses(Long universityId)
		{
			List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
			
			if(universityId != null && uniDataSets.containsKey(universityId))
			{
				Collection<CourseInfo> courses = uniDataSets.get(universityId).pastCourses.values();
				Iterator<CourseInfo> i = courses.iterator();
				
				while(i.hasNext())
				{
					CourseInfo currentCourse = i.next();
					ListItemDAO newListItem = new ListItemDAO();
					newListItem.setTitle(currentCourse.getName());
					// Set bookmark flag as courses are always bookmarked
					newListItem.setIsBookmark(true);
					listItems.add(newListItem);
				}
			}
			
			return listItems;
		}
		
		public List<UniversityInfo> getUniversities()
		{
			List<UniversityInfo> universities = new ArrayList<UniversityInfo>();
			
			Collection<UniversityDataSet> universityDataSetList = uniDataSets.values();
			Iterator<UniversityDataSet> i = universityDataSetList.iterator();
			
			while(i.hasNext())
			{
				UniversityDataSet currentUniDataSet = i.next();
				universities.add(currentUniDataSet.getUniversity());
			}
			
			return universities;
		}
		
		
		
		private class UniversityDataSet {
			Map<Long, CourseInfo> currentCourses;
			Map<Long, CourseInfo> pastCourses;
			Map<Long, InstituteInfo> currentInstitutes;
			Map<Long, Boolean> instituteBookmarks;
			Map<Long, InstituteInfo> pastInstitutes;
			Map<Long, DepartmentInfo> departments;
			Map<Long, Boolean> departmentBookmarks;
			UniversityInfo university;
			
			
			public UniversityDataSet(UniversityInfo university) {
				this.university = university;
				
				currentCourses = new HashMap<Long, CourseInfo>();
				pastCourses = new HashMap<Long, CourseInfo>();
				currentInstitutes = new HashMap<Long, InstituteInfo>();
				instituteBookmarks = new HashMap<Long, Boolean>();
				pastInstitutes = new HashMap<Long, InstituteInfo>();
				departments = new HashMap<Long, DepartmentInfo>();
				departmentBookmarks = new HashMap<Long, Boolean>();			
			}
			
			public void setUniversity(UniversityInfo newUni)
			{
				university = newUni;
			}
			
			public UniversityInfo getUniversity()
			{
				return university;
			}
			
			public void addDepartment(DepartmentInfo department)
			{
				departments.put(department.getId(), department);
			}
			
			public void addDepartmentBookmark(DepartmentInfo department)
			{
				addDepartment(department);
				departmentBookmarks.put(department.getId(), true);
			}
			
			
			public void addInstitute(InstituteInfo institute, boolean isCurrent)
			{
				Long id = institute.getId();
				
				if(isCurrent == true)
				{
					if(pastInstitutes.containsKey(id))
						pastInstitutes.remove(id);
					
					currentInstitutes.put(id, institute);
				}
				else
				{
					if(!currentInstitutes.containsKey(id))
						pastInstitutes.put(id, institute);
				}
			}
			
			public void addInstituteBookmark(InstituteInfo institute)
			{
				addInstitute(institute, true);
				instituteBookmarks.put(institute.getId(), true);
				
			}
			
			public void addCourse(CourseInfo course, boolean isCurrent)
			{
				Long id = course.getId();
				
				if(isCurrent == true)
				{
					if(pastCourses.containsKey(id))
						pastCourses.remove(id);
					
					currentCourses.put(id, course);
				}
				else
				{
					if(!currentCourses.containsKey(id))
						pastCourses.put(id, course);
				}
			}
		}
		
		
	}

}
