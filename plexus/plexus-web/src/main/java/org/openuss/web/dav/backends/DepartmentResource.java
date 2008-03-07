package org.openuss.web.dav.backends;

import java.util.Map;
import java.util.Set;

import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.springframework.web.context.WebApplicationContext;

public class DepartmentResource extends AbstractOrganisationResource{
	
	protected final DepartmentInfo di;

	public DepartmentResource(WebApplicationContext wac, WebDAVPath path, DepartmentInfo di) {
		super(wac, path, di.getId());
		this.di = di;
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