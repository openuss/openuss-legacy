//OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.wiki;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.wiki.WikiService
 */
public class WikiServiceImpl
extends org.openuss.wiki.WikiServiceBase
{

	@Override
	protected void handleDeleteWikiSite(Long wikiSiteId) throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");

		getWikiSiteDao().remove(wikiSiteId);
	}

	@Override
	protected void handleDeleteWikiSiteVersion(Long wikiSiteVersionId)
	throws Exception {
		// TODO Auto-generated method stub
		getWikiSiteVersionDao().remove(wikiSiteVersionId);
	}

	@Override
	protected WikiSiteContentInfo handleFindWikiSiteContentByDomainObjectAndName(
			Long domainId, String siteName) throws Exception {
		Validate.notNull(domainId, "Parameter domainId must not be null!");
		Validate.notNull(siteName, "Parameter siteName must not be null!");

		WikiSiteInfo wikiSite = (WikiSiteInfo) getWikiSiteDao().findByDomainIdAndName(WikiSiteDao.TRANSFORM_WIKISITEINFO, domainId, siteName);
		WikiSiteContentInfo wikiSiteContent = null;
		if (wikiSite != null) {
			wikiSiteContent = handleGetNewestWikiSiteContent(wikiSite.getWikiSiteId());
		}
		return wikiSiteContent;
	}

	/** 
	 * @return List of WikiSiteInfo
	 */
	@Override
	protected List handleFindWikiSiteVersionsByWikiSite(Long wikiSiteId)
	throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");

		WikiSite wikiSite = getWikiSiteDao().load(wikiSiteId);
		return getWikiSiteVersionDao().findByWikiSite(WikiSiteVersionDao.TRANSFORM_WIKISITEINFO, wikiSite);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindWikiSitesByDomainObject(Long domainId)
	throws Exception {
		Validate.notNull(domainId, "Parameter domainId must not be null!");

		List<WikiSiteInfo> result = getWikiSiteDao().findByDomainId(WikiSiteDao.TRANSFORM_WIKISITEINFO, domainId);
		return result;
	}

	@Override
	protected WikiSiteContentInfo handleGetNewestWikiSiteContent(Long wikiSiteId)
	throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
		WikiSite site = getWikiSiteDao().load(wikiSiteId);
		Validate.notNull(site, "No wikiSite found for wikiSiteId:" + wikiSiteId);

		String query = "from org.openuss.wiki.WikiSiteVersion as f where f.wikiSite = ? order by f.creationDate desc";
		List<WikiSiteInfo> list = getWikiSiteVersionDao().findByWikiSite(WikiSiteVersionDao.TRANSFORM_WIKISITEINFO, query, site);
		if (list.isEmpty()) {
			return null;
		} else {
			WikiSiteInfo wikiSite = list.get(0);
			return (WikiSiteContentInfo) getWikiSiteVersionDao().load(WikiSiteVersionDao.TRANSFORM_WIKISITECONTENTINFO, wikiSite.getId());
		}
	}

	@Override
	protected WikiSiteInfo handleGetWikiSite(Long wikiSiteId) throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
		return (WikiSiteInfo)getWikiSiteDao().load(WikiSiteDao.TRANSFORM_WIKISITEINFO, wikiSiteId);
	}

	@Override
	protected WikiSiteContentInfo handleGetWikiSiteContent(
			Long wikiSiteVersionId) throws Exception {
		Validate.notNull(wikiSiteVersionId, "Parameter wikiSiteId must not be null!");
		return (WikiSiteContentInfo)getWikiSiteVersionDao().load(WikiSiteVersionDao.TRANSFORM_WIKISITECONTENTINFO, wikiSiteVersionId);
	}

	@Override
	protected void handleSaveWikiSite(WikiSiteInfo wikiSiteInfo)
	throws Exception {
		Validate.notNull(wikiSiteInfo, "Parameter wikiSiteInfo cannot be null.");
		Validate.notNull(wikiSiteInfo.getWikiSiteId(), "getWikiSiteId cannot be null.");

		final WikiSite wikiSite = getWikiSiteDao().wikiSiteInfoToEntity(wikiSiteInfo);
		wikiSite.setId(wikiSiteInfo.getWikiSiteId());

		getWikiSiteDao().update(wikiSite);
	}

	/** Creates or updates a wikisite.
	 * 
	 */
	@Override
	protected void handleSaveWikiSite(WikiSiteContentInfo wikiSiteContentInfo)
	throws Exception {
		Validate.notNull(wikiSiteContentInfo, "Parameter wikiSiteContentInfo cannot be null.");
		Validate.notNull(wikiSiteContentInfo.getDomainId(), "getDomainId cannot be null.");

		if (wikiSiteContentInfo.getId() != null) {
			// update
			WikiSiteVersion wikiSiteVersion = getWikiSiteVersionDao().wikiSiteContentInfoToEntity(wikiSiteContentInfo);
			getWikiSiteVersionDao().update(wikiSiteVersion);

		} else {
			// create

			// find/create WikiSite
			WikiSite wikiSite = null;

			//It is possible, that a site without versions exist. check:
			wikiSite = getWikiSiteDao().findByDomainIdAndName(wikiSiteContentInfo.getDomainId(), wikiSiteContentInfo.getName());
			if (wikiSite!=null){
				wikiSiteContentInfo.setWikiSiteId(wikiSite.getId());
			}


			if (wikiSiteContentInfo.getWikiSiteId() != null) {
				wikiSite = getWikiSiteDao().load(wikiSiteContentInfo.getWikiSiteId());
				Validate.notNull(wikiSite, "Cannot find wikiSite for id: " + wikiSiteContentInfo.getWikiSiteId());
			} else {
				wikiSite = this.getWikiSiteDao().wikiSiteInfoToEntity(wikiSiteContentInfo);
				Validate.notNull(wikiSite, "Cannot transform wikiSiteInfo to entity.");

				// Save Entity
				this.getWikiSiteDao().create(wikiSite);
				Validate.notNull(wikiSite, "Id of wikiSite cannot be null.");

				getSecurityService().createObjectIdentity(wikiSite, null);
			}


			WikiSiteVersion wikiSiteVersion = getWikiSiteVersionDao().wikiSiteContentInfoToEntity(wikiSiteContentInfo);

			User author = getSecurityService().getCurrentUser();
			wikiSiteVersion.setAuthor(author);

			getWikiSiteVersionDao().create(wikiSiteVersion);

			wikiSite.getWikiPageVersions().add(wikiSiteVersion);
			wikiSiteVersion.setWikiSite(wikiSite);

			getWikiSiteDao().update(wikiSite);
			getWikiSiteVersionDao().update(wikiSiteVersion);

			wikiSiteContentInfo.setId(wikiSiteVersion.getId());
			wikiSiteContentInfo.setWikiSiteId(wikiSite.getId());

			// add object identity to security
			getSecurityService().createObjectIdentity(wikiSiteVersion, wikiSite);

			// Set Security

			//FIXME: don't know what this does:	this.getSecurityService().createObjectIdentity(workspaceEntity, workspaceEntity.getCourseType());
		}
	}

	@Override
	protected void handleDeleteImage(Long fileId) throws Exception {
		Validate.notNull(fileId, "Parameter fileId cannot be null.");

		getDocumentService().removeFolderEntry(fileId);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindImagesByDomainId(Long domainId) throws Exception {
		Validate.notNull(domainId, "Parameter domainId cannot be null.");

		WikiSite indexSite = getWikiSiteDao().findByDomainIdAndName(domainId, "index");
		return getDocumentService().getFolderEntries(indexSite, null);
	}

	@Override
	protected void handleSaveImage(final WikiSiteInfo wikiSiteInfo, final FileInfo image)
	throws Exception {
		Validate.notNull(wikiSiteInfo, "Parameter wikiSiteInfo cannot be null.");
		Validate.notNull(image, "Parameter image cannot be null.");

		WikiSite indexSite = getWikiSiteDao().findByDomainIdAndName(wikiSiteInfo.getDomainId(), "index");
		FolderInfo folder = getDocumentService().getFolder(indexSite);
		getDocumentService().createFileEntry(image, folder);

		getSecurityService().setPermissions(Roles.ANONYMOUS, image, LectureAclEntry.READ);
		getSecurityService().setPermissions(Roles.USER, image, LectureAclEntry.READ);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected WikiSiteContentInfo handleGetNewestStableWikiSiteContent(Long wikiSiteId) throws Exception {
		// TODO Auto-generated method stub

		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
		WikiSite site = getWikiSiteDao().load(wikiSiteId);
		Validate.notNull(site, "No wikiSite found for wikiSiteId:" + wikiSiteId);

		String query = "from org.openuss.wiki.WikiSiteVersion as f where f.wikiSite = ? and f.stable = true order by f.creationDate desc";
		List<WikiSiteInfo> list = getWikiSiteVersionDao().findByWikiSite(WikiSiteVersionDao.TRANSFORM_WIKISITEINFO, query, site);
		if (list.isEmpty()) {
			return null;
		} else {
			WikiSiteInfo wikiSite = list.get(0);
			return (WikiSiteContentInfo) getWikiSiteVersionDao().load(WikiSiteVersionDao.TRANSFORM_WIKISITECONTENTINFO, wikiSite.getId());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleImportWikiSites(Long importDomainId, Long exportDomainId) throws Exception {
		Validate.notNull(importDomainId, "Parameter importDomainId must not be null!");
		Validate.notNull(exportDomainId, "Parameter exportDomainId must not be null!");

		deleteAllWikiSites(importDomainId);

		List<WikiSiteInfo> exportWikiSites = findWikiSitesByDomainObject(exportDomainId);
		for (WikiSiteInfo exportWikiSite : exportWikiSites) {
			WikiSiteContentInfo newestWikiSiteContent = getNewestWikiSiteContent(exportWikiSite.getWikiSiteId());
			importWikiSiteContent(importDomainId, newestWikiSiteContent);
		}
	}

	@SuppressWarnings("unchecked")
	@Override

	protected void handleImportWikiVersions(Long importDomainId, Long exportDomainId) throws Exception {
		Validate.notNull(importDomainId, "Parameter importDomainId must not be null!");
		Validate.notNull(exportDomainId, "Parameter exportDomainId must not be null!");

		deleteAllWikiSites(importDomainId);

		List<WikiSiteInfo> exportWikiSites = findWikiSitesByDomainObject(exportDomainId);
		for (WikiSiteInfo exportWikiSite : exportWikiSites) {

			List<WikiSiteInfo> exportWikiSiteVersions = findWikiSiteVersionsByWikiSite(exportWikiSite.getWikiSiteId());
			for (WikiSiteInfo exportWikiSiteVersion : exportWikiSiteVersions) {
				WikiSiteContentInfo exportWikiSiteVersionContent = getWikiSiteContent(exportWikiSiteVersion.getId());
				importWikiSiteContent(importDomainId, exportWikiSiteVersionContent);
			}
		}
	}

	/**
	 * Deletes all WikiSites that refer to a specific DomainObject. 
	 * @param deleteDomainId ID of the specific DomainObject.
	 */
	@SuppressWarnings("unchecked")
	private void deleteAllWikiSites(Long deleteDomainId) {
		List<WikiSiteInfo> oldImportWikiSites = findWikiSitesByDomainObject(deleteDomainId);
		for (WikiSiteInfo oldImportWikiSite : oldImportWikiSites) {
			deleteWikiSite(oldImportWikiSite.getWikiSiteId());
		}
	}

	/**
	 * Clones existing WikiSite and imports it to a specific DomainObject.
	 * @param importDomainId ID of the specific DomainObject.
	 * @param exportWikiSiteContent Exported WikiSite.
	 */
	private void importWikiSiteContent(Long importDomainId, WikiSiteContentInfo exportWikiSiteContent) {
		WikiSiteContentInfo importWikiSiteContent = new WikiSiteContentInfo(exportWikiSiteContent);
		importWikiSiteContent.setId(null);
		importWikiSiteContent.setWikiSiteId(null);
		importWikiSiteContent.setDomainId(importDomainId);

		saveWikiSite(importWikiSiteContent);
	}

	@Override
	protected WikiSiteInfo handleGetNewestWikiSite(Long wikiSiteId)
	throws Exception {

		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
		WikiSite site = getWikiSiteDao().load(wikiSiteId);
		Validate.notNull(site, "No wikiSite found for wikiSiteId:" + wikiSiteId);

		String query = "from org.openuss.wiki.WikiSiteVersion as f where f.wikiSite = ? order by f.creationDate desc";
		List<WikiSiteInfo> list = getWikiSiteVersionDao().findByWikiSite(WikiSiteVersionDao.TRANSFORM_WIKISITEINFO, query, site);
		if (list.isEmpty()) {
			return null;
		} else {
			WikiSiteInfo wikiSite = list.get(0);
			return wikiSite;
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindAllExportableWikiCoursesByInstituteAndUser(InstituteInfo institute, User user, CourseInfo importCourse) throws Exception {
		Validate.notNull(institute, "Parameter institute must not be null!");
		Validate.notNull(institute.getId(), "Parameter institute.getId() must not be null!");

		Validate.notNull(user, "Parameter user must not be null!");
		Validate.notNull(user.getId(), "Parameter user.getId must not be null!");
		
		Validate.notNull(importCourse, "Parameter importCourse must not be null!");
		Validate.notNull(importCourse.getId(), "Parameter importCourse.getId must not be null!");

		List<CourseInfo> exportableWikiCourses = new LinkedList<CourseInfo>();
		
		List<CourseInfo> availableCourses = this.getCourseService().findAllCoursesByInstitute(institute.getId());
		availableCourses.remove(importCourse);

		for (CourseInfo availableCourse : availableCourses) {
			List<CourseMemberInfo> assistants = getCourseService().getAssistants(availableCourse);
			for (CourseMemberInfo assistant : assistants) {
				if (assistant.getUserId().equals(user.getId())) {
					exportableWikiCourses.add(availableCourse);
				}
			}
		}
		
		return exportableWikiCourses;
	}

}