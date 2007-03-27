// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.documents.FileInfo;
import org.openuss.documents.DocumentServiceIntegrationTest.DomainObject;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate BrainContestService class.
 * @see org.openuss.braincontest.BrainContestService
 */
public class BrainContestServiceIntegrationTest extends BrainContestServiceIntegrationTestBase {

	private TestUtility testUtility;
	

	/**
	 * 
	 */
	public void testCreateAndGetContest(){		
		DomainObject domainObject = createDomainObject();
		List<BrainContestInfo> entries = brainContestService.getContests(domainObject);
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
		
		BrainContestInfo brainContest = createBrainContestInfo(domainObject.getId());
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
		assertEquals(brainContest.getDomainIdentifier(), domainObject.getId());
		
		entries = brainContestService.getContests(domainObject);
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
		} catch (BrainContestApplicationException e) {
		}
		
		entries = brainContestService.getContests(domainObject);
		assertNotNull(entries);
		assertEquals(1, entries.size());
	}
	
	public void testSaveContest(){
		DomainObject domainObject = createDomainObject();
		List<BrainContestInfo> entries = brainContestService.getContests(domainObject);
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
		
		BrainContestInfo brainContest = createBrainContestInfo(domainObject.getId());
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
		assertEquals(brainContest.getDomainIdentifier(), domainObject.getId());

		entries = brainContestService.getContests(domainObject);
		assertEquals(entries.size(), 1);
		BrainContestInfo addedContest = entries.get(0);
		assertEquals(brainContest.getId(), addedContest.getId());
		assertEquals(brainContest.getTitle(), addedContest.getTitle());
		assertEquals(brainContest.getDescription(), addedContest.getDescription());
		assertEquals(brainContest.getReleaseDate(), addedContest.getReleaseDate());
		assertEquals(brainContest.getSolution(), addedContest.getSolution());
		assertEquals(brainContest.getTries(), addedContest.getTries());
		assertEquals(brainContest.getDomainIdentifier(), addedContest.getDomainIdentifier());
		
		DomainObject newDomain = createDomainObject();
		Date newDate = new Date(System.currentTimeMillis());		
		addedContest.setDomainIdentifier(newDomain.getId());
		addedContest.setReleaseDate(newDate);
		addedContest.setDescription("newDescription");
		addedContest.setTitle("newTitle");
		addedContest.setTries(1);
		addedContest.setSolution("newSolution");
		try {
			brainContestService.saveContest(addedContest);
		} catch (BrainContestApplicationException e) {
			fail("wrong BrainContestApplicationException was thrown while saving BrainContest");
		}
		
		entries = brainContestService.getContests(domainObject);
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
		} catch (BrainContestApplicationException e) {
		}			
	}
	
	public void testAttachments(){
		testUtility.createSecureContext();
		DomainObject domainObject = createDomainObject();
		List<BrainContestInfo> entries = brainContestService.getContests(domainObject);
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
		
		BrainContestInfo brainContest = createBrainContestInfo(domainObject.getId());
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
		assertEquals(brainContest.getDomainIdentifier(), domainObject.getId());

		entries = brainContestService.getContests(domainObject);
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
		try {
			brainContestService.addAttachment(addedContest, fileInfo);
		} catch (BrainContestApplicationException e) {
			fail("Illegal BrainContestApplicationException thrown");			
		}
		List<FileInfo> readFiles = brainContestService.getAttachments(addedContest);
		assertEquals(1,readFiles.size());
		FileInfo read = readFiles.get(0);
		assertEquals(fileInfo.getId(), read.getId());
		try {
			brainContestService.addAttachment(null, null);
			fail("BrainContestApplicationException should have been thrown");			
		} catch (BrainContestApplicationException e) {
		}
		
		try {
			brainContestService.removeAttachment(addedContest, read);
		} catch (BrainContestApplicationException e) {
			fail("Illegal BrainContestApplicationException thrown");			
		}
		entries = brainContestService.getAttachments(addedContest);
		assertEquals(0, entries.size());

		try {
			brainContestService.removeAttachment(addedContest, read);
			fail("BrainContestApplicationException should have thrown");			
		} catch (BrainContestApplicationException e) {
		}

		try {
			brainContestService.removeAttachment(null, null);
			fail("BrainContestApplicationException should have thrown");			
		} catch (BrainContestApplicationException e) {
		}
		
	}
	
	public void testAnswers(){
		DomainObject domainObject = createDomainObject();
		List<BrainContestInfo> entries = brainContestService.getContests(domainObject);
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
		
		BrainContestInfo brainContest = createBrainContestInfo(domainObject.getId());
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
		assertEquals(brainContest.getDomainIdentifier(), domainObject.getId());

		entries = brainContestService.getContests(domainObject);
		assertEquals(entries.size(), 1);
		BrainContestInfo addedContest = entries.get(0);
		assertEquals(brainContest.getId(), addedContest.getId());
		assertEquals(brainContest.getTitle(), addedContest.getTitle());
		assertEquals(brainContest.getDescription(), addedContest.getDescription());
		assertEquals(brainContest.getReleaseDate(), addedContest.getReleaseDate());
		assertEquals(brainContest.getSolution(), addedContest.getSolution());
		assertEquals(brainContest.getTries(), addedContest.getTries());
		assertEquals(brainContest.getDomainIdentifier(), addedContest.getDomainIdentifier());
		
		User user = testUtility.createUserInDB();
		boolean test = false;
		AnswerInfo answer;
		//check case right answer + no adding to top list
		try {
			test = brainContestService.answer("testSolution", user, addedContest, false);
		} catch (BrainContestApplicationException e) {
			fail("illegal BrainContestApplicationException thrown");
		}
		if (!test) fail("Right answer handled as wrong");		
		assertEquals(1, addedContest.getTries().intValue());
		Collection<AnswerInfo> answers = brainContestService.getAnswers(addedContest);		
		assertNull(answers);
		//check case right answer + no adding to top list
		try {
			test = brainContestService.answer("xxx", user, addedContest, false);
		} catch (BrainContestApplicationException e) {
			fail("illegal BrainContestApplicationException thrown");
		}
		if (test) fail("'Wrong answer handled as right");		
		answers = brainContestService.getAnswers(addedContest);		
		assertNull(answers);		
		assertEquals(2, addedContest.getTries().intValue());
		//check case right answer + no adding to top list		
		try {
			test = brainContestService.answer("xxx", user, addedContest, true);
		} catch (BrainContestApplicationException e) {
			fail("illegal BrainContestApplicationException thrown");
		}
		if (test) fail("Wrong answer handled as right");		
		answers = brainContestService.getAnswers(addedContest);		
		assertNull(answers);
		assertEquals(3, addedContest.getTries().intValue());
		//check case right answer + adding to top list		
		try {
			test = brainContestService.answer("testSolution", user, addedContest, true);
			commit();
		} catch (BrainContestApplicationException e) {
			fail("illegal BrainContestApplicationException thrown");
		}
		if (!test) fail("Right answer handled as wrong");		
		answers = brainContestService.getAnswers(addedContest);		
		assertNotNull(answers);
		assertEquals(1, answers.size());		
		answer = answers.iterator().next();
		assertEquals(user.getImageId(), answer.getImageId());	
		assertEquals(4, addedContest.getTries().intValue());
	}
	
	public void testRemove(){
		try{
			getBrainContestService().removeContest(null);
			fail("BrainContestApplicationException should have been thrown");
		}catch (BrainContestApplicationException e){			
		}
		DomainObject domainObject = createDomainObject();
		List<BrainContestInfo> entries = brainContestService.getContests(domainObject);
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
		
		BrainContestInfo brainContest = createBrainContestInfo(domainObject.getId());
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
		assertEquals(brainContest.getDomainIdentifier(), domainObject.getId());

		entries = brainContestService.getContests(domainObject);
		assertEquals(entries.size(), 1);
		BrainContestInfo addedContest = entries.get(0);
		assertEquals(brainContest.getId(), addedContest.getId());
		assertEquals(brainContest.getTitle(), addedContest.getTitle());
		assertEquals(brainContest.getDescription(), addedContest.getDescription());
		assertEquals(brainContest.getReleaseDate(), addedContest.getReleaseDate());
		assertEquals(brainContest.getSolution(), addedContest.getSolution());
		assertEquals(brainContest.getTries(), addedContest.getTries());
		assertEquals(brainContest.getDomainIdentifier(), addedContest.getDomainIdentifier());	
		
		try {
			brainContestService.removeContest(addedContest);
		} catch (BrainContestApplicationException e) {
			fail("Illegal BrainContestApplicationException was thrown");
		}
		entries = brainContestService.getContests(domainObject);
		assertEquals(0,entries.size());
		
	}
	
	
	private FileInfo createFileInfo(String name) {
		FileInfo info = new FileInfo();
		byte[] data = "this is the content of the file".getBytes();
		info.setName(name);
		info.setDescription("description");
		info.setContentType("plain/text");
		info.setSize(data.length);
		info.setCreated(new Date());
		info.setInputStream(new ByteArrayInputStream(data));
		return info;
	}
	private BrainContestInfo createBrainContestInfo(Long id){
		BrainContestInfo brainContestInfo = new BrainContestInfo();
		brainContestInfo.setDescription("testDescription");
		brainContestInfo.setTitle("testTitle");
		brainContestInfo.setSolution("testSolution");
		brainContestInfo.setReleaseDate(new Date(System.currentTimeMillis()));
		brainContestInfo.setDomainIdentifier(id);
		return brainContestInfo;
	}
		
	private DomainObject createDomainObject() {
		DomainObject domainObject = new DomainObject(testUtility.unique());
		return domainObject;
	}
	
	private void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}


	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}