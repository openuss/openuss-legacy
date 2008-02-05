// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.paperSubmission;

import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.TimeZone;



import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.collaboration.Workspace;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseMember;
import org.openuss.lecture.CourseMemberDao;
import org.openuss.lecture.CourseMemberInfo;


/**
 * @see org.openuss.paperSubmission.PaperSubmissionService
 */
public class PaperSubmissionServiceImpl
    extends org.openuss.paperSubmission.PaperSubmissionServiceBase
{

	private static final Logger logger = Logger.getLogger(PaperSubmissionServiceImpl.class);
	
    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#createExam(org.openuss.paperSubmission.ExamInfo)
     */
    protected void handleCreateExam(org.openuss.paperSubmission.ExamInfo examInfo)
        throws java.lang.Exception
    {
        // @todo implement protected void handleCreateExam(org.openuss.paperSubmission.ExamInfo examInfo)
        //throw new java.lang.UnsupportedOperationException("org.openuss.paperSubmission.PaperSubmissionService.handleCreateExam(org.openuss.paperSubmission.ExamInfo examInfo) Not implemented!");
    	
    	Validate.notNull(examInfo, "examInfo cannot be null.");
    	Validate.notNull(examInfo.getCourseId(), "getId cannot be null.");
    	
    	//Transform VO to Entity
    	Exam examEntity = this.getExamDao().examInfoToEntity(examInfo);
    	Validate.notNull(examEntity, "examInfoEntity cannot be null.");
    	
    	//Add to a course    	
    	Course course = this.getCourseDao().load(examInfo.getCourseId());
    	course.getExams().add(examEntity);
    	examEntity.setCourse(course);
    	    	
    	
    	this.getExamDao().create(examEntity);
    	Validate.notNull(examEntity, "examId cannot be null");
    	
		// Update input parameter for aspects to get the right domain objects.    	

    	examInfo.setId(examEntity.getId());
    	
		// add object identity to security
    	this.getSecurityService().createObjectIdentity(examEntity, examEntity.getCourse());
    	this.getCourseDao().update(course);		

	// Set Security
	// FIXME: don't know what this does:	this.getSecurityService().createObjectIdentity(examInfoEntity, examInfoEntity.getCourseType());
    // updateAccessTypePermission(examInfoEntity);
  }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#removeExam(java.lang.Long)
     */
    protected void handleRemoveExam(java.lang.Long examId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemoveExam(java.lang.Long examId)
        //throw new java.lang.UnsupportedOperationException("org.openuss.paperSubmission.PaperSubmissionService.handleRemoveExam(java.lang.Long examId) Not implemented!");
    	
    	Validate.notNull(examId, "examId cannot be null.");
    	//delete examId    	
        Exam examEntity = this.getExamDao().load(examId);
        getExamDao().remove(examEntity);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#updateExam(org.openuss.paperSubmission.ExamInfo)
     */
    protected void handleUpdateExam(org.openuss.paperSubmission.ExamInfo examInfo)
        throws java.lang.Exception
    {
        // @todo implement protected void handleUpdateExam(org.openuss.paperSubmission.ExamInfo examInfo)
        //throw new java.lang.UnsupportedOperationException("org.openuss.paperSubmission.PaperSubmissionService.handleUpdateExam(org.openuss.paperSubmission.ExamInfo examInfo) Not implemented!");
    	
    	logger.debug("Starting method handleUpdateExam");
    	Validate.notNull(examInfo,"examInfo cannot be null");
    	Validate.notNull(examInfo.getId(),"Parameter examInfo must contain a valid id.");
    	
    	//transform VO to an entity
    	Exam examEntity = getExamDao().examInfoToEntity(examInfo);
    	
    	//update the exam
    	getExamDao().update(examEntity);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#createPaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo)
     */
    protected void handleCreatePaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo)
        throws java.lang.Exception
    {
        // @todo implement protected void handleCreatePaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo)
        //throw new java.lang.UnsupportedOperationException("org.openuss.paperSubmission.PaperSubmissionService.handleCreatePaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo) Not implemented!");
    	
    	Validate.notNull(paperSubmissionInfo, "paperSubmissionInfo cannot be null.");
    	Validate.notNull(paperSubmissionInfo.getExamId(), "ExanId cannot be null.");
    	
    	//Transform to entity
    	PaperSubmission paperSubmissionEntity = this.getPaperSubmissionDao().paperSubmissionInfoToEntity(paperSubmissionInfo);
    	Validate.notNull(paperSubmissionEntity, "paperSubmissionEntity cannot be null.");
    	
    	//FIXME: Add to a course and exam (NOT VERY SURE)
    	
    	//Course course = this.getCourseDao().load(paperSubmissionEntity.getId());
    	Exam exam = this.getExamDao().load(paperSubmissionInfo.getExamId());
    	CourseMemberInfo courseMemberInfo = (CourseMemberInfo) this.getCourseMemberDao().load(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, paperSubmissionInfo.getCourseMemberId());
    	CourseMember courseMember = this.getCourseMemberDao().courseMemberInfoToEntity(courseMemberInfo);
    	    	
    	paperSubmissionEntity.setExam(exam);
    	paperSubmissionEntity.setSender(courseMember);
    	paperSubmissionEntity.setDeliverDate(new Date());
    	
    	
    	this.getPaperSubmissionDao().create(paperSubmissionEntity);
    	Validate.notNull(paperSubmissionEntity, "paperSubmissionId cannot be null");
    	

    	
		// FIXME - Kai, Indexing should not base on VOs!
		// Kai: Do not delete this!!! Set id of institute VO for indexing
		// Update input parameter for aspects to get the right domain objects.
    	
    	paperSubmissionInfo.setId(paperSubmissionEntity.getId());

    	    	
		// add object identity to security
    	
    	this.getSecurityService().createObjectIdentity(paperSubmissionEntity, paperSubmissionEntity.getExam());
    	
    	this.getExamDao().update(exam);

	// Set Security
	// FIXME: don't know what this does:	this.getSecurityService().createObjectIdentity(examInfoEntity, examInfoEntity.getCourseType());
    // updateAccessTypePermission(examInfoEntity);   	
  }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#removePaperSubmission(java.lang.Long)
     */
    protected void handleRemovePaperSubmission(java.lang.Long paperSubmissionId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemovePaperSubmission(java.lang.Long paperSubmissionId)
        //throw new java.lang.UnsupportedOperationException("org.openuss.paperSubmission.PaperSubmissionService.handleRemovePaperSubmission(java.lang.Long paperSubmissionId) Not implemented!");
    	
    	Validate.notNull(paperSubmissionId, "paperSubmissionId cannot be null.");
    	
    	//delete paperSubmissionId
    	PaperSubmission  paperSubmissionEntity = this.getPaperSubmissionDao().load(paperSubmissionId);
    	getPaperSubmissionDao().remove(paperSubmissionEntity);   	
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findExamsByCourse(java.lang.Long)
     */
    protected java.util.List handleFindExamsByCourse(java.lang.Long courseId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindExamsByCourse(java.lang.Long courseId)
        //return null;
    	Validate.notNull(courseId, "courseId cannot be null.");
    	
      	Course course = this.getCourseDao().load(courseId);    	
      	return this.getExamDao().findByCourse(ExamDao.TRANSFORM_EXAMINFO, course);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findPaperSubmissionsByExam(java.lang.Long)
     */
    protected java.util.List handleFindPaperSubmissionsByExam(java.lang.Long examId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindPaperSubmissionsByExam(java.lang.Long examId)
    	Validate.notNull(examId, "examId cannot be null.");
    	
    	Exam exam = this.getExamDao().load(examId);
    	return this.getPaperSubmissionDao().findByExam(PaperSubmissionDao.TRANSFORM_NONE, exam);    	
    	
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findExamsByCourseMember(java.lang.Long)
     */
    protected java.util.List handleFindExamsByCourseMember(java.lang.Long userId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindExamsByCourseMember(java.lang.Long userId)
    	Validate.notNull(userId, "userId cannot be null.");
    	
    	CourseMember courseMember = this.getCourseMemberDao().load(userId);
    	return this.getExamDao().findByCourse(ExamDao.TRANSFORM_EXAMINFO,courseMember.getCourse());
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#getExam(java.lang.Long)
     */
    protected org.openuss.paperSubmission.ExamInfo handleGetExam(java.lang.Long examId)
        throws java.lang.Exception
    {
        // @todo implement protected org.openuss.paperSubmission.ExamInfo handleGetExam(java.lang.Long examId)
        //return null;
    	Validate.notNull(examId, "examId cannot be null.");
    	
    	return (ExamInfo)getExamDao().load(ExamDao.TRANSFORM_EXAMINFO, examId);  	

    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findPaperSubmissionsByExamAndCourseMember(java.lang.Long, java.lang.Long)
     */
    protected java.util.List handleFindPaperSubmissionsByExamAndCourseMember(java.lang.Long examId, java.lang.Long courseMemberId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindPaperSubmissionsByExamAndCourseMember(java.lang.Long examId, java.lang.Long courseMemberId)
        //return null;
    	Validate.notNull(examId, "examId cannot be null.");
    	Validate.notNull(courseMemberId, "courseMemberId cannot be null");    	
   
    	CourseMemberInfo courseMemberInfo = (CourseMemberInfo) this.getCourseMemberDao().load(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, courseMemberId);
    	CourseMember courseMember = this.getCourseMemberDao().courseMemberInfoToEntity(courseMemberInfo);
    	Exam exam = this.getExamDao().load(examId);
    	
    	return this.getPaperSubmissionDao().findByExamAndCourseMember(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, exam, courseMember);
    	
        }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#getPaperSubmission(java.lang.Long)
     */
    protected org.openuss.paperSubmission.PaperSubmissionInfo handleGetPaperSubmission(java.lang.Long paperSubmissionId)
        throws java.lang.Exception
    {
        // @todo implement protected org.openuss.paperSubmission.PaperSubmissionInfo handleGetPaperSubmission(java.lang.Long paperSubmissionId)
        //return null;
    	
    	Validate.notNull(paperSubmissionId, "paperSubmissionId cannot be null");
    	
    	return (PaperSubmissionInfo)getPaperSubmissionDao().load(PaperSubmissionDao.TRANSFORM_PAPERSUBMISSIONINFO, paperSubmissionId);
    	
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#deletePaperSubmission(java.lang.Long)
     */
    protected void handleDeletePaperSubmission(java.lang.Long paperSubmissionId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleDeletePaperSubmission(java.lang.Long paperSubmissionId)
        //throw new java.lang.UnsupportedOperationException("org.openuss.paperSubmission.PaperSubmissionService.handleDeletePaperSubmission(java.lang.Long paperSubmissionId) Not implemented!");
    	
    	Validate.notNull(paperSubmissionId, "paperSubmissionId cannot be null");
    	
    	//delete paperSubmission
    	PaperSubmission paperSubmissionEntity = getPaperSubmissionDao().load(paperSubmissionId);    	
    	getPaperSubmission(paperSubmissionEntity.getId());
    	
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findActiveExamsByCourse(java.lang.Long)
     */
    protected java.util.List handleFindActiveExamsByCourse(java.lang.Long courseId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindActiveExamsByCourse(java.lang.Long courseId)
        //return null;
    	Validate.notNull(courseId, "courseId cannot be null.");
    	
    	
    	Course course = this.getCourseDao().load(courseId);
    	
    	//Filter the inactives --> deadline expired
    	
    	List <Exam> list = getExamDao().findByCourse(ExamDao.TRANSFORM_EXAMINFO, course);
    	
    	for (int i = 0; i < list.size(); i++){
    		if (this.handleIsExamDeadlineExpired(list.get(i).getId()))
    			list.remove(i);
    	}
    	    
    	return list;
    	
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionService#findInactiveExamsByCourse(java.lang.Long)
     */
    protected java.util.List handleFindInactiveExamsByCourse(java.lang.Long courseId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindInactiveExamsByCourse(java.lang.Long courseId)
        //return null;
    	Validate.notNull(courseId, "courseId cannot be null.");
    	
      	Course course = this.getCourseDao().load(courseId);
      	
    	//Delete exams with no deadline expired
    	
    	List <Exam> list = getExamDao().findByCourse(ExamDao.TRANSFORM_EXAMINFO, course);
    	
    	for (int i = 0; i < list.size(); i++){
    		if (!this.handleIsExamDeadlineExpired(list.get(i).getId()))
    			list.remove(i);
    	}
    	    
    	return list;
    }

	@Override
	protected Boolean handleIsExamDeadlineExpired(Long examId) throws Exception {
		
		Validate.notNull(examId, "examId cannot be null.");
		
	 
		Exam examEntity = getExamDao().load(examId);
		Date dead_line = examEntity.getDeadline();
		
		//long actual_ms cale.getTimeInMillis();
		
		//get the actual time (date or int or long)
		
		return (dead_line.getTime() < Calendar.getInstance().getTimeInMillis());		
	}

	@Override
	protected List handleFindActiveExamsByCourseMember(Long courseMemberId)
			throws Exception {
		
    	Validate.notNull(courseMemberId, "userId cannot be null.");
    	
    	CourseMember courseMember = this.getCourseMemberDao().load(courseMemberId);

    	//Filter the inactive exams --> deadline expired
    	List<Exam> list = this.getExamDao().findByCourse(ExamDao.TRANSFORM_EXAMINFO,courseMember.getCourse());

    	for (int i = 0; i < list.size(); i++){
    		if (this.handleIsExamDeadlineExpired(list.get(i).getId()))
    			list.remove(i);
    	}
    	    
    	return list;
	}

	@Override
	protected void handleUpdatePaperSubmission(
			PaperSubmissionInfo paperSubmissionInfo) throws Exception {

		logger.debug("Starting method handleUpdatePaperSubmission");
		Validate.notNull(paperSubmissionInfo, "paperSubmissionInfo cannot be null");
		Validate.notNull(paperSubmissionInfo.getId(), "Parameter paperSubmissionInfo must contain a valid id");
		
		//Transfor VO to an entity
		PaperSubmission paperSubmissionEntity =  getPaperSubmissionDao().paperSubmissionInfoToEntity(paperSubmissionInfo);
		
		//update the PaperSubmission
		getPaperSubmissionDao().update(paperSubmissionEntity);		
	}

}