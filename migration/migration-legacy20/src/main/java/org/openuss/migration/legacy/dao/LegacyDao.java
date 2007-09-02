package org.openuss.migration.legacy.dao;

import java.util.List;

import org.hibernate.ScrollableResults;
import org.openuss.migration.legacy.domain.Assistant2;
import org.openuss.migration.legacy.domain.Faculty2;
import org.openuss.migration.legacy.domain.Filebase2;
import org.openuss.migration.legacy.domain.Student2;

/**
 * @author Ingo Dueppe
 */
public interface LegacyDao {

	public abstract Assistant2 loadAssistant(String id);

	public abstract ScrollableResults loadAllAssistants();
	
	public abstract Student2 loadStudent(String id);
	
	public abstract ScrollableResults loadAllStudents();
	
	public abstract Faculty2 loadFaculty(String id);
	
	public abstract List<Faculty2> loadAllInstitutes();

	public abstract ScrollableResults loadAllAssistantEnrollments();

	public abstract ScrollableResults loadAllAssistantFaculty();

	public abstract ScrollableResults loadAllStudentEnrollments();

	public abstract ScrollableResults loadAllStudentFaculty();

	public abstract ScrollableResults loadAllStudentSubject();

	public abstract ScrollableResults loadAllFacultyInformation();

	public abstract Filebase2 loadFileBase(String fileid);

	public abstract ScrollableResults loadAllEnrollmentInformation();

	public abstract ScrollableResults loadAllLectures();

	public abstract ScrollableResults loadAllEnrollmentAccessList();
	
	public abstract ScrollableResults loadAllMailingListSubscribers();
	
	public abstract ScrollableResults loadAllMailingListMessages();

	public abstract byte[] loadLectureFileData(String id);

}