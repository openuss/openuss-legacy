package org.openuss.web.dav.backends;

import java.util.Map;
import java.util.Set;

import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.springframework.web.context.WebApplicationContext;

public class CourseResource extends AbstractOrganisationResource{
	
	protected final CourseInfo ci;
	
	public CourseResource(WebApplicationContext wac, WebDAVPath path, CourseInfo ci) {
		super(wac, path, ci.getId());
		this.ci = ci;
	}

	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<Long, String> getRawChildNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, String> simpleGetProperties(Set<String> propNames) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isReadable()
	 */
	public boolean isReadable() {
		// TODO Auto-generated method stub
		return true;
	}


	/**
	 * @param info The info object of the organisation to represent.  
	 * @return The (raw) name to use in the WebDAV context. 
	 */
	public static String getNameByData(CourseInfo info) {
		return info.getShortName();
	}
}
