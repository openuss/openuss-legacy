package org.openuss.web.dav.backends;

import java.util.AbstractCollection; // TODOs
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openuss.lecture.UniversityInfo;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.springframework.web.context.WebApplicationContext;

/**
 * A backend resource that represents a UniversityResource.
 */
public class UniversityResource extends AbstractOrganisationResource {
	protected final UniversityInfo ui;
	
	public UniversityResource(WebApplicationContext wac, WebDAVPath path, UniversityInfo ui) {
		super(wac, path, ui.getId());
		this.ui = ui;
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
		
		Map<String,String> res = new HashMap<String, String>();
		if ((propNames == null) || (propNames.contains("displayname"))) {
			res.put("displayname", ui.getName());
		}
		
		return res;
	}

}
