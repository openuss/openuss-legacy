package org.openuss.web.dav.backends;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.springframework.web.context.WebApplicationContext;

public class CourseResource extends AbstractOrganisationResource{
	
	protected final CourseInfo info;
	
	public CourseResource(WebApplicationContext wac, WebDAVPath path, CourseInfo ci) {
		super(wac, path, ci.getId());
		this.info = ci;
	}

	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<Long, String> getRawChildNames() {
		// TODO Auto-generated method stub
		return Collections.emptyMap();
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
	 * @see org.openuss.webdav.WebDAVResource#isReadable()
	 */
	public boolean isReadable() {
		return AcegiUtils.hasPermission(info, new Integer[] {LectureAclEntry.READ});
	}

	/**
	 * @param info The info object of the organisation to represent.  
	 * @return The (raw) name to use in the WebDAV context. 
	 */
	public static String getNameByData(CourseInfo info) {
		return info.getName();
	}
}
