package org.openuss.migration.legacy.dao;

import java.util.Collection;

import org.openuss.migration.legacy.domain.Assistant2;
import org.openuss.migration.legacy.domain.Faculty2;
import org.openuss.migration.legacy.domain.Student2;

/**
 * @author Ingo Dueppe
 */
public interface LegacyDao {

	public abstract Assistant2 loadAssistant(String id);

	public abstract Collection<Assistant2> loadAllAssistants();
	
	public abstract Student2 loadStudent(String id);
	
	public abstract Collection<Student2> loadAllStudents();
	
	public abstract Faculty2 loadFaculty(String id);
	
	public abstract Collection<Faculty2> loadAllInstitutes();

}