// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.paperSubmission;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
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

	@Override
	protected List handleFindPaperSubmissionsByExam(Long examId)
			throws Exception {
		Validate.notNull(examId, "examId cannot be null.");
    	
    	Exam exam = this.getExamDao().load(examId);
    	return this.getPaperSubmissionDao().findByExam(PaperSubmissionDao.TRANSFORM_NONE, exam);    	
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
    	
    	return (PaperSubmissionInfo)getPaperSubmissionDao().load(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, paperSubmissionId);
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
		
		//Transfor VO to an entity
		PaperSubmission paperSubmissionEntity =  getPaperSubmissionDao().paperSubmissionInfoToEntity(paperSubmissionInfo);
		
		// FIXME update files!!!
		
		//update the PaperSubmission
		getPaperSubmissionDao().update(paperSubmissionEntity);
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
	
    

}