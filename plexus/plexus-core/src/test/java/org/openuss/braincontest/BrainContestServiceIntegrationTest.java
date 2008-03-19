// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.acegisecurity.acl.AclManager;
import org.openuss.TestUtility;
import org.openuss.documents.FileInfo;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * JUnit Test for Spring Hibernate BrainContestService class.
 * @see org.openuss.braincontest.BrainContestService
 */
@SuppressWarnings("unchecked")
public class BrainContestServiceIntegrationTest extends BrainContestServiceIntegrationTestBase {

	private SecurityService securityService;
	
	private AclManager aclManager;

	private DefaultDomainObject defaultDomainObject;

	private User user;
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		AcegiUtils.setAclManager(aclManager);
		defaultDomainObject = createDomainObject();
		user = testUtility.createUserSecureContext();
		securityService.createObjectIdentity(defaultDomainObject, null);
		securityService.setPermissions(user, defaultDomainObject, LectureAclEntry.ASSIST);
	}
	
	private BrainContestInfo createContest(Long domainId, boolean released){
		BrainContestInfo brainContestInfo = new BrainContestInfo();
		brainContestInfo.setDescription("testDescription");
		brainContestInfo.setTitle("testTitle");
		brainContestInfo.setSolution("testSolution");
		if (released){
			brainContestInfo.setReleaseDate(new Date(System.currentTimeMillis()));
		} if (!released){
			brainContestInfo.setReleaseDate(new Date(System.currentTimeMillis()+564546545));			
		}
		brainContestInfo.setDomainIdentifier(domainId);
		return brainContestInfo;
	
	}
	
	public void testFiltered(){
		
		List<BrainContestInfo> entries = brainContestService.getContests(defaultDomainObject);
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
		
		BrainContestInfo brainContest = createBrainContestInfo(defaultDomainObject.getId());
		assertNull(brainContest.getId());
		
		try {
			brainContestService.createContest(createContest(defaultDomainObject.getId(), true));			
			brainContestService.createContest(createContest(defaultDomainObject.getId(), false));			
		} catch (BrainContestApplicationException e) {
			fail(e.getMessage());
		}
		entries = brainContestService.getContests(defaultDomainObject);
		assertNotNull(entries);
		assertEquals(2, entries.size());
		//remove rights from user
	
		securityService.setPermissions(user, defaultDomainObject, LectureAclEntry.READ);
		entries = brainContestService.getContests(defaultDomainObject);
		assertNotNull(entries);
		assertEquals(1, entries.size());
		
		
	}
	
	public void testCreateAndGetContest() throws BrainContestApplicationException{		
		List<BrainContestInfo> entries = brainContestService.getContests(defaultDomainObject);
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
		
		BrainContestInfo brainContest = createBrainContestInfo(defaultDomainObject.getId());
		assertNull(brainContest.getId());
		
		try {
			brainContestService.createContest(brainContest);			
		} catch (BrainContestApplicationException e) {
			fail(e.getMessage());
		}
		assertNotNull(brainContest.getId());
		assertEquals("testDescription", brainContest.getDescription());
		assertEquals("testTitle", brainContest.getTitle());
		assertEquals(brainContest.getTries().intValue(), 0);
		assertEquals(brainContest.getDomainIdentifier(), defaultDomainObject.getId());
		
		entries = brainContestService.getContests(defaultDomainObject);
		assertEquals(entries.size(), 1);
		BrainContestInfo addedContest = entries.get(0);
		assertEquals(brainContest.getId(), addedContest.getId());
		assertEquals(brainContest.getTitle(), addedContest.getTitle());
		assertEquals(brainContest.getDescription(), addedContest.getDescription());
		assertEquals(brainContest.getReleaseDate(), addedContest.getReleaseDate());
		assertEquals(brainContest.getSolution(), addedContest.getSolution());
		assertEquals(brainContest.getTries(), addedContest.getTries());
		assertEquals(brainContest.getDomainIdentifier(), addedContest.getDomainIdentifier());
		
		addedContest = null;
		addedContest = brainContestService.getContest(brainContest);
		assertEquals(brainContest.getId(), addedContest.getId());
		assertEquals(brainContest.getTitle(), addedContest.getTitle());
		assertEquals(brainContest.getDescription(), addedContest.getDescription());
		assertEquals(brainContest.getReleaseDate(), addedContest.getReleaseDate());
		assertEquals(brainContest.getSolution(), addedContest.getSolution());
		assertEquals(brainContest.getTries(), addedContest.getTries());
		assertEquals(brainContest.getDomainIdentifier(), addedContest.getDomainIdentifier());
		brainContest = new BrainContestInfo();
		try {
			brainContestService.createContest(brainContest);
			fail("No BrainContestApplicationException thrown at non possible BrainContest create");
		} catch (BrainContestServiceException e) {
		}
		
		entries = brainContestService.getContests(defaultDomainObject);
		assertNotNull(entries);
		assertEquals(1, entries.size());
	}
	
	public void testSaveContest() throws BrainContestApplicationException{
		List<BrainContestInfo> entries;
		BrainContestInfo addedContest = createAndVerifyBrainContest();
		
		DefaultDomainObject newDomain = createDomainObject();
		Date newDate = new Date(System.currentTimeMillis());		
		addedContest.setDomainIdentifier(newDomain.getId());
		addedContest.setReleaseDate(newDate);
		addedContest.setDescription("newDescription");
		addedContest.setTitle("newTitle");
		addedContest.setTries(1);
		addedContest.setSolution("newSolution");

		brainContestService.saveContest(addedContest);
		
		entries = brainContestService.getContests(defaultDomainObject);
		assertEquals(0, entries.size());
		entries = brainContestService.getContests(newDomain);
		assertEquals(1, entries.size());
		BrainContestInfo newContest = entries.get(0);
		assertEquals(newContest.getId(), addedContest.getId());
		assertEquals(newContest.getTitle(), addedContest.getTitle());
		assertEquals(newContest.getDescription(), addedContest.getDescription());
		assertEquals(newContest.getReleaseDate(), addedContest.getReleaseDate());
		assertEquals(newContest.getSolution(), addedContest.getSolution());
		assertEquals(newContest.getTries(), addedContest.getTries());
		assertEquals(newContest.getDomainIdentifier(), addedContest.getDomainIdentifier());
		
		addedContest.setDomainIdentifier(null);
		try {
			brainContestService.saveContest(addedContest);
			fail("BrainContestApplicationException should have been thrown while wrong save");
		} catch (BrainContestServiceException e) {
		}			
	}

	private BrainContestInfo createAndVerifyBrainContest() throws BrainContestApplicationException {
		List<BrainContestInfo> entries = brainContestService.getContests(defaultDomainObject);
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
		
		BrainContestInfo brainContest = createBrainContestInfo(defaultDomainObject.getId());
		assertNull(brainContest.getId());
		
		brainContestService.createContest(brainContest);
		flush();
		
		assertNotNull(brainContest.getId());
		assertEquals("testDescription", brainContest.getDescription());
		assertEquals("testTitle", brainContest.getTitle());
		assertEquals(brainContest.getTries().intValue(), 0);
		assertEquals(brainContest.getDomainIdentifier(), defaultDomainObject.getId());

		entries = brainContestService.getContests(defaultDomainObject);
		assertEquals(1, entries.size());
		BrainContestInfo addedContest = entries.get(0);
		assertEquals(brainContest.getId(), addedContest.getId());
		assertEquals(brainContest.getTitle(), addedContest.getTitle());
		assertEquals(brainContest.getDescription(), addedContest.getDescription());
		assertEquals(brainContest.getReleaseDate(), addedContest.getReleaseDate());
		assertEquals(brainContest.getSolution(), addedContest.getSolution());
		assertEquals(brainContest.getTries(), addedContest.getTries());
		assertEquals(brainContest.getDomainIdentifier(), addedContest.getDomainIdentifier());
		return addedContest;
	}

	public void testAttachmentUpdate() throws BrainContestApplicationException {
		BrainContestInfo brainContest = createBrainContestInfo(defaultDomainObject.getId());
		
		FileInfo fileOne = createFileInfo("one");
		FileInfo fileTwo = createFileInfo("two");
		FileInfo fileThree = createFileInfo("three");
		
		List<FileInfo> attachments = new ArrayList<FileInfo>();
		attachments.add(fileOne);
		attachments.add(fileTwo);
		
		brainContest.setAttachments(attachments);
		
		brainContestService.createContest(brainContest);
		
		assertNotNull(fileOne.getId());
		assertNotNull(fileTwo.getId());
		
		commit();
		
		BrainContestInfo loadedContest = brainContestService.getContest(brainContest);
		assertNotNull(loadedContest.getAttachments());
		assertEquals(2, loadedContest.getAttachments().size());
		assertTrue(loadedContest.getAttachments().contains(fileOne));
		assertTrue(loadedContest.getAttachments().contains(fileTwo));
		
		brainContest.getAttachments().remove(fileOne);
		brainContest.getAttachments().add(fileThree);
		
		brainContestService.saveContest(brainContest);
		commit();
		
		BrainContestInfo loadedContest2 = brainContestService.getContest(brainContest);
		assertNotNull(loadedContest2.getAttachments());
		assertEquals(2, loadedContest2.getAttachments().size());
		assertTrue(loadedContest2.getAttachments().contains(fileTwo));
		assertTrue(loadedContest2.getAttachments().contains(fileThree));
	}
	
	public void testAttachments() throws BrainContestApplicationException{
		List<BrainContestInfo> entries = brainContestService.getContests(defaultDomainObject);
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
		
		BrainContestInfo brainContest = createBrainContestInfo(defaultDomainObject.getId());
		assertNull(brainContest.getId());
		
		brainContestService.createContest(brainContest);
		commit();

		assertNotNull(brainContest.getId());
		assertEquals("testDescription", brainContest.getDescription());
		assertEquals("testTitle", brainContest.getTitle());
		assertEquals(brainContest.getTries().intValue(), 0);
		assertEquals(brainContest.getDomainIdentifier(), defaultDomainObject.getId());

		entries = brainContestService.getContests(defaultDomainObject);
		assertEquals(entries.size(), 1);
		BrainContestInfo addedContest = entries.get(0);
		assertEquals(brainContest.getId(), addedContest.getId());
		assertEquals(brainContest.getTitle(), addedContest.getTitle());
		assertEquals(brainContest.getDescription(), addedContest.getDescription());
		assertEquals(brainContest.getReleaseDate(), addedContest.getReleaseDate());
		assertEquals(brainContest.getSolution(), addedContest.getSolution());
		assertEquals(brainContest.getTries(), addedContest.getTries());
		assertEquals(brainContest.getDomainIdentifier(), addedContest.getDomainIdentifier());
		
		FileInfo fileInfo = createFileInfo("eins");
		brainContestService.addAttachment(addedContest, fileInfo);
		
		List<FileInfo> readFiles = brainContestService.getAttachments(addedContest);
		assertEquals(1,readFiles.size());
		FileInfo read = readFiles.get(0);
		assertEquals(fileInfo.getId(), read.getId());
		
		brainContestService.removeAttachment(addedContest, read);
		commit();
		entries = brainContestService.getAttachments(addedContest);
		assertEquals(0, entries.size());

		try {
			brainContestService.removeAttachment(null, null);
			fail("BrainContestApplicationException should have thrown");			
		} catch (BrainContestServiceException e) {
			// expected
		}
		
	}
	
	public void testAnswers() throws BrainContestApplicationException{
		BrainContestInfo addedContest = createAndVerifyBrainContest();
		
		User user = testUtility.createUniqueUserInDB();
		UserInfo userInfo = getSecurityService().getUser(user.getId());
		AnswerInfo answer;

		//check case right answer + no adding to top list
		assertTrue("Right answer handled as wrong",brainContestService.answer("testSolution", userInfo, addedContest, false));

		assertEquals(1, addedContest.getTries().intValue());
		brainContestService.getAnswers(addedContest);		
		//check case wrong answer + no adding to top list
		assertFalse("Wrong answer handled as right",brainContestService.answer("xxx", userInfo, addedContest, false));

		brainContestService.getAnswers(addedContest);		
		assertEquals(2, addedContest.getTries().intValue());
		//check case wrong answer + adding to top list		

		assertFalse("Wrong answer handled as right",brainContestService.answer("xxx", userInfo, addedContest, true));
		assertEquals(3, addedContest.getTries().intValue());
		//check case right answer + adding to top list		

		assertTrue("Right answer handled as wrong",brainContestService.answer("testSolution", userInfo, addedContest, true));
		flush();

		Collection<AnswerInfo> answers = brainContestService.getAnswers(addedContest);		
		assertNotNull(answers);
		assertEquals(1, answers.size());		
		answer = answers.iterator().next();
		assertEquals(user.getImageId(), answer.getImageId());	
		assertEquals(4, addedContest.getTries().intValue());
	}
	
	public void testRemove() throws BrainContestApplicationException{
		try{
			getBrainContestService().removeContest(null);
			fail("BrainContestApplicationException should have been thrown");
		}catch (BrainContestServiceException e){
			// succeed
		}
		List<BrainContestInfo> entries = brainContestService.getContests(defaultDomainObject);
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
		
		BrainContestInfo brainContest = createBrainContestInfo(defaultDomainObject.getId());
		assertNull(brainContest.getId());
		
		brainContestService.createContest(brainContest);
		
		assertNotNull(brainContest.getId());
		assertEquals("testDescription", brainContest.getDescription());
		assertEquals("testTitle", brainContest.getTitle());
		assertEquals(brainContest.getTries().intValue(), 0);
		assertEquals(brainContest.getDomainIdentifier(), defaultDomainObject.getId());

		entries = brainContestService.getContests(defaultDomainObject);
		assertEquals(entries.size(), 1);
		BrainContestInfo addedContest = entries.get(0);
		assertEquals(brainContest.getId(), addedContest.getId());
		assertEquals(brainContest.getTitle(), addedContest.getTitle());
		assertEquals(brainContest.getDescription(), addedContest.getDescription());
		assertEquals(brainContest.getReleaseDate(), addedContest.getReleaseDate());
		assertEquals(brainContest.getSolution(), addedContest.getSolution());
		assertEquals(brainContest.getTries(), addedContest.getTries());
		assertEquals(brainContest.getDomainIdentifier(), addedContest.getDomainIdentifier());	
		
		brainContestService.removeContest(addedContest);

		entries = brainContestService.getContests(defaultDomainObject);
		assertEquals(0,entries.size());
		
	}
	
	
	private FileInfo createFileInfo(String name) {
		FileInfo info = new FileInfo();
		byte[] data = "this is the content of the file".getBytes();
		info.setName(name);
		info.setFileName(name+".txt");
		info.setDescription("description");
		info.setContentType("plain/text");
		info.setFileSize(data.length);
		info.setCreated(new Date());
		info.setModified(new Date());
		info.setInputStream(new ByteArrayInputStream(data));
		return info;
	}
	private BrainContestInfo createBrainContestInfo(Long domainId){
		BrainContestInfo brainContestInfo = new BrainContestInfo();
		brainContestInfo.setDescription("testDescription");
		brainContestInfo.setTitle("testTitle");
		brainContestInfo.setSolution("testSolution");
		brainContestInfo.setReleaseDate(new Date(System.currentTimeMillis()));
		brainContestInfo.setDomainIdentifier(domainId);
		return brainContestInfo;
	}
		
	private DefaultDomainObject createDomainObject() {
		DefaultDomainObject defaultDomainObject = new DefaultDomainObject(TestUtility.unique());
		return defaultDomainObject;
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

	public AclManager getAclManager() {
		return aclManager;
	}

	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}
}