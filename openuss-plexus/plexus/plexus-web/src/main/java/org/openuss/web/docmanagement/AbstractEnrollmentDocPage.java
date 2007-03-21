package org.openuss.web.docmanagement;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.Resource;
import org.openuss.lecture.Enrollment;


public abstract class AbstractEnrollmentDocPage extends ExceptionHandler{
	
	@Property(value="#{enrollment}")
	public Enrollment enrollment;
	
	final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	public static final Logger enrollmentLogger = Logger.getLogger(AbstractEnrollmentDocPage.class);
	
	public  boolean hasReadPermission(Resource resource){		
		if ((resource.getVisibility()&DocRights.READ_ALL)>0) return true;
		if ((resource.getVisibility()&DocRights.READ_ASSIST)>0){
			GrantedAuthority[] ga = auth.getAuthorities();
			for (int i = 0; i < ga.length; i++){
				//if (ga[i].getAuthority().startsWith("GROUP_FACULTY_"+id.toString())) return true;
				enrollmentLogger.debug(ga[i].getAuthority());
			}			
		}
		if ((resource.getVisibility()&DocRights.READ_OWNER)>0){
			//TODO
		}
		
		return false;
	}

	public  boolean hasWritePermission(Resource resource){		
		if ((resource.getVisibility()&DocRights.EDIT_ALL)>0) return true;
		if ((resource.getVisibility()&DocRights.EDIT_ASSIST)>0){

		}
		if ((resource.getVisibility()&DocRights.EDIT_OWNER)>0){
			//TODO
		}
		
		return false;
	}
	
	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

}