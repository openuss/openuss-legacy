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
import org.openuss.desktop.MyUniInfo;
import org.openuss.desktop.MyUniUniversityInfo;
import org.openuss.desktop.MyUniDepartmentInfo;
import org.openuss.desktop.MyUniInstituteInfo;
import org.openuss.desktop.MyUniCourseInfo;
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
	
	private Map<Long, MyUniInfo> myUniData;

	private static final String myUniBasePath = "/openuss-plexus/views/secured/myuni/myuni.faces";
	private static final String departmentsBasePath = "/openuss-plexus/views/public/department/department.faces";
	private static final String institutesBasePath = "/openuss-plexus/views/public/institute/institute.faces";
	private static final String coursesBasePath = "/openuss-plexus/views/secured/course/main.faces";
	
	ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
	String locale = (String)binding.getValue(getFacesContext());
	ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));

	
	
	@Prerender
	public void prerender() {
		logger.debug("Prerender MyUni-Page");
		refreshDesktop();
		loadParams();
		removeBookmarks();
		prepareData();
		breadcrumbs.loadMyUniCrumbs();
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
	
	
	public void prepareData()
	{
		logger.debug("Preparing MyUni data");		
		if(myUniData == null)
		{
			try {
				myUniData = (Map<Long, MyUniInfo>)desktopService2.getMyUniInfo(user.getId());
			} catch (Exception e) {
				
			}
		}
	}


	private void loadValuesForDepartmentList(UIFlexList departmentsList)
	{
		logger.debug("Setting values for departments flexlist");
		if(departmentsList != null)
		{
			prepareData();
			Long universityId = chooseUniversity();
			
			
/*			if(universityId != null && myUniDataSet != null && departmentsList != null)
			{
				departmentsList.getAttributes().put("visibleItems", myUniDataSet.getDepartments(universityId));
			}
*/
			if(universityId != null && myUniData != null)
			{
				departmentsList.getAttributes().put("visibleItems", getDepartmentListItems(universityId));
			}
		}
	}

	private void loadValuesForInstituteList(UIFlexList institutesList)
	{
		logger.debug("Setting values for institutes flexlist");
		if(institutesList != null)
		{
			prepareData();
			Long universityId = chooseUniversity();
				
			if(universityId != null && myUniData != null)
			{
				institutesList.getAttributes().put("visibleItems", getVisibleInstituteListItems(universityId));
				institutesList.getAttributes().put("hiddenItems", getHiddenInstituteListItems(universityId));
			}
		}
	}

	private void loadValuesForCourseList(UIFlexList coursesList)
	{
		logger.debug("Setting values for courses flexlist");
		if(coursesList != null)
		{
			prepareData();
			Long universityId = chooseUniversity();
			
			if(universityId != null && myUniData != null)
			{
				coursesList.getAttributes().put("visibleItems", getVisibleCourseListItems(universityId));
				coursesList.getAttributes().put("hiddenItems", getHiddenCourseListItems(universityId));
			}
		}
	}

	private void loadValuesForTabs(UITabs tabs)
	{
		logger.debug("Setting values for MyUni-Tabs");
		if(tabs != null)
			{
			prepareData();
			Long universityId = chooseUniversity();
			
			
			if(universityId != null && myUniData != null)
			{
				MyUniUniversityInfo universityInfo;
				ListItemDAO newItem;
				ListItemDAO currentItem = null;
				List<ListItemDAO> items = new ArrayList<ListItemDAO>();
				
				
				for(MyUniInfo myUniInfo : myUniData.values())
				{
					universityInfo = myUniInfo.getMyUniUniversityInfo();
					
					if(universityInfo != null)
					{
						newItem = new ListItemDAO();
						newItem.setTitle(universityInfo.getName());
						newItem.setUrl(myUniBasePath + "?university=" + universityInfo.getId().toString());
						
						if(universityId != null && universityId.longValue() == universityInfo.getId().longValue())
						{
							currentItem = newItem;
							currentItem.setUrl(universityBasePath + universityId);
						}
						else
							items.add(newItem);
					}
				}
				
				tabs.getAttributes().put("currentItem", currentItem);
				tabs.getAttributes().put("items", items);
			}
		}
	}

	private List<ListItemDAO> getDepartmentListItems(Long universityId)
	{
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
		
		if(myUniData != null)
		{
			MyUniInfo myUniInfo = myUniData.get(universityId);
			if(myUniInfo != null)
			{
				ListItemDAO newItem;
				Collection<MyUniDepartmentInfo> departmentCollection = myUniInfo.getDepartments();

				for(MyUniDepartmentInfo departmentInfo : departmentCollection)
				{
					newItem = new ListItemDAO();
					newItem.setTitle(departmentInfo.getName());
					newItem.setUrl(departmentsBasePath + "?department=" + departmentInfo.getId());
					if(departmentInfo.isBookmarked())
						newItem.setRemoveBookmarkUrl(myUniBasePath + "?university=" + universityId + "&remove_department=" + departmentInfo.getId());

					listItems.add(newItem);
				}
			}
		}
		
		return listItems;
	}

	private List<ListItemDAO> getVisibleInstituteListItems(Long universityId)
	{
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
		
		if(myUniData != null)
		{
			MyUniInfo myUniInfo = myUniData.get(universityId);
			if(myUniInfo != null)
			{
				ListItemDAO newItem;
				Collection<MyUniInstituteInfo> instituteCollection = myUniInfo.getCurrentInstitutes();

				for(MyUniInstituteInfo instituteInfo : instituteCollection)
				{
					newItem = new ListItemDAO();
					newItem.setTitle(instituteInfo.getName());
					newItem.setUrl(institutesBasePath + "?institute=" + instituteInfo.getId());
					if(instituteInfo.isBookmarked())
						newItem.setRemoveBookmarkUrl(myUniBasePath + "?university=" + universityId + "&remove_institute=" + instituteInfo.getId());

					listItems.add(newItem);
				}
			}
		}
		
		return listItems;
	}

	private List<ListItemDAO> getHiddenInstituteListItems(Long universityId)
	{
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
		
		if(myUniData != null)
		{
			MyUniInfo myUniInfo = myUniData.get(universityId);
			if(myUniInfo != null)
			{
				ListItemDAO newItem;
				Collection<MyUniInstituteInfo> instituteCollection = myUniInfo.getPastInstitutes();

				for(MyUniInstituteInfo instituteInfo : instituteCollection)
				{
					newItem = new ListItemDAO();
					newItem.setTitle(instituteInfo.getName());
					newItem.setUrl(institutesBasePath + "?institute=" + instituteInfo.getId());
					if(instituteInfo.isBookmarked())
						newItem.setRemoveBookmarkUrl(myUniBasePath + "?university=" + universityId + "&remove_institute=" + instituteInfo.getId());

					listItems.add(newItem);
				}
			}
		}
		
		return listItems;
	}

	private List<ListItemDAO> getVisibleCourseListItems(Long universityId)
	{
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
		
		if(myUniData != null)
		{
			MyUniInfo myUniInfo = myUniData.get(universityId);
			if(myUniInfo != null)
			{
				ListItemDAO newItem;
				Collection<MyUniCourseInfo> courseCollection = myUniInfo.getCurrentCourses();

				for(MyUniCourseInfo courseInfo : courseCollection)
				{
					newItem = new ListItemDAO();
					newItem.setTitle(courseInfo.getName());
					newItem.setUrl(coursesBasePath + "?course=" + courseInfo.getId());
					listItems.add(newItem);
				}
			}
		}
		
		return listItems;
	}

	private List<ListItemDAO> getHiddenCourseListItems(Long universityId)
	{
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
		
		if(myUniData != null)
		{
			MyUniInfo myUniInfo = myUniData.get(universityId);
			if(myUniInfo != null)
			{
				ListItemDAO newItem;
				Collection<MyUniCourseInfo> courseCollection = myUniInfo.getPastCourses();

				for(MyUniCourseInfo courseInfo : courseCollection)
				{
					newItem = new ListItemDAO();
					newItem.setTitle(courseInfo.getName());
					newItem.setUrl(coursesBasePath + "?course=" + courseInfo.getId());
					listItems.add(newItem);
				}
			}
		}
		
		return listItems;
	}


	private Long chooseUniversity()
	{
		if(myUniData != null)
		{
			// Return the university parameter if it is contained in out data set
			if(paramUniversity != null)
				if(myUniData.containsKey(paramUniversity))
					return paramUniversity;
			
			// Return the university id in our data set
			Iterator<MyUniInfo> iterator = myUniData.values().iterator();
			MyUniInfo firstUni = iterator.next();
			if(firstUni != null)
			{
				MyUniUniversityInfo uniInfo = firstUni.getMyUniUniversityInfo();
				if(uniInfo != null)
					return uniInfo.getId();
			}
		}
		
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
		departmentsList.getAttributes().put("alternateRemoveBookmarkLinkTitle", bundle.getString("flexlist_remove_bookmark"));
		
		loadValuesForDepartmentList(departmentsList);
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
		institutesList.getAttributes().put("alternateRemoveBookmarkLinkTitle", bundle.getString("flexlist_remove_bookmark"));
		
		
		loadValuesForInstituteList(institutesList);
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
		coursesList.getAttributes().put("alternateRemoveBookmarkLinkTitle", bundle.getString("flexlist_remove_bookmark"));
		
		loadValuesForCourseList(coursesList);
	}

	public UITabs getTabs() {
		return tabs;
	}

	public void setTabs(UITabs tabs) {
		logger.debug("Setting MyUni-tabs component");
		this.tabs = tabs;
		tabs.getAttributes().put("alternateLinkTitle", bundle.getString("flexlist_tabs_details"));
		
		loadValuesForTabs(tabs);
	}
}
