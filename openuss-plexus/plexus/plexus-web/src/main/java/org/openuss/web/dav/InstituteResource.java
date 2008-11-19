package org.openuss.web.dav;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.Constants;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;

/**
 * A WebDAVResource representing an OpenUSS institute.
 */
public class InstituteResource extends AbstractOrganisationResource{
	protected UniversityService universityService;
	/**
	 * Cache for the organization unit info objects contained in this one.
	 */
	protected Collection<PeriodInfo> childrenData = null;
	protected final InstituteInfo info;
	
	public InstituteResource(WebDAVContext context, WebDAVPath path, InstituteInfo info) {
		super(context, path, info.getId());
		this.info = info;
		
		universityService = (UniversityService) getWAC().getBean(Constants.UNIVERSITY_SERVICE);
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		PeriodInfo periodInfo = null;
		
		if (id != ID_NONE) {
			periodInfo = universityService.findPeriod(id);
		} else {
			for (PeriodInfo childData : getChildrenData()) {
				if (name.equals(sanitizeName(InstitutePeriodResource.getNameByData(childData)))) {
					periodInfo = childData;
					break;
				}
			}
		}
		
		if (periodInfo != null) {
			return new InstitutePeriodResource(getContext(), path, info, periodInfo);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getRawChildNames()
	 */
	@Override
	protected Map<Long, String> getRawChildNames() {
		Map<Long,String> resMap = new TreeMap<Long, String>();

		for (PeriodInfo info : getChildrenData()) {
			resMap.put(info.getId(), InstitutePeriodResource.getNameByData(info));
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

	/* (non-Javadoc)
	 * @return Every user is allowed to see each institute, iff it is enabled.
	 * @see org.openuss.webdav.WebDAVResource#isReadable()
	 */
	public boolean isReadable() {
		return info.isEnabled();
	}
	
	/**
	 * @return All child organisations
	 */
	@SuppressWarnings("unchecked")
	protected Collection<PeriodInfo> getChildrenData() {
		if (childrenData == null) {
			childrenData = universityService.findPeriodsByInstituteWithCoursesOrActive(info);
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
