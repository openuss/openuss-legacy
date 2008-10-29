// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.acegisecurity.acl.AclManager;
import org.openuss.TestUtility;
import org.openuss.documents.FileInfo;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.SecurityService;

/**
 * JUnit Test for Spring Hibernate NewsService class.
 * 
 * @see org.openuss.news.NewsService
 */
public class NewsServiceIntegrationTest extends NewsServiceIntegrationTestBase {

	private Date past = new Date(System.currentTimeMillis() - 1000000);
	private Date future = new Date(System.currentTimeMillis() + 1000000);
	
	private SecurityService securityService;
	private AclManager aclManager;

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		testUtility.createAdminSecureContext();
		//FIXME ACEGI - AclUtility should be configured by dependency injection
		AcegiUtils.setAclManager(aclManager);
	}

	public void testCreateSaveDeleteNewsItem() {
		NewsItemInfo info = createNewsItem();
		
		securityService.createObjectIdentity(info.getPublisherIdentifier(), null);
		
		List<FileInfo> attachments = new ArrayList<FileInfo>();
		FileInfo file1 = createFileInfo("readme");
		FileInfo file2 = createFileInfo("license");
		attachments.add(file1);
		attachments.add(file2);
		
		info.setAttachments(attachments);
		
		newsService.saveNewsItem(info);
		assertNotNull(info.getId());
		assertNotNull(info.getAttachments().get(0).getId());
		assertNotNull(info.getAttachments().get(1).getId());
		
		commit();
		
		NewsItemInfo loaded = newsService.getNewsItem(info);
		assertNotNull(loaded);
		assertNotNull(loaded.getId());
		validateNewsItemInfo(info, loaded);
		assertEquals(2, loaded.getAttachments().size());
		assertTrue(loaded.getAttachments().contains(file1));
		assertTrue(loaded.getAttachments().contains(file2));
		
		info.getAttachments().remove(file1);
		FileInfo file3 = createFileInfo("newfile");
		info.getAttachments().add(file3);
		
		newsService.saveNewsItem(info);
		assertNotNull(info.getId());
		assertNotNull(info.getAttachments().get(0).getId());
		assertNotNull(info.getAttachments().get(1).getId());
		commit();
		
		loaded = newsService.getNewsItem(info);
		assertNotNull(loaded);
		assertNotNull(loaded.getId());
		validateNewsItemInfo(info, loaded);
		assertEquals(2, loaded.getAttachments().size());
		assertFalse(loaded.getAttachments().contains(file1));
		assertTrue(loaded.getAttachments().contains(file2));
		assertTrue(loaded.getAttachments().contains(file3));
		
		newsService.deleteNewsItem(info);
		flush();
		assertNull(newsService.getNewsItem(info));
	}
	
	public void testGetNewsItemsByPublisher() {
		DomainObject publisher = new DefaultDomainObject(TestUtility.unique());
		securityService.createObjectIdentity(publisher, null);
		
		NewsItemInfo info1 = createNewsItem();
		info1.setPublisherIdentifier(publisher.getId());
		newsService.saveNewsItem(info1);
		
		NewsItemInfo info2 = createNewsItem();
		securityService.createObjectIdentity(info2.getPublisherIdentifier(), null);
		newsService.saveNewsItem(info2);

		NewsItemInfo info3 = createNewsItem();
		info3.setPublisherIdentifier(publisher.getId());
		newsService.saveNewsItem(info3);
		
		flush();
		
		List<NewsItemInfo> news = newsService.getNewsItems(publisher);
		assertNotNull(news);
		assertEquals(2, news.size());
		assertTrue(news.contains(info1));
		assertFalse(news.contains(info2));
		assertTrue(news.contains(info3));
		
		assertTrue(2 == newsService.getPublishedNewsItemsCount(publisher));
	}

	private NewsItemInfo createNewsItem() {
		NewsItemInfo vo = new NewsItemInfo();
		vo.setCategory(NewsCategory.GLOBAL);
		vo.setPublisherIdentifier(TestUtility.unique());
		vo.setPublisherName("publishername of newsitem can be 250 character long");
		vo.setTitle("titel of newsitem can be 250 character long");
		vo.setText("text of the newsitem");
		vo.setAuthor("author of the newsitem");
		vo.setPublishDate(past);
		vo.setExpireDate(future);
		vo.setPublisherType(PublisherType.COURSE);
		return vo;
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
	
	private void validateNewsItemInfo(NewsItemInfo expected, NewsItemInfo newsItem) {
		assertEquals(expected.getCategory(), newsItem.getCategory());
		assertEquals(expected.getPublisherIdentifier(), newsItem.getPublisherIdentifier());
		assertEquals(expected.getPublisherName(),newsItem.getPublisherName());
		assertEquals(expected.getTitle(), newsItem.getTitle());
		assertEquals(expected.getText(), newsItem.getText());
		assertEquals(expected.getAuthor(), newsItem.getAuthor());
		assertEquals(expected.getPublishDate(), newsItem.getPublishDate());
		assertEquals(expected.getExpireDate(), newsItem.getExpireDate());
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public AclManager getAclManager() {
		return aclManager;
	}

	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}
}