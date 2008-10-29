package org.openuss.wiki;

import java.security.Principal;
import java.util.List;

import org.andromda.spring.PrincipalStore;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.security.SecurityService;
import org.openuss.security.UserDao;
import org.openuss.security.UserInfo;

/**
 * <p>
 * Spring Service base class for <code>WikiService</code>, provides access to
 * all services and entities referenced by this service.
 * </p>
 * 
 * @see WikiService
 */
public abstract class WikiServiceBase implements WikiService {

	private SecurityService securityService;

	/**
	 * Sets the reference to <code>securityService</code>.
	 */
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	/**
	 * Gets the reference to <code>securityService</code>.
	 */
	protected SecurityService getSecurityService() {
		return this.securityService;
	}

	private DocumentService documentService;

	/**
	 * Sets the reference to <code>documentService</code>.
	 */
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	/**
	 * Gets the reference to <code>documentService</code>.
	 */
	protected DocumentService getDocumentService() {
		return this.documentService;
	}

	private org.openuss.lecture.CourseService courseService;

	/**
	 * Sets the reference to <code>courseService</code>.
	 */
	public void setCourseService(org.openuss.lecture.CourseService courseService) {
		this.courseService = courseService;
	}

	/**
	 * Gets the reference to <code>courseService</code>.
	 */
	protected org.openuss.lecture.CourseService getCourseService() {
		return this.courseService;
	}

	private org.openuss.lecture.InstituteService instituteService;

