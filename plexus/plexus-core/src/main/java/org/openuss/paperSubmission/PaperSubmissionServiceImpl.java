// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.paperSubmission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.Authority;
import org.openuss.security.Group;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

/**
 * @author  Projektseminar WS 07/08, Team Collaboration
 * @see org.openuss.paperSubmission.PaperSubmissionService
 */
public class PaperSubmissionServiceImpl
    extends org.openuss.paperSubmission.PaperSubmissionServiceBase
{

	private static final Logger logger = Logger.getLogger(PaperSubmissionServiceImpl.class);

	@Override
	protected void handleCreateExam(ExamInfo examInfo) throws Exception {
		Validate.notNull(examInfo, "examInfo cannot be null.");
    	Validate.notNull(examInfo.getDomainId(), "domainId cannot be null.");
    	
    	//Transform VO to Entity
    	final Exam examEntity = getExamDao().examInfoToEntity(examInfo);
    	Validate.notNull(examEntity, "examInfoEntity cannot be null.");
    	
    	getExamDao().create(examEntity);
    	Validate.notNull(examEntity, "examEntity cannot be null");
    	
		// Update input parameter for aspects to get the right domain objects.    	
    	examInfo.setId(examEntity.getId());
    	getSecurityService().createObjectIdentity(examEntity, examInfo.getDomainId());
    	
    	if (examInfo.getAttachments() != null && !examInfo.getAttachments().isEmpty()) {
			logger.debug("found " + examInfo.getAttachments().size()+" attachments.");
			final FolderInfo folder = getDocumentService().getFolder(examEntity);
			for (FileInfo attachment : examInfo.getAttachments()) {
				getDocumentService().createFileEntry(attachment, folder);
			}
		}
    	
    	

		getExamDao().toExamInfo(examEntity, examInfo);
	}

	@Override
	protected void handleCreatePaperSubmission(
			PaperSubmissionInfo paperSubmissionInfo) throws Exception {
		Validate.notNull(paperSubmissionInfo, "paperSubmissionInfo cannot be null.");
    	Validate.notNull(paperSubmissionInfo.getExamId(), "ExanId cannot be null.");
    	
    	//Transform to entity
    	final PaperSubmission paperSubmissionEntity = getPaperSubmissionDao().paperSubmissionInfoToEntity(paperSubmissionInfo);
    	Validate.notNull(paperSubmissionEntity, "paperSubmissionEntity cannot be null.");
    	
    	final Exam exam = getExamDao().load(paperSubmissionInfo.getExamId());
    	final User user = getUserDao().load(paperSubmissionInfo.getUserId());
    	    	
    	paperSubmissionEntity.setExam(exam);
    	paperSubmissionEntity.setSender(user);
    	paperSubmissionEntity.setDeliverDate(new Date());
    	
    	getPaperSubmissionDao().create(paperSubmissionEntity);
    	Validate.notNull(paperSubmissionEntity, "paperSubmissionId cannot be null");
    	
		// Update input parameter for aspects to get the right domain objects.
    	paperSubmissionInfo.setId(paperSubmissionEntity.getId());

    	getExamDao().update(exam);
    	
		// add object identity to security
    	getSecurityService().createObjectIdentity(paperSubmissionEntity, paperSubmissionEntity.getExam());
    	
	}

	/** 
	 * @return List of PaperSubmissionInfo
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindPaperSubmissionsByExam(Long examId)
			throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
    	final Exam exam = getExamDao().load(examId);
    	
    	final List<PaperSubmissionInfo> submissions = getPaperSubmissionDao().findByExam(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, exam);
    	
    	for(PaperSubmissionInfo submission : submissions){
    		final UserInfo user = getSecurityService().getUser(submission.getUserId());
    		submission.setFirstName(user.getFirstName());
    		submission.setLastName(user.getLastName());
    		submission.setDisplayName(user.getDisplayName());
    		if(exam.getDeadline().after(submission.getDeliverDate())){
    			submission.setSubmissionStatus(SubmissionStatus.IN_TIME);
    		}else{
    			submission.setSubmissionStatus(SubmissionStatus.NOT_IN_TIME);
    		}
       	}
    	return submissions;
	}
	
	/** 
	 * @return List of PaperSubmissionInfo
	 */
	@Override
	protected List handleFindPaperSubmissionsByExamAndUser(Long examId,
			Long userId) throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
    	Validate.notNull(userId, "userId cannot be null");    	
   
    	final User user = getUserDao().load(userId);
    	final Exam exam = getExamDao().load(examId);
    	
    	final List<PaperSubmissionInfo> submissions = getPaperSubmissionDao().findByExamAndUser(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, exam, user);;
    	
    	for(PaperSubmissionInfo submission : submissions){
    		submission.setFirstName(user.getFirstName());
    		submission.setLastName(user.getLastName());
    		submission.setDisplayName(user.getDisplayName());
    		if(exam.getDeadline().after(submission.getDeliverDate())){
    			submission.setSubmissionStatus(SubmissionStatus.IN_TIME);
    		}else{
    			submission.setSubmissionStatus(SubmissionStatus.NOT_IN_TIME);
    		}
       	}
    	
    	return submissions;
	}

	@Override
	protected ExamInfo handleGetExam(Long examId) throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
		
		final ExamInfo examInfo = (ExamInfo) getExamDao().load(ExamDao.TRANSFORM_EXAMINFO, examId);
		final List<FileInfo> attachments = getDocumentService().getFileEntries(examInfo);
		examInfo.setAttachments(attachments);
    	
    	return examInfo;
	}

	@Override
	protected PaperSubmissionInfo handleGetPaperSubmission(
			Long paperSubmissionId) throws Exception {
		Validate.notNull(paperSubmissionId, "paperSubmissionId cannot be null");
		
		final PaperSubmissionInfo submission = (PaperSubmissionInfo)getPaperSubmissionDao().load(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, paperSubmissionId); 
		final Exam exam = getExamDao().load(submission.getExamId());
		final UserInfo user = getSecurityService().getUser(submission.getUserId());
		
		submission.setFirstName(user.getFirstName());
		submission.setLastName(user.getLastName());
		submission.setDisplayName(user.getDisplayName());
		if(exam.getDeadline().after(submission.getDeliverDate())){
			submission.setSubmissionStatus(SubmissionStatus.IN_TIME);
		}else{
			submission.setSubmissionStatus(SubmissionStatus.NOT_IN_TIME);
		}

		return submission;
    	
	}

	@Override
	protected void handleRemoveExam(Long examId) throws Exception {
    	Validate.notNull(examId, "examId cannot be null.");

    	final Exam examEntity = getExamDao().load(examId);
        
    	getExamDao().remove(examEntity);
	}

	@Override
	protected void handleUpdateExam(ExamInfo examInfo) throws Exception {
		logger.debug("Starting method handleUpdateExam");
    	Validate.notNull(examInfo,"examInfo cannot be null");
    	Validate.notNull(examInfo.getId(),"Parameter examInfo must contain a valid id.");
    	
    	//transform VO to an entity
    	final Exam examEntity = getExamDao().examInfoToEntity(examInfo);
    	
    	//update the exam
    	logger.debug("Updating exam");
    	getExamDao().update(examEntity);
    	
    	logger.debug("Updating file attachments");
    	getDocumentService().diffSave(examEntity, examInfo.getAttachments());
		
		getExamDao().toExamInfo(examEntity, examInfo);
	}

	@Override
	protected PaperSubmissionInfo handleUpdatePaperSubmission(
			PaperSubmissionInfo paperSubmissionInfo) throws Exception {
		logger.debug("Starting method handleUpdatePaperSubmission");
		Validate.notNull(paperSubmissionInfo, "paperSubmissionInfo cannot be null");
		Validate.notNull(paperSubmissionInfo.getId(), "Parameter paperSubmissionInfo must contain a valid id");
		
		final ExamInfo exam = getExam(paperSubmissionInfo.getExamId());
		
		paperSubmissionInfo.setDeliverDate(new Date());		
		if(paperSubmissionInfo.getSubmissionStatus().equals(SubmissionStatus.IN_TIME) && exam.getDeadline().before(paperSubmissionInfo.getDeliverDate())){
			//Creating a new exam which delivery date is not in time
			final PaperSubmissionInfo newSubmissionInfo = new PaperSubmissionInfo();			
			newSubmissionInfo.setDeliverDate(paperSubmissionInfo.getDeliverDate());
			newSubmissionInfo.setExamId(paperSubmissionInfo.getExamId());
			newSubmissionInfo.setUserId(paperSubmissionInfo.getUserId());
			this.createPaperSubmission(newSubmissionInfo);
			
			return newSubmissionInfo;
			
		}else{
			//PaperSubmission is still in time and will be updated
			//Transfor VO to an entity
			final PaperSubmission paperSubmissionEntity =  getPaperSubmissionDao().paperSubmissionInfoToEntity(paperSubmissionInfo);
			
			//update the PaperSubmission
			getPaperSubmissionDao().update(paperSubmissionEntity);
			
			return paperSubmissionInfo;
		}
		
	}

	/** 
	 * @return List of ExamInfo
	 */	
	@Override
	protected List handleFindExamsByDomainId(Long domainId) throws Exception {
		Validate.notNull(domainId, "domainId cannot be null.");
    	
      	return getExamDao().findByDomainId(ExamDao.TRANSFORM_EXAMINFO, domainId);
	}
	
	/** 
	 * @return List of ExamInfo
	 */
	@Override
	protected List handleFindActiveExamsByDomainId(Long domainId)
			throws Exception {
		Validate.notNull(domainId, "courseId cannot be null.");
    	
		//List of all exams of the domainId
		final List<ExamInfo> exams = getExamDao().findByDomainId(ExamDao.TRANSFORM_EXAMINFO, domainId);
    	
		//Filtering the active exams
		final List<ExamInfo> activeExams = new ArrayList<ExamInfo>();
    	for (ExamInfo exam : exams){
    		if (exam.getDeadline().after(new Date()))
    			activeExams.add(exam);
    	}
    	
    	return activeExams;
	}

	/** 
	 * @return List of ExamInfo
	 */
	@Override
	protected List handleFindInactiveExamsByDomainId(Long domainId)
			throws Exception {
		Validate.notNull(domainId, "domainId cannot be null.");
    	
		//List of all exams of the domainId
    	final List<ExamInfo> exams = getExamDao().findByDomainId(ExamDao.TRANSFORM_EXAMINFO, domainId);
    	
    	//Filtering the inactive exams
    	final List<ExamInfo> inactiveExams = new ArrayList<ExamInfo>();
    	for (ExamInfo exam : exams){
    		if (exam.getDeadline().before(new Date()))
    			inactiveExams.add(exam);
    	}
    	
    	return inactiveExams;
	}


	/** 
	 * @return List of SubmissionInfo
	 */
	@Override
	protected List handleGetMembersAsPaperSubmissionsByExam(Long examId)
			throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
		
		final Exam exam = getExamDao().load(examId);
    	
		final List<PaperSubmissionInfo> allSubmissions = new ArrayList();
    	final List<PaperSubmissionInfo> submissions = findPaperSubmissionsByExam(exam.getId());
    	
    	final List<UserInfo> members = loadCourseMembers(exam.getDomainId());
    	
    	//A list of PaperSubmissions is created, whereas for each Member of the course at least one and at most two PaperSubmissionInfos a created
    	for(UserInfo member : members){
    		boolean submitted = false;
    		
    		for(PaperSubmissionInfo submission : submissions){
    			if(member.getId().equals(submission.getUserId())){
    				final PaperSubmissionInfo paper = new PaperSubmissionInfo();
    				paper.setUserId(member.getId());
    				paper.setDisplayName(member.getLastName()+", "+member.getFirstName());
    	    		paper.setId(submission.getId());
    				paper.setSubmissionStatus(submission.getSubmissionStatus());
    				allSubmissions.add(paper);
 					submitted=true;
    			}
    		}
    		if(submitted==false){
    			final PaperSubmissionInfo paper = new PaperSubmissionInfo();
    			paper.setUserId(member.getId());
          		paper.setDisplayName(member.getLastName()+", "+member.getFirstName());
    			paper.setSubmissionStatus(SubmissionStatus.NOT_SUBMITTED);
				allSubmissions.add(paper);
    		}
    	}
    	return allSubmissions;
	}
	
	/** 
	 * @return List of UserInfo
	 */
	@SuppressWarnings("unchecked")
	private List<UserInfo> loadCourseMembers(long domainId) {
		// FIXME: extremely dirty!!! There must be an easier way
		Group group = getSecurityService().getGroupByName("GROUP_COURSE_" + domainId + "_PARTICIPANTS");
		
		List<Authority> members = group.getMembers();
		List<UserInfo> courseMembers = new ArrayList<UserInfo>(members.size());
		for (Authority auth : members) {
			courseMembers.add(getSecurityService().getUser(auth.getId()));
		}
		
		return courseMembers;
	}

	/** 
	 * @return List of FileInfo
	 */
	@Override
	protected List handleGetPaperSubmissionFiles(Collection submissions)
			throws Exception {
		Validate.notNull(submissions, "submissions cannot be null.");
		final List<FileInfo> allFiles = new ArrayList<FileInfo>();
		
		for(PaperSubmissionInfo submission: (Collection<PaperSubmissionInfo>)submissions){
			final List<FileInfo> filesOfSubmission = new ArrayList<FileInfo>();
			
			final FolderInfo folder = getDocumentService().getFolder(submission); 
			final List<FolderEntryInfo> files = getDocumentService().getFolderEntries(submission, folder);
			filesOfSubmission.addAll(getDocumentService().allFileEntries(files));
			
			for(FileInfo file : filesOfSubmission){
				String path = submission.getFirstName()+"_"+submission.getLastName();
				file.setPath(path);
			}
			allFiles.addAll(filesOfSubmission);
		}
		return allFiles;
	}

	/** 
	 * @return List of SubmissionInfo
	 */
	@Override
	protected List handleFindInTimePaperSubmissionsByExam(Long examId) throws Exception {
		final List<PaperSubmissionInfo> allSubmissions = findPaperSubmissionsByExam(examId);
		final List<PaperSubmissionInfo> inTimeSubmissions = new ArrayList<PaperSubmissionInfo>();
		
		for (PaperSubmissionInfo submission : allSubmissions) {
			if (SubmissionStatus.IN_TIME.equals(submission.getSubmissionStatus())) {
				inTimeSubmissions.add(submission);
			}
		}
		
		return inTimeSubmissions;
	}

}