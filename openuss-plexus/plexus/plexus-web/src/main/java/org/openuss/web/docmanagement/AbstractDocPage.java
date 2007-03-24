package org.openuss.web.docmanagement;

import java.io.IOException;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.docmanagement.BigFile;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.FileImpl;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.Link;
import org.openuss.docmanagement.Resource;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.Faculty;

public abstract class AbstractDocPage extends ExceptionHandler {

	@Property(value = "#{enrollment}")
	public Enrollment enrollment;

	@Property(value = "#{faculty}")
	public Faculty faculty;

	public final Authentication auth = SecurityContextHolder.getContext()
			.getAuthentication();

	public static final Logger enrollmentLogger = Logger
			.getLogger(AbstractDocPage.class);

	/**
	 * checks if user has read permission
	 * @param resource
	 * @return
	 */
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

	/**
	 * checks if distribution time is reached
	 * @param resource
	 * @return
	 */
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

	/**
	 * checks if current user has write permissions on give resource
	 * @param resource
	 * @return
	 */
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

	/**
	 * convenience method, which triggers the download of a file
	 * @param bigFile
	 */
	public void triggerDownload(BigFile bigFile) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();
		int read = 0;
		byte[] bytes = new byte[1024];

		response.setContentType(bigFile.getMimeType());
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ bigFile.getName() + "\"");
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			while ((read = bigFile.getFile().read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			enrollmentLogger.error("IOException: ", e);
		}
		FacesContext.getCurrentInstance().responseComplete();
	}	
	
	/**
	 * convenience method to change a File to a filetableentry
	 * 
	 * @param r
	 * @return
	 */
	public FileTableEntry file2FTE(File f) {
		FileTableEntry fte = new FileTableEntry();
		fte.setCreated(f.getCreated());
		fte.setDistributionTime(f.getDistributionTime());
		fte.setId(f.getId());
		fte.setLastModification(f.getLastModification());
		fte.setLength(f.getLength());
		fte.setMessage(f.getMessage());
		fte.setMimeType(f.getMimeType());
		fte.setName(f.getName());
		fte.setPath(f.getPath());
		fte.setPredecessor(f.getPredecessor());
		fte.setVersion(f.getVersion());
		fte.setVisibility(f.getVisibility());
		fte.setOwner(f.getOwner());
		fte.setViewed(f.getViewed());
		fte.setViewer(auth.getName());
		return fte;
	}
	
	/**
	 * convenience method, which converts a FileTableEntry object into an File
	 * object
	 * 
	 * @param fte
	 *            FileTableEntry
	 * @return
	 */
	public File fileTableEntry2File(FileTableEntry fte) {
		return new FileImpl(fte.getDistributionTime(), fte.getId(), fte
				.getLastModification(), fte.getLength(), fte.getMessage(), fte
				.getMimeType(), fte.getName(), fte.getPath(), fte
				.getPredecessor(), fte.getVersion(), fte.getVisibility(), fte.getOwner(), fte.getViewed(), fte.getViewer());
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