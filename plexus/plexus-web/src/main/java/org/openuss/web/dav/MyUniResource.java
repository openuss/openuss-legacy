package org.openuss.web.dav;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.security.SecurityService;
import org.openuss.web.Constants;
import org.openuss.webdav.CollisionAvoidingSimpleWebDAVResource;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.openuss.webdav.WebDAVStatusCodes;

/**
 * Resource implementing a WebDAV view of MyUni.
 */
public class MyUniResource extends CollisionAvoidingSimpleWebDAVResource{
	protected SecurityService securityService;
	protected DesktopService2 desktopService;
	protected DepartmentService departmentService;
	
	protected Collection<UniversityInfo> universities = null;
	protected Collection<DepartmentInfo> departments = null;
	protected Collection<InstituteInfo> institutes = null;
	protected Collection<CourseInfo> courses = null;
	
	protected DesktopInfo desktopInfo = null;
	
	public MyUniResource(WebDAVContext context, WebDAVPath path, long id) {
		super(context, path, id);
		
		desktopService = (DesktopService2)(getWAC().getBean("desktopService2"));
		securityService = (SecurityService) getWAC().getBean(Constants.SECURITY_SERVICE);
		departmentService = (DepartmentService)(getWAC().getBean(Constants.DEPARTMENT_SERVICE));
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		for (UniversityInfo info : getUniversities()) {
			if ((info.getId() == id) ||
					((id == ID_NONE) && (sanitizeName(UniversityResource.getNameByData(info)).equals(name)))) {
				return new UniversityResource(getContext(), path, info);
			}
		}
		
		for (DepartmentInfo info : getDepartments()) {
			if ((info.getId() == id) ||
					((id == ID_NONE) && (sanitizeName(DepartmentResource.getNameByData(info)).equals(name)))) {
				return new DepartmentResource(getContext(), path, info);
			}
		}
		
		for (InstituteInfo info : getInstitutes()) {
			if ((info.getId() == id) ||
					((id == ID_NONE) && (sanitizeName(InstituteResource.getNameByData(info)).equals(name)))) {
				return new InstituteResource(getContext(), path, info);
			}
		}
		
		for (CourseInfo info : getCourses()) {
			if ((info.getId() == id) ||
					((id == ID_NONE) && (sanitizeName(CourseResource.getNameByData(info)).equals(name)))) {
				return new CourseResource(getContext(), path, info);
			}
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#createCollectionImpl(java.lang.String)
	 */
	@Override
	protected WebDAVResource createCollectionImpl(String name) throws WebDAVResourceException {
		// should never be called because isWritable() returns false.
		throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Creation of organisations via WebDAV is not implemented");
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#createFileImpl(java.lang.String, org.openuss.webdav.IOContext)
	 */
	@Override
	protected WebDAVResource createFileImpl(String name, IOContext ioc)
			throws WebDAVResourceException {
		// should never be called because isWritable() returns false.
		throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Creation of organisations via WebDAV is not implemented");
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#deleteImpl()
	 */
	@Override
	protected void deleteImpl() throws WebDAVResourceException {
		// should never be called because isWritable() returns false.
		throw new WebDAVResourceException(WebDAVStatusCodes.SC_BAD_REQUEST, this, "MyUni can not be deleted");
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.CollisionAvoidingSimpleWebDAVResource#getRawChildNames()
	 */
	protected Map<Long, String> getRawChildNames() {
		Map<Long, String> childNames = new HashMap<Long, String>();
		
		for (UniversityInfo info : getUniversities()) {
			String name = UniversityResource.getNameByData(info);
			childNames.put(info.getId(), name);
		}
		
		for (DepartmentInfo info : getDepartments()) {
			String name = DepartmentResource.getNameByData(info);
			childNames.put(info.getId(), name);
		}
		
		for (InstituteInfo info : getInstitutes()) {
			String name = InstituteResource.getNameByData(info);
			childNames.put(info.getId(), name);
		}
		
		for (CourseInfo info : getCourses()) {
			String name = CourseResource.getNameByData(info);
			childNames.put(info.getId(), name);
		}
		
		return childNames;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isReadable()
	 */
	public boolean isReadable() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isWritable()
	 */
	public boolean isWritable() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#readContentImpl()
	 */
	@Override
	protected IOContext readContentImpl() throws WebDAVResourceException,
			IOException {
		return readCollectionContent();
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#simpleGetProperties(java.util.Set)
	 */
	@Override
	protected Map<String, String> simpleGetProperties(Set<String> propNames) {
		return Collections.emptyMap();
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#writeContentImpl(org.openuss.webdav.IOContext)
	 */
	@Override
	protected void writeContentImpl(IOContext ioc)
			throws WebDAVResourceException {
		// should never be called because isWritable() returns false.
		throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "Writing to organisations via WebDAV is not implemented");
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isCollection()
	 */
	public boolean isCollection() {
		return true;
	}
	
	/**
	 * @return All universities shown in MyUni.
	 */
	@SuppressWarnings("unchecked")
	protected Collection<UniversityInfo> getUniversities() {
		if (universities == null) {
			universities = getDesktopInfo().getUniversityInfos();
		}
		
		return universities;
	}

	@SuppressWarnings("unchecked")
	protected Collection<DepartmentInfo> getDepartments() {
		if (departments == null) {
			departments = new TreeSet<DepartmentInfo>(new Comparator<DepartmentInfo>() {
				public int compare(DepartmentInfo d1, DepartmentInfo d2) {
					return d1.getId().compareTo(d2.getId());
				}
			}); 
			departments.addAll(getDesktopInfo().getDepartmentInfos());
			
			for (InstituteInfo ii : getInstitutes()) {
				DepartmentInfo di = departmentService.findDepartment(ii.getDepartmentId());
				
				departments.add(di);
			}
		}
		
		return departments;
	}

	@SuppressWarnings("unchecked")
	protected Collection<InstituteInfo> getInstitutes() {
		if (institutes == null) {
			institutes = getDesktopInfo().getInstituteInfos();
		}
		
		return institutes;
	}

	@SuppressWarnings("unchecked")
	protected Collection<CourseInfo> getCourses() {
		if (courses == null) {
			courses = getDesktopInfo().getCourseInfos();
		}
		
		return courses;
	}

	protected DesktopInfo getDesktopInfo() {
		if (desktopInfo == null) {
			try {
				desktopInfo = desktopService.findDesktopByUser(getCurrentUserId());
			} catch (DesktopException e) {
				throw new RuntimeException(e);
			}
		}
		
		return desktopInfo;
	}
	
	/**
	 * @return The id of the currently logged in user
	 */
	protected long getCurrentUserId() {
		return securityService.getCurrentUser().getId();
	}
}
