//OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.wiki;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

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
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public class WikiServiceIntegrationTest extends WikiServiceIntegrationTestBase {
	
	private enum ImageType {
		JPG("jpg"), PNG("png");
		
		private final String extension;
		
		private ImageType(String extension) {
			this.extension = extension;
		}

		public String getExtension() {
			return extension;
		}
	}
	
	// FIXME dyck: handle hardcodes!!
	// TODO dyck: Javadoc
	
	private static final String siteText = "default-test-site-text";
	private static final String siteNote = "default-test-site-note";
	private static final String WIKI_STARTSITE_NAME = "index";
	
	private static final String SAMPLE_IMAGE_FILE_NAME_TEMPLATE = "sample-image";
	private static final String SAMPLE_IMAGE_FILE_NAME_SEPARATOR = ".";
	

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
		final String siteName2 = "create-and-find-wiki-site-test-new";		
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo(siteName);
		wikiService.saveWikiSite(wikiSite);
		assertNotNull(wikiSite.getId());
		assertNotNull(wikiSite.getWikiSiteId());
		
		final WikiSiteInfo newWikiSite = wikiService.getWikiSite(wikiSite.getWikiSiteId());
		assertNotNull(newWikiSite);
		
		newWikiSite.setName(siteName2);
		wikiService.saveWikiSite(newWikiSite);
		final WikiSiteInfo newWikiSite2 = wikiService.getWikiSite(newWikiSite.getWikiSiteId());
		assertEquals(newWikiSite2.getName(), siteName2);
		
		final WikiSiteContentInfo newWikiSite3 = wikiService.getWikiSiteContent(wikiSite.getWikiSiteId());
		assertNotNull(newWikiSite3);
		
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
	
	@SuppressWarnings("unchecked")
	public void testCreateFindAndRemoveImage() throws IOException {
		final Map<String, FileInfo> savedFiles = new HashMap<String, FileInfo>();
		final RenderedImage sampleImage = createSampleImage();
		final WikiSiteContentInfo defaultIndexSite = wikiService.findWikiSiteContentByDomainObjectAndName(defaultDomainObject.getId(), "index");
		assertNotNull(defaultIndexSite);
		
		for (ImageType imageType : ImageType.values()) {
			final FileInfo fileInfo = createSampleImageFileInfo(imageType, sampleImage);
			savedFiles.put(imageType.getExtension(), fileInfo);
			wikiService.saveImage(defaultIndexSite, fileInfo);
		}
		
		final List<FolderEntryInfo> imageFolderEntries = wikiService.findImagesByDomainId(defaultDomainObject.getId());
		assertEquals(ImageType.values().length, imageFolderEntries.size());
		
		for (FolderEntryInfo imageFolderEntry : imageFolderEntries) {
			FileInfo imageFile = documentService.getFileEntry(imageFolderEntry.getId(), true);
			assertNotNull(imageFile);
			
			final String extension = imageFile.getExtension();
			final FileInfo originalImageFile = savedFiles.get(extension);
			assertNotNull(originalImageFile);
			assertEquals(originalImageFile, imageFile);
			
			wikiService.deleteImage(imageFile.getId());
			savedFiles.remove(extension);
		}
		
		assertTrue(savedFiles.isEmpty());
	}
	
	public void testImportWiki(){
		final String siteName = "testImportWiki";
		final WikiSiteContentInfo defaultIndexSite = wikiService.findWikiSiteContentByDomainObjectAndName(defaultDomainObject.getId(), "index");
		assertNotNull(defaultIndexSite);
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo(siteName);
		final Course courseB = testUtility.createUniqueCourseInDB();
		final Course courseC = testUtility.createUniqueCourseInDB();
		
		wikiService.saveWikiSite(wikiSite);
		wikiService.importWikiSites(courseB.getId(), wikiSite.getDomainId());
		WikiSiteContentInfo newWikiSite = wikiService.findWikiSiteContentByDomainObjectAndName(courseB.getId(), siteName);
		assertNotNull(newWikiSite);
		
		wikiService.importWikiVersions(courseC.getId(), wikiSite.getDomainId());
		WikiSiteContentInfo newWikiSite2 = wikiService.findWikiSiteContentByDomainObjectAndName(courseB.getId(), siteName);
		assertNotNull(newWikiSite2);
		
	}
	
	private RenderedImage createSampleImage() {
		final int width = 100;
		final int height = 100;
		final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		return bufferedImage;
	}
	
	private FileInfo createSampleImageFileInfo(ImageType imageType, RenderedImage image) throws IOException {
		final String extension = SAMPLE_IMAGE_FILE_NAME_SEPARATOR + imageType.getExtension();
		final String fileName = SAMPLE_IMAGE_FILE_NAME_TEMPLATE + extension;
		final Date creationDate = new Date();
		final File file = File.createTempFile(SAMPLE_IMAGE_FILE_NAME_TEMPLATE, extension);
		ImageIO.write(image, imageType.getExtension(), file);
		final FileInputStream inputStream = new FileInputStream(file);
		
		final FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(fileName);
		fileInfo.setDescription("");
		fileInfo.setContentType("");
		fileInfo.setFileSize((int) file.length());
		fileInfo.setCreated(creationDate);
		fileInfo.setModified(creationDate);
		fileInfo.setInputStream(inputStream);
		
		return fileInfo;
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

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}

}