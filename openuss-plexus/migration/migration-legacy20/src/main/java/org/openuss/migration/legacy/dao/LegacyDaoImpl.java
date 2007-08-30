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
 * 
 * @author Ingo Dueppe
 */
public class LegacyDaoImpl extends org.springframework.orm.hibernate3.support.HibernateDaoSupport implements LegacyDao {
	
	/**
	 * {@inheritDoc}
	 */
	public Assistant2 loadAssistant(String id) {
		return (Assistant2) this.getHibernateTemplate().get(Assistant2.class, id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Assistant2> loadAllAssistants() {
		return (List<Assistant2>) this.getHibernateTemplate().loadAll(Assistant2.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Student2 loadStudent(String id) {
		return (Student2) this.getHibernateTemplate().load(Student2.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Student2> loadAllStudents() {
		return (List<Student2>) this.getHibernateTemplate().loadAll(Student2.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Faculty2> loadAllInstitutes() {
		return (List<Faculty2>) this.getHibernateTemplate().loadAll(Faculty2.class);
	}

	
	/** {@inheritDoc} */
	public Faculty2 loadFaculty(String id) {
		return (Faculty2) this.getHibernateTemplate().load(Faculty2.class, id);
	}

	/** {@inheritDoc} */
	public List<Assistantenrollment2> loadAllAssistantEnrollments() {
		return (List<Assistantenrollment2>) this.getHibernateTemplate().loadAll(Assistantenrollment2.class);
	}

	/** {@inheritDoc} */
	public List<Assistantfaculty2> loadAllAssistantFaculty() {
		return (List<Assistantfaculty2>) this.getHibernateTemplate().loadAll(Assistantfaculty2.class);
	}

	/** {@inheritDoc} */
	public List<Studentenrollment2> loadAllStudentEnrollments() {
		return (List<Studentenrollment2>) this.getHibernateTemplate().loadAll(Studentenrollment2.class);
	}

	/** {@inheritDoc} */
	public List<Studentfaculty2> loadAllStudentFaculty() {
		return (List<Studentfaculty2>) this.getHibernateTemplate().loadAll(Studentfaculty2.class);
	}

	/** {@inheritDoc} */
	public List<Studentsubject2> loadAllStudentSubject() {
		return (List<Studentsubject2>) this.getHibernateTemplate().loadAll(Studentsubject2.class);
	}

	public List<Facultyinformation2> loadAllFacultyInformation() {
		return (List<Facultyinformation2>) this.getHibernateTemplate().find("from Facultyinformation2 order by faculty");
	}

	public Filebase2 loadFileBase(String id) {
		return (Filebase2) this.getHibernateTemplate().load(Filebase2.class, id);
	}

	public List<Enrollmentinformation2> loadAllEnrollmentInformation() {
		return (List<Enrollmentinformation2>) this.getHibernateTemplate().find("from Enrollmentinformation2 info order by enrollmentpk");
	}

	public List<Lecture2> loadAllLectures() {
		return (List<Lecture2>) this.getHibernateTemplate().loadAll(Lecture2.class);
	}

	public List<Enrollmentaccesslist2> loadAllEnrollmentAccessList() {
		return (List<Enrollmentaccesslist2>)this.getHibernateTemplate().find("from Enrollmentaccesslist2 order by enrollment ");
		
	}

}
