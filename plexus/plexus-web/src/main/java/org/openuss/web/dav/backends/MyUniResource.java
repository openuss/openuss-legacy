package org.openuss.web.dav.backends;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.acegisecurity.acls.domain.AclImpl;
import org.acegisecurity.context.SecurityContext;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;
import org.openuss.web.dav.CollisionAvoidingSimpleWebDAVResource;
import org.openuss.web.dav.SimpleWebDAVResource;
import org.openuss.web.dav.WebDAVContext;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.openuss.webdav.WebDAVStatusCodes;
import org.springframework.web.context.WebApplicationContext;

public class MyUniResource extends CollisionAvoidingSimpleWebDAVResource{
	
	protected MyUniResource(WebDAVContext context, WebDAVPath path, long id) {
		super(context, path, id);
		
		desktopService = (DesktopService2)(getWAC().getBean("desktopService2"));
	}

	protected DesktopService2 desktopService;
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
				desktopInfo = desktopService.findDesktopByUser(getUser().getId());
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
				if (UniversityResource.getNameByData(u).equals(name)) {
					return new UniversityResource(getContext(), path, u);
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
				if (DepartmentResource.getNameByData(d).equals(name)) {
					return new DepartmentResource(getContext(), path, d);
				}
			}
		}
		
		if (institutes == null) {
			institutes = desktopInfo.getInstituteInfos();
		}
		for (InstituteInfo i : institutes) {
			long instID = i.getId();
			if (instID == id) {
				return new InstituteResource(getContext(), path, i);
			}
			if (id == ID_NONE) {
				if (InstituteResource.getNameByData(i).equals(name)) {
					return new InstituteResource(getContext(), path, i);
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
				if (CourseResource.getNameByData(c).equals(name)) {
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
		WebApplicationContext context = getWAC();
		if (desktopInfo == null){
			try {
				// TODO Why is user.getId() == null ?
//				String[] names = context.getBeanDefinitionNames();
//				for(int i=0; i<names.length;i++){
//					System.out.println(names[i]);
//				}
				//User user = (User) context.getBean("sessionScope."+Constants.USER_SESSION_KEY);
				//User user = ((User)(context.getBean(Constants.USER, User.class)));
				desktopInfo = desktopService.findDesktopByUser(getUser().getId());
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
