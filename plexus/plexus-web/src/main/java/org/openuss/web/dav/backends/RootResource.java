package org.openuss.web.dav.backends;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shale.tiger.managed.Property;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.dav.WebDAVPathImpl;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;

import java.util.AbstractCollection; // TODO MyUni

public class RootResource extends AbstractOrganisationResource {
	@Property(value = "#{universityService}")
	protected UniversityService universityService;

	/**
	 * Cache of all universities
	 */
	protected List<UniversityInfo> allUniversities;
	
	/**
	 * @param path The root path
	 */
	protected RootResource(WebDAVPath path) {
		super(path, ID_ROOT);
		
		allUniversities = null;
	}
	
	/**
	 * Get the root resource with the prefix of all paths.
	 * 
	 * @param prefix The prefix of all WebDAV paths.
	 * @return An OrganisationResource representing the root.
	 */
	public static AbstractOrganisationResource getRoot(String prefix) {
		return new RootResource(WebDAVPathImpl.getRoot(prefix));
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		if (id == ID_NONE) {
			for (UniversityInfo uni : getAllUniversities()) {
				if (uni.getShortName().equals(name)) {
					return new UniversityResource(path, uni);
				}
			}
		} else {
			
		}
		
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getRawChildNames()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map<Long, String> getRawChildNames() {
		Map<Long,String> resMap = new HashMap<Long, String>();
		
		for (Object el : getAllUniversities()) {
			UniversityInfo ui = (UniversityInfo) el;
			
			resMap.put(ui.getId(), ui.getShortName());
		}
		
		return resMap;
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#simpleGetProperties(java.util.Set)
	 */
	@Override
	protected Map<String, String> simpleGetProperties(Set<String> propNames) {
		// Nothing supported, this is the root!
		return Collections.emptyMap();
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#exists()
	 */
	public boolean exists() {
		return true;
	}

	/**
	 * @return A list of all universities.
	 */
	@SuppressWarnings("unchecked")
	private List<UniversityInfo> getAllUniversities() {
		if (allUniversities == null) {
			allUniversities = universityService.findAllUniversities();
		}
		
		return allUniversities;
	}
}

	