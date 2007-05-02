package org.openuss.lecture;

/**
 * Lecture Faculty listener informs listeners if a domain object will be created, updated or removed.
 * 
 * @author Ingo Dueppe
 *
 */
public interface LectureListener {

	/**
	 * Fired if an new faculty is created
	 * @param faculty
	 * @throws LectureException
	 */
	public void createdFaculty(Faculty faculty) throws LectureException;
	
	/**
	 * Fired if an faculty is going to be removed
	 * @param faculty
	 * @throws LectureException to cancel the transaction
	 */
	public void removingFaculty(Faculty faculty) throws LectureException;

	/**
	 * Fired if an faculty object is updated
	 * @param faculty
	 * @throws LectureException 
	 */
	public void updateFaculty(Faculty faculty) throws LectureException;
	
	
	/**
	 * Fired if an enrollment is going to be removed.
	 * @param enrollment
	 * @throws LectureException to cancel the transaction
	 */
	public void removingEnrollment(Enrollment enrollment) throws LectureException;
	
	/**
	 * Fired if an subject is going to be removed
	 * @param enrollment
	 * @throws LectureException to cancel the transaction
	 */
	public void removingSubject(Subject enrollment) throws LectureException; 
}
