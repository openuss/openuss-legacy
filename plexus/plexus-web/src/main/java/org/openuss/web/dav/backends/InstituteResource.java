package org.openuss.web.dav.backends;

import java.util.AbstractCollection; // TODO security
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.web.Constants;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.springframework.web.context.WebApplicationContext;

/**
 * A WebDAVResource representing an OpenUSS institute.
 */
public class InstituteResource extends AbstractOrganisationResource{
	protected CourseService courseService;
	/**
	 * Cache for the organization unit info objects contained in this one.
	 */
	protected Collection<CourseInfo> childrenData = null;
	protected final InstituteInfo info;
	
	public InstituteResource(WebApplicationContext wac, WebDAVPath path, InstituteInfo info) {
		super(wac, path, info.getId());
		this.info = info;
		
		courseService = (CourseService) getWAC().getBean(Constants.COURSE_SERVICE, CourseService.class);
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		CourseInfo resInfo = null;
		
		if (id != ID_NONE) {
			resInfo = courseService.findCourse(id);
		} else {
			for (CourseInfo childData : getSubCourses()) {
				if (name.equals(sanitizeName(CourseResource.getNameByData(childData)))) {
					resInfo = childData;
					break;
				}
			}
		}
		
		if (resInfo != null) {
			return new CourseResource(getWAC(), path, resInfo);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getRawChildNames()
	 */
	@Override
	protected Map<Long, String> getRawChildNames() {
		Map<Long,String> resMap = new TreeMap<Long, String>();

		for (CourseInfo info : getSubCourses()) {
			resMap.put(info.getId(), CourseResource.getNameByData(info));
		}

		return resMap;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#simpleGetProperties(java.util.Set)
	 */
	@Override
	protected Map<String, String> simpleGetProperties(Set<String> propNames) {
		Map<String,String> res = new TreeMap<String, String>();
		if ((propNames == null) || (propNames.contains(WebDAVConstants.XML_DISPLAYNAME))) {
			res.put(WebDAVConstants.XML_DISPLAYNAME, info.getName());
		}
		
		return res;
	}

	public boolean isReadable() {
		// TODO security
		return true;
	}
	
	/**
	 * @return All institutes in this department
	 */
	@SuppressWarnings("unchecked")
	protected Collection<CourseInfo> getSubCourses() {
		if (childrenData == null) {
			childrenData = courseService.findCoursesByInstituteAndEnabled(getId(), true);
		}
		
		return childrenData;
	}
	
	/**
	 * @param info The info object of the organisation to represent.  
	 * @return The (raw) name to use in the WebDAV context. 
	 */
	public static String getNameByData(InstituteInfo info) {
		return info.getShortName();
	}
}
