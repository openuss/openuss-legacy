// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.paperSubmission; // NOPMD
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
        super.toExamInfo(sourceEntity, targetVO);
    }

    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.paperSubmission.Exam loadExamFromExamInfo(org.openuss.paperSubmission.ExamInfo examInfo)
    {
        if (examInfo.getId() != null){
        	return this.load(examInfo.getId());
        } else {
        	return new ExamImpl();
        }
    }

    
    /**
     * @see org.openuss.paperSubmission.ExamDao#examInfoToEntity(org.openuss.paperSubmission.ExamInfo)
     */
    public org.openuss.paperSubmission.Exam examInfoToEntity(org.openuss.paperSubmission.ExamInfo examInfo)
    {
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
        super.examInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}