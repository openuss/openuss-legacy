//OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.wiki;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteMember;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @author  Projektseminar WS 07/08, Team Collaboration
 * @see org.openuss.wiki.WikiService
 * 
 */
public class WikiServiceImpl extends org.openuss.wiki.WikiServiceBase {

	@Override
	protected void handleDeleteWikiSite(Long wikiSiteId) throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");

		getWikiSiteDao().remove(wikiSiteId);
	}

	@Override
	protected void handleDeleteWikiSiteVersion(Long wikiSiteVersionId)
	throws Exception {
		WikiSiteVersion version = getWikiSiteVersionDao().load(wikiSiteVersionId);
		WikiSite site = version.getWikiSite();
		
		getWikiSiteVersionDao().remove(version);
		
		site.getWikiSiteVersions().remove(version);
		if (site.getWikiSiteVersions().isEmpty()) {
			getWikiSiteDao().remove(site);
		}
	}

	@Override
	protected WikiSiteContentInfo handleFindWikiSiteContentByDomainObjectAndName(Long domainId, String siteName) throws Exception {
		Validate.notNull(domainId, "Parameter domainId must not be null!");
		Validate.notNull(siteName, "Parameter siteName must not be null!");

		final WikiSiteInfo wikiSite = (WikiSiteInfo) getWikiSiteDao().findByDomainIdAndName(WikiSiteDao.TRANSFORM_WIKISITEINFO, domainId, siteName);
		WikiSiteContentInfo wikiSiteContent = null;
		if (wikiSite != null) {
			wikiSiteContent = handleGetNewestWikiSiteContent(wikiSite.getWikiSiteId());
		}
		return wikiSiteContent;
	}

	/** 
	 * @return List of WikiSiteInfo
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindWikiSiteVersionsByWikiSite(Long wikiSiteId) throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");

		WikiSite wikiSite = getWikiSiteDao().load(wikiSiteId);
		return getWikiSiteVersionDao().findByWikiSite(WikiSiteVersionDao.TRANSFORM_WIKISITEINFO, wikiSite);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindWikiSitesByDomainObject(Long domainId) throws Exception {
		Validate.notNull(domainId, "Parameter domainId must not be null!");

		final List<WikiSiteInfo> result = getWikiSiteDao().findByDomainId(WikiSiteDao.TRANSFORM_WIKISITEINFO, domainId);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected WikiSiteContentInfo handleGetNewestWikiSiteContent(Long wikiSiteId) throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
		final WikiSite site = getWikiSiteDao().load(wikiSiteId);
		Validate.notNull(site, "No wikiSite found for wikiSiteId:" + wikiSiteId);

		final String query = "from org.openuss.wiki.WikiSiteVersion as f where f.wikiSite = ? order by f.creationDate desc";
		final List<WikiSiteInfo> list = getWikiSiteVersionDao().findByWikiSite(WikiSiteVersionDao.TRANSFORM_WIKISITEINFO, query, site);
		if (list.isEmpty()) {
			return null;
		} else {
			final WikiSiteInfo wikiSite = list.get(0);
			return (WikiSiteContentInfo) getWikiSiteVersionDao().load(WikiSiteVersionDao.TRANSFORM_WIKISITECONTENTINFO, wikiSite.getId());
		}
	}

	@Override
	protected WikiSiteInfo handleGetWikiSite(Long wikiSiteId) throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
		return (WikiSiteInfo)getWikiSiteDao().load(WikiSiteDao.TRANSFORM_WIKISITEINFO, wikiSiteId);
	}

	@Override
	protected WikiSiteContentInfo handleGetWikiSiteContent(Long wikiSiteVersionId) throws Exception {
		Validate.notNull(wikiSiteVersionId, "Parameter wikiSiteId must not be null!");
		return (WikiSiteContentInfo)getWikiSiteVersionDao().load(WikiSiteVersionDao.TRANSFORM_WIKISITECONTENTINFO, wikiSiteVersionId);
	}

	@Override
	protected void handleSaveWikiSite(WikiSiteInfo wikiSiteInfo) throws Exception {
		Validate.notNull(wikiSiteInfo, "Parameter wikiSiteInfo cannot be null.");
		Validate.notNull(wikiSiteInfo.getWikiSiteId(), "getWikiSiteId cannot be null.");

		final WikiSite wikiSite = getWikiSiteDao().wikiSiteInfoToEntity(wikiSiteInfo);
		wikiSite.setId(wikiSiteInfo.getWikiSiteId());

		getWikiSiteDao().update(wikiSite);
	}

	@Override
	protected void handleSaveWikiSite(WikiSiteContentInfo wikiSiteContentInfo) throws Exception {
		Validate.notNull(wikiSiteContentInfo, "Parameter wikiSiteContentInfo cannot be null.");
		Validate.notNull(wikiSiteContentInfo.getDomainId(), "getDomainId cannot be null.");

		if (wikiSiteContentInfo.getId() != null) {
			saveWikiSiteUpdate(wikiSiteContentInfo);
		} else {
			saveWikiSiteCreate(wikiSiteContentInfo);
		}
	}
	
	/**
	 * Creates a WikiSite.
	 * @param wikiSiteContentInfo Info-Object with the information for the WikiSite
	 */
	private void saveWikiSiteCreate(WikiSiteContentInfo wikiSiteContentInfo) {
		WikiSite wikiSite = null;

		wikiSite = getWikiSiteDao().findByDomainIdAndName(wikiSiteContentInfo.getDomainId(), wikiSiteContentInfo.getName());
		if (wikiSite != null){
			wikiSiteContentInfo.setWikiSiteId(wikiSite.getId());
		}

		if (wikiSiteContentInfo.getWikiSiteId() != null) {
			wikiSite = getWikiSiteDao().load(wikiSiteContentInfo.getWikiSiteId());
			Validate.notNull(wikiSite, "Cannot find wikiSite for id: " + wikiSiteContentInfo.getWikiSiteId());
		} else {
			wikiSite = this.getWikiSiteDao().wikiSiteInfoToEntity(wikiSiteContentInfo);
			Validate.notNull(wikiSite, "Cannot transform wikiSiteInfo to entity.");

			this.getWikiSiteDao().create(wikiSite);
			Validate.notNull(wikiSite, "Id of wikiSite cannot be null.");

			getSecurityService().createObjectIdentity(wikiSite, null);
		}

		final WikiSiteVersion wikiSiteVersion = getWikiSiteVersionDao().wikiSiteContentInfoToEntity(wikiSiteContentInfo);

		final User author = getUserDao().load(getSecurityService().getCurrentUser().getId());
		wikiSiteVersion.setAuthor(author);

		getWikiSiteVersionDao().create(wikiSiteVersion);

		wikiSite.getWikiSiteVersions().add(wikiSiteVersion);
		wikiSiteVersion.setWikiSite(wikiSite);

		getWikiSiteDao().update(wikiSite);
		getWikiSiteVersionDao().update(wikiSiteVersion);

		wikiSiteContentInfo.setId(wikiSiteVersion.getId());
		wikiSiteContentInfo.setWikiSiteId(wikiSite.getId());

		getSecurityService().createObjectIdentity(wikiSiteVersion, wikiSite);
	}
	
	/**
	 * Updates a WikiSite.
	 * @param wikiSiteContentInfo Info-Object with the information for the WikiSite
	 */
	private void saveWikiSiteUpdate(WikiSiteContentInfo wikiSiteContentInfo) {
		final WikiSiteVersion wikiSiteVersion = getWikiSiteVersionDao().wikiSiteContentInfoToEntity(wikiSiteContentInfo);
		getWikiSiteVersionDao().update(wikiSiteVersion);
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

		final WikiSite indexSite = getWikiSiteDao().findByDomainIdAndName(domainId, "index");
		return getDocumentService().getFolderEntries(indexSite, null);
	}

	@Override
	protected void handleSaveImage(final WikiSiteInfo wikiSiteInfo, final FileInfo image) throws Exception {
		Validate.notNull(wikiSiteInfo, "Parameter wikiSiteInfo cannot be null.");
		Validate.notNull(image, "Parameter image cannot be null.");

		this.handleSaveImage(wikiSiteInfo.getDomainId(), image);
	}
	
	private void handleSaveImage(Long domainId, FileInfo image) throws DocumentApplicationException {
		final WikiSite indexSite = getWikiSiteDao().findByDomainIdAndName(domainId, "index");
		final FolderInfo folder = getDocumentService().getFolder(indexSite);
		getDocumentService().createFileEntry(image, folder);

		getSecurityService().setPermissions(Roles.ANONYMOUS, image, LectureAclEntry.READ);
		getSecurityService().setPermissions(Roles.USER, image, LectureAclEntry.READ);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected WikiSiteContentInfo handleGetNewestStableWikiSiteContent(Long wikiSiteId) throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
		final WikiSite site = getWikiSiteDao().load(wikiSiteId);
		Validate.notNull(site, "No wikiSite found for wikiSiteId:" + wikiSiteId);

		final String query = "from org.openuss.wiki.WikiSiteVersion as f where f.wikiSite = ? and f.stable = true order by f.creationDate desc";
		final List<WikiSiteInfo> list = getWikiSiteVersionDao().findByWikiSite(WikiSiteVersionDao.TRANSFORM_WIKISITEINFO, query, site);
		if (list.isEmpty()) {
			return null;
		} else {
			final WikiSiteInfo wikiSite = list.get(0);
			return (WikiSiteContentInfo) getWikiSiteVersionDao().load(WikiSiteVersionDao.TRANSFORM_WIKISITECONTENTINFO, wikiSite.getId());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleImportWikiSites(Long importDomainId, Long exportDomainId) throws Exception {
		Validate.notNull(importDomainId, "Parameter importDomainId must not be null!");
		Validate.notNull(exportDomainId, "Parameter exportDomainId must not be null!");

		deleteAllWikiSites(importDomainId);
		
		final List<WikiSiteInfo> exportWikiSites = findWikiSitesByDomainObject(exportDomainId);
		final List<WikiSiteContentInfo> importWikiSites = new LinkedList<WikiSiteContentInfo>();
		
		for (WikiSiteInfo exportWikiSite : exportWikiSites) {
			final WikiSiteContentInfo newestWikiSiteContent = getNewestWikiSiteContent(exportWikiSite.getWikiSiteId());
			final WikiSiteContentInfo importWikiSiteContent = importWikiSiteContent(importDomainId, newestWikiSiteContent);
			importWikiSites.add(importWikiSiteContent);
		}
		
		final Map<Long, Long> imageImportMap = importWikiSiteImages(importDomainId, exportDomainId);
		for (WikiSiteContentInfo importWikiSite : importWikiSites) {
			updateWikiSiteImages(importWikiSite, imageImportMap);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleImportWikiVersions(Long importDomainId, Long exportDomainId) throws Exception {
		Validate.notNull(importDomainId, "Parameter importDomainId must not be null!");
		Validate.notNull(exportDomainId, "Parameter exportDomainId must not be null!");

		final Map<Long, Long> imageImportMap = importWikiSiteImages(importDomainId, exportDomainId);
		
		deleteAllWikiSites(importDomainId);
		
		final List<WikiSiteInfo> exportWikiSites = findWikiSitesByDomainObject(exportDomainId);
		for (WikiSiteInfo exportWikiSite : exportWikiSites) {
			final List<WikiSiteInfo> exportWikiSiteVersions = findWikiSiteVersionsByWikiSite(exportWikiSite.getWikiSiteId());
			for (WikiSiteInfo exportWikiSiteVersion : exportWikiSiteVersions) {
				final WikiSiteContentInfo exportWikiSiteVersionContent = getWikiSiteContent(exportWikiSiteVersion.getId());
				final WikiSiteContentInfo importWikiSiteContent = importWikiSiteContent(importDomainId, exportWikiSiteVersionContent);
				updateWikiSiteImages(importWikiSiteContent, imageImportMap);
				saveWikiSite(importWikiSiteContent);
			}
		}
		
		
	}

	/**
	 * Deletes all WikiSites that refer to a specific DomainObject. 
	 * @param deleteDomainId ID of the specific DomainObject.
	 */
	@SuppressWarnings("unchecked")
	private void deleteAllWikiSites(Long deleteDomainId) {
		final List<WikiSiteInfo> oldImportWikiSites = findWikiSitesByDomainObject(deleteDomainId);
		for (WikiSiteInfo oldImportWikiSite : oldImportWikiSites) {
			deleteWikiSite(oldImportWikiSite.getWikiSiteId());
		}
	}

	/**
	 * Clones existing WikiSite and imports it to a specific DomainObject.
	 * @param importDomainId ID of the specific DomainObject.
	 * @param exportWikiSiteContent Exported WikiSite.
	 */
	private WikiSiteContentInfo importWikiSiteContent(Long importDomainId, WikiSiteContentInfo exportWikiSiteContent) {
		final WikiSiteContentInfo importWikiSiteContent = new WikiSiteContentInfo(exportWikiSiteContent);
		importWikiSiteContent.setId(null);
		importWikiSiteContent.setWikiSiteId(null);
		importWikiSiteContent.setDomainId(importDomainId);

		saveWikiSite(importWikiSiteContent);
		
		return importWikiSiteContent;
	}
	
	/**
	 * Clones existing Images and imports it to a specific DomainObject.
	 * @param importDomainId ID of the specific DomainObject.
	 * @param exportWikiSiteContent Exported WikiSite.
	 * @throws DocumentApplicationException
	 */
	@SuppressWarnings("unchecked")
	private Map<Long, Long> importWikiSiteImages(Long importDomainId, Long exportDomainId) throws DocumentApplicationException {
		final Map<Long, Long> imageImportMap = new HashMap<Long, Long>();
		
		final List<FolderEntryInfo> imageFolderEntries = findImagesByDomainId(exportDomainId);
		
		for (FolderEntryInfo fileEntry : imageFolderEntries) {
			FileInfo imageFile = getDocumentService().getFileEntry(fileEntry.getId(), true);
			Long exportImageId = imageFile.getId();
			imageFile.setId(null);
			
			handleSaveImage(importDomainId, imageFile);
			Long importImageId = imageFile.getId();
			
			imageImportMap.put(exportImageId, importImageId);
		}
		
		return imageImportMap;
	}
	
	private void updateWikiSiteImages(WikiSiteContentInfo wikiSiteContent, Map<Long, Long> imageImportMap) {
		String newContent = wikiSiteContent.getText();
		
		final Set<Entry <Long, Long>> entrySet = imageImportMap.entrySet();
		for (Entry<Long, Long> entry : entrySet) {
			final String searchString = "fileid=" + entry.getKey();
			final String replaceString = "fileid=" + entry.getValue();
			newContent = newContent.replaceAll(searchString, replaceString);
		}
		
		wikiSiteContent.setText(newContent);
		
		saveWikiSiteUpdate(wikiSiteContent);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected WikiSiteInfo handleGetNewestWikiSite(Long wikiSiteId) throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
		final WikiSite site = getWikiSiteDao().load(wikiSiteId);
		Validate.notNull(site, "No wikiSite found for wikiSiteId:" + wikiSiteId);

		String query = "from org.openuss.wiki.WikiSiteVersion as f where f.wikiSite = ? order by f.creationDate desc";
		List<WikiSiteInfo> list = getWikiSiteVersionDao().findByWikiSite(WikiSiteVersionDao.TRANSFORM_WIKISITEINFO, query, site);
		if (list.isEmpty()) {
			return null;
		} else {
			final WikiSiteInfo wikiSite = list.get(0);
			return wikiSite;
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindAllExportableWikiCoursesByInstituteAndUser(InstituteInfo institute, UserInfo user, CourseInfo importCourse) throws Exception {
		Validate.notNull(institute, "Parameter institute must not be null!");
		Validate.notNull(institute.getId(), "Parameter institute.getId() must not be null!");

		Validate.notNull(user, "Parameter user must not be null!");
		Validate.notNull(user.getId(), "Parameter user.getId must not be null!");
		
		Validate.notNull(importCourse, "Parameter importCourse must not be null!");
		Validate.notNull(importCourse.getId(), "Parameter importCourse.getId must not be null!");
		
		final List<CourseInfo> availableCourses = this.getCourseService().findAllCoursesByInstitute(institute.getId());
		availableCourses.remove(importCourse);
		
		if (userIsInstituteMember(institute, user)) {
			return availableCourses;
		}
		
		return findAssistantCourses(availableCourses, user);
	}
	
	/**
	 * Checks if a User is Member of an Institute.
	 * @param institute Specified Institute.
	 * @param user Specified User.
	 * @return <code>true</code> if the User is Member of the Institute, otherwise <code>false</code>.
	 */
	private boolean userIsInstituteMember(InstituteInfo institute, UserInfo user) {
		final List<InstituteMember> instituteMembers = getInstituteService().getInstituteSecurity(institute.getId()).getMembers();
		for (InstituteMember instituteMember : instituteMembers) {
			if (user.getId().equals(instituteMember.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Finds all Courses a User is Assistant of.
	 * @param availableCourses List of checkable Courses.
	 * @param user Specified User.
	 * @return List of all Courses the User is Assistant of.
	 */
	@SuppressWarnings("unchecked")
	private List<CourseInfo> findAssistantCourses(List<CourseInfo> availableCourses, UserInfo user) {
		final List<CourseInfo> assistantCourses = new LinkedList<CourseInfo>();

		for (CourseInfo availableCourse : availableCourses) {
			// FIXME: replace by group. don't know how...
			final List<CourseMemberInfo> assistants = getCourseService().getAssistants(availableCourse);
			for (CourseMemberInfo assistant : assistants) {
				if (assistant.getUserId().equals(user.getId())) {
					assistantCourses.add(availableCourse);
				}
			}
		}
		
		return assistantCourses;
	}

}