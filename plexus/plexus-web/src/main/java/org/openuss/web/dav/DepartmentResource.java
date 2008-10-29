package org.openuss.web.dav;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.web.Constants;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;

/**
 * A WebDAV resource representing an OpenUSS department.
 */
public class DepartmentResource extends AbstractOrganisationResource{
	protected InstituteService instituteService;
	/**
	 * Cache for the organization unit info objects contained in this one.
	 */
	protected Collection<InstituteInfo> childrenData = null;
	protected final DepartmentInfo info;

	public DepartmentResource(WebDAVContext context, WebDAVPath path, DepartmentInfo di) {
		super(context, path, di.getId());
		this.info = di;
		
		instituteService = (InstituteService) getWAC().getBean(Constants.INSTITUTE_SERVICE, InstituteService.class);
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		InstituteInfo resInfo = null;
		
		if (id != ID_NONE) {
			resInfo = (InstituteInfo) instituteService.findInstitute(id);
		} else {
			for (InstituteInfo childData : getSubInstitutes()) {
				if (name.equals(sanitizeName(InstituteResource.getNameByData(childData)))) {
					resInfo = childData;
					break;
				}
			}
		}
		
		if (resInfo != null) {
			return new InstituteResource(getContext(), path, resInfo);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getRawChildNames()
	 */
	@Override
	protected Map<Long, String> getRawChildNames() {
		Map<Long,String> resMap = new TreeMap<Long, String>();

		for (InstituteInfo di : getSubInstitutes()) {
			resMap.put(di.getId(), InstituteResource.getNameByData(di));
		}

		return resMap;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#simpleGetProperties(java.util.Set)
	 */
	@Override
	protected Map<String, String> simpleGetProperties(Set<String> propNames) {
		Map<String,String> res = new HashMap<String, String>();
		if ((propNames == null) || (propNames.contains(WebDAVConstants.XML_DISPLAYNAME))) {
			res.put(WebDAVConstants.XML_DISPLAYNAME, info.getName());
		}
		
		return res;
	}

	/* (non-Javadoc)
	 * @return Every user is allowed to see each department, iff it is enabled.
	 * @see org.openuss.webdav.WebDAVResource#isReadable()
	 */
	public boolean isReadable() {
		return info.isEnabled();
	}
	
	/**
	 * @return All institutes in this department
	 */
	@SuppressWarnings("unchecked")
	protected Collection<InstituteInfo> getSubInstitutes() {
		if (childrenData == null) {
			childrenData = instituteService.findInstitutesByDepartmentAndEnabled(getId(), true);
		}
		
		return childrenData;
	}

	/**
	 * @param info The info object of the organisation to represent.  
	 * @return The (raw) name to use in the WebDAV context. 
	 */
	public static String getNameByData(DepartmentInfo info) {
		return info.getShortName();
	}
}
