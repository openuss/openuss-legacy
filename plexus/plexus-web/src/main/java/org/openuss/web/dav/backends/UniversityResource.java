package org.openuss.web.dav.backends;

import java.util.AbstractCollection; // TODO security
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.UniversityInfo;
import org.openuss.web.Constants;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.springframework.web.context.WebApplicationContext;

/**
 * A backend resource that represents a UniversityResource.
 */
public class UniversityResource extends AbstractOrganisationResource {
	protected DepartmentService departmentService;
	protected Collection<DepartmentInfo> subDepartments = null;
	protected final UniversityInfo ui;
	
	public UniversityResource(WebApplicationContext wac, WebDAVPath path, UniversityInfo ui) {
		super(wac, path, ui.getId());
		this.ui = ui;
		
		departmentService = (DepartmentService) getWAC().getBean(Constants.DEPARTMENT_SERVICE, DepartmentService.class);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		DepartmentInfo di = null;
		
		if (id != ID_NONE) {
			di = departmentService.findDepartment(id);
		} else {
			for (DepartmentInfo adi : getSubDepartments()) {
				if (name.equals(sanitizeName(adi.getShortName()))) {
					di = adi;
					break;
				}
			}
		}
		
		if (di != null) {
			return new DepartmentResource(getWAC(), path, di);
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
			resMap.put(di.getId(), di.getShortName());
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
			res.put(WebDAVConstants.XML_DISPLAYNAME, ui.getName());
		}
		
		return res;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isReadable()
	 */
	public boolean isReadable() {
		// TODO Security
		return true;
	}
	
	/**
	 * @return All departments in this university
	 */
	@SuppressWarnings("unchecked")
	protected Collection<DepartmentInfo> getSubDepartments() {
		if (subDepartments == null) {
			subDepartments = departmentService.findDepartmentsByUniversityAndEnabled(getId(), true);
		}
		
		return subDepartments;
	}
	
	/**
	 * @param info The info object of the organisation to represent.  
	 * @return The (raw) name to use in the WebDAV context. 
	 */
	public static String getNameByData(UniversityInfo info) {
		return info.getShortName();
	}
}
