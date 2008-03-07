package org.openuss.web.dav.backends;

import java.util.Map;
import java.util.Set;

import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.springframework.web.context.WebApplicationContext;

public class InstituteResource extends AbstractOrganisationResource{
	
	protected final InstituteInfo ii;
	
	public InstituteResource(WebApplicationContext wac, WebDAVPath path, InstituteInfo ii) {
		super(wac, path, ii.getId());
		this.ii = ii;
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

	public boolean isReadable() {
		// TODO Auto-generated method stub
		return true;
	}

}
