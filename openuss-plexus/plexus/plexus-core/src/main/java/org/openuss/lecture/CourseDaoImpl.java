// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;
/**
 * @see org.openuss.lecture.Course
 */
public class CourseDaoImpl
    extends org.openuss.lecture.CourseDaoBase
{
    /**
     * @see org.openuss.lecture.CourseDao#toCourseInfo(org.openuss.lecture.Course, org.openuss.lecture.CourseInfo)
     */
    public void toCourseInfo(Course sourceEntity,
        CourseInfo targetVO)
    {
        super.toCourseInfo(sourceEntity, targetVO);
        targetVO.setName(sourceEntity.getName());
    }


    /**
     * @see org.openuss.lecture.CourseDao#toCourseInfo(org.openuss.lecture.Course)
     */
    public CourseInfo toCourseInfo(final Course entity)
    {
    	CourseInfo targetVO = new CourseInfo();
        toCourseInfo(entity, targetVO);
        return targetVO;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private Course loadCourseFromCourseInfo(CourseInfo courseInfo)
    {
        Course course = this.load(courseInfo.getId());
        if (course == null)
        {
            course = Course.Factory.newInstance();
        }
        return course;
    }

    
    /**
     * @see org.openuss.lecture.CourseDao#courseInfoToEntity(org.openuss.lecture.CourseInfo)
     */
    public Course courseInfoToEntity(CourseInfo courseInfo)
    {
        Course entity = this.loadCourseFromCourseInfo(courseInfo);
        this.courseInfoToEntity(courseInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.lecture.CourseDao#courseInfoToEntity(org.openuss.lecture.CourseInfo, org.openuss.lecture.Course)
     */
    public void courseInfoToEntity(
        org.openuss.lecture.CourseInfo sourceVO,
        org.openuss.lecture.Course targetEntity,
        boolean copyIfNull)
    {
        super.courseInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}