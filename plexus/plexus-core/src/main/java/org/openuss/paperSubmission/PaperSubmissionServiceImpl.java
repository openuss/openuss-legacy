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
    	Exam examEntity = this.getExamDao().examInfoToEntity(examInfo);
    	Validate.notNull(examEntity, "examInfoEntity cannot be null.");
    	
    	this.getExamDao().create(examEntity);
    	Validate.notNull(examEntity, "examId cannot be null");
    	
		// Update input parameter for aspects to get the right domain objects.    	
    	examInfo.setId(examEntity.getId());
    	this.getSecurityService().createObjectIdentity(examEntity, null);
    	
    	
    	if (examInfo.getAttachments() != null && !examInfo.getAttachments().isEmpty()) {
			logger.debug("found "+examInfo.getAttachments().size()+" attachments.");
			FolderInfo folder = getDocumentService().getFolder(examEntity);

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
    	PaperSubmission paperSubmissionEntity = this.getPaperSubmissionDao().paperSubmissionInfoToEntity(paperSubmissionInfo);
    	Validate.notNull(paperSubmissionEntity, "paperSubmissionEntity cannot be null.");
    	
    	Exam exam = this.getExamDao().load(paperSubmissionInfo.getExamId());
    	User user = this.getUserDao().load(paperSubmissionInfo.getUserId());
    	    	
    	paperSubmissionEntity.setExam(exam);
    	paperSubmissionEntity.setSender(user);
    	paperSubmissionEntity.setDeliverDate(new Date());
    	
    	this.getPaperSubmissionDao().create(paperSubmissionEntity);
    	Validate.notNull(paperSubmissionEntity, "paperSubmissionId cannot be null");
    	
		// Update input parameter for aspects to get the right domain objects.
    	paperSubmissionInfo.setId(paperSubmissionEntity.getId());

    	this.getExamDao().update(exam);
    	
		// add object identity to security
    	this.getSecurityService().createObjectIdentity(paperSubmissionEntity, paperSubmissionEntity.getExam());
    	
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindPaperSubmissionsByExam(Long examId)
			throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
    	Exam exam = this.getExamDao().load(examId);
    	List<PaperSubmissionInfo> submissions = this.getPaperSubmissionDao().findByExam(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, exam);
    	
    	for(PaperSubmissionInfo submission : submissions){
    		UserInfo user = getSecurityService().getUser(submission.getUserId());
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
	protected List handleFindPaperSubmissionsByExamAndUser(Long examId,
			Long userId) throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
    	Validate.notNull(userId, "userId cannot be null");    	
   
    	User user = this.getUserDao().load(userId);
    	Exam exam = this.getExamDao().load(examId);
    	
    	List<PaperSubmissionInfo> submissions = this.getPaperSubmissionDao().findByExamAndUser(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, exam, user);;
    	
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
		
		ExamInfo examInfo = (ExamInfo) getExamDao().load(ExamDao.TRANSFORM_EXAMINFO, examId);
		
		List<FileInfo> attachments = getDocumentService().getFileEntries(examInfo);
		examInfo.setAttachments(attachments);
    	
    	return examInfo;
	}

	@Override
	protected PaperSubmissionInfo handleGetPaperSubmission(
			Long paperSubmissionId) throws Exception {
		Validate.notNull(paperSubmissionId, "paperSubmissionId cannot be null");
		
		PaperSubmissionInfo submission = (PaperSubmissionInfo)getPaperSubmissionDao().load(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, paperSubmissionId); 
		
		Exam exam = this.getExamDao().load(submission.getExamId());
		UserInfo user = getSecurityService().getUser(submission.getUserId());
		
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
    	
    	//delete examId    	
        Exam examEntity = this.getExamDao().load(examId);
        this.getExamDao().remove(examEntity);
	}

//	@Override
//	protected void handleRemovePaperSubmission(Long paperSubmissionId)
//			throws Exception {
//		Validate.notNull(paperSubmissionId, "paperSubmissionId cannot be null.");
//    	
//    	//delete paperSubmissionId
//    	PaperSubmission  paperSubmissionEntity = this.getPaperSubmissionDao().load(paperSubmissionId);
//    	getPaperSubmissionDao().remove(paperSubmissionEntity);   	
//	}

	@Override
	protected void handleUpdateExam(ExamInfo examInfo) throws Exception {
		logger.debug("Starting method handleUpdateExam");
    	Validate.notNull(examInfo,"examInfo cannot be null");
    	Validate.notNull(examInfo.getId(),"Parameter examInfo must contain a valid id.");
    	
    	//transform VO to an entity
    	Exam examEntity = getExamDao().examInfoToEntity(examInfo);
    	
    	//update the exam
    	getExamDao().update(examEntity);
    	
    	getDocumentService().diffSave(examEntity, examInfo.getAttachments());
		
		getExamDao().toExamInfo(examEntity, examInfo);
	}

	@Override
	protected PaperSubmissionInfo handleUpdatePaperSubmission(
			PaperSubmissionInfo paperSubmissionInfo) throws Exception {
		logger.debug("Starting method handleUpdatePaperSubmission");
		Validate.notNull(paperSubmissionInfo, "paperSubmissionInfo cannot be null");
		Validate.notNull(paperSubmissionInfo.getId(), "Parameter paperSubmissionInfo must contain a valid id");
		
		ExamInfo exam = this.getExam(paperSubmissionInfo.getExamId());
		
		paperSubmissionInfo.setDeliverDate(new Date());		
		if(paperSubmissionInfo.getSubmissionStatus().equals(SubmissionStatus.IN_TIME) && exam.getDeadline().before(paperSubmissionInfo.getDeliverDate())){
			PaperSubmissionInfo newSubmissionInfo = new PaperSubmissionInfo();			
			newSubmissionInfo.setDeliverDate(paperSubmissionInfo.getDeliverDate());
			newSubmissionInfo.setExamId(paperSubmissionInfo.getExamId());
			newSubmissionInfo.setUserId(paperSubmissionInfo.getUserId());
			this.createPaperSubmission(newSubmissionInfo);
//			FolderInfo folder = getDocumentService().getFolder(paperSubmissionInfo);
//			List<FileInfo> files = getDocumentService().getFileEntries(paperSubmissionInfo);
//		
//			//Copy Files from the old submission to the new
//			FolderInfo newFolder = getDocumentService().getFolder(newSubmissionInfo);
//			for(FileInfo file : files){
//				file = getDocumentService().getFileEntry(file.getId(), true);
//				file.setId(null);
//				getDocumentService().createFileEntry(file, newFolder);
//			}
			return newSubmissionInfo;
			
		}else{
			//Transfor VO to an entity
			PaperSubmission paperSubmissionEntity =  getPaperSubmissionDao().paperSubmissionInfoToEntity(paperSubmissionInfo);
			
			//update the PaperSubmission
			getPaperSubmissionDao().update(paperSubmissionEntity);
			return paperSubmissionInfo;
		}
		
	}

	@Override
	protected List handleFindActiveExamsByDomainId(Long domainId)
			throws Exception {
		Validate.notNull(domainId, "courseId cannot be null.");
    	
		
    	List<ExamInfo> exams = getExamDao().findByDomainId(ExamDao.TRANSFORM_EXAMINFO, domainId);
    	List<ExamInfo> activeExams = new ArrayList<ExamInfo>();
    	for (ExamInfo exam : exams){
    		if (exam.getDeadline().after(new Date()))
    			activeExams.add(exam);
    	}
    	
    	return activeExams;
	}

	@Override
	protected List handleFindExamsByDomainId(Long domainId) throws Exception {
		Validate.notNull(domainId, "courseId cannot be null.");
    	
      	return this.getExamDao().findByDomainId(ExamDao.TRANSFORM_EXAMINFO, domainId);
	}

	@Override
	protected List handleFindInactiveExamsByDomainId(Long domainId)
			throws Exception {
		Validate.notNull(domainId, "courseId cannot be null.");
    	
    	List<ExamInfo> exams = getExamDao().findByDomainId(ExamDao.TRANSFORM_EXAMINFO, domainId);
    	List<ExamInfo> inactiveExams = new ArrayList<ExamInfo>();
    	for (ExamInfo exam : exams){
    		if (exam.getDeadline().before(new Date()))
    			inactiveExams.add(exam);
    	}
    	
    	return inactiveExams;
	}


	@Override
	protected List handleGetMembersAsPaperSubmissionsByExam(Long examId)
			throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
		
		Exam exam = this.getExamDao().load(examId);
    	
		List<PaperSubmissionInfo> allSubmissions = new ArrayList();
    	List<PaperSubmissionInfo> submissions = this.findPaperSubmissionsByExam(exam.getId());
    	//FIXME CourseMembers will be deprecated soon. Alternatives?
    	CourseInfo course = this.getCourseService().getCourseInfo(exam.getDomainId());
    	List<UserInfo> members = loadCourseMembers(exam.getDomainId());
    	
    	for(UserInfo member : members){
    		boolean submitted = false;
    		
    		for(PaperSubmissionInfo submission : submissions){
    			if(member.getId().equals(submission.getUserId())){
    				PaperSubmissionInfo paper = new PaperSubmissionInfo();
    				paper.setUserId(member.getId());
    				paper.setDisplayName(member.getLastName()+", "+member.getFirstName());
    	    		paper.setId(submission.getId());
    				paper.setSubmissionStatus(submission.getSubmissionStatus());
    				allSubmissions.add(paper);
 					submitted=true;
    			}
    		}
    		if(submitted==false){
    			PaperSubmissionInfo paper = new PaperSubmissionInfo();
    			paper.setUserId(member.getId());
          		paper.setDisplayName(member.getLastName()+", "+member.getFirstName());
    			paper.setSubmissionStatus(SubmissionStatus.NOT_SUBMITTED);
				allSubmissions.add(paper);
    		}
    	}
    	return allSubmissions;
	}
	
	@SuppressWarnings("unchecked")
	private List<UserInfo> loadCourseMembers(long domainId) {
		Group group = getSecurityService().getGroupByName("GROUP_COURSE_" + domainId + "_PARTICIPANTS");
		
		List<Authority> members = group.getMembers();
		List<UserInfo> courseMembers = new ArrayList<UserInfo>(members.size());
		for (Authority auth : members) {
			courseMembers.add(getSecurityService().getUser(auth.getId()));
		}
		
		return courseMembers;
	}

	@Override
	protected List handleGetPaperSubmissions(Collection submissions, Long examId)
			throws Exception {
		List<FileInfo> allFiles = new ArrayList<FileInfo>();
		
		for(PaperSubmissionInfo submission: (Collection<PaperSubmissionInfo>)submissions){
			List<FileInfo> filesOfSubmission = new ArrayList<FileInfo>();
			
			FolderInfo folder = getDocumentService().getFolder(submission); 
			List<FolderEntryInfo> files = getDocumentService().getFolderEntries(submission, folder);
			filesOfSubmission.addAll(getDocumentService().allFileEntries(files));
			
			for(FileInfo file : filesOfSubmission){
				String path = submission.getFirstName()+"_"+submission.getLastName();
				file.setPath(path);
			}
			allFiles.addAll(filesOfSubmission);
		}
		return allFiles;
	}

	@Override
	protected List handleFindInTimePaperSubmissionsByExam(Long examId) throws Exception {
		List<PaperSubmissionInfo> allSubmissions = findPaperSubmissionsByExam(examId);
		List<PaperSubmissionInfo> inTimeSubmissions = new ArrayList<PaperSubmissionInfo>();
		
		for (PaperSubmissionInfo submission : allSubmissions) {
			if (SubmissionStatus.IN_TIME.equals(submission.getSubmissionStatus())) {
				inTimeSubmissions.add(submission);
			}
		}
		
		return inTimeSubmissions;
	}

}