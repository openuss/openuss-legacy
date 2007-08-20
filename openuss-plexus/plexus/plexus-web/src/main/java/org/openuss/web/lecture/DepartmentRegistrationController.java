package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.lecture.CourseMember;
import org.openuss.lecture.CourseMemberType;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityInfo;
import org.openuss.web.Constants;


/**
 * Backing bean for the department registration. Is responsible starting the wizard, binding the values and registrating
 * the department.
 * 
 * @author Kai Stettner
 *
 */

@Bean(name=Constants.DEPARTMENT_REGISTRATION_CONTROLLER, scope=Scope.REQUEST)
@View
public class DepartmentRegistrationController extends AbstractDepartmentPage{

	private static final Logger logger = Logger.getLogger(DepartmentRegistrationController.class);

	protected UniversityInfo universityInfo = (UniversityInfo)this.getSessionBean(Constants.UNIVERSITY_INFO);
	
	private List<SelectItem> localeItems;
	
	private List<SelectItem> universityItems;
	private List<UniversityInfo> allUniversities;
	
	private DepartmentType departmentType;
	

	public String start() {
		
		logger.debug("start registration process");
		departmentInfo = new DepartmentInfo() ;
		setSessionBean(Constants.DEPARTMENT_INFO, departmentInfo);
		
		return Constants.DEPARTMENT_REGISTRATION_STEP1_PAGE;
	}
	
	public List<SelectItem> getSupportedDepartmentTypes() {
		
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
		String locale = (String)binding.getValue(getFacesContext());
		ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));
	
		localeItems = new ArrayList<SelectItem>();
	
		SelectItem item1 = new SelectItem(departmentType.OFFICIAL, bundle.getString("departmenttype_official"));
		SelectItem item2 = new SelectItem(departmentType.NONOFFICIAL, bundle.getString("departmenttype_non_offical"));
		
		localeItems.add(item1);
		localeItems.add(item2);

		return localeItems;
	}
	
	public List<SelectItem> getAllUniversities() {
		
		universityItems = new ArrayList<SelectItem>();
		
		allUniversities = universityService.findAllUniversities();
		
		Iterator<UniversityInfo> iter =  allUniversities.iterator();
		UniversityInfo university;
		
		while (iter.hasNext()) {
			university = iter.next();
			SelectItem item = new SelectItem(university.getId(),university.getName());
			universityItems.add(item);
		}
		
		return universityItems;
	}
	
	public String registrate() throws DesktopException, LectureException {
		
		//create department
		
		//by default set department enabled
		departmentInfo.setEnabled(true);
		departmentService.create(departmentInfo, user.getId());
	
		// bookmark department to myuni page
		desktopService2.linkDepartment(desktopInfo.getId(), departmentInfo.getId());
		
		return Constants.DEPARTMENT_PAGE;
	}
	
}
