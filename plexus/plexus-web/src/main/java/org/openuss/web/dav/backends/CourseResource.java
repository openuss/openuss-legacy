package org.openuss.web.dav.backends;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openuss.documents.DocumentService;
import org.openuss.documents.Folder;
import org.openuss.documents.FolderDao;
import org.openuss.documents.FolderInfo;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.openuss.web.dav.WebDAVContext;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;

public class CourseResource extends AbstractOrganisationResource{
	protected DocumentService documentService;
	protected FolderDao folderDao;
	/**
	 * pro forma id for the materials backend.
	 */
	protected final static long MATERIALS_ID = 0;
	protected final CourseInfo info;
	
	public CourseResource(WebDAVContext context, WebDAVPath path, CourseInfo ci) {
		super(context, path, ci.getId());
		this.info = ci;
		
		documentService = (DocumentService) getWAC().getBean(Constants.DOCUMENT_SERVICE, DocumentService.class);
	}

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.CollisionAvoidingSimpleWebDAVResource#getChild(long, java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	@Override
	protected WebDAVResource getChild(long id, String sname, WebDAVPath path) {
		if (id == ID_NONE) {
			if (sname.equals(sanitizeName(getMaterialsName()))) {
				return getMaterialsBackend(path);
			}
		} else {
			if (id == MATERIALS_ID) {
				return getMaterialsBackend(path);
			}
		}
		
		
		return null;
	}
	
	/**
	 * @param path The path of the returned resource.
	 * @return The materials backend resource of this course.
	 */
	protected WebDAVResource getMaterialsBackend(WebDAVPath path) {
		
		
		return new DocumentResource(getContext(), path, f);
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
