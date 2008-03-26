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
	
	private static final String WIKI_STARTSITE_NAME = "index";
	
	private static final String DEFAULT_TEST_SITE_NAME = "default-test-site-name";
	private static final String DEFAULT_TEST_SITE_TEXT = "default-test-site-text";
	private static final String DEFAULT_TEST_SITE_NOTE = "default-test-site-note";
	
	private static final String SAMPLE_IMAGE_FILE_NAME_TEMPLATE = "sample-image";
	private static final String SAMPLE_IMAGE_FILE_NAME_SEPARATOR = ".";
	
	private static final int NUMBER_OF_NEW_WIKI_SITES = 10;
	private static final int NUMBER_OF_NEW_WIKI_SITE_VERSIONS = 5;

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
	
	/**
	 * Tests the creation of a default index site when there is currently no
	 * WikiSiteVersion for WIKI_STARTSITE_NAME.
	 */
	public void testCreateDefaultIndexSite() {
		final WikiSiteContentInfo defaultIndexSite = wikiService.findWikiSiteContentByDomainObjectAndName(defaultDomainObject.getId(), WIKI_STARTSITE_NAME);
		assertNotNull(defaultIndexSite);
	}
	
	/**
	 * Tests the creation, storage and selection of the newest version of
	 * a WikiSiteContentInfo.
	 */
	public void testCreateAndFindNewestWikiSite() {
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo();
		wikiService.saveWikiSite(wikiSite);
		assertNotNull(wikiSite.getId());
		assertNotNull(wikiSite.getWikiSiteId());
		
		final WikiSiteContentInfo newestWikiSite = wikiService.getNewestWikiSiteContent(wikiSite.getWikiSiteId());
		assertEquals(wikiSite, newestWikiSite);
	}
	
	/**
	 * Tests the creation, storage and selection of several
	 * WikiSiteContentInfos respectively WikiSiteInfos.
	 */
	public void testCreateAndFindWikiSite() {
		final String newSiteName = DEFAULT_TEST_SITE_NAME + "-new";		
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo();
		wikiService.saveWikiSite(wikiSite);
		assertNotNull(wikiSite.getId());
		assertNotNull(wikiSite.getWikiSiteId());
		
		final WikiSiteInfo newWikiSite = wikiService.getWikiSite(wikiSite.getWikiSiteId());
		assertNotNull(newWikiSite);
		
		newWikiSite.setName(newSiteName);
		wikiService.saveWikiSite(newWikiSite);
		final WikiSiteInfo foundNewWikiSite = wikiService.getWikiSite(newWikiSite.getWikiSiteId());
		assertEquals(foundNewWikiSite.getName(), newSiteName);
		
		final WikiSiteContentInfo foundNewWikiSiteContent = wikiService.getWikiSiteContent(wikiSite.getId());
		assertNotNull(foundNewWikiSiteContent.getId());
	}
	
	/**
	 * Tests the creation, storage, version control and selection of several
	 * WikiSiteContentInfos respectively WikiSiteInfos.
	 */
	@SuppressWarnings("unchecked")
	public void testCreateAndFindWikiSiteVersion() {		
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo();
		for (int counter = 0; counter < NUMBER_OF_NEW_WIKI_SITES; counter++) {
			wikiSite.setId(null);
			wikiService.saveWikiSite(wikiSite);
		}
		
		final List<WikiSiteInfo> wikiSites = wikiService.findWikiSiteVersionsByWikiSite(wikiSite.getWikiSiteId());
		assertEquals(NUMBER_OF_NEW_WIKI_SITES, wikiSites.size());
	}
	
	/**
	 * Tests the creation, storage and selection of several WikiSiteInfos.
	 */
	@SuppressWarnings("unchecked")
	public void testCreateAndFindWikiSites() {
		final Set<String> createdSiteNames = createTestSites(NUMBER_OF_NEW_WIKI_SITES);
		final Set<String> foundSiteNames = new HashSet<String>();
		
		final List<WikiSiteInfo> wikiSites = wikiService.findWikiSitesByDomainObject(defaultDomainObject.getId());
		assertEquals(NUMBER_OF_NEW_WIKI_SITES, wikiSites.size());
		
		for (WikiSiteInfo wikiSite : wikiSites) {
			foundSiteNames.add(wikiSite.getName());
		}
		assertEquals(createdSiteNames, foundSiteNames);
	}
	
	private Set<String> createTestSites(final int numberOfNewWikiSites) {
		final String siteNameTemplate = DEFAULT_TEST_SITE_NAME;
		final Set<String> createdSiteNames = new HashSet<String>();
		
		for (int counter = 0; counter < numberOfNewWikiSites; counter++) {
			final String siteName = siteNameTemplate + counter;
			createdSiteNames.add(siteName);
			
			final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo(siteName);
			wikiService.saveWikiSite(wikiSite);
		}
		
		return createdSiteNames;
	}
	
	/**
	 * Tests the creation, storage and deleting of a WikiSiteContentInfo.
	 */
	public void testCreateAndDeleteWikiSiteVersion(){
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo();
		wikiService.saveWikiSite(wikiSite);
		
		final Long wikiSiteVersionId = wikiSite.getId();
		final Long wikiSiteId = wikiSite.getWikiSiteId();
		wikiService.deleteWikiSiteVersion(wikiSiteVersionId);
		wikiService.deleteWikiSite(wikiSiteId);		
		
		final WikiSiteInfo wikiSiteInfo = wikiService.getWikiSite(wikiSiteId);
		assertNull(wikiSiteInfo);
	}
	
	/**
	 * Tests the creation, storage, selection and deleting of an Image.
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void testCreateFindAndRemoveImage() throws IOException {
		final Map<String, FileInfo> savedFiles = new HashMap<String, FileInfo>();
		final RenderedImage sampleImage = createSampleImage();
		final WikiSiteContentInfo defaultIndexSite = wikiService.findWikiSiteContentByDomainObjectAndName(defaultDomainObject.getId(), WIKI_STARTSITE_NAME);
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
	
	@SuppressWarnings("unchecked")
	public void testImportWikiSites() {
		Set<String> exportWikiSiteNames = createWikiImportSetup();
		
		final Course importCourse = testUtility.createUniqueCourseInDB();
		wikiService.importWikiSites(importCourse.getId(), defaultDomainObject.getId());
		
		final List<WikiSiteInfo> importedWikiSites = wikiService.findWikiSitesByDomainObject(importCourse.getId());
		for (WikiSiteInfo importedWikiSite : importedWikiSites) {
			assertTrue(exportWikiSiteNames.contains(importedWikiSite.getName()));
			
			final List<WikiSiteInfo> importedWikiSiteVersions = wikiService.findWikiSiteVersionsByWikiSite(importedWikiSite.getWikiSiteId());
			assertEquals(1, importedWikiSiteVersions.size());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void testImportWikiSiteVersions() {
		Set<String> exportWikiSiteNames = createWikiImportSetup();
		
		final Course importCourse = testUtility.createUniqueCourseInDB();
		wikiService.importWikiVersions(importCourse.getId(), defaultDomainObject.getId());
		
		final List<WikiSiteInfo> importedWikiSites = wikiService.findWikiSitesByDomainObject(importCourse.getId());
		for (WikiSiteInfo importedWikiSite : importedWikiSites) {
			assertTrue(exportWikiSiteNames.contains(importedWikiSite.getName()));
			
			final List<WikiSiteInfo> importedWikiSiteVersions = wikiService.findWikiSiteVersionsByWikiSite(importedWikiSite.getWikiSiteId());
			assertEquals(NUMBER_OF_NEW_WIKI_SITE_VERSIONS, importedWikiSiteVersions.size());
		}
	}
	
	private Set<String> createWikiImportSetup() {
		wikiService.findWikiSiteContentByDomainObjectAndName(defaultDomainObject.getId(), WIKI_STARTSITE_NAME);
		
		Set<String> exportWikiSiteNames = createTestSites(NUMBER_OF_NEW_WIKI_SITES);
		for (int counter = 1; counter < NUMBER_OF_NEW_WIKI_SITE_VERSIONS; counter++) {
			exportWikiSiteNames = createTestSites(NUMBER_OF_NEW_WIKI_SITES);
			wikiService.saveWikiSite(createDefaultWikiSiteContentInfo(WIKI_STARTSITE_NAME));
		}
		
		exportWikiSiteNames.add(WIKI_STARTSITE_NAME);
		
		return exportWikiSiteNames;
	}
	
//	public void testFindInstitutes(){
//		Institute institute = testUtility.createUniqueInstituteInDB();
//		User user = testUtility.createUniqueUserInDB();
//		
//		Course course = testUtility.createUniqueCourseInDB();
//		wikiService.findAllExportableWikiCoursesByInstituteAndUser(institute, user, course);
//		
//	}
	
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
	
	public void testStableVersion() {
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo();
		wikiSite.setStable(true);
		wikiService.saveWikiSite(wikiSite);
		
		WikiSiteContentInfo newStable = wikiService.getNewestStableWikiSiteContent(wikiSite.getWikiSiteId());
		
		assertEquals(newStable.isStable(),true);
	}
	
	private WikiSiteContentInfo createDefaultWikiSiteContentInfo() {
		return createDefaultWikiSiteContentInfo(DEFAULT_TEST_SITE_NAME);
	}
	
	private WikiSiteContentInfo createDefaultWikiSiteContentInfo(String siteName) {
		Date creationDate = new Date();
		
		final WikiSiteContentInfo wikiSite = new WikiSiteContentInfo();
		wikiSite.setName(siteName);
		wikiSite.setText(DEFAULT_TEST_SITE_TEXT);
		wikiSite.setNote(DEFAULT_TEST_SITE_NOTE);
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
	
	/**
	 * Enum representing the supported Image formats.
	 * @author Projektseminar WS 07/08, Team Collaboration
	 */
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