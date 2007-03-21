package org.openuss.web.docmanagement;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.Resource;
import org.openuss.lecture.Faculty;

public abstract class AbstractFacultyDocPage extends ExceptionHandler{
	
	
	final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	public static final Logger log = Logger.getLogger(AbstractFacultyDocPage.class);
	
	@Property(value="#{faculty}")
	public Faculty faculty;
	
	public Long id;
	
	public  boolean hasReadPermission(Resource resource){		
		if ((resource.getVisibility()&DocRights.READ_ALL)>0) return true;
		if ((resource.getVisibility()&DocRights.READ_ASSIST)>0){
			GrantedAuthority[] ga = auth.getAuthorities();
			for (int i = 0; i < ga.length; i++){
				if (ga[i].getAuthority().startsWith("GROUP_FACULTY_"+id.toString())) return true;
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
			GrantedAuthority[] ga = auth.getAuthorities();
			for (int i = 0; i < ga.length; i++){
				if (ga[i].getAuthority().startsWith("GROUP_FACULTY_"+id.toString())) return true;
			}
		}
		if ((resource.getVisibility()&DocRights.EDIT_OWNER)>0){
			//TODO
		}
		
		return false;
	}

	public Faculty getFaculty() {
		return faculty;
	}


	public void setFaculty(Faculty faculty) {
		this.id = faculty.getId();
		this.faculty = faculty;
	}
	


}