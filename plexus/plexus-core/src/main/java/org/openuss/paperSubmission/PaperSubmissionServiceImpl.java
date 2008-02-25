// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.paperSubmission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMember;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.security.User;


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
	}

	@Override
	protected void handleCreatePaperSubmission(
			PaperSubmissionInfo paperSubmissionInfo) throws Exception {
		Validate.notNull(paperSubmissionInfo, "paperSubmissionInfo cannot be null.");
    	Validate.notNull(paperSubmissionInfo.getExamId(), "ExanId cannot be null.");
    	
    	//Transform to entity
    	PaperSubmission paperSubmissionEntity = this.getPaperSubmissionDao().paperSubmissionInfoToEntity(paperSubmissionInfo);
    	Validate.notNull(paperSubmissionEntity, "paperSubmissionEntity cannot be null.");
    	
    	//FIXME: Add to a course and exam (NOT VERY SURE)
    	
    	//Course course = this.getCourseDao().load(paperSubmissionEntity.getId());
    	Exam exam = this.getExamDao().load(paperSubmissionInfo.getExamId());
    	User user = this.getUserDao().load(paperSubmissionInfo.getUserId());
    	    	
    	paperSubmissionEntity.setExam(exam);
    	paperSubmissionEntity.setSender(user);
    	paperSubmissionEntity.setDeliverDate(new Date());
    	if(exam.getDeadline().after(paperSubmissionEntity.getDeliverDate())){
    		paperSubmissionEntity.setSubmissionType("INTIME");
    	}else{
    		paperSubmissionEntity.setSubmissionType("NOTINTIME");
    	}
    	
    	
    	this.getPaperSubmissionDao().create(paperSubmissionEntity);
    	Validate.notNull(paperSubmissionEntity, "paperSubmissionId cannot be null");
    	
		// Update input parameter for aspects to get the right domain objects.
    	paperSubmissionInfo.setId(paperSubmissionEntity.getId());

    	
		// add object identity to security
    	this.getSecurityService().createObjectIdentity(paperSubmissionEntity, paperSubmissionEntity.getExam());
    	
    	this.getExamDao().update(exam);
    	

    	
    	
	}

	@Override
	protected List handleFindActiveExamsByUser(Long userId)
			throws Exception {
		Validate.notNull(userId, "userId cannot be null.");
    	
		// FIXME implement!
		/*
    	User courseMember = this.getCourseMemberDao().load(courseMemberId);

    	//Filter the inactive exams --> deadline expired
    	List<Exam> list = this.getExamDao().findByCourse(ExamDao.TRANSFORM_EXAMINFO,courseMember.getCourse());

    	for (int i = 0; i < list.size(); i++){
    		if (this.handleIsExamDeadlineExpired(list.get(i).getId())) {
    			list.remove(i);
    		}
    	}*/

    	return null; //list;
    }

	@SuppressWarnings("unchecked")
	@Override
	protected List handleFindPaperSubmissionsByExam(Long examId)
			throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
    	List<PaperSubmissionInfo> allSubmissions = new ArrayList();
    	Exam exam = this.getExamDao().load(examId);
    	List<PaperSubmissionInfo> submissions = this.getPaperSubmissionDao().findByExam(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, exam);
    	//FIXME CourseMembers will be deprecated soon. Alternatives?
    	CourseInfo course = this.getCourseService().getCourseInfo(exam.getDomainId());
    	List<CourseMemberInfo> members = this.getCourseService().getParticipants(course);
    	for(CourseMemberInfo member : members){
    		boolean submitted = false;
    		PaperSubmissionInfo paper = new PaperSubmissionInfo();
    		paper.setUserId(member.getUserId());
    		paper.setFirstName(member.getFirstName());
    		paper.setLastName(member.getLastName());
    		
    		for(PaperSubmissionInfo submission : submissions){
    			if(member.getUserId().equals(submission.getUserId())){
    				paper.setSubmissionType(submission.getSubmissionType());
    				allSubmissions.add(paper);
 					submitted=true;
    			}
    		}
    		if(submitted==false){
    			paper.setSubmissionType("NOTSUBMITTED");
				allSubmissions.add(paper);
    		}
    	}
    	return allSubmissions;
	}

	@Override
	protected List handleFindPaperSubmissionsByExamAndUser(Long examId,
			Long userId) throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
    	Validate.notNull(userId, "userId cannot be null");    	
   
    	User user = this.getUserDao().load(userId);
    	Exam exam = this.getExamDao().load(examId);
    	
    	return this.getPaperSubmissionDao().findByExamAndUser(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, exam, user);
	}

	@Override
	protected ExamInfo handleGetExam(Long examId) throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
    	
    	return (ExamInfo)getExamDao().load(ExamDao.TRANSFORM_EXAMINFO, examId);
	}

	@Override
	protected PaperSubmissionInfo handleGetPaperSubmission(
			Long paperSubmissionId) throws Exception {
		Validate.notNull(paperSubmissionId, "paperSubmissionId cannot be null");
		PaperSubmissionInfo submissionInfo = new PaperSubmissionInfo();
		submissionInfo = (PaperSubmissionInfo)getPaperSubmissionDao().load(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, paperSubmissionId);
		submissionInfo.setFiles(getDocumentService().getFileEntries(submissionInfo));
		
		return submissionInfo;
    	//return (PaperSubmissionInfo)getPaperSubmissionDao().load(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, paperSubmissionId);
	}

	@Override
	protected Boolean handleIsExamDeadlineExpired(Long examId) throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
		
		Exam examEntity = getExamDao().load(examId);
		long deadline = examEntity.getDeadline().getTime();
		
		long now = System.currentTimeMillis();
		return (deadline < now);
	}

	@Override
	protected void handleRemoveExam(Long examId) throws Exception {
    	Validate.notNull(examId, "examId cannot be null.");
    	
    	//delete examId    	
        Exam examEntity = this.getExamDao().load(examId);
        this.getExamDao().remove(examEntity);
	}

	@Override
	protected void handleRemovePaperSubmission(Long paperSubmissionId)
			throws Exception {
		Validate.notNull(paperSubmissionId, "paperSubmissionId cannot be null.");
    	
    	//delete paperSubmissionId
    	PaperSubmission  paperSubmissionEntity = this.getPaperSubmissionDao().load(paperSubmissionId);
    	getPaperSubmissionDao().remove(paperSubmissionEntity);   	
	}

	@Override
	protected void handleUpdateExam(ExamInfo examInfo) throws Exception {
		logger.debug("Starting method handleUpdateExam");
    	Validate.notNull(examInfo,"examInfo cannot be null");
    	Validate.notNull(examInfo.getId(),"Parameter examInfo must contain a valid id.");
    	
    	//transform VO to an entity
    	Exam examEntity = getExamDao().examInfoToEntity(examInfo);
    	
    	//update the exam
    	getExamDao().update(examEntity);
	}

	@Override
	protected void handleUpdatePaperSubmission(
			PaperSubmissionInfo paperSubmissionInfo) throws Exception {
		logger.debug("Starting method handleUpdatePaperSubmission");
		Validate.notNull(paperSubmissionInfo, "paperSubmissionInfo cannot be null");
		Validate.notNull(paperSubmissionInfo.getId(), "Parameter paperSubmissionInfo must contain a valid id");
		
		ExamInfo exam = this.getExam(paperSubmissionInfo.getExamId());
		
		paperSubmissionInfo.setDeliverDate(new Date());		
		if(paperSubmissionInfo.getSubmissionType().equals("INTIME") && exam.getDeadline().before(paperSubmissionInfo.getDeliverDate())){
			PaperSubmissionInfo newSubmissionInfo = new PaperSubmissionInfo();			
			newSubmissionInfo.setDeliverDate(paperSubmissionInfo.getDeliverDate());
			newSubmissionInfo.setExamId(paperSubmissionInfo.getExamId());
			newSubmissionInfo.setUserId(paperSubmissionInfo.getUserId());
			this.createPaperSubmission(newSubmissionInfo);
			FolderInfo folder = getDocumentService().getFolder(paperSubmissionInfo);
			List<FileInfo> files = getDocumentService().getFileEntries(paperSubmissionInfo);
			FolderInfo newFolder = getDocumentService().getFolder(newSubmissionInfo);
			getDocumentService().createFileEntries(files, newFolder);
		}else{
			//Transfor VO to an entity
			PaperSubmission paperSubmissionEntity =  getPaperSubmissionDao().paperSubmissionInfoToEntity(paperSubmissionInfo);
			
			// FIXME update files!!!
			
			//update the PaperSubmission
			getPaperSubmissionDao().update(paperSubmissionEntity);
		}
		
	}

	@Override
	protected List handleFindActiveExamsByDomainId(Long domainId)
			throws Exception {
		Validate.notNull(domainId, "courseId cannot be null.");
    	
    	//Filter the inactives --> deadline expired
		/*
    	List <Exam> list = getExamDao().findByCourse(ExamDao.TRANSFORM_EXAMINFO, course);
    	
    	for (int i = 0; i < list.size(); i++){
    		if (this.handleIsExamDeadlineExpired(list.get(i).getId()))
    			list.remove(i);
    	}
    	*/
    	return null;//list;
	}

	@Override
	protected List handleFindExamsByDomainId(Long domainId) throws Exception {
		Validate.notNull(domainId, "courseId cannot be null.");
    	
      	return this.getExamDao().findByDomainId(ExamDao.TRANSFORM_EXAMINFO, domainId);
	}

	@Override
	protected List handleFindInactiveExamsByDomainId(Long domainId)
			throws Exception {
		// FIXME implement!!!!!!
		return null;
	}

	@Override
	protected List handleGetPaperSubmissionsbyExam(Long examId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
    

}