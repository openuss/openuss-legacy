package org.openuss.wiki;

import java.util.List;

/**
 * <p>
 * The WikiService serves all functions of the wiki to the presentation layer.
 * </p>
 * <p>
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 *         </p>
 */
public interface WikiService {

	/**
	 * <p>
	 * Updates a WikiSite for the course.
	 * </p>
	 * <p>
	 * 
	 * @param wikiSiteInfo
	 *            Info-Object with the information for the WikiSite.
	 *            </p>
	 */
	public void saveWikiSite(org.openuss.wiki.WikiSiteInfo wikiSiteInfo);

	/**
	 * <p>
	 * Creates a new WikiSite and WikiSiteVersion for the course or stores a new
	 * WikiSiteVersion if the wiki exists already.
	 * </p>
	 * <p>
	 * 
	 * @param wikiSiteContentInfo
	 *            Info-Object with the information for the WikiSite
	 *            </p>
	 */
	public void saveWikiSite(org.openuss.wiki.WikiSiteContentInfo wikiSiteContentInfo);

	/**
	 * <p>
	 * removes a WikiSite physically
	 * </p>
	 * <p>
	 * 
	 * @param wikiSiteId
	 *            the Id of the WikiSite to remove
	 *            </p>
	 */
	public void deleteWikiSite(Long wikiSiteId);

	/**
	 * <p>
	 * removes a WikiSiteVersion physically
	 * </p>
	 * <p>
	 * 
	 * @param wikiSiteVersionId
	 *            the Id of the WikiSiteVersion to remove
	 *            </p>
	 */
	public void deleteWikiSiteVersion(Long wikiSiteVersionId);

	/**
	 * <p>
	 * Gets a exisiting WikiSite
	 * </p>
	 * <p>
	 * 
	 * @param wikiSiteId
	 *            Id of the called WikiSite
	 *            </p>
	 *            <p>
	 * @return the WikiSiteInfo-Object of the called WikiSite
	 *         </p>
	 */
	public org.openuss.wiki.WikiSiteInfo getWikiSite(Long wikiSiteId);

	/**
	 * <p>
	 * gets a existing wikiSiteVersion to a WikiSite
	 * </p>
	 * <p>
	 * 
	 * @param wikiSiteVersionId
	 *            the Id of the called WikiSiteVersion
	 *            </p>
	 *            <p>
	 * @return the wikiSiteContentInfo-Object of the called WikiSiteVersion
	 *         </p>
	 */
	public org.openuss.wiki.WikiSiteContentInfo getWikiSiteContent(Long wikiSiteVersionId);

	/**
	 * <p>
	 * gets the newest WikiSiteVersion to a WikiSite
	 * </p>
	 * <p>
	 * 
	 * @param wikiSiteId
	 *            Id of the WikiSite the newest WikiSiteVersion is called
	 *            </p>
	 *            <p>
	 * @return wikiSiteContentInfo the Info-Object of the newest WikiSiteVersion
	 *         to the given WikiSiteId
	 *         </p>
	 */
	public org.openuss.wiki.WikiSiteContentInfo getNewestWikiSiteContent(Long wikiSiteId);

	/**
	 * <p>
	 * gets the newest WikiSiteVersion to a WikiSite
	 * </p>
	 * <p>
	 * 
	 * @param wikiSiteId
	 *            Id of the WikiSite the newest WikiSiteVersion is called
	 *            </p>
	 *            <p>
	 * @return wikiSiteInfo the Info-Object of the newest WikiSiteVersion to the
	 *         given WikiSiteId
	 *         </p>
	 */
	public org.openuss.wiki.WikiSiteInfo getNewestWikiSite(Long wikiSiteId);

	/**
	 * <p>
	 * Returns the newest stable WikiSiteVersion to a WikiSite
	 * </p>
	 * <p>
	 * 
	 * @param wikiSiteId
	 *            Id of the WikiSite the newest stable WikiSiteVersion is
	 *            called.
	 *            </p>
	 *            <p>
	 * @return wikiSiteInfo The Info-Object of the newest stable WikiSiteVersion
	 *         to the given WikiSiteId or <code>null</code> if there is no
	 *         stable Versions existing.
	 *         </p>
	 */
	public org.openuss.wiki.WikiSiteContentInfo getNewestStableWikiSiteContent(Long wikiSiteId);

	/**
	 * <p>
	 * finds all WikiSiteVersions to a WikiSite
	 * </p>
	 * <p>
	 * 
	 * @param wikiSiteId
	 *            the Id of the site the Versions are searched for.
	 *            </p>
	 *            <p>
	 * @return a List of all WikiSiteInfo-Objects of the WikiSite
	 *         </p>
	 */
	public List<WikiSiteInfo> findWikiSiteVersionsByWikiSite(Long wikiSiteId);

