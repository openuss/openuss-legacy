package org.openuss.web.dav.backends;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openuss.documents.DocumentService;
import org.openuss.documents.FolderEntry;
import org.openuss.documents.FolderInfo;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.springframework.web.context.WebApplicationContext;

public class CourseResource extends AbstractOrganisationResource{
	protected DocumentService documentService;
	/**
	 * pro forma id for the materials backend.
	 */
	protected final static long MATERIALS_ID = 0;
	protected final CourseInfo info;
	
	public CourseResource(WebApplicationContext wac, WebDAVPath path, CourseInfo ci) {
		super(wac, path, ci.getId());
		this.info = ci;
		
		documentService = (DocumentService) getWAC().getBean("documentService", DocumentService.class);
	}

	@Override
	protected WebDAVResource getChild(long id, String name, WebDAVPath path) {
		if (id == ID_NONE) {
			
		}
		return null;
	}
	
	/**
	 * @param path The path of the returned resource.
	 * @return The materials backend resource of this course.
	 */
	protected WebDAVResource getMaterialsBackend(WebDAVPath path) {
		FolderInfo fi = documentService.getFolder(info);
				
		//return new DocumentResource(getWAC(), path, fi);
		return null; //TODO
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getRawChildNames()
	 */
	@Override
	protected Map<Long, String> getRawChildNames() {
		Map<Long, String> res = new TreeMap<Long, String>();
		
		res.put(MATERIALS_ID, getMaterialsName());
		
		return res;
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
	
	/**
	 * @return The localized name of the document backend.
	 */
	protected String getMaterialsName() {
		// TODO localize
		return "documents";
	}
}