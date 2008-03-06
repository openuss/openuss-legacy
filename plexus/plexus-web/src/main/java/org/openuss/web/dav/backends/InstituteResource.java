package org.openuss.web.dav.backends;

import java.util.Map;
import java.util.Set;

import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;

public class InstituteResource extends AbstractOrganisationResource{
	
	protected final InstituteInfo ii;
	
	public InstituteResource(WebDAVPath path, InstituteInfo ii) {
		super(path, ii.getId());
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

}
