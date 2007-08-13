package org.openuss.web.desktop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

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
public class MyUniPage extends BasePage {
	private static final Logger logger = Logger.getLogger(DesktopPage.class);

	private String	currentUniversity;
	private Long	longCurrentUniversity;
//	private DepartmentsFlexlistController	departmentsController;
	private UIFlexList flexlist;
	
	private MyUniDataSet myUniDataSet;

	
	
	@Prerender
	public void prerender() {
//		logger.debug("prerender desktop");
		refreshDesktop();
//		crumbs.clear();
	}
	
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

	public void setCurrentUniversity(String id)
	{
		currentUniversity = id;
		longCurrentUniversity = Long.valueOf(id);
	}
	
	
	public String getCurrentUniversity()
	{
		return currentUniversity;
	}
	
	public List<SelectItem> getUniversities()
	{
		ArrayList<SelectItem> result = new ArrayList<SelectItem>();
		result.add(new SelectItem(
				"1", 
				"text1"));
		result.add(new SelectItem(
				"2", 
				"text2"));
		result.add(new SelectItem(
				"3", 
				"text3"));
		return result;
	}
	
	public String getUniversityName()
	{
		if(longCurrentUniversity != null)
			switch(longCurrentUniversity.intValue())
			{
				case 1: return "abc";
				case 2: return "cde";
				case 3: return "fgh";
				default: return "keine angabe";
			}
		else return "keine Auswahl";
	}
	

	
	

			
	public UIFlexList getFlexlist()
	{
		return flexlist;
	}
	
	public void setFlexlist(UIFlexList flexlist)
	{
		this.flexlist = flexlist;
		
		initValues();
	}
	
	private void initValues()
	{
		ArrayList<ListItemDAO> visibleList = this.getVisibleItems();
		flexlist.getAttributes().put("title", getTitle());
		flexlist.getAttributes().put("showButtonTitle", getShowButtonTitle());
		flexlist.getAttributes().put("hideButtonTitle", getHideButtonTitle());
		flexlist.getAttributes().put("visibleItems", visibleList);
		flexlist.getAttributes().put("hiddenItems", getHiddenItems());
	//	flexlist.getAttributes().put("abc", new ArrayList<Long>());
	}
	


	public String getHideButtonTitle() {
		return "Weniger...";
	}

	public String getShowButtonTitle() {
		return "Mehr...";
	}
	

	public String getTitle() {
		return "Test-Liste";
	}


	public ArrayList getVisibleItems() {
		ArrayList list = new ArrayList();
		ListItemDAO newItem;
		try {
		newItem = new ListItemDAO();
		newItem.setTitle("Item 1");
		newItem.setMetaInformation("Info 1");
		list.add(newItem);
		
		newItem = new ListItemDAO();
		newItem.setTitle("Item 2");
		newItem.setMetaInformation("Info 2");
		list.add(newItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList getHiddenItems() {
		ArrayList list = new ArrayList();
		ListItemDAO newItem;
		
		newItem = new ListItemDAO();
		newItem.setTitle("Item 1");
		newItem.setMetaInformation("Info 1");
		list.add(newItem);
		
		
		newItem = new ListItemDAO();
		newItem.setTitle("Item 2");
		newItem.setMetaInformation("Info 2");
		list.add(newItem);
		
		return list;
	}
	
	public void prepareData()
	{
		myUniDataSet = new MyUniDataSet();
		Iterator iterator;
		
		
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