	/**
	 * <p>
	 * finds all WikiSites for a DomainObject.
	 * </p>
	 * <p>
	 * 
	 * @param domainId
	 *            Id of the course the pages should be displayed for
	 *            </p>
	 *            <p>
	 * @return a List of all WikiSiteInfos for the Course
	 *         </p>
	 */
	public List findWikiSitesByDomainObject(Long domainId);

	/**
	 * <p>
	 * finds a WikiSite by a DomainObject and the name of the site.
	 * </p>
	 * <p>
	 * 
	 * @param domainId
	 *            Id of the course the site is inside
	 *            </p>
	 *            <p>
	 * @param siteName
	 *            Name of the site that is searched
	 *            </p>
	 *            <p>
	 * @return WikiSiteContentInfo-Object of the Site
	 *         </p>
	 */
	public org.openuss.wiki.WikiSiteContentInfo findWikiSiteContentByDomainObjectAndName(Long domainId, String siteName);

	/**
	 * <p>
	 * Saves an Image.
	 * </p>
	 * <p>
	 * 
	 * @param wikiSiteInfo
	 *            WikiSiteInfo Object that belongs to the Image.
	 *            </p>
	 *            <p>
	 * @param image
	 *            Image that will be saved.
	 *            </p>
	 */
	public void saveImage(org.openuss.wiki.WikiSiteInfo wikiSiteInfo, org.openuss.documents.FileInfo image);

	/**
	 * <p>
	 * Deletes an Image.
	 * </p>
	 * <p>
	 * 
	 * @param fileId
	 *            Id of the Image that will be deleted.
	 *            </p>
	 */
	public void deleteImage(Long fileId);

	/**
	 * <p>
	 * finds all Images for a DomainObject.
	 * </p>
	 * <p>
	 * 
	 * @param domainId
	 *            Id of the DomainObject the Images should be displayed for.
	 *            </p>
	 *            <p>
	 * @return a List of all Images for the DomainObject.
	 *         </p>
	 */
	public List findImagesByDomainId(Long domainId);

	/**
	 * <p>
	 * Finds all Courses in a specific Institute where a specific User has the
	 * role Assistant.
	 * </p>
	 * <p>
	 * 
	 * @param institute
	 *            The Institute of the of the Courses that are searched for.
	 *            </p>
	 *            <p>
	 * @param user
	 *            The UserInfo of the of the courses that are searched for.
	 *            </p>
	 *            <p>
	 * @param importCourse
	 *            The Course that will import the Wiki. It will not be listet in
	 *            the result.
	 *            </p>
	 *            <p>
	 * @return A List of all CourseInfo-Objects in a specific Institute where a
	 *         specific User has the role Assisant.
	 *         </p>
	 */
	public List findAllExportableWikiCoursesByInstituteAndUser(org.openuss.lecture.InstituteInfo institute,
			org.openuss.security.UserInfo user, org.openuss.lecture.CourseInfo importCourse);

	/**
	 * <p>
	 * Imports the Wiki of the selected course including all versions of each
	 * Wiki site. The Wiki that refers to the DomainObject with
	 * <code>importDomainId</code> will be deleted and replaced by the imported
	 * one that refers to the DomainObject with <code>exportDomainId</code> when
	 * you proceed.
	 * </p>
	 * <p>
	 * 
	 * @param importDomainId
	 *            DomainId that refers to the DomainObject from which the Wiki
	 *            will be imported.
	 *            </p>
	 *            <p>
	 * @param exportDomainId
	 *            DomainId that refers to the DomainObject from which the Wiki
	 *            will be exported.
	 *            </p>
	 */
	public void importWikiVersions(Long importDomainId, Long exportDomainId);

	/**
	 * <p>
	 * Imports the Wiki of the selected course including only the newest version
	 * of each Wiki site. The Wiki that refers to the DomainObject with
	 * <code>importDomainId</code> will be deleted and replaced by the imported
	 * one that refers to the DomainObject with <code>exportDomainId</code> when
	 * you proceed.
	 * </p>
	 * <p>
	 * 
	 * @param importDomainId
	 *            DomainId that refers to the DomainObject from which the Wiki
	 *            will be imported.
	 *            </p>
	 *            <p>
	 * @param exportDomainId
	 *            DomainId that refers to the DomainObject from which the Wiki
	 *            will be exported.
	 *            </p>
	 */
	public void importWikiSites(Long importDomainId, Long exportDomainId);

}
