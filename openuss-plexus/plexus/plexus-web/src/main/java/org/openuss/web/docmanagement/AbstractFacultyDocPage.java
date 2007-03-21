package org.openuss.web.docmanagement;

import org.apache.shale.tiger.managed.Property;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.InformationService;
import org.openuss.docmanagement.Resource;
import org.openuss.lecture.Faculty;
import org.openuss.security.User;

public abstract class AbstractFacultyDocPage extends ExceptionHandler{
	
	@Property(value="#{faculty}")
	public Faculty faculty;
	
	@Property(value="#{informationService}")
	public InformationService informationService;
	
	@Property(value="#{user}")
	public User user;
	
	public  boolean hasReadPermission(Resource resource){		
		if ((resource.getVisibility()&DocRights.READ_ALL)>0) return true;
		if ((resource.getVisibility()&DocRights.READ_ASSIST)>0){
			if (informationService.userIsAssistant(faculty.getId(), user.getUsername())) return true;
		}
		if ((resource.getVisibility()&DocRights.READ_OWNER)>0){
			if (informationService.userIsAssistant(faculty.getId(), user.getUsername())||informationService.userIsOwner(resource.getPath(), user.getUsername())) return true;
		}
		
		return false;
	}

	public  boolean hasWritePermission(Resource resource){		
		if ((resource.getVisibility()&DocRights.EDIT_ALL)>0) return true;
		if ((resource.getVisibility()&DocRights.EDIT_ASSIST)>0){
			if (informationService.userIsAssistant(faculty.getId(), user.getUsername())) return true;
		}
		if ((resource.getVisibility()&DocRights.EDIT_OWNER)>0){
			if (informationService.userIsAssistant(faculty.getId(), user.getUsername())||informationService.userIsOwner(resource.getPath(), user.getUsername())) return true;
		}
		
		return false;
	}

	
	public InformationService getInformationService() {
		return informationService;
	}

	public void setInformationService(InformationService informationService) {
		this.informationService = informationService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Faculty getFaculty() {
		return faculty;
	}


	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}


}