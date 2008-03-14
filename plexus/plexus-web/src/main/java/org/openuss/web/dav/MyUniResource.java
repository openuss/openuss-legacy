package org.openuss.web.dav;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	protected MyUniResource(WebDAVContext context, WebDAVPath path, long id) {
		super(context, path, id);
		
		desktopService = (DesktopService2)(getWAC().getBean("desktopService2"));
		securityService = (SecurityService) getWAC().getBean(Constants.SECURITY_SERVICE);
	}
	
	protected SecurityService securityService;
	protected DesktopService2 desktopService;
	protected DepartmentService departmentService;
	protected List<UniversityInfo> universities;
	protected List<DepartmentInfo> departments;
	protected List<InstituteInfo> institutes;
	protected List<CourseInfo> courses;
	protected DesktopInfo desktopInfo;
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		if (desktopInfo == null) {
			try {
				desktopInfo = desktopService.findDesktopByUser(securityService.getCurrentUser().getId());
			} catch (DesktopException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		if (universities == null) {
			universities = desktopInfo.getUniversityInfos();
		}
		for (UniversityInfo u : universities) {
			long uniID = u.getId();
			if (uniID == id) {
				return new UniversityResource(getContext(), path, u);
			}
			if (id == ID_NONE) {
				if (sanitizeName(UniversityResource.getNameByData(u)).equals(name)) {
					return new UniversityResource(getContext(), path, u);
				}
			}
		}
		
		if (institutes == null) {
			institutes = desktopInfo.getInstituteInfos();
		}
		for (InstituteInfo i : institutes) {
			long instID = i.getId();
			
			long depID = i.getDepartmentId();
			if (departmentService == null){
				departmentService = (DepartmentService)(getWAC().getBean(Constants.DEPARTMENT_SERVICE));
			}
			DepartmentInfo dep = departmentService.findDepartment(depID);
			if (depID == id) {
				return new DepartmentResource(getContext(), path, dep);
			}
			
			if (instID == id) {
				return new InstituteResource(getContext(), path, i);
			}
			if (id == ID_NONE) {
				if (sanitizeName(InstituteResource.getNameByData(i)).equals(name)) {
					return new InstituteResource(getContext(), path, i);
				}
				if (sanitizeName(DepartmentResource.getNameByData(dep)).equals(name)) {
					return new DepartmentResource(getContext(), path, dep);
				}
			}
		}
		
		if (departments == null) {
			departments = desktopInfo.getDepartmentInfos();
		}
		for (DepartmentInfo d : departments) {
			long depID = d.getId();
			if (depID == id){
				return new DepartmentResource(getContext(), path, d);
			}
			if (id == ID_NONE) {
				if (sanitizeName(DepartmentResource.getNameByData(d)).equals(name)) {
					return new DepartmentResource(getContext(), path, d);
				}
			}
		}
		
		if (courses == null) {
			courses = desktopInfo.getCourseInfos();
		}
		for (CourseInfo c : courses) {
			long courseID = c.getId();
			if (courseID == id){
				return new CourseResource(getContext(), path, c);
			}
			if (id == ID_NONE) {
				if (sanitizeName(CourseResource.getNameByData(c)).equals(name)) {
					return new CourseResource(getContext(), path, c);
				}
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
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getRawChildNames()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map<Long, String> getRawChildNames() {
		Map<Long, String> childNames = new HashMap<Long, String>();
		if (desktopInfo == null){
			try {
				long userId = securityService.getCurrentUser().getId();
				desktopInfo = desktopService.findDesktopByUser(userId);
			} catch (DesktopException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		if (universities == null) {
			universities = desktopInfo.getUniversityInfos();
		}
		for (UniversityInfo u : universities) {
			long uniID = u.getId();
			String name = UniversityResource.getNameByData(u);
			childNames.put(uniID, name);
		}
		
		if (departments == null) {
			departments = desktopInfo.getDepartmentInfos();
		}
		for (DepartmentInfo d : departments) {
			long depID = d.getId();
			String name = DepartmentResource.getNameByData(d);
			childNames.put(depID, name);
		}
		
		if (institutes == null) {
			institutes = desktopInfo.getInstituteInfos();
		}
		for (InstituteInfo i : institutes) {
			long depID = i.getDepartmentId();
			if (departmentService == null){
				departmentService = (DepartmentService)(getWAC().getBean(Constants.DEPARTMENT_SERVICE));
			}
			DepartmentInfo dep = departmentService.findDepartment(depID);
			String depName = DepartmentResource.getNameByData(dep);
			departments.add(dep);
			childNames.put(depID, depName);
			long instID = i.getId();
			String name = InstituteResource.getNameByData(i);
			childNames.put(instID, name);
		}
		
		if (courses == null) {
			courses = desktopInfo.getCourseInfos();
		}
		for (CourseInfo c : courses) {
			long courseID = c.getId();
			String name = CourseResource.getNameByData(c);
			childNames.put(courseID, name);
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
}
