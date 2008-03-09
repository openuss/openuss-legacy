package org.openuss.web.dav.backends;

import java.util.AbstractCollection; // various TODOs
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.Constants;
import org.openuss.web.dav.WebDAVPathImpl;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.springframework.web.context.WebApplicationContext;

/**
 * The resource representing the WebDAV root.
 */
public class RootResource extends AbstractOrganisationResource {
	protected UniversityService universityService;
	/**
	 * The Id of the MyUniBackend.
	 */
	protected static final long MYUNI_ID = 0;
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
	protected WebDAVResource getChild(long id, String sname, WebDAVPath path) {
		if (id == ID_NONE) {
			// TODO add myUni
			/* 
			if (sname.equals(sanitizeName(getMyUniName()))) {
				return new MyUniResource(getWAC(), path, MYUNI_ID);
			}
			*/
			for (UniversityInfo uni : getAllUniversities()) {
				if (sname.equals(sanitizeName(UniversityResource.getNameByData(uni)))) {
					return new UniversityResource(getWAC(), path, uni);
				}
			}
		} else {
			// TODO add MyUni
			
			UniversityInfo ui = universityService.findUniversity(id);
			
			if (ui != null) {
				return new UniversityResource(getWAC(), path, ui);
			}
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
		
		// TODO MyUni
		// resMap.put(MYUNI_ID, getMyUniName());
		
		for (Object el : getAllUniversities()) {
			UniversityInfo ui = (UniversityInfo) el;
			
			resMap.put(ui.getId(), UniversityResource.getNameByData(ui));
		}
		 
		return resMap;
	}

	/**
	 * @return The translated name of MyUni.
	 */
	protected String getMyUniName() {
		// TODO localize
		return "MyUni";
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

	