	/**
	 * Sets the reference to <code>instituteService</code>.
	 */
	public void setInstituteService(org.openuss.lecture.InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	/**
	 * Gets the reference to <code>instituteService</code>.
	 */
	protected org.openuss.lecture.InstituteService getInstituteService() {
		return this.instituteService;
	}

	private WikiSiteVersionDao wikiSiteVersionDao;

	/**
	 * Sets the reference to <code>wikiSiteVersion</code>'s DAO.
	 */
	public void setWikiSiteVersionDao(WikiSiteVersionDao wikiSiteVersionDao) {
		this.wikiSiteVersionDao = wikiSiteVersionDao;
	}

	/**
	 * Gets the reference to <code>wikiSiteVersion</code>'s DAO.
	 */
	protected WikiSiteVersionDao getWikiSiteVersionDao() {
		return this.wikiSiteVersionDao;
	}

	private WikiSiteDao wikiSiteDao;

	/**
	 * Sets the reference to <code>wikiSite</code>'s DAO.
	 */
	public void setWikiSiteDao(WikiSiteDao wikiSiteDao) {
		this.wikiSiteDao = wikiSiteDao;
	}

	/**
	 * Gets the reference to <code>wikiSite</code>'s DAO.
	 */
	protected WikiSiteDao getWikiSiteDao() {
		return this.wikiSiteDao;
	}

	private UserDao userDao;

	/**
	 * Sets the reference to <code>user</code>'s DAO.
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Gets the reference to <code>user</code>'s DAO.
	 */
	protected UserDao getUserDao() {
		return this.userDao;
	}

	/**
	 * @see WikiService#saveWikiSite(WikiSiteInfo)
	 */
	public void saveWikiSite(WikiSiteInfo wikiSiteInfo) {
		try {
			this.handleSaveWikiSite(wikiSiteInfo);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.saveWikiSite(WikiSiteInfo wikiSiteInfo)' --> " + th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #saveWikiSite(WikiSiteInfo)}
	 */
	protected abstract void handleSaveWikiSite(WikiSiteInfo wikiSiteInfo) throws Exception;

	/**
	 * @see WikiService#saveWikiSite(WikiSiteContentInfo)
	 */
	public void saveWikiSite(WikiSiteContentInfo wikiSiteContentInfo) {
		try {
			this.handleSaveWikiSite(wikiSiteContentInfo);
		} catch (Throwable th) {
			throw new WikiServiceException(
					"Error performing 'WikiService.saveWikiSite(WikiSiteContentInfo wikiSiteContentInfo)' --> " + th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #saveWikiSite(WikiSiteContentInfo)}
	 */
	protected abstract void handleSaveWikiSite(WikiSiteContentInfo wikiSiteContentInfo) throws Exception;

	/**
	 * @see WikiService#deleteWikiSite(Long)
	 */
	public void deleteWikiSite(Long wikiSiteId) {
		try {
			this.handleDeleteWikiSite(wikiSiteId);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.deleteWikiSite(Long wikiSiteId)' --> " + th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #deleteWikiSite(Long)}
	 */
	protected abstract void handleDeleteWikiSite(Long wikiSiteId) throws Exception;

	/**
	 * @see WikiService#deleteWikiSiteVersion(Long)
	 */
	public void deleteWikiSiteVersion(Long wikiSiteVersionId) {
		try {
			this.handleDeleteWikiSiteVersion(wikiSiteVersionId);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.deleteWikiSiteVersion(Long wikiSiteVersionId)' --> "
					+ th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #deleteWikiSiteVersion(Long)}
	 */
	protected abstract void handleDeleteWikiSiteVersion(Long wikiSiteVersionId) throws Exception;

	/**
	 * @see WikiService#getWikiSite(Long)
	 */
	public WikiSiteInfo getWikiSite(Long wikiSiteId) {
		try {
			return this.handleGetWikiSite(wikiSiteId);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.getWikiSite(Long wikiSiteId)' --> " + th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #getWikiSite(Long)}
	 */
	protected abstract WikiSiteInfo handleGetWikiSite(Long wikiSiteId) throws Exception;

	/**
	 * @see WikiService#getWikiSiteContent(Long)
	 */
	public WikiSiteContentInfo getWikiSiteContent(Long wikiSiteVersionId) {
		try {
			return this.handleGetWikiSiteContent(wikiSiteVersionId);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.getWikiSiteContent(Long wikiSiteVersionId)' --> " + th,
					th);
		}
	}

	/**
	 * Performs the core logic for {@link #getWikiSiteContent(Long)}
	 */
	protected abstract WikiSiteContentInfo handleGetWikiSiteContent(Long wikiSiteVersionId) throws Exception;

	/**
	 * @see WikiService#getNewestWikiSiteContent(Long)
	 */
	public WikiSiteContentInfo getNewestWikiSiteContent(Long wikiSiteId) {
		try {
			return this.handleGetNewestWikiSiteContent(wikiSiteId);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.getNewestWikiSiteContent(Long wikiSiteId)' --> " + th,
					th);
		}
	}

	/**
	 * Performs the core logic for {@link #getNewestWikiSiteContent(Long)}
	 */
	protected abstract WikiSiteContentInfo handleGetNewestWikiSiteContent(Long wikiSiteId) throws Exception;

	/**
	 * @see WikiService#getNewestWikiSite(Long)
	 */
	public WikiSiteInfo getNewestWikiSite(Long wikiSiteId) {
		try {
			return this.handleGetNewestWikiSite(wikiSiteId);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.getNewestWikiSite(Long wikiSiteId)' --> " + th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #getNewestWikiSite(Long)}
	 */
	protected abstract WikiSiteInfo handleGetNewestWikiSite(Long wikiSiteId) throws Exception;

	/**
	 * @see WikiService#getNewestStableWikiSiteContent(Long)
	 */
	public WikiSiteContentInfo getNewestStableWikiSiteContent(Long wikiSiteId) {
		try {
			return this.handleGetNewestStableWikiSiteContent(wikiSiteId);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.getNewestStableWikiSiteContent(Long wikiSiteId)' --> "
					+ th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #getNewestStableWikiSiteContent(Long)}
	 */
	protected abstract WikiSiteContentInfo handleGetNewestStableWikiSiteContent(Long wikiSiteId) throws Exception;

	/**
	 * @see WikiService#findWikiSiteVersionsByWikiSite(Long)
	 */
	public List<WikiSiteInfo> findWikiSiteVersionsByWikiSite(Long wikiSiteId) {
		try {
			return this.handleFindWikiSiteVersionsByWikiSite(wikiSiteId);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.findWikiSiteVersionsByWikiSite(Long wikiSiteId)' --> "
					+ th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #findWikiSiteVersionsByWikiSite(Long)}
	 */
	protected abstract List<WikiSiteInfo> handleFindWikiSiteVersionsByWikiSite(Long wikiSiteId) throws Exception;

	/**
	 * @see WikiService#findWikiSitesByDomainObject(Long)
	 */
	public List findWikiSitesByDomainObject(Long domainId) {
		try {
			return this.handleFindWikiSitesByDomainObject(domainId);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.findWikiSitesByDomainObject(Long domainId)' --> " + th,
					th);
		}
	}

	/**
	 * Performs the core logic for {@link #findWikiSitesByDomainObject(Long)}
	 */
	protected abstract List handleFindWikiSitesByDomainObject(Long domainId) throws Exception;

	/**
	 * @see WikiService#findWikiSiteContentByDomainObjectAndName(Long, String)
	 */
	public WikiSiteContentInfo findWikiSiteContentByDomainObjectAndName(Long domainId, String siteName) {
		try {
			return this.handleFindWikiSiteContentByDomainObjectAndName(domainId, siteName);
		} catch (Throwable th) {
			throw new WikiServiceException(
					"Error performing 'WikiService.findWikiSiteContentByDomainObjectAndName(Long domainId, String siteName)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for
	 * {@link #findWikiSiteContentByDomainObjectAndName(Long, String)}
	 */
	protected abstract WikiSiteContentInfo handleFindWikiSiteContentByDomainObjectAndName(Long domainId, String siteName)
			throws Exception;

	/**
	 * @see WikiService#saveImage(WikiSiteInfo, FileInfo)
	 */
	public void saveImage(WikiSiteInfo wikiSiteInfo, FileInfo image) {
		try {
			this.handleSaveImage(wikiSiteInfo, image);
		} catch (Throwable th) {
			throw new WikiServiceException(
					"Error performing 'WikiService.saveImage(WikiSiteInfo wikiSiteInfo, FileInfo image)' --> " + th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #saveImage(WikiSiteInfo, FileInfo)}
	 */
	protected abstract void handleSaveImage(WikiSiteInfo wikiSiteInfo, FileInfo image) throws Exception;

	/**
	 * @see WikiService#deleteImage(Long)
	 */
	public void deleteImage(Long fileId) {
		try {
			this.handleDeleteImage(fileId);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.deleteImage(Long fileId)' --> " + th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #deleteImage(Long)}
	 */
	protected abstract void handleDeleteImage(Long fileId) throws Exception;

	/**
	 * @see WikiService#findImagesByDomainId(Long)
	 */
	public List findImagesByDomainId(Long domainId) {
		try {
			return this.handleFindImagesByDomainId(domainId);
		} catch (Throwable th) {
			throw new WikiServiceException("Error performing 'WikiService.findImagesByDomainId(Long domainId)' --> " + th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #findImagesByDomainId(Long)}
	 */
	protected abstract List handleFindImagesByDomainId(Long domainId) throws Exception;

	/**
	 * @see WikiService#findAllExportableWikiCoursesByInstituteAndUser(org.openuss.lecture.InstituteInfo,
	 *      UserInfo, org.openuss.lecture.CourseInfo)
	 */
	public List<WikiSiteInfo> findAllExportableWikiCoursesByInstituteAndUser(InstituteInfo institute, UserInfo user,
			CourseInfo importCourse) {
		try {
			return this.handleFindAllExportableWikiCoursesByInstituteAndUser(institute, user, importCourse);
		} catch (Throwable th) {
			throw new WikiServiceException(
					"Error performing 'WikiService.findAllExportableWikiCoursesByInstituteAndUser(org.openuss.lecture.InstituteInfo institute, UserInfo user, org.openuss.lecture.CourseInfo importCourse)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for
	 * {@link #findAllExportableWikiCoursesByInstituteAndUser(org.openuss.lecture.InstituteInfo, UserInfo, org.openuss.lecture.CourseInfo)}
	 */
	protected abstract List<WikiSiteInfo> handleFindAllExportableWikiCoursesByInstituteAndUser(InstituteInfo institute,
			UserInfo user, CourseInfo importCourse) throws Exception;

	/**
	 * @see WikiService#importWikiVersions(Long, Long)
	 */
	public void importWikiVersions(Long importDomainId, Long exportDomainId) {
		try {
			this.handleImportWikiVersions(importDomainId, exportDomainId);
		} catch (Throwable th) {
			throw new WikiServiceException(
					"Error performing 'WikiService.importWikiVersions(Long importDomainId, Long exportDomainId)' --> " + th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #importWikiVersions(Long, Long)}
	 */
	protected abstract void handleImportWikiVersions(Long importDomainId, Long exportDomainId) throws Exception;

	/**
	 * @see WikiService#importWikiSites(Long, Long)
	 */
	public void importWikiSites(Long importDomainId, Long exportDomainId) {
		try {
			this.handleImportWikiSites(importDomainId, exportDomainId);
		} catch (Throwable th) {
			throw new WikiServiceException(
					"Error performing 'WikiService.importWikiSites(Long importDomainId, Long exportDomainId)' --> " + th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #importWikiSites(Long, Long)}
	 */
	protected abstract void handleImportWikiSites(Long importDomainId, Long exportDomainId) throws Exception;

	/**
	 * Gets the current <code>principal</code> if one has been set, otherwise
	 * returns <code>null</code>.
	 * 
	 * @return the current principal
	 */
	protected Principal getPrincipal() {
		return PrincipalStore.get();
	}
}