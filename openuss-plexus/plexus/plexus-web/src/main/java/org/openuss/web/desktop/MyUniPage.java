package org.openuss.web.desktop;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Institute;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.framework.jsfcontrols.components.flexlist.ListItemDAO;
import org.openuss.framework.jsfcontrols.components.flexlist.UIFlexList;

/**
 * Display of the Startpage, after the user logged in.
 * 
 * @author Peter Grosskopf
 * @author Julian Reimann
 */
@Bean(name = "views$secured$myuni$myuni", scope = Scope.SESSION)
public class MyUniPage extends BasePage {
	private static final Logger logger = Logger.getLogger(DesktopPage.class);

	private String	currentUniversity;
	private Long	longCurrentUniversity;
//	private DepartmentsFlexlistController	departmentsController;
	private UIFlexList flexlist;
	
	
	public MyUniPage()
	{
		super();
		
		setCurrentUniversity("2");
//		departmentsController = new DepartmentsFlexlistController();
	}
	
	@Prerender
	public void prerender() {
//		logger.debug("prerender desktop");
//		refreshDesktop();
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
		ArrayList<SelectItem> resultTypes = new ArrayList<SelectItem>();
		resultTypes.add(new SelectItem(
				"1", 
				"text1"));
		resultTypes.add(new SelectItem(
				"2", 
				"text2"));
		resultTypes.add(new SelectItem(
				"3", 
				"text3"));
		return resultTypes;
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
		flexlist.getAttributes().put("title", getTitle());
		flexlist.getAttributes().put("showButtonTitle", getShowButtonTitle());
		flexlist.getAttributes().put("hideButtonTitle", getHideButtonTitle());
		flexlist.getAttributes().put("visibleItems", getVisibleItems());
		flexlist.getAttributes().put("hiddenItems", getHiddenItems());
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
	
}
