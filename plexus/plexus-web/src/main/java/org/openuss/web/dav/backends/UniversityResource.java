package org.openuss.web.dav.backends;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.UniversityInfo;
import org.openuss.web.Constants;
import org.openuss.web.dav.WebDAVContext;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.springframework.web.context.WebApplicationContext;

/**
 * A backend resource that represents a UniversityResource.
 */
public class UniversityResource extends AbstractOrganisationResource {
	protected DepartmentService departmentService;
	/**
	 * Cache for the organization unit info objects contained in this one.
	 */
	protected Collection<DepartmentInfo> childrenData = null;
	protected final UniversityInfo info;
	
	public UniversityResource(WebDAVContext context, WebDAVPath path, UniversityInfo ui) {
		super(context, path, ui.getId());
		this.info = ui;
		
		departmentService = (DepartmentService) getWAC().getBean(Constants.DEPARTMENT_SERVICE, DepartmentService.class);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		DepartmentInfo resInfo = null;
		
		if (id != ID_NONE) {
			resInfo = departmentService.findDepartment(id);
		} else {
			for (DepartmentInfo childData : getSubDepartments()) {
				if (name.equals(sanitizeName(DepartmentResource.getNameByData(childData)))) {
					resInfo = childData;
					break;
				}
			}
		}
		
		if (resInfo != null) {
			return new DepartmentResource(getContext(), path, resInfo);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getRawChildNames()
	 */
	@Override
	protected Map<Long, String> getRawChildNames() {
		Map<Long,String> resMap = new TreeMap<Long, String>();
		
		for (DepartmentInfo di : getSubDepartments()) {
			resMap.put(di.getId(), DepartmentResource.getNameByData(di));
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
	 * @return Every user is allowed to see each university, iff it is enabled.
	 * @see org.openuss.webdav.WebDAVResource#isReadable()
	 */
	public boolean isReadable() {
		return info.isEnabled();
	}
	
	/**
	 * @return All departments in this university
	 */
	@SuppressWarnings("unchecked")
	protected Collection<DepartmentInfo> getSubDepartments() {
		if (childrenData == null) {
			childrenData = departmentService.findDepartmentsByUniversityAndEnabled(getId(), true);
		}
		
		return childrenData;
	}
	
	/**
	 * @param info The info object of the organisation to represent.  
	 * @return The (raw) name to use in the WebDAV context. 
	 */
	public static String getNameByData(UniversityInfo info) {
		return info.getShortName();
	}
}
