package org.openuss.migration.legacy.dao;

import java.util.Collection;

import org.openuss.migration.legacy.domain.Assistant2;
import org.openuss.migration.legacy.domain.Faculty2;
import org.openuss.migration.legacy.domain.Student2;

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
	public Collection<Assistant2> loadAllAssistants() {
		return (Collection<Assistant2>) this.getHibernateTemplate().loadAll(Assistant2.class);
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
	public Collection<Student2> loadAllStudents() {
//		query.setReadOnly(true);
//		query.setFetchSize(4);
//		ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
//		return results;
		return (Collection<Student2>) this.getHibernateTemplate().loadAll(Student2.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Faculty2> loadAllFaculties() {
		return (Collection<Faculty2>) this.getHibernateTemplate().loadAll(Faculty2.class);
	}

	
	/**
	 * {@inheritDoc}
	 */
	public Faculty2 loadFaculty(String id) {
		return (Faculty2) this.getHibernateTemplate().load(Faculty2.class, id);
	}

}
