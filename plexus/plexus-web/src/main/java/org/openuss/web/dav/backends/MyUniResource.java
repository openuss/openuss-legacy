package org.openuss.web.dav.backends;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shale.tiger.managed.Property;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.OrganisationService;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.security.User;
import org.openuss.web.dav.SimpleWebDAVResource;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.openuss.webdav.WebDAVStatusCodes;
import org.springframework.web.context.WebApplicationContext;

public class MyUniResource extends SimpleWebDAVResource{
	
	protected MyUniResource(WebApplicationContext wac, WebDAVPath path, long id) {
		super(wac, path, id);
	}

	
	protected DesktopInfo desktopInfo;
	protected List<UniversityInfo> universities;
	protected List<DepartmentInfo> departments;
	protected List<InstituteInfo> institutes;
	protected List<CourseInfo> courses;
	


	@Property(value = "#{sessionScope.user}")
	protected User user;
	
	@Property(value = "#{desktopService2}")
	protected DesktopService2 desktopService2;
	
	@Property(value = "#{courseService}")
	protected CourseService courseService;
	
	@Property(value = "#{organisationService}")
	protected OrganisationService organisationService;
	
	@Property(value = "#{departmentService}")
	protected DepartmentService departmentService;
	
	@Property(value = "#{universityService}")
	protected UniversityService universityService;


	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		try {
			if (desktopInfo == null) {
				desktopInfo = desktopService2.findDesktopByUser(user.getId());
			}
			if (universities == null) {
				universities = desktopInfo.getUniversityInfos();
			}
			for (UniversityInfo u : universities) {
				long uniID = u.getId();
				if (uniID == id) {
					return new UniversityResource(getWAC(), path, u);
				}
				if (id == ID_NONE) {
					if (u.getName().equals(name)) {
						return new UniversityResource(getWAC(), path, u);
					}
				}
			}
			
			if (departments == null) {
				departments = desktopInfo.getDepartmentInfos();
			}
			for (DepartmentInfo d : departments) {
				long depID = d.getId();
				if (depID == id){
					return new DepartmentResource(getWAC(), path, d);
				}
				if (id == ID_NONE) {
					if (d.getName().equals(name)) {
						return new DepartmentResource(getWAC(), path, d);
					}
				}
			}
			
			if (institutes == null) {
				institutes = desktopInfo.getInstituteInfos();
			}
			for (InstituteInfo i : institutes) {
				long instID = i.getId();
				if (instID == id) {
					return new InstituteResource(getWAC(), path, i);
				}
				if (id == ID_NONE) {
					if (i.getName().equals(name)) {
						return new InstituteResource(getWAC(), path, i);
					}
				}
			}
			
			if (courses == null) {
				courses = desktopInfo.getCourseInfos();
			}
			for (CourseInfo c : courses) {
				long courseID = c.getId();
				if (courseID == id){
					return new CourseResource(getWAC(), path, c);
				}
				if (id == ID_NONE) {
					if (c.getName().equals(name)) {
						return new CourseResource(getWAC(), path, c);
					}
				}
			}
			return null;
		} catch (DesktopException e) {
			throw new RuntimeException(e.getMessage());
		}
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
	 * @see org.openuss.web.dav.SimpleWebDAVResource#createFileImpl(java.lang.String)
	 */
	@Override
	protected WebDAVResource createFileImpl(String name)
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
		try {
			if (desktopInfo == null) {
				desktopInfo = desktopService2.findDesktopByUser(user.getId());
			}
			if (universities == null) {
				universities = desktopInfo.getUniversityInfos();
			}
			for (UniversityInfo u : universities) {
				long uniID = u.getId();
				String name = u.getShortcut();
				childNames.put(uniID, name);
			}
			
			if (departments == null) {
				departments = desktopInfo.getDepartmentInfos();
			}
			for (DepartmentInfo d : departments) {
				long depID = d.getId();
				String name = d.getShortcut();
				childNames.put(depID, name);
			}
			
			if (institutes == null) {
				institutes = desktopInfo.getInstituteInfos();
			}
			for (InstituteInfo i : institutes) {
				long instID = i.getId();
				String name = i.getShortcut();
				childNames.put(instID, name);
			}
			
			if (courses == null) {
				courses = desktopInfo.getCourseInfos();
			}
			for (CourseInfo c : courses) {
				long courseID = c.getId();
				String name = c.getShortcut();
				childNames.put(courseID, name);
			}
			return childNames;
		} catch (DesktopException e) {
			throw new RuntimeException(e.getMessage());
		}
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

	@Override
	protected IOContext readContentImpl() throws WebDAVResourceException,
			IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, String> simpleGetProperties(Set<String> propNames) {
		// TODO Auto-generated method stub
		return null;
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
