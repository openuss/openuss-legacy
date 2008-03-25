// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.paperSubmission;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.acegisecurity.acl.AclManager;
import org.openuss.TestUtility;
import org.openuss.braincontest.BrainContestApplicationException;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.security.Group;
import org.openuss.security.GroupType;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * JUnit Test for Spring Hibernate PaperSubmissionService class.
 * @see org.openuss.paperSubmission.PaperSubmissionService
 */
public class PaperSubmissionServiceIntegrationTest extends PaperSubmissionServiceIntegrationTestBase {

	private SecurityService securityService;
	
	private AclManager aclManager;

	private DefaultDomainObject defaultDomainObject;

	private User user1;
	
	private User user2;
	
	private TestUtility testUtility;
	
	private CourseDao courseDao;
	
	private DocumentService documentService;
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		AcegiUtils.setAclManager(aclManager);
		defaultDomainObject = createDomainObject();
		user2 = testUtility.createUserSecureContext();
		securityService.createObjectIdentity(defaultDomainObject, null);
		securityService.setPermissions(user2, defaultDomainObject, LectureAclEntry.ASSIST);
		
		user1 = testUtility.createUserSecureContext();
		securityService.setPermissions(user1, defaultDomainObject, LectureAclEntry.PAPER_PARTICIPANT);
	}
	
	public void testCreateAndGetExam() throws PaperSubmissionServiceException {

		ExamInfo examInfo = createExamInfo("name");
		
		try {
			this.getPaperSubmissionService().createExam(examInfo);
		} catch (PaperSubmissionServiceException e) {
			fail(e.getMessage());
		}
		
		Long examId = examInfo.getId();
		assertNotNull(examId);
		
		ExamInfo extractedExamInfo = paperSubmissionService.getExam(examId);
		assertNotNull(extractedExamInfo);
		assertEquals(examInfo.getId(), extractedExamInfo.getId());
		assertEquals(examInfo.getName(), extractedExamInfo.getName());
		assertEquals(examInfo.getDeadline(), extractedExamInfo.getDeadline());
		assertEquals(examInfo.getDescription(), extractedExamInfo.getDescription());
		assertEquals(examInfo.getDomainId(), extractedExamInfo.getDomainId());
				
		//Update Exam
		Long oldId = examInfo.getId();
		Date oldDate = examInfo.getDeadline();
		examInfo.setDeadline(new Date());
		paperSubmissionService.updateExam(examInfo);
		extractedExamInfo = new ExamInfo();
		extractedExamInfo = paperSubmissionService.getExam(examId);
		assertNotNull(extractedExamInfo);
		assertEquals(extractedExamInfo.getId(), oldId);
		assertNotSame(extractedExamInfo.getDeadline(), oldDate);
		
		examInfo = new ExamInfo();
		
		try {
			paperSubmissionService.createExam(examInfo);
			fail("No PaperSubmissionServiceException thrown at non possible Exam create");
		} catch (PaperSubmissionServiceException e) {
		}
	}
	
	public void testCreateAndGetPaperSubmission() throws PaperSubmissionServiceException {
		ExamInfo examInfo = createExamInfo("name");
		try {
			this.getPaperSubmissionService().createExam(examInfo);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		Long examId = examInfo.getId();
		assertNotNull(examId);
		
		PaperSubmissionInfo paperSubmissionInfo = createPaperSubmissionInfo(examId);
		
		
		try {
			this.paperSubmissionService.createPaperSubmission(paperSubmissionInfo);
		} catch (PaperSubmissionServiceException e) {
			fail(e.getMessage());
		}
		Long paperSubmissionId = paperSubmissionInfo.getId();
		assertNotNull(paperSubmissionId);
		
		PaperSubmissionInfo extractedSubmissionInfo = this.paperSubmissionService.getPaperSubmission(paperSubmissionId);
		assertNotNull(extractedSubmissionInfo);
		assertEquals(paperSubmissionInfo.getId(), extractedSubmissionInfo.getId());
		//assertEquals(paperSubmissionInfo.getDeliverDate(), extractedSubmissionInfo.getDeliverDate());
		assertEquals(paperSubmissionInfo.getDisplayName(), extractedSubmissionInfo.getDisplayName());
		assertEquals(paperSubmissionInfo.getExamId(), extractedSubmissionInfo.getExamId());
		assertEquals(paperSubmissionInfo.getFirstName(), extractedSubmissionInfo.getFirstName());
		assertEquals(paperSubmissionInfo.getLastName(), extractedSubmissionInfo.getLastName());
		assertEquals(paperSubmissionInfo.getUserId(), extractedSubmissionInfo.getUserId());
		
		//Update PaperSubmission
		Long oldId = paperSubmissionInfo.getId();
		Date oldDate = paperSubmissionInfo.getDeliverDate();
		paperSubmissionInfo.setDeliverDate(new Date());
		paperSubmissionService.updatePaperSubmission(paperSubmissionInfo);
		extractedSubmissionInfo = new PaperSubmissionInfo();
		extractedSubmissionInfo = paperSubmissionService.getPaperSubmission(paperSubmissionId);
		assertNotNull(extractedSubmissionInfo);
		assertEquals(extractedSubmissionInfo.getId(), oldId);
		assertNotSame(extractedSubmissionInfo.getDeliverDate(), oldDate);
		
		paperSubmissionInfo = new PaperSubmissionInfo();
		
		try {
			paperSubmissionService.createPaperSubmission(paperSubmissionInfo);
			fail("No PaperSubmissionServiceException thrown at non possible PaperSubmission create");
		} catch (PaperSubmissionServiceException e) {
		}
		
	}
		
	public void testRemoveExam(){
		ExamInfo examInfo = createExamInfo("name");
		getPaperSubmissionService().createExam(examInfo);
		Long examId = examInfo.getId();
		assertNotNull(examId);
		
		PaperSubmissionInfo paperSubmissionInfo = createPaperSubmissionInfo(examId);
		paperSubmissionService.createPaperSubmission(paperSubmissionInfo);
		Long paperSubmissionId = paperSubmissionInfo.getId();
		assertNotNull(paperSubmissionId);
				
		ExamInfo examInfo2 = createExamInfo("name2");
		getPaperSubmissionService().createExam(examInfo2);
		Long examId2 = examInfo2.getId();
		assertNotNull(examId2);
		
		try {
			this.paperSubmissionService.removeExam(examId);
			this.paperSubmissionService.removeExam(examId2);
		} catch (PaperSubmissionServiceException e) {
			fail(e.getMessage());
		}
		
//		PaperSubmissionInfo extractedSubmissionInfo = paperSubmissionService.getPaperSubmission(paperSubmissionId);
//		assertNull(extractedSubmissionInfo);
		
		ExamInfo extractedExamInfo = paperSubmissionService.getExam(examId);
		assertNull(extractedExamInfo);
		extractedExamInfo = paperSubmissionService.getExam(examId2);
		assertNull(extractedExamInfo);		
	}
	
	public void testFindExams(){
		ExamInfo activeExamInfo = createExamInfo("test_active");
		activeExamInfo.setDeadline(new Date(activeExamInfo.getDeadline().getTime()+100000000));
		paperSubmissionService.createExam(activeExamInfo);
		
		ExamInfo inactiveExamInfo = createExamInfo("test_inactive");
		inactiveExamInfo.setDeadline(new Date(0));
		paperSubmissionService.createExam(inactiveExamInfo);
		
		int absoluteExamCount = 2;
		int activeExamCount	  = 1;
		int inactiveExamCount = 1;
		
		List<ExamInfo> allExamList = paperSubmissionService.findExamsByDomainId(defaultDomainObject.getId());
		assertNotNull(allExamList);
		assertEquals(absoluteExamCount, allExamList.size());
		
		List<ExamInfo> activeExamList = paperSubmissionService.findActiveExamsByDomainId(defaultDomainObject.getId());
		assertNotNull(activeExamList);
		assertEquals(activeExamCount, activeExamList.size());
		for(ExamInfo exam : activeExamList) {
			assert(exam.getDeadline().after(new Date()));
		}
		
		List<ExamInfo> inactiveExamList = paperSubmissionService.findInactiveExamsByDomainId(defaultDomainObject.getId());
		assertNotNull(inactiveExamList);
		assertEquals(inactiveExamCount, inactiveExamList.size());
		for(ExamInfo exam : inactiveExamList) {
			assert(exam.getDeadline().before(new Date()));
		}
	}
	
	public void testFindPaperSubmissions(){
		Course course = createCourseDomain();
		
		ExamInfo examInfo = createExamInfo("test_exam");
		examInfo.setDomainId(course.getId());
		Long futureDate = new Date().getTime()+1000000000;
		examInfo.setDeadline(new Date(futureDate));
		getPaperSubmissionService().createExam(examInfo);
		Long examId = examInfo.getId();
		assertNotNull(examId);
		
		FileInfo fileOne = createFileInfo("one");
		FileInfo fileTwo = createFileInfo("two");
		FileInfo fileThree = createFileInfo("three");
		
		PaperSubmissionInfo paperSubmissionInfo = createPaperSubmissionInfo(examId);
		paperSubmissionService.createPaperSubmission(paperSubmissionInfo);
		Long paperSubmissionId = paperSubmissionInfo.getId();
		assertNotNull(paperSubmissionId);
		
		FolderInfo folder = getDocumentService().getFolder(paperSubmissionInfo);
		try {
			getDocumentService().createFileEntry(fileOne, folder);
			getDocumentService().createFileEntry(fileTwo, folder);
			getDocumentService().createFileEntry(fileThree, folder);
		} catch (DocumentApplicationException e) {
			fail(e.getMessage());
		}
		
		List<PaperSubmissionInfo> submissionListByExam = paperSubmissionService.findPaperSubmissionsByExam(examId);
		assertNotNull(submissionListByExam);
		assertEquals(1,submissionListByExam.size());
		assertEquals(submissionListByExam.get(0).getExamId(), examId);
		
		List<PaperSubmissionInfo> submissionListByExamAndUser = paperSubmissionService.findPaperSubmissionsByExamAndUser(examId, user1.getId());
		assertNotNull(submissionListByExamAndUser);
		assertEquals(1,submissionListByExamAndUser.size());
		assertEquals(submissionListByExamAndUser.get(0).getExamId(), examId);
		assertEquals(submissionListByExamAndUser.get(0).getUserId(), user1.getId());
		
		List<PaperSubmissionInfo> intimeSubmissionListByExam = paperSubmissionService.findInTimePaperSubmissionsByExam(examId);
		assertNotNull(intimeSubmissionListByExam);
		assertEquals(1,intimeSubmissionListByExam.size());
		assertEquals(intimeSubmissionListByExam.get(0).getExamId(), examId);
		assertEquals(SubmissionStatus.IN_TIME, intimeSubmissionListByExam.get(0).getSubmissionStatus());
		
		List<FileInfo> fileInfoList = paperSubmissionService.getPaperSubmissionFiles(submissionListByExam);
		assertNotNull(fileInfoList);
		assertEquals(3,fileInfoList.size());
	}
	
	
	public void testMembersAsPaperSubmissions() {
		Course course = createCourseDomain();
		
		ExamInfo examInfo = createExamInfo("test_exam");
		examInfo.setDomainId(course.getId());
		Long futureDate = new Date().getTime()+1000000000;
		examInfo.setDeadline(new Date(futureDate));
		getPaperSubmissionService().createExam(examInfo);
		Long examId = examInfo.getId();
		assertNotNull(examId);
		
		PaperSubmissionInfo paperSubmissionInfo = createPaperSubmissionInfo(examId);
		paperSubmissionService.createPaperSubmission(paperSubmissionInfo);
		Long paperSubmissionId = paperSubmissionInfo.getId();
		assertNotNull(paperSubmissionId);
		
		Group group = getSecurityService().getGroupByName("GROUP_COURSE_" + course.getId() + "_PARTICIPANTS");
		group.addMember(user1);
		group.addMember(user2);
		
		List<PaperSubmissionInfo> membersAsSubmissions = paperSubmissionService.getMembersAsPaperSubmissionsByExam(examId);
		assertNotNull(membersAsSubmissions);
		assertEquals(2, membersAsSubmissions.size());
		assertNotSame(membersAsSubmissions.get(0).getSubmissionStatus(), SubmissionStatus.NOT_SUBMITTED);
		assertEquals(membersAsSubmissions.get(1).getSubmissionStatus(), SubmissionStatus.NOT_SUBMITTED);
		
	}
	
	public void testAttachmentUpdate() throws Exception {
		Course course = createCourseDomain();
		
		ExamInfo examInfo = createExamInfo("test_exam");
		examInfo.setDomainId(course.getId());
		//paperSubmissionService.createExam(examInfo);
		
		FileInfo fileOne = createFileInfo("one");
		FileInfo fileTwo = createFileInfo("two");
		FileInfo fileThree = createFileInfo("three");
		
		List<FileInfo> attachments = new ArrayList<FileInfo>();
		attachments.add(fileOne);
		attachments.add(fileTwo);
		
		examInfo.setAttachments(attachments);
		
		paperSubmissionService.createExam(examInfo);
		
		assertNotNull(fileOne.getId());
		assertNotNull(fileTwo.getId());
		
		commit();
		
		ExamInfo extractedExam = paperSubmissionService.getExam(examInfo.getId());
		assertNotNull(extractedExam.getAttachments());
		assertEquals(2, extractedExam.getAttachments().size());
		assertTrue(extractedExam.getAttachments().contains(fileOne));
		assertTrue(extractedExam.getAttachments().contains(fileTwo));
		
		examInfo.getAttachments().remove(fileOne);
		examInfo.getAttachments().add(fileThree);
		
		paperSubmissionService.updateExam(examInfo);
		commit();
		
		ExamInfo extractedExam2 = paperSubmissionService.getExam(examInfo.getId());
		assertNotNull(extractedExam2.getAttachments());
		assertEquals(2, extractedExam2.getAttachments().size());
		assertTrue(extractedExam2.getAttachments().contains(fileTwo));
		assertTrue(extractedExam2.getAttachments().contains(fileThree));
	}
	
	

	public void testAttachments() throws BrainContestApplicationException{
		Course course = createCourseDomain();
		
		List<ExamInfo> entries = paperSubmissionService.findExamsByDomainId(defaultDomainObject.getId());
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
		
		ExamInfo examInfo = createExamInfo("test_exam");
		examInfo.setDomainId(course.getId());
		assertNull(examInfo.getId());
		paperSubmissionService.createExam(examInfo);
		commit();

		assertNotNull(examInfo.getId());
		assertEquals("description", examInfo.getDescription());
		assertEquals(examInfo.getDomainId(), course.getId());
		assertEquals("test_exam", examInfo.getName());

		entries = paperSubmissionService.findExamsByDomainId(course.getId());
		assertEquals(entries.size(), 1);
		
		ExamInfo addedExam = entries.get(0);
		assertEquals(examInfo.getId(), addedExam.getId());
		assertEquals(examInfo.getName(), addedExam.getName());
		assertEquals(examInfo.getDescription(), addedExam.getDescription());
		assertEquals(examInfo.getDeadline(), addedExam.getDeadline());
		assertEquals(examInfo.getDomainId(), addedExam.getDomainId());
		
		FileInfo fileInfo = createFileInfo("one");
		if(addedExam.getAttachments() == null){
			addedExam.setAttachments(new ArrayList<FileInfo>());
		}
		addedExam.getAttachments().add(fileInfo);
		
		paperSubmissionService.updateExam(addedExam);
		
		List<FileInfo> readFiles = addedExam.getAttachments();
		assertEquals(1,readFiles.size());
		FileInfo read = readFiles.get(0);
		assertEquals(fileInfo.getId(), read.getId());
		
		addedExam.getAttachments().remove(read);
		paperSubmissionService.updateExam(addedExam);
		
		commit();
		
		readFiles = addedExam.getAttachments();
		assertEquals(0, readFiles.size());
		
	}
	
	
	private DefaultDomainObject createDomainObject() {
		DefaultDomainObject defaultDomainObject = new DefaultDomainObject(TestUtility.unique());
		return defaultDomainObject;
	}
	
	private Course createCourseDomain() {
		Course course = testUtility.createUniqueCourseInDB();
		// Create default Group for Course
		Group participantsGroup = getSecurityService().createGroup("COURSE_" + course.getId() + "_PARTICIPANTS",
				"autogroup_participant_label", null, GroupType.PARTICIPANT);
		Set<Group> groups = course.getGroups();
		if (groups == null) {
			groups = new HashSet<Group>();
		}
		groups.add(participantsGroup);
		course.setGroups(groups);
		getCourseDao().update(course);

		return course;
	}
	
	private ExamInfo createExamInfo(String name) {
		ExamInfo examInfo = new ExamInfo();
		examInfo.setDeadline(new Date());
		examInfo.setDescription("description");
		examInfo.setDomainId(defaultDomainObject.getId());
		examInfo.setName(name);
		examInfo.setAttachments(null);
		
		return examInfo;
	}
	
	private PaperSubmissionInfo createPaperSubmissionInfo(Long examId) {
		PaperSubmissionInfo paperSubmissionInfo = new PaperSubmissionInfo();
		paperSubmissionInfo.setDeliverDate(new Date());
		paperSubmissionInfo.setDisplayName(user1.getDisplayName());
		paperSubmissionInfo.setFirstName(user1.getFirstName());
		paperSubmissionInfo.setLastName(user1.getLastName());
		paperSubmissionInfo.setUserId(user1.getId());
		paperSubmissionInfo.setSubmissionStatus(SubmissionStatus.NOT_IN_TIME);
		paperSubmissionInfo.setExamId(examId);
		
		return paperSubmissionInfo;
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

	public TestUtility getTestUtility() {
		return testUtility;
	}


	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}
}