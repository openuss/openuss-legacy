package org.openuss.web.dav;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.PeriodInfo;
import org.openuss.web.Constants;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;

/**
 * The view of all periods of a course
 */
public class InstitutePeriodResource extends AbstractOrganisationResource {
	/**
	 * Cache for the organization unit info objects contained in this one.
	 */
	protected Collection<CourseInfo> childrenData = null;
	protected CourseService courseService;
	protected final PeriodInfo info;
	protected final InstituteInfo instituteInfo;
	/**
	 * pro forma id for the materials backend.
	 */
	protected final static long MATERIALS_ID = 0;
	
	protected InstitutePeriodResource(WebDAVContext context, WebDAVPath path, InstituteInfo instituteInfo, PeriodInfo info) {
		super(context, path, info.getId());
		this.instituteInfo = instituteInfo;
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
			for (CourseInfo childData : getChildrenData()) {
				if (name.equals(sanitizeName(CourseResource.getNameByData(childData)))) {
					resInfo = childData;
					break;
				}
			}
		}
		
		if (resInfo != null) {
			return new CourseResource(getContext(), path, resInfo);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getRawChildNames()
	 */
	@Override
	protected Map<Long, String> getRawChildNames() {
		Map<Long,String> resMap = new TreeMap<Long, String>();

		for (CourseInfo info : getChildrenData()) {
			resMap.put(info.getId(), CourseResource.getNameByData(info));
		}

		return resMap;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.SimpleWebDAVResource#simpleGetProperties(java.util.Set)
	 */
	@Override
	protected Map<String, String> simpleGetProperties(Set<String> propNames) {
		Map<String,String> res = new HashMap<String, String>(1);
		if ((propNames == null) || (propNames.contains(WebDAVConstants.XML_DISPLAYNAME))) {
			res.put(WebDAVConstants.XML_DISPLAYNAME, info.getName());
		}
		
		return res;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isReadable()
	 */
	public boolean isReadable() {
		return instituteInfo.isEnabled();
	}
	
	/**
	 * @return All institutes in this department
	 */
	@SuppressWarnings("unchecked")
	protected Collection<CourseInfo> getChildrenData() {
		if (childrenData == null) {
			childrenData = courseService.findCoursesByPeriodAndInstitute(info.getId(), instituteInfo.getId());
		}
		
		return childrenData;
	}
	
	/**
	 * @param info The info object of the organisation to represent.  
	 * @return The (raw) name to use in the WebDAV context. 
	 */
	public static String getNameByData(PeriodInfo info) {
		return info.getName();
	}
}
