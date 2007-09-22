package org.openuss.lecture;

/**
 * Lecture Institute listener informs listeners if a domain object will be created, updated or removed.
 * 
 * @author Ingo Dueppe
 *
 */
public interface LectureListener {

	/**
	 * Fired if an new institute is created
	 * @param institute
	 * @throws LectureException
	 */
	public void createdInstitute(Institute institute) throws LectureException;
	
	/**
	 * Fired if an institute is going to be removed
	 * @param institute
	 * @throws LectureException to cancel the transaction
	 */
	public void removingInstitute(Institute institute) throws LectureException;

	/**
	 * Fired if an institute object is updated
	 * @param institute
	 * @throws LectureException 
	 */
	public void updateInstitute(Institute institute) throws LectureException;
	
	
	/**
	 * Fired if an course is going to be removed.
	 * @param course
	 * @throws LectureException to cancel the transaction
	 */
	public void removingCourse(Course course) throws LectureException;
	
	/**
	 * Fired if an courseType is going to be removed
	 * @param course
	 * @throws LectureException to cancel the transaction
	 */
	public void removingCourseType(CourseType course) throws LectureException; 
}
