package org.openuss.paperSubmission;

import java.util.Collection;
import java.util.List;

/**
 * PaperSubmissionService is the central service to work with exams
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public interface PaperSubmissionService {

	/**
	 * <p>
	 * createExam creates a new exam
	 * </p>
	 * <p>
	 * 
	 * @param examInfo
	 *            is the exam which is going to be created
	 *            </p>
	 */
	public void createExam(org.openuss.paperSubmission.ExamInfo examInfo);

	/**
	 * <p>
	 * updateExam updates an existing exam and its file attachments
	 * </p>
	 * <p>
	 * 
	 * @param examInfo
	 *            with new information
	 *            </p>
	 */
	public void updateExam(org.openuss.paperSubmission.ExamInfo examInfo);

	/**
	 * <p>
	 * removeExam deletes the exam with id = examId
	 * </p>
	 * <p>
	 * 
	 * @param examId
	 *            is the id of the exam which is going to be deleted
	 *            </p>
	 */
	public void removeExam(Long examId);

	/**
	 * <p>
	 * createPaperSubmission creates a new PaperSubmission
	 * </p>
	 * <p>
	 * 
	 * @param paperSubmissionInfo
	 *            contains the inforamation
	 *            </p>
	 */
	public void createPaperSubmission(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo);

	/**
	 * <p>
	 * updates a PaperSubmission (e.g. with a new delivery date)
	 * </p>
	 * <p>
	 * if the submission time is past the exam's deadline and there is a former
	 * PaperSubmission which submission has been in time, than a new
	 * PaperSubmission will be created
	 * </p>
	 * <p>
	 * 
	 * @param paperSubmissionInfo
	 *            ValueObject with the new Information
	 *            </p>
	 */
	public org.openuss.paperSubmission.PaperSubmissionInfo updatePaperSubmission(
			org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo, boolean changeDeliverDate);

	/**
	 * <p>
	 * shows the corresponding exams for an existing domain-object.
	 * </p>
	 * <p>
	 * 
	 * @param domainId
	 *            is the domain id
	 *            </p>
	 *            <p>
	 * @return a List of ExamInfo-Objects
	 *         </p>
	 */
	public List findExamsByDomainId(Long domainId);

	/**
	 * <p>
	 * finds all the ACTIVE exams in the demanded domain-object.
	 * </p>
	 * <p>
	 * 
	 * @param domainId
	 *            is the domain id
	 *            </p>
	 *            <p>
	 * @return a List of ExamInfo-Objects
	 *         </p>
	 */
	public List findActiveExamsByDomainId(Long domainId);

	/**
	 * <p>
	 * finds all the INACTIVE exams in the demanded domain-object.
	 * </p>
	 * <p>
	 * 
	 * @param domainId
	 *            is the domain id
	 *            </p>
	 *            <p>
	 * @return a List of ExamInfo-Objects
	 *         </p>
	 */
	public List findInactiveExamsByDomainId(Long domainId);

	/**
	 * <p>
	 * findPaperSubmissionsByExam finds all PaperSubmissions where the
	 * corresponding exam is
	 * </p>
	 * <p>
	 * 
	 * @param examId
	 *            is the exam큦 id which is searching.
	 *            </p>
	 *            <p>
	 * @result List of PaperSubmissionInfo-Objects in the Exam
	 *         </p>
	 */
	public List findPaperSubmissionsByExam(Long examId);

	/**
	 * <p>
	 * findPaperSubmissionsByExam finds all in time PaperSubmissions where the
	 * corresponding exam is
	 * </p>
	 * <p>
	 * 
	 * @param examId
	 *            is the exam큦 id which is searching.
	 *            </p>
	 *            <p>
	 * @result List of in time PaperSubmissionInfo-Objects in the Exam
	 *         </p>
	 */
	public List findInTimePaperSubmissionsByExam(Long examId);

	/**
	 * <p>
	 * getExam gets the demanded exam with its corresponding file attachments
	 * </p>
	 * <p>
	 * 
	 * @param examId
	 *            is the exam큦 id
	 *            </p>
	 *            <p>
	 * @result the ValueObject of the exam
	 *         </p>
	 */
	public org.openuss.paperSubmission.ExamInfo getExam(Long examId);

	/**
	 * <p>
	 * getPaperSubmission gets the demanded PaperSubmission
	 * </p>
	 * <p>
	 * 
	 * @param paperSubmissionId
	 *            is the paperSubmision큦 id
	 *            </p>
	 *            <p>
	 * @return the demanded PaperSubmissionInfo-Object
	 *         </p>
	 */
	public org.openuss.paperSubmission.PaperSubmissionInfo getPaperSubmission(Long paperSubmissionId);

	/**
	 * <p>
	 * finds the paperSubmissions of a special Exam and User.
	 * </p>
	 * <p>
	 * 
	 * @param examId
	 *            is the exams id
	 *            </p>
	 *            <p>
	 * @param userId
	 *            is the users id
	 *            </p>
	 *            <p>
	 * @return a List of PaperSubmissionInfo-Objects
	 *         </p>
	 */
	public List findPaperSubmissionsByExamAndUser(Long examId, Long userId);

	/**
	 * <p>
	 * getPaperSubmissionFiles gets a list of all the files of the collection of
	 * PaperSubmissionInfos
	 * </p>
	 * <p>
	 * 
	 * @param collection
	 *            of PaperSubmissionInfos, which files shall be returned
	 *            </p>
	 *            <p>
	 * @return a list of the files of the demanded PaperSubmissionInfos
	 *         </p>
	 */
	public List getPaperSubmissionFiles(Collection submissions);

	/**
	 * <p>
	 * Creates a list of all submissions of an exam containing submissions of
	 * every member of the course. A member can have 2 submissions, one in time
	 * and the other not in time. For members, who don't submit a paper, a
	 * PaperSubmissionInfo object is created virtually and appended to the
	 * submissions list.
	 * </p>
	 */
	public List getMembersAsPaperSubmissionsByExam(Long examId);

}
