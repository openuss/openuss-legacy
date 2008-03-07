package org.openuss.web.dav.backends;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.Constants;
import org.openuss.web.dav.WebDAVPathImpl;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.springframework.web.context.WebApplicationContext;

import java.util.AbstractCollection; // TODO MyUni, TODOs

public class RootResource extends AbstractOrganisationResource {
	private Logger logger = Logger.getLogger(RootResource.class);
	protected UniversityService universityService;
	/**
	 * Cache of all universities
	 */
	protected List<UniversityInfo> allUniversities;
	

	/**
	 * @param wac The Spring context.
	 * @param path The root path
	 */
	protected RootResource(WebApplicationContext wac, WebDAVPath path) {
		super(wac, path, ID_ROOT);
		
		allUniversities = null;
		universityService = (UniversityService) getWAC().getBean(Constants.UNIVERSITY_SERVICE, UniversityService.class);
	}
	
	/**
	 * Get the root resource with the prefix of all paths.
	 * 
	 * @param wac The WebApplicationContext.
	 * @param prefix The prefix of all WebDAV paths.
	 * @return A WebDAVResource representing the root.
	 */
	public static RootResource getRoot(WebApplicationContext wac, String prefix) {
		return new RootResource(wac, WebDAVPathImpl.getRoot(prefix));
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		if (id == ID_NONE) {
			for (UniversityInfo uni : getAllUniversities()) {
				if (uni.getShortName().equals(name)) {
					return new UniversityResource(getWAC(), path, uni);
				}
			}
		} else {
			UniversityInfo ui = universityService.findUniversity(id);
			
			if (ui != null) {
				return new UniversityResource(getWAC(), path, ui);
			}
			
			// TODO implement
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
			
			
			logger.error("ADDING uni " + ui.getShortName());
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

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isReadable()
	 */
	public boolean isReadable() {
		// Root is always allowed
		return true;
	}
}

	