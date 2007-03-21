package org.openuss.web.docmanagement;

import org.apache.shale.tiger.managed.Property;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.InformationService;
import org.openuss.docmanagement.Resource;
import org.openuss.lecture.Enrollment;
import org.openuss.security.User;

public abstract class AbstractEnrollmentDocPage extends ExceptionHandler{
	
	@Property(value="#{enrollment}")
	public Enrollment enrollment;
	
	@Property(value="#{informationService}")
	public InformationService informationService;
	
	@Property(value="#{user}")
	public User user;
	
	public  boolean hasReadPermission(Resource resource){		
		if ((resource.getVisibility()&DocRights.READ_ALL)>0) return true;
		if ((resource.getVisibility()&DocRights.READ_ASSIST)>0){
			if (informationService.userIsAssistant(enrollment, user.getUsername())) return true;
		}
		if ((resource.getVisibility()&DocRights.READ_OWNER)>0){
			if (informationService.userIsAssistant(enrollment, user.getUsername())||informationService.userIsOwner(resource.getPath(), user.getUsername())) return true;
		}
		
		return false;
	}

	public  boolean hasWritePermission(Resource resource){		
		if ((resource.getVisibility()&DocRights.EDIT_ALL)>0) return true;
		if ((resource.getVisibility()&DocRights.EDIT_ASSIST)>0){
			if (informationService.userIsAssistant(enrollment, user.getUsername())) return true;
		}
		if ((resource.getVisibility()&DocRights.EDIT_OWNER)>0){
			if (informationService.userIsAssistant(enrollment, user.getUsername())||informationService.userIsOwner(resource.getPath(), user.getUsername())) return true;
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
	

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

}