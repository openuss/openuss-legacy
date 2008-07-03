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
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Department;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteInfo;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
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
	
	private static final String DEFAULT_IMAGE_FILE_NAME_TEMPLATE = "default-image";
	private static final String DEFAULT_IMAGE_FILE_NAME_SEPARATOR = ".";
	private static final String DEFAULT_IMAGE_SITE_TEXT_TEMPLATE = "<img src=\"%s?fileid=%s\" />";
	private static final int DEFAULT_IMAGE_WIDTH = 100;
	private static final int DEFAULT_IMAGE_HEIGHT = 100;
	
	private static final int NUMBER_OF_NEW_WIKI_SITES = 10;
	private static final int NUMBER_OF_NEW_WIKI_SITE_VERSIONS = 5;

	private SecurityService securityService;

	private AclManager aclManager;
	
	private Department defaultDepartment;
	
	private Institute defaultInstitute;
	
	private CourseType defaultCourseType;

	private Course defaultCourse;

	private User assistantUser;

	private TestUtility testUtility;
	
	private DocumentService documentService;
	
	protected CourseService courseService;

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		AcegiUtils.setAclManager(aclManager);
		
		assistantUser = testUtility.createUserSecureContext();
		
		setupOrganizations();
	}
	
	/**
	 * Creates a default Organization structure in the form of
	 * a default Department, Institute, CourseType and Course.
	 */
	private void setupOrganizations() {
		defaultDepartment = testUtility.createUniqueDepartmentInDB();
		defaultInstitute = testUtility.createUniqueInstituteInDB();
		defaultCourseType = testUtility.createUniqueCourseTypeInDB();
		defaultCourse = testUtility.createUniqueCourseInDB();
		
		defaultDepartment.add(defaultInstitute);
		testUtility.getDepartmentDao().update(defaultDepartment);
		
		defaultInstitute.add(defaultCourseType);
		testUtility.getInstituteDao().update(defaultInstitute);
		
		defaultCourseType.add(defaultCourse);
		testUtility.getCourseTypeDao().update(defaultCourseType);
		
		securityService.setPermissions(assistantUser, defaultCourse, LectureAclEntry.ASSIST);
	}
	
	/**
	 * Tests the creation of a default index site when there is currently no
	 * WikiSiteVersion for WIKI_STARTSITE_NAME.
	 */
	public void testCreateDefaultIndexSite() {
		final WikiSiteContentInfo defaultIndexSite = wikiService.findWikiSiteContentByDomainObjectAndName(defaultCourse.getId(), WIKI_STARTSITE_NAME);
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
	 * Creates a WikiSiteContentInfo with the name <code>DEFAULT_TEST_SITE_NAME</code>.
	 * @return Created WikiSiteContentInfo.
	 */
	private WikiSiteContentInfo createDefaultWikiSiteContentInfo() {
		return createDefaultWikiSiteContentInfo(DEFAULT_TEST_SITE_NAME);
	}
	
	/**
	 * Creates a WikiSiteContentInfo.
	 * @param siteName Name of the WikiSiteContentInfo.
	 * @return Created WikiSiteContentInfo.
	 */
	private WikiSiteContentInfo createDefaultWikiSiteContentInfo(String siteName) {
		Date creationDate = new Date();
		
		final WikiSiteContentInfo wikiSite = new WikiSiteContentInfo();
		wikiSite.setName(siteName);
		wikiSite.setText(DEFAULT_TEST_SITE_TEXT);
		wikiSite.setNote(DEFAULT_TEST_SITE_NOTE);
		wikiSite.setCreationDate(creationDate);
		wikiSite.setAuthorId(assistantUser.getId());
		wikiSite.setDomainId(defaultCourse.getId());
		wikiSite.setDeleted(false);
		wikiSite.setReadOnly(false);
		wikiSite.setStable(false);
		
		return wikiSite;
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
		final Set<String> createdSiteNames = createTestSites();
		final Set<String> foundSiteNames = new HashSet<String>();
		
		final List<WikiSiteInfo> wikiSites = wikiService.findWikiSitesByDomainObject(defaultCourse.getId());
		assertEquals(NUMBER_OF_NEW_WIKI_SITES, wikiSites.size());
		
		for (WikiSiteInfo wikiSite : wikiSites) {
			foundSiteNames.add(wikiSite.getName());
		}
		assertEquals(createdSiteNames, foundSiteNames);
	}
	
	/**
	 * Creates <code>NUMBER_OF_NEW_WIKI_SITES</code> default
	 * WikiSite with one version each.
	 * @return Set of names of created WikiSites.
	 */
	private Set<String> createTestSites() {
		final String siteNameTemplate = DEFAULT_TEST_SITE_NAME;
		final Set<String> createdSiteNames = new HashSet<String>();
		
		for (int counter = 0; counter < NUMBER_OF_NEW_WIKI_SITES; counter++) {
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

	public void testRemoveUserDependencies(){
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo();
		wikiService.saveWikiSite(wikiSite);
		
		final Long wikiSiteVersionId = wikiSite.getId();
		final Long wikiSiteId = wikiSite.getWikiSiteId();
		
		wikiService.removeUserDependencies(assistantUser);
		
		WikiSiteContentInfo content = wikiService.getWikiSiteContent(wikiSiteVersionId);
		
		assertTrue(content.getAuthorId().equals(new Long(-11)));
	}
	
	/**
	 * Tests the creation, storage, selection and deleting of an Image.
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void testCreateFindAndRemoveImage() throws IOException {
		final Map<String, FileInfo> savedFiles = new HashMap<String, FileInfo>();
		final RenderedImage sampleImage = createDefaultImage();
		final WikiSiteContentInfo defaultIndexSite = wikiService.findWikiSiteContentByDomainObjectAndName(defaultCourse.getId(), WIKI_STARTSITE_NAME);
		assertNotNull(defaultIndexSite);
		
		for (ImageType imageType : ImageType.values()) {
			final FileInfo fileInfo = createDefaultImageFileInfo(imageType, sampleImage);
			savedFiles.put(imageType.getExtension(), fileInfo);
			wikiService.saveImage(defaultIndexSite, fileInfo);
		}
		
		final List<FolderEntryInfo> imageFolderEntries = wikiService.findImagesByDomainId(defaultCourse.getId());
		assertEquals(ImageType.values().length, imageFolderEntries.size());
		
		for (FolderEntryInfo imageFolderEntry : imageFolderEntries) {
			final FileInfo imageFile = documentService.getFileEntry(imageFolderEntry.getId(), true);
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
	
	/**
	 * Creates a default Image.
	 * @return Default Image.
	 */
	private RenderedImage createDefaultImage() {
		final int width = DEFAULT_IMAGE_WIDTH;
		final int height = DEFAULT_IMAGE_HEIGHT;
		final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		return bufferedImage;
	}
	
	/**
	 * Creates a FileInfo in a specific ImageType regarding a specific Image.
	 * @param imageType Specific ImageType.
	 * @param image Specific Image.
	 * @return FileInfo in the specified ImagaType regarding the specified Image.
	 * @throws IOException
	 */
	private FileInfo createDefaultImageFileInfo(ImageType imageType, RenderedImage image) throws IOException {
		final String extension = DEFAULT_IMAGE_FILE_NAME_SEPARATOR + imageType.getExtension();
		final String fileName = DEFAULT_IMAGE_FILE_NAME_TEMPLATE + extension;
		final Date creationDate = new Date();
		final File file = File.createTempFile(DEFAULT_IMAGE_FILE_NAME_TEMPLATE, extension);
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
	
	/**
	 * Test the Import of WikiSites with the newest Version of each WikiSite.
	 */
	@SuppressWarnings("unchecked")
	public void testImportWikiSites() {
		final Set<String> exportWikiSiteNames = createWikiImportSetup();
		
		final Course importCourse = testUtility.createUniqueCourseInDB();
		wikiService.importWikiSites(importCourse.getId(), defaultCourse.getId());
		
		final List<WikiSiteInfo> importedWikiSites = wikiService.findWikiSitesByDomainObject(importCourse.getId());
		for (WikiSiteInfo importedWikiSite : importedWikiSites) {
			assertTrue(exportWikiSiteNames.contains(importedWikiSite.getName()));
			
			final List<WikiSiteInfo> importedWikiSiteVersions = wikiService.findWikiSiteVersionsByWikiSite(importedWikiSite.getWikiSiteId());
			assertEquals(1, importedWikiSiteVersions.size());
		}
	}
	
	/**
	 * Test the Import of WikiSites with all Versions of each WikiSite.
	 */
	@SuppressWarnings("unchecked")
	public void testImportWikiSiteVersions() {
		final Set<String> exportWikiSiteNames = createWikiImportSetup();
		
		final Course importCourse = testUtility.createUniqueCourseInDB();
		wikiService.importWikiVersions(importCourse.getId(), defaultCourse.getId());
		
		final List<WikiSiteInfo> importedWikiSites = wikiService.findWikiSitesByDomainObject(importCourse.getId());
		for (WikiSiteInfo importedWikiSite : importedWikiSites) {
			assertTrue(exportWikiSiteNames.contains(importedWikiSite.getName()));
			
			final List<WikiSiteInfo> importedWikiSiteVersions = wikiService.findWikiSiteVersionsByWikiSite(importedWikiSite.getWikiSiteId());
			assertEquals(NUMBER_OF_NEW_WIKI_SITE_VERSIONS, importedWikiSiteVersions.size());
		}
	}
	
	/**
	 * Creates a default Wiki with <code>NUMBER_OF_NEW_WIKI_SITE_VERSIONS</code>
	 * WikiSites plus the default <code>WIKI_STARTSITE_NAME</code>. Each WikiSite
	 * has <code>NUMBER_OF_NEW_WIKI_SITE_VERSIONS</code> Versions.
	 * @return Set of names of created WikiSites.
	 */
	private Set<String> createWikiImportSetup() {
		wikiService.findWikiSiteContentByDomainObjectAndName(defaultCourse.getId(), WIKI_STARTSITE_NAME);
		
		Set<String> exportWikiSiteNames = createTestSites();
		for (int counter = 1; counter < NUMBER_OF_NEW_WIKI_SITE_VERSIONS; counter++) {
			exportWikiSiteNames = createTestSites();
			wikiService.saveWikiSite(createDefaultWikiSiteContentInfo(WIKI_STARTSITE_NAME));
		}
		
		exportWikiSiteNames.add(WIKI_STARTSITE_NAME);
		
		return exportWikiSiteNames;
	}
	
	/**
	 * Tests the Import of a Wiki with uploaded Images.
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void testWikiImportImages() throws IOException {
		final Course importCourse = testUtility.createUniqueCourseInDB();
		final Map<String, FileInfo> savedFiles = new HashMap<String, FileInfo>();
		StringBuilder siteContent = new StringBuilder();
		final RenderedImage sampleImage = createDefaultImage();
		final WikiSiteContentInfo defaultIndexSite = wikiService.findWikiSiteContentByDomainObjectAndName(defaultCourse.getId(), WIKI_STARTSITE_NAME);
		assertNotNull(defaultIndexSite);
		
		for (ImageType imageType : ImageType.values()) {
			final FileInfo fileInfo = createDefaultImageFileInfo(imageType, sampleImage);
			savedFiles.put(imageType.getExtension(), fileInfo);
			wikiService.saveImage(defaultIndexSite, fileInfo);
			siteContent.append(String.format(DEFAULT_IMAGE_SITE_TEXT_TEMPLATE, fileInfo.getFileName(), fileInfo.getId()));
		}
		
		defaultIndexSite.setText(siteContent.toString());
		wikiService.saveWikiSite(defaultIndexSite);
		
		wikiService.importWikiSites(importCourse.getId(), defaultCourse.getId());
		
		final List<FolderEntryInfo> imageFolderEntries = wikiService.findImagesByDomainId(importCourse.getId());
		assertEquals(ImageType.values().length, imageFolderEntries.size());
		
		siteContent = new StringBuilder();
		
		for (FolderEntryInfo imageFolderEntry : imageFolderEntries) {
			final FileInfo imageFile = documentService.getFileEntry(imageFolderEntry.getId(), true);
			assertNotNull(imageFile);
			
			final String extension = imageFile.getExtension();
			final FileInfo originalImageFile = savedFiles.get(extension);
			assertNotNull(originalImageFile);
			siteContent.append(String.format(DEFAULT_IMAGE_SITE_TEXT_TEMPLATE, imageFolderEntry.getFileName(), imageFolderEntry.getId()));		
		}
		
		final WikiSiteContentInfo importedIndexSite = wikiService.findWikiSiteContentByDomainObjectAndName(importCourse.getId(), WIKI_STARTSITE_NAME);
		assertEquals(siteContent.toString(), importedIndexSite.getText());
	}
	
	/**
	 * Tests the selection of Courses that provide a Wiki for export.
	 */
	@SuppressWarnings("unchecked")
	public void testFindExportableWikiCourses() {
		final UserInfo assistantUserInfo = testUtility.getUserDao().toUserInfo(assistantUser);
		
		final Set<CourseInfo> exportableWikiCourses = setupExportableWikiCourses();
		
		final InstituteInfo defaultInstituteInfo = testUtility.getInstituteDao().toInstituteInfo(defaultInstitute);
		final CourseInfo defaultCourseInfo = testUtility.getCourseDao().toCourseInfo(defaultCourse);
		
		final List<CourseInfo> foundExportableWikiCourses = wikiService.findAllExportableWikiCoursesByInstituteAndUser(defaultInstituteInfo, assistantUserInfo, defaultCourseInfo);
		assertEquals(exportableWikiCourses.size(), foundExportableWikiCourses.size());
	}
	
	/**
	 * Creates a basic setup with exportable Wiki Courses.
	 * @return Set of CourseInfo of Courses that provide a Wiki for export.
	 */
	private Set<CourseInfo> setupExportableWikiCourses() {
		final Set<CourseInfo> exportableWikiCourses = new HashSet<CourseInfo>();
		final UserInfo assistantUserInfo = testUtility.getUserDao().toUserInfo(assistantUser);
		
		final Course assistantCourse = testUtility.createUniqueCourseInDB();
		assistantCourse.setWiki(true);
		final CourseInfo assistantCourseInfo = testUtility.getCourseDao().toCourseInfo(assistantCourse);
		courseService.addAssistant(assistantCourseInfo, assistantUserInfo);
		defaultCourseType.add(assistantCourse);
		securityService.setPermissions(assistantUser, assistantCourse, LectureAclEntry.ASSIST);
		testUtility.getCourseTypeDao().update(defaultCourseType);
		exportableWikiCourses.add(assistantCourseInfo);
		
		final Course noWikiCourse = testUtility.createUniqueCourseInDB();
		final CourseInfo noWikiCourseInfo = testUtility.getCourseDao().toCourseInfo(noWikiCourse);
		courseService.addAssistant(noWikiCourseInfo, assistantUserInfo);
		defaultCourseType.add(noWikiCourse);
		securityService.setPermissions(assistantUser, noWikiCourseInfo, LectureAclEntry.ASSIST);
		testUtility.getCourseTypeDao().update(defaultCourseType);
		
		return exportableWikiCourses;
	}
	
	/**
	 * Tests the setting a WikiSiteVersion stable.
	 */
	public void testStableVersion() {
		final WikiSiteContentInfo wikiSite = createDefaultWikiSiteContentInfo();
		wikiSite.setStable(true);
		wikiService.saveWikiSite(wikiSite);
		
		final WikiSiteContentInfo newerWikiSite = createDefaultWikiSiteContentInfo();
		wikiService.saveWikiSite(newerWikiSite);
		
		final WikiSiteContentInfo stableWikiSite = wikiService.getNewestStableWikiSiteContent(wikiSite.getWikiSiteId());
		assertEquals(wikiSite, stableWikiSite);
		assertTrue(stableWikiSite.isStable());
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

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

}