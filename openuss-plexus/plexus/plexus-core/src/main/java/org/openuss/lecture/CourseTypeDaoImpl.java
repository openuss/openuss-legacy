// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;
/**
 * @see org.openuss.lecture.CourseType
 */
public class CourseTypeDaoImpl
    extends org.openuss.lecture.CourseTypeDaoBase
{
    /**
     * @see org.openuss.lecture.CourseTypeDao#toCourseTypeInfo(org.openuss.lecture.CourseType, org.openuss.lecture.CourseTypeInfo)
     */
    public void toCourseTypeInfo(
        org.openuss.lecture.CourseType sourceEntity,
        org.openuss.lecture.CourseTypeInfo targetVO)
    {
        super.toCourseTypeInfo(sourceEntity, targetVO);
        if (sourceEntity.getInstitute() != null) {
        	targetVO.setInstituteId(sourceEntity.getInstitute().getId());
        }
    }


    /**
     * @see org.openuss.lecture.CourseTypeDao#toCourseTypeInfo(org.openuss.lecture.CourseType)
     */
    public org.openuss.lecture.CourseTypeInfo toCourseTypeInfo(final org.openuss.lecture.CourseType entity)
    {
        // @todo verify behavior of toCourseTypeInfo
        return super.toCourseTypeInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.lecture.CourseType loadCourseTypeFromCourseTypeInfo(org.openuss.lecture.CourseTypeInfo courseTypeInfo)
    {
    	CourseType courseType = new CourseTypeImpl();
        if (courseTypeInfo.getId() != null)
        {
            courseType = this.load(courseTypeInfo.getId());
        }
        return courseType;
    }

    
    /**
     * @see org.openuss.lecture.CourseTypeDao#courseTypeInfoToEntity(org.openuss.lecture.CourseTypeInfo)
     */
    public org.openuss.lecture.CourseType courseTypeInfoToEntity(org.openuss.lecture.CourseTypeInfo courseTypeInfo)
    {
        org.openuss.lecture.CourseType entity = this.loadCourseTypeFromCourseTypeInfo(courseTypeInfo);
        this.courseTypeInfoToEntity(courseTypeInfo, entity, true);
        
        if (courseTypeInfo.getInstituteId() != null) {
        	Institute institute = this.getInstituteDao().load(courseTypeInfo.getInstituteId());
        	entity.setInstitute(institute);
        }
        
        return entity;
    }


    /**
     * @see org.openuss.lecture.CourseTypeDao#courseTypeInfoToEntity(org.openuss.lecture.CourseTypeInfo, org.openuss.lecture.CourseType)
     */
    public void courseTypeInfoToEntity(
        org.openuss.lecture.CourseTypeInfo sourceVO,
        org.openuss.lecture.CourseType targetEntity,
        boolean copyIfNull)
    {
        super.courseTypeInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}