//OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.wiki;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.acegisecurity.acl.AclManager;
import org.openuss.TestUtility;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.Course;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * JUnit Test for Spring Hibernate WikiService class.
 * @see org.openuss.wiki.WikiService
 */
public class WikiServiceIntegrationTest extends WikiServiceIntegrationTestBase {
	
	private static final String siteText = "default-test-site-text";
	private static final String siteNote = "default-test-site-note";
	private static final String WIKI_STARTSITE_NAME = "index";

	private SecurityService securityService;

	private AclManager aclManager;

	private DefaultDomainObject defaultDomainObject;

	private User assistantUser;

	private User nonAssistantUser;

	private TestUtility testUtility;
	
	private DocumentService documentService;

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		AcegiUtils.setAclManager(aclManager);
		defaultDomainObject = createDomainObject();
		assistantUser = testUtility.createUserSecureContext();
		securityService.createObjectIdentity(defaultDomainObject, null);
		securityService.setPermissions(assistantUser, defaultDomainObject, LectureAclEntry.ASSIST);

		nonAssistantUser = testUtility.createUserSecureContext();
		securityService.setPermissions(nonAssistantUser, defaultDomainObject, LectureAclEntry.PAPER_PARTICIPANT);
	}
	
	public void testCreateDefaultIndexSite() {
		final WikiSiteContentInfo defaultIndexSite = wikiService.findWikiSiteContentByDomainObjectAndName(defaultDomainObject.getId(), "index");
		assertNotNull(defaultIndexSite);
	}
	
	public void testCreateAndFindNewestWikiSite() {
		final String siteName = "create-and-find-wiki-site-test";
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo(siteName);
		wikiService.saveWikiSite(wikiSite);
		assertNotNull(wikiSite.getId());
		assertNotNull(wikiSite.getWikiSiteId());
		
		final WikiSiteContentInfo newestWikiSite = wikiService.getNewestWikiSiteContent(wikiSite.getWikiSiteId());
		assertEquals(wikiSite, newestWikiSite);
	}
	
	public void testCreateAndFindWikiSite() {
		final String siteName = "create-and-find-wiki-site-test";
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo(siteName);
		wikiService.saveWikiSite(wikiSite);
		assertNotNull(wikiSite.getId());
		assertNotNull(wikiSite.getWikiSiteId());
		
		final WikiSiteInfo newWikiSite = wikiService.getWikiSite(wikiSite.getWikiSiteId());
		assertNotNull(newWikiSite);
	}
	
	
	@SuppressWarnings("unchecked")
	public void testCreateAndFindWikiSiteVersion() {
		final String siteName = "create-and-find-wiki-site-version-test";
		final int iterations = 10;
		
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo(siteName);
		for (int counter = 0; counter < iterations; counter++) {
			wikiSite.setId(null);
			wikiService.saveWikiSite(wikiSite);
		}
		
		final List<WikiSiteInfo> wikiSites = wikiService.findWikiSiteVersionsByWikiSite(wikiSite.getWikiSiteId());
		assertEquals(iterations, wikiSites.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testCreateAndFindWikiSites() {
		final String siteNameTemplate = "create-and-find-wiki-sites-test";
		final int iterations = 10;
		final Set<String> createdSiteNames = new HashSet<String>();
		final Set<String> foundSiteNames = new HashSet<String>();
		
		for (int counter = 0; counter < iterations; counter++) {
			final String siteName = siteNameTemplate + counter;
			createdSiteNames.add(siteName);
			
			final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo(siteName);
			wikiService.saveWikiSite(wikiSite);
		}
		
		final List<WikiSiteInfo> wikiSites = wikiService.findWikiSitesByDomainObject(defaultDomainObject.getId());
		assertEquals(iterations, wikiSites.size());
		
		for (WikiSiteInfo wikiSite : wikiSites) {
			foundSiteNames.add(wikiSite.getName());
		}
		assertEquals(createdSiteNames, foundSiteNames);
	}
	
	public void testCreateAndDeleteWikiSiteVersion(){
		final String siteName = "testCreateAndDeleteWikiSiteVersion";
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo(siteName);
		wikiService.saveWikiSite(wikiSite);
		
		final Long wikiSiteVersionId = wikiSite.getId();
		final Long wikiSiteId = wikiSite.getWikiSiteId();
		wikiService.deleteWikiSiteVersion(wikiSiteVersionId);
		wikiService.deleteWikiSite(wikiSiteId);		
		
		final WikiSiteInfo wikiSiteInfo = wikiService.getWikiSite(wikiSiteId);
		assertNull(wikiSiteInfo);
	}
	
	public void testCreateNewIndexSite(){
		final Course course =  testUtility.createUniqueCourseInDB();
		final WikiSiteContentInfo wikiSiteContentInfo = wikiService.findWikiSiteContentByDomainObjectAndName(course.getId(),WIKI_STARTSITE_NAME);
		assertNotNull(wikiSiteContentInfo.getText());
	}
	
	@SuppressWarnings("unchecked")	
	public void testCreateRetrieveImage(){
		final FileInfo image = createFileInfo("file");
		final Course course =  testUtility.createUniqueCourseInDB();
		final WikiSiteContentInfo wikiSiteContentInfo = wikiService.findWikiSiteContentByDomainObjectAndName(course.getId(),WIKI_STARTSITE_NAME);
		final WikiSiteInfo wikiSiteInfo = wikiService.getWikiSite(wikiSiteContentInfo.getWikiSiteId());
		wikiService.saveImage(wikiSiteInfo, image);
		
//		List<FolderEntryInfo> images = wikiService.findImagesByDomainId(course.getId()); FIXME null pointer exeption
//		for(FolderEntryInfo img : images){
//			assertNotNull(img);
//		}
		
		wikiService.deleteImage(image.getId());		
		
	}
	
	public void testStableVersion(){
		final String siteName = "testCreateAndDeleteWikiSiteVersion";
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo(siteName);
		wikiSite.setStable(true);
		wikiService.saveWikiSite(wikiSite);
		
		WikiSiteContentInfo newStable = wikiService.getNewestStableWikiSiteContent(wikiSite.getWikiSiteId());
		
		assertEquals(newStable.isStable(),true);
	}
	
	private WikiSiteContentInfo createDefaultWikiSiteContentInfo(String siteName) {
		Date creationDate = new Date();
		
		final WikiSiteContentInfo wikiSite = new WikiSiteContentInfo();
		wikiSite.setName(siteName);
		wikiSite.setText(siteText);
		wikiSite.setNote(siteNote);
		wikiSite.setCreationDate(creationDate);
		wikiSite.setAuthorId(assistantUser.getId());
		wikiSite.setDomainId(defaultDomainObject.getId());
		wikiSite.setDeleted(false);
		wikiSite.setReadOnly(false);
		wikiSite.setStable(false);
		
		return wikiSite;
	}
	
	private DefaultDomainObject createDomainObject() {
		DefaultDomainObject defaultDomainObject = new DefaultDomainObject(TestUtility.unique());
		return defaultDomainObject;
	}
	
	private FileInfo createFileInfo(String name) {
		FileInfo info = new FileInfo();
		byte[] data = "this is the content of the file".getBytes();
		info.setFileName(name);
		info.setDescription("description");
		info.setContentType("plain/text");
		info.setFileSize(data.length);
		info.setCreated(new Date());
		info.setModified(new Date());
		info.setInputStream(new ByteArrayInputStream(data));
		return info;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}