package org.openuss.migration.legacy.dao;

import java.util.List;

import org.openuss.migration.legacy.domain.Assistant2;
import org.openuss.migration.legacy.domain.Assistantenrollment2;
import org.openuss.migration.legacy.domain.Assistantfaculty2;
import org.openuss.migration.legacy.domain.Enrollmentaccesslist2;
import org.openuss.migration.legacy.domain.Enrollmentinformation2;
import org.openuss.migration.legacy.domain.Faculty2;
import org.openuss.migration.legacy.domain.Facultyinformation2;
import org.openuss.migration.legacy.domain.Filebase2;
import org.openuss.migration.legacy.domain.Lecture2;
import org.openuss.migration.legacy.domain.Student2;
import org.openuss.migration.legacy.domain.Studentenrollment2;
import org.openuss.migration.legacy.domain.Studentfaculty2;
import org.openuss.migration.legacy.domain.Studentsubject2;

/**
 * @author Ingo Dueppe
 */
public interface LegacyDao {

	public abstract Assistant2 loadAssistant(String id);

	public abstract List<Assistant2> loadAllAssistants();
	
	public abstract Student2 loadStudent(String id);
	
	public abstract List<Student2> loadAllStudents();
	
	public abstract Faculty2 loadFaculty(String id);
	
	public abstract List<Faculty2> loadAllInstitutes();

	public abstract List<Assistantenrollment2> loadAllAssistantEnrollments();

	public abstract List<Assistantfaculty2> loadAllAssistantFaculty();

	public abstract List<Studentenrollment2> loadAllStudentEnrollments();

	public abstract List<Studentfaculty2> loadAllStudentFaculty();

	public abstract List<Studentsubject2> loadAllStudentSubject();

	public abstract List<Facultyinformation2> loadAllFacultyInformation();

	public abstract Filebase2 loadFileBase(String fileid);

	public abstract List<Enrollmentinformation2> loadAllEnrollmentInformation();

	public abstract List<Lecture2> loadAllLectures();

	public abstract List<Enrollmentaccesslist2> loadAllEnrollmentAccessList();

}