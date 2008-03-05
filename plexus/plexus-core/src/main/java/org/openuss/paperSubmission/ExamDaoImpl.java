// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.paperSubmission;
/**
 * @see org.openuss.paperSubmission.Exam
 */
public class ExamDaoImpl
    extends org.openuss.paperSubmission.ExamDaoBase
{
    /**
     * @see org.openuss.paperSubmission.ExamDao#toExamInfo(org.openuss.paperSubmission.Exam, org.openuss.paperSubmission.ExamInfo)
     */
    public void toExamInfo(
        org.openuss.paperSubmission.Exam sourceEntity,
        org.openuss.paperSubmission.ExamInfo targetVO)
    {
        // @todo verify behavior of toExamInfo
        super.toExamInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.paperSubmission.ExamDao#toExamInfo(org.openuss.paperSubmission.Exam)
     */
    public org.openuss.paperSubmission.ExamInfo toExamInfo(final org.openuss.paperSubmission.Exam entity)
    {
        // @todo verify behavior of toExamInfo
        return super.toExamInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.paperSubmission.Exam loadExamFromExamInfo(org.openuss.paperSubmission.ExamInfo examInfo)
    {
        // @todo implement loadExamFromExamInfo
        //throw new java.lang.UnsupportedOperationException("org.openuss.paperSubmission.loadExamFromExamInfo(org.openuss.paperSubmission.ExamInfo) not yet implemented.");

    	Exam exam = null;

        if (examInfo.getId() != null){
        	exam = this.load(examInfo.getId());
        }else{
        	exam = Exam.Factory.newInstance();
        }
        return exam;        
    }

    
    /**
     * @see org.openuss.paperSubmission.ExamDao#examInfoToEntity(org.openuss.paperSubmission.ExamInfo)
     */
    public org.openuss.paperSubmission.Exam examInfoToEntity(org.openuss.paperSubmission.ExamInfo examInfo)
    {
        // @todo verify behavior of examInfoToEntity
        org.openuss.paperSubmission.Exam entity = this.loadExamFromExamInfo(examInfo);
        this.examInfoToEntity(examInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.paperSubmission.ExamDao#examInfoToEntity(org.openuss.paperSubmission.ExamInfo, org.openuss.paperSubmission.Exam)
     */
    public void examInfoToEntity(
        org.openuss.paperSubmission.ExamInfo sourceVO,
        org.openuss.paperSubmission.Exam targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of examInfoToEntity
        super.examInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}