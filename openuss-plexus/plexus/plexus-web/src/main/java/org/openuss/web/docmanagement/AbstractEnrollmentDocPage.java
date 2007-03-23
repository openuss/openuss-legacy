package org.openuss.web.docmanagement;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.Link;
import org.openuss.docmanagement.Resource;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.Faculty;

public abstract class AbstractEnrollmentDocPage extends ExceptionHandler {

	@Property(value = "#{enrollment}")
	public Enrollment enrollment;

	@Property(value = "#{faculty}")
	public Faculty faculty;

	public final Authentication auth = SecurityContextHolder.getContext()
			.getAuthentication();

	public static final Logger enrollmentLogger = Logger
			.getLogger(AbstractEnrollmentDocPage.class);

	public boolean hasReadPermission(Resource resource) {
		boolean allowed = false;
		if ((resource.getVisibility() & DocRights.READ_ALL) > 0) {
			if (distTimeReached(resource))
				allowed = true;
			;
			GrantedAuthority[] ga = auth.getAuthorities();
			for (int i = 0; i < ga.length; i++) {
				if (ga[i].getAuthority().startsWith(
						"GROUP_FACULTY_" + faculty.getId().toString()))
					allowed = true;
				;
			}
		}
		if (allowed)
			return true;
		if ((resource.getVisibility() & DocRights.READ_ASSIST) > 0) {
			GrantedAuthority[] ga = auth.getAuthorities();
			for (int i = 0; i < ga.length; i++) {
				if (ga[i].getAuthority().startsWith(
						"GROUP_FACULTY_" + faculty.getId().toString()))
					allowed = true;
				;
			}
		}
		if ((resource.getVisibility() & DocRights.READ_OWNER) > 0) {
			if (auth.getName().equals(((File) resource).getOwner()))
				allowed = true;

		}
		return allowed;
	}

	private boolean distTimeReached(Resource resource) {
		Link li;
		File fi;
		if (resource instanceof File) {
			fi = (File) resource;
			if (fi.getDistributionTime().getTime() < System.currentTimeMillis())
				return true;
			return false;
		}
		if (resource instanceof Folder) {
			return true;
		}
		if (resource instanceof Link) {
			li = (Link) resource;
			if (li.getDistributionDate().getTime() < System.currentTimeMillis())
				return true;
			return false;
		}
		return false;
	}

	public boolean hasWritePermission(Resource resource) {
		if ((resource.getVisibility() & DocRights.EDIT_ALL) > 0)
			return true;
		if ((resource.getVisibility() & DocRights.EDIT_ASSIST) > 0) {
			GrantedAuthority[] ga = auth.getAuthorities();
			for (int i = 0; i < ga.length; i++) {
				if (ga[i].getAuthority().startsWith(
						"GROUP_FACULTY_" + faculty.getId().toString()))
					return true;
			}
		}
		if ((resource.getVisibility() & DocRights.EDIT_OWNER) > 0) {
			if (auth.getName().equals(((File) resource).getOwner())) return true;
		}

		return false;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

}