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
import org.apache.shale.tiger.view.Preprocess;
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
import org.openuss.lecture.Period;
import org.openuss.lecture.LectureService;
import org.openuss.security.User;
import org.openuss.desktop.DesktopDao;


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
	
	private static final String universityBasePath = "/openuss-plexus/views/public/university/university.faces?university=";

	private Long	paramUniversity = null;
	private Long	paramRemoveDepartment = null;
	private Long	paramRemoveInstitute = null;
	private UIFlexList departmentsList;
	private UIFlexList institutesList;
	private UIFlexList coursesList;
	private UITabs tabs;
	private Desktop desktop;
	
	private MyUniDataSet myUniDataSet;

	ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
	String locale = (String)binding.getValue(getFacesContext());
	ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));

	
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
	
	public UniversityService getUniversityService() {
		return universityService;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public UniversityDao getUniversityDao() {
		return universityDao;
	}
	
	
	@Preprocess
	public void preprocess()
	{
		String s = "";
		s = s + " ";
	}
	
	@Prerender
	public void prerender() {
		logger.debug("Prerender MyUni-Page");
		breadcrumbs.loadMyUniCrumbs();
		refreshDesktop();
		loadParams();
		removeBookmarks();
		prepareData();
		loadValuesForDepartmentList();
		loadValuesForInstituteList();
		loadValuesForCourseList();
		loadValuesForTabs();
//		crumbs.clear();
	}
	
	private void refreshDesktop() {
		if (user != null) {
			try {
				if (desktopInfo == null) {
					logger.error("No desktop found for user " + user.getUsername() + ". Create new one.");
					desktopInfo = desktopService2.findDesktopByUser(user.getId());
					
				} else {
					logger.debug("refreshing desktop data");
					desktopInfo = desktopService2.findDesktop(desktopInfo.getId());
				}
				setSessionBean(Constants.DESKTOP_INFO, desktopInfo);
			
				assert desktopDao != null;
				desktop = desktopDao.load(desktopInfo.getId());
				assert desktop != null;
				
			} catch (DesktopException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}
	
	public void removeBookmarks()
	{
		if(user != null && desktopService2 != null)
		{
			Long desktopId = null;
			
			try {
				DesktopInfo desktopInfo = desktopService2.findDesktopByUser(user.getId());
				desktopId = desktopInfo.getId();
			} catch (Exception e) {
			}
			
			if(desktopId != null)
			{
				if(paramRemoveDepartment != null)
				{
					try {
						desktopService2.unlinkDepartment(desktopId, paramRemoveDepartment);
					} catch (Exception e) {
						
					}
				}

				if(paramRemoveInstitute != null)
				{
					try {
						desktopService2.unlinkInstitute(desktopId, paramRemoveInstitute);
					} catch (Exception e) {
						
					}
				}
			}
		}
	}
	
	
	public void prepareData()
	{
		logger.debug("Preparing MyUni data");
/*		if(myUniDataSet == null)
		{
			myUniDataSet = new MyUniDataSet();
			myUniDataSet.loadTestData();
		}
*/		
		
		
		if(myUniDataSet == null)
		{
			logger.debug("MyUni data not initialized, reating new data set");
			myUniDataSet = new MyUniDataSet();
			myUniDataSet.setCourseDao(courseDao);
			myUniDataSet.setDepartmentDao(departmentDao);
			myUniDataSet.setInstituteDao(instituteDao);
			myUniDataSet.setUniversityDao(universityDao);
			myUniDataSet.setUniversityService(universityService);
			myUniDataSet.setDesktop(desktop);
			
			try {
				myUniDataSet.loadData();
			} catch (Exception e) {
				logger.error("Loading MyUni data failed");
				myUniDataSet = null;
			}
		} 
	}

	
	
	private void loadParams()
	{
		logger.debug("Loading request parameters");
		Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		try {
			String stringParamUniversity = (String)params.get("university");
			paramUniversity = Long.valueOf(stringParamUniversity);
		} catch (Exception e) {
			logger.error("Error while parsing university parameter");
			paramUniversity = null;
		}
		
		try {
			String stringParamRemoveDepartment = (String)params.get("remove_department");
			paramRemoveDepartment = Long.valueOf(stringParamRemoveDepartment);
		} catch (Exception e) {
			logger.error("Error while parsing remove_department parameter");
			paramRemoveDepartment = null;
		}
		
		try {
			String stringParamRemoveDepartment = (String)params.get("remove_institute");
			paramRemoveInstitute = Long.valueOf(stringParamRemoveDepartment);
		} catch (Exception e) {
			logger.error("Error while parsing remove_institute parameter");
			paramRemoveInstitute = null;
		}
	}
	
	private void loadValuesForDepartmentList()
	{
		logger.debug("Setting values for departments flexlist");
		prepareData();
		Long universityId = chooseUniversity();
		
		
		if(universityId != null && myUniDataSet != null && departmentsList != null)
		{
			departmentsList.getAttributes().put("visibleItems", myUniDataSet.getDepartments(universityId));
		}
			
	}
	
	private void loadValuesForInstituteList()
	{
		logger.debug("Setting values for institutes flexlist");
		prepareData();
		Long universityId = chooseUniversity();
		
		
		if(universityId != null && myUniDataSet != null && institutesList != null)
		{
			institutesList.getAttributes().put("visibleItems", myUniDataSet.getInstitutes(universityId));
			institutesList.getAttributes().put("hiddenItems", myUniDataSet.getAdditionalInstitutes(universityId));
		}
	}
	
	private void loadValuesForCourseList()
	{
		logger.debug("Setting values for courses flexlist");
		prepareData();
		Long universityId = chooseUniversity();
		
		
		if(universityId != null && myUniDataSet != null && coursesList != null)
		{
			coursesList.getAttributes().put("visibleItems", myUniDataSet.getCourses(universityId));
			coursesList.getAttributes().put("hiddenItems", myUniDataSet.getAdditionalCourses(universityId));
		}
	}
	
	private void loadValuesForTabs()
	{
		logger.debug("Setting values for MyUni-Tabs");
		prepareData();
		Long universityId = chooseUniversity();
		
		
		if(universityId != null && myUniDataSet != null && tabs != null)
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
				{
					currentItem = newItem;
					currentItem.setUrl(universityBasePath + universityId);
				}
				else
					items.add(newItem);
			}
			
			tabs.getAttributes().put("currentItem", currentItem);
			tabs.getAttributes().put("items", items);
		}
	}
	

	private Long chooseUniversity()
	{
		if(myUniDataSet != null)
		{
			if(paramUniversity != null)
			{
				if(myUniDataSet.containsUniversity(paramUniversity))
					return paramUniversity;
				else
					return myUniDataSet.chooseDefaultUniversity();
			}
			else
			{
				return myUniDataSet.chooseDefaultUniversity();
			}
		}
		else 
			return null;
	}
	


			
	public UIFlexList getDepartmentsList()
	{
		return departmentsList;
	}
	
	public void setDepartmentsList(UIFlexList departmentsList)
	{
		logger.debug("Setting departments flexlist component");
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
		logger.debug("Setting institutes flexlist component");
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
		logger.debug("Setting courses flexlist component");
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
		logger.debug("Setting MyUni-tabs component");
		this.tabs = tabs;
		
		loadValuesForTabs();
	}

	
	public class MyUniDataSet
	{
		private UniversityService universityService;
		private Desktop desktop;
		private DepartmentDao departmentDao;
		private CourseDao courseDao;
		private InstituteDao instituteDao;
		private UniversityDao universityDao;
		
		private Map<Long, UniversityDataSet> uniDataSets;
		
		private static final String myuniBasePath = "/openuss-plexus/views/secured/myuni/myuni.faces";
		private static final String departmentsBasePath = "/openuss-plexus/views/public/department/department.faces?department=";
		private static final String institutesBasePath = "/openuss-plexus/views/public/institute/institute.faces?institute=";

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
			
		public boolean containsUniversity(Long id)
		{
			if(uniDataSets != null)
				return uniDataSets.containsKey(id);
			else
				return false;
		}
		
		
		public UniversityService getUniversityService() {
			return universityService;
		}

		public DepartmentDao getDepartmentDao() {
			return departmentDao;
		}

		public CourseDao getCourseDao() {
			return courseDao;
		}

		public InstituteDao getInstituteDao() {
			return instituteDao;
		}

		public UniversityDao getUniversityDao() {
			return universityDao;
		}
		
		public Desktop getDesktop() {
			return desktop;
		}

		public void setDesktop(Desktop desktop) {
			this.desktop = desktop;
		}
		
		
		
		private Long processDepartment(Department department)
		{
			if(department == null) 
				return null;
			
			
			University uni = department.getUniversity();
			if(uni == null)
				return null;

			
			Long uniID = uni.getId();
			if(uniID == null)
				return null;
			
			// Create a new data set for the uni if it does not exist yet
			assert uniDataSets != null;
			if(!uniDataSets.containsKey(uniID))
			{
				assert universityDao != null;
				UniversityInfo uniInfo = universityDao.toUniversityInfo(uni);
				if(uniInfo == null)
					return null;
				uniDataSets.put(uniID, new UniversityDataSet(uniInfo));
			}
			
			// Add the department to the university data set
			assert departmentDao != null;
			DepartmentInfo departmentInfo = departmentDao.toDepartmentInfo(department);

			if(departmentInfo == null)
				return uniID;
			
			uniDataSets.get(uniID).addDepartment(departmentInfo);
			
			return uniID;
		}
		
		
		private Long processDepartmentBookmark(Department department)
		{
			if(department == null)
				return null;
			
			University uni = department.getUniversity();
			if(uni == null)
				return null;
			
			Long uniID = uni.getId();
			if(uniID == null)
				return null;
			
			// Create a new data set for the uni if it does not exist yet
			assert uniDataSets != null;
			if(!uniDataSets.containsKey(uniID))
			{
				assert universityDao != null;
				UniversityInfo uniInfo = universityDao.toUniversityInfo(uni);
				if(uniInfo == null)
					return null;
				uniDataSets.put(uniID, new UniversityDataSet(uniInfo));
			}
			
			// Add the department to the university data set as a bookmark
			assert departmentDao != null;
			DepartmentInfo departmentInfo = departmentDao.toDepartmentInfo(department);
			
			if(departmentInfo == null)
				return uniID;
			
			uniDataSets.get(uniID).addDepartmentBookmark(departmentInfo);
			
			// return the uni id
			return uniID;
		}
		
		private Long processInstitute(Institute institute, boolean hasCorrespondingCurrentCourse)
		{	
			if(institute == null)
				return null;
			
			// process the department of the institute
			Department department = institute.getDepartment();
			if(department == null)
				return null;

			Long uniID = processDepartment(department);
			if(uniID == null)
				return null;
			
			// Add the instititute to the corresponding university data set
			assert uniDataSets != null;
			UniversityDataSet currentDataSet = uniDataSets.get(uniID);
			assert currentDataSet != null;
			
			assert instituteDao != null;
			InstituteInfo instituteInfo = instituteDao.toInstituteInfo(institute);
			if(instituteInfo == null)
				return uniID;
			
			currentDataSet.addInstitute(instituteInfo, hasCorrespondingCurrentCourse);
			
			// return the uni id
			return uniID;
			
		}
		
		private Long processInstituteBookmark(Institute institute)
		{	
			if(institute == null)
				return null;
			
			Department department = institute.getDepartment();
			if(department == null)
				return null;
			
			Long uniID = processDepartment(department);
			if(uniID == null)
				return null;
			
			InstituteInfo instituteInfo = instituteDao.toInstituteInfo(institute);
			if(instituteInfo == null)
				return uniID;
			
			UniversityDataSet currentDataSet = uniDataSets.get(uniID);
			assert currentDataSet != null;
			
			currentDataSet.addInstituteBookmark(instituteInfo);
			
			return uniID;
		
		}
		
		private Long processCourse(Course course)
		{
			if(course == null)
				return null;
			
			Period coursePeriod = course.getPeriod();
			boolean isCurrent;
			
			if(coursePeriod == null)
				isCurrent = false;
			else
				isCurrent = coursePeriod.isActive();
			
			Long uniID = processInstitute(course.getCourseType().getInstitute(), isCurrent);
			if(uniID == null)
				return null;
			
			UniversityDataSet currentDataSet = uniDataSets.get(uniID);
			assert currentDataSet != null;
			currentDataSet.addCourse(courseDao.toCourseInfo(course), isCurrent);
			
			return uniID;
	
		}
		
		public void loadData() throws Exception
		{
			if(desktop == null)
				throw new Exception("Desktop not set");
			
			if(universityService == null)
				throw new Exception("UniversityService not set");
			
			if(departmentDao == null)
				throw new Exception("DepartmentDao not set");
			
			if(courseDao == null)
				throw new Exception("CourseDao not set");
			
			if(instituteDao == null)
				throw new Exception("InstituteDao not set");
			
			if(universityDao == null)
				throw new Exception("UniversityDao not set");
			
			
			List<Course> courseBookmarks = desktop.getCourses();
			List<Institute> instituteBookmarks = desktop.getInstitutes();
			List<Department> departmentBookmarks = desktop.getDepartments();
			
			
			if(courseBookmarks != null)
			{
				Iterator<Course> courseIterator = courseBookmarks.iterator();
				while (courseIterator.hasNext()) {
					Course course = (Course)courseIterator.next();
					myUniDataSet.processCourse(course);
				}
			}
			
			if(instituteBookmarks != null)
			{
				Iterator<Institute> instituteIterator = instituteBookmarks.iterator();
				while (instituteIterator.hasNext()) {
					Institute institute = (Institute)instituteIterator.next();
					myUniDataSet.processInstituteBookmark(institute);
				}
			}
			
			if(departmentBookmarks != null)
			{
				Iterator<Department> departmentIterator = departmentBookmarks.iterator();
				while (departmentIterator.hasNext()) {
					Department department = (Department)departmentIterator.next();
					myUniDataSet.processDepartmentBookmark(department);
				}
			}
		}
		
		
		public void loadTestData()
		{
			logger.debug("Loading MyUni test data");
			
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
		
		public Long chooseDefaultUniversity()
		{
			List<UniversityInfo> unis = getUniversities();
				
			if(unis != null && unis.size() > 0)
			{
				return unis.get(0).getId();
			}
			else
				return null;
		}
		
		public List<ListItemDAO> getDepartments(Long universityId)
		{
			logger.debug("Retrieving list of departments from MyUni data set");
			
			List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
			
			if(universityId == null) {
				logger.error("universitId is null, returning empty list");
				return listItems;
			}
			
			if(uniDataSets == null) {
				logger.error("MyUni data sets are null, returning empty list");
				return listItems;
			}
				
			UniversityDataSet currentDataSet = uniDataSets.get(universityId);
			if(currentDataSet == null) {
				logger.error("University data set does not exist for the given university, returning empty list");
				return listItems;
			}
			
			Collection<DepartmentInfo> departments = currentDataSet.departments.values();
			Map<Long, Boolean> departmentBookmarks = currentDataSet.departmentBookmarks;
			
			if(departments == null) {
				logger.error("Conversion of departments map to collection object failed");
				return listItems;
			}
			
			Iterator<DepartmentInfo> i = departments.iterator();
			DepartmentInfo currentDepartment;
			ListItemDAO newListItem;
			Boolean isBookmark;
			Long departmentId;
			
			while(i.hasNext())
			{
				currentDepartment = i.next();
				newListItem = new ListItemDAO();
				newListItem.setTitle(currentDepartment.getName());
				departmentId = currentDepartment.getId();
				isBookmark = null;
				if(departmentId != null)
				{
					newListItem.setUrl(departmentsBasePath + departmentId);
					if(departmentBookmarks != null)
						isBookmark = departmentBookmarks.get(departmentId);
				}
				
				if(isBookmark != null && isBookmark.booleanValue() == true)
					newListItem.setRemoveBookmarkUrl(myuniBasePath + "?university=" + universityId + "&remove_department=" + departmentId);
				
				listItems.add(newListItem);
			}
			
			return listItems;
		}
		
		public List<ListItemDAO> getInstitutes(Long universityId)
		{
			logger.debug("Retrieving list of visible institutes from MyUni data set");
			
			List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
			
			if(universityId == null) {
				logger.error("universitId is null, returning empty list");
				return listItems;
			}
			
			if(uniDataSets == null) {
				logger.error("MyUni data sets are null, returning empty list");
				return listItems;
			}
			
			UniversityDataSet currentDataSet = uniDataSets.get(universityId);
			if(currentDataSet == null) {
				logger.error("University data set does not exist for the given university, returning empty list");
				return listItems;
			}
			
		
			Collection<InstituteInfo> institutes = currentDataSet.currentInstitutes.values();
			if(institutes == null) {
				logger.error("Conversion of institutes map to collection object failed");
				return listItems;
			}
			
			ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
			String locale = (String)binding.getValue(getFacesContext());
			ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));
			String institutesMetaInfoString = "";
			if(bundle != null)
				institutesMetaInfoString = bundle.getString(Constants.MYUNI_INSTITUTE_COURSECOUNT_STRING);

			if(bundle == null || institutesMetaInfoString == "")
				institutesMetaInfoString = "current courses";
			
			
			Map<Long, Boolean> instituteBookmarks = currentDataSet.instituteBookmarks;
			
			Iterator<InstituteInfo> i = institutes.iterator();
			InstituteInfo currentInstitute;
			Boolean isBookmark;
			ListItemDAO newListItem;
			Long instituteId;
			Integer courseCount;
			
			while(i.hasNext())
			{
				currentInstitute = i.next();
				newListItem = new ListItemDAO();
				newListItem.setTitle(currentInstitute.getName());
				instituteId = currentInstitute.getId();
				isBookmark = null;
				courseCount = null;
				
				if(instituteId != null)
				{
					newListItem.setUrl(institutesBasePath + instituteId);
					if(instituteBookmarks != null)
						isBookmark = instituteBookmarks.get(instituteId);
					
					if(currentDataSet.instituteCurrentCoursesCount != null)
						courseCount = currentDataSet.instituteCurrentCoursesCount.get(instituteId);
					
					if(courseCount != null)
						newListItem.setMetaInformation(courseCount.toString() + " " + institutesMetaInfoString);
				}
				
				if(isBookmark != null && isBookmark.booleanValue() == true)
					newListItem.setRemoveBookmarkUrl(myuniBasePath + "?university=" + universityId + "&remove_institute=" + instituteId);
				
				listItems.add(newListItem);
			}
			
			return listItems;
		}
		
		public List<ListItemDAO> getAdditionalInstitutes(Long universityId)
		{
			logger.debug("Retrieving list of hidden institutes from MyUni data set");
			
			List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
		
			if(universityId == null) {
				logger.error("universitId is null, returning empty list");
				return listItems;
			}
			
			if(uniDataSets == null) {
				logger.error("MyUni data sets are null, returning empty list");
				return listItems;
			}
			
			UniversityDataSet currentDataSet = uniDataSets.get(universityId);
			if(currentDataSet == null) {
				logger.error("University data set does not exist for the given university, returning empty list");
				return listItems;
			}
			
			Collection<InstituteInfo> institutes = currentDataSet.pastInstitutes.values();
			if(institutes == null) {
				logger.error("Conversion of institutes map to collection object failed");
				return listItems;
			}
			
			Iterator<InstituteInfo> i = institutes.iterator();
			InstituteInfo currentInstitute;
			ListItemDAO newListItem;
			Long instituteId;
			
			while(i.hasNext())
			{
				currentInstitute = i.next();
				newListItem = new ListItemDAO();
				newListItem.setTitle(currentInstitute.getName());
				instituteId = currentInstitute.getId();
				if(instituteId != null)
					newListItem.setUrl(institutesBasePath + instituteId);
				
				listItems.add(newListItem);
			}
			
			return listItems;
		}
		
		public List<ListItemDAO> getCourses(Long universityId)
		{
			logger.debug("Retrieving list of visible courses from MyUni data set");
			
			List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
			
			if(universityId == null) {
				logger.error("universitId is null, returning empty list");
				return listItems;
			}
			
			if(uniDataSets == null)  {
				logger.error("MyUni data sets are null, returning empty list");
				return listItems;
			}
			
			UniversityDataSet currentDataSet = uniDataSets.get(universityId);
			if(currentDataSet == null) {
				logger.error("University data set does not exist for the given university, returning empty list");
				return listItems;
			}
			
			Collection<CourseInfo> courses = currentDataSet.currentCourses.values();
			if(courses == null) {
				logger.error("Conversion of courses map to collection object failed");
				return listItems;
			}
			
			Iterator<CourseInfo> i = courses.iterator();
			CourseInfo currentCourse;
			ListItemDAO newListItem;
			
			while(i.hasNext())
			{
				currentCourse = i.next();
				newListItem = new ListItemDAO();
				newListItem.setTitle(currentCourse.getName());
				
				listItems.add(newListItem);
			}
			
			return listItems;
		}
		
		public List<ListItemDAO> getAdditionalCourses(Long universityId)
		{
			logger.debug("Retrieving list of hidden courses from MyUni data set");
			
			List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
			
			if(universityId == null) {
				logger.error("universitId is null, returning empty list");
				return listItems;
			}
			
			if(uniDataSets == null) {
				logger.error("MyUni data sets are null, returning empty list");
				return listItems;
			}
			
			UniversityDataSet currentDataSet = uniDataSets.get(universityId);
			if(currentDataSet == null) {
				logger.error("University data set does not exist for the given university, returning empty list");
				return listItems;
			}
			
	
			Collection<CourseInfo> courses = currentDataSet.pastCourses.values();
			if(courses == null) {
				logger.error("Conversion of courses map to collection object failed");
				return listItems;
			}
			
			Iterator<CourseInfo> i = courses.iterator();
			CourseInfo currentCourse;
			ListItemDAO newListItem;
			
			while(i.hasNext())
			{
				currentCourse = i.next();
				newListItem = new ListItemDAO();
				newListItem.setTitle(currentCourse.getName());
				
				listItems.add(newListItem);
			}
			
			return listItems;
		}
		
		public List<UniversityInfo> getUniversities()
		{
			logger.debug("Retrieving list of universities from MyUni data set");
			List<UniversityInfo> universities = new ArrayList<UniversityInfo>();
			
			if(uniDataSets == null) {
				logger.error("MyUni data sets are null, returning empty list");
				return universities;
			}
			
			Collection<UniversityDataSet> universityDataSetList = uniDataSets.values();
			if(universityDataSetList == null) {
				logger.error("Conversion of universities map to collection object failed");
				return universities;
			}
			
			Iterator<UniversityDataSet> i = universityDataSetList.iterator();
			UniversityDataSet currentUniDataSet;
			UniversityInfo currentUniInfo;
			
			while(i.hasNext())
			{
				currentUniDataSet = i.next();
				currentUniInfo = currentUniDataSet.getUniversity();
				universities.add(currentUniInfo);
			}
			
			return universities;
		}
		
		
		
		private class UniversityDataSet {
			Map<Long, CourseInfo> currentCourses;
			Map<Long, CourseInfo> pastCourses;
			Map<Long, InstituteInfo> currentInstitutes;
			Map<Long, Boolean> instituteBookmarks;
			Map<Long, Integer> instituteCurrentCoursesCount;
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
				instituteCurrentCoursesCount = new HashMap<Long, Integer>();
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
					
					if(!currentCourses.containsKey(id))
					{
						currentCourses.put(id, course);
						
						Long instituteId = course.getInstituteId();
						Integer courseCount;
						courseCount = instituteCurrentCoursesCount.get(id);
						
						if(courseCount == null)
							courseCount = 0;
						courseCount++;
						instituteCurrentCoursesCount.put(instituteId, courseCount);
					}
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
