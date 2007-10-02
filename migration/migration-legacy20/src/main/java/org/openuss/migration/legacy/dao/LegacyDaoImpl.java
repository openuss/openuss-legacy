package org.openuss.migration.legacy.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.openuss.business.extension.lecture.file.filedescription.lecturefilebase.LectureFileObject;
import org.openuss.migration.legacy.domain.Assistant2;
import org.openuss.migration.legacy.domain.Faculty2;
import org.openuss.migration.legacy.domain.Filebase2;
import org.openuss.migration.legacy.domain.Lecturefilebase2;
import org.openuss.migration.legacy.domain.Quizfile2;
import org.openuss.migration.legacy.domain.Student2;
import org.openuss.utility.FileObjectWrapper;
import org.springframework.orm.hibernate3.HibernateCallback;

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
	public ScrollableResults loadAllAssistants() {
		return query("from Assistant2");
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
	public ScrollableResults loadAllStudents() {
		return query("from Student2");
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
	public ScrollableResults loadAllAssistantEnrollments() {
		return query("from Assistantenrollment2");
	}

	/** {@inheritDoc} */
	public ScrollableResults loadAllAssistantFaculty() {
		return query("from Assistantfaculty2");
	}

	/** {@inheritDoc} */
	public ScrollableResults loadAllStudentEnrollments() {
		return query("from Studentenrollment2");
	}

	/** {@inheritDoc} */
	public ScrollableResults loadAllStudentFaculty() {
		return query("from Studentfaculty2");
	}

	/** {@inheritDoc} */
	public ScrollableResults loadAllStudentSubject() {
		return query("from Studentsubject2");
	}

	public ScrollableResults loadAllFacultyInformation() {
		return query("from Facultyinformation2 order by faculty");
	}

	public Filebase2 loadFileBase(String id) {
		return (Filebase2) this.getHibernateTemplate().load(Filebase2.class, id);
	}

	public ScrollableResults loadAllEnrollmentInformation() {
		return query("from Enrollmentinformation2 info order by enrollmentpk");
	}

	public ScrollableResults loadAllLectures() {
		return query ("from Lecture2 ");
	}

	public ScrollableResults loadAllEnrollmentAccessList() {
		return query("from Enrollmentaccesslist2 order by enrollment");
	}
	

	public byte[] loadLectureFileData(String id) {
		Lecturefilebase2 base = (Lecturefilebase2) this.getHibernateTemplate().load(Lecturefilebase2.class, id);
		if (base != null) {
			try {
				ByteArrayInputStream bis = new ByteArrayInputStream(base.getBasefile());
				ObjectInputStream ois = new ObjectInputStream(bis);
				LectureFileObject fileObject = (LectureFileObject) ois.readObject();
				ois.close();
				bis.close();
				byte[] data = fileObject.getData();
				getSession().evict(base);
				return data;
			} catch (ClassNotFoundException e) {
				logger.error(e);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return null;
	}

	public ScrollableResults loadAllMailingListSubscribers() {
		return query("from Mailinglist2 order by enrollmentpk");
	}

	public ScrollableResults loadAllMailingListMessages() {
		return query("from Mailinglistmessage2 order by enrollmentPk");
	}

	private ScrollableResults query (final String hql) {
		return (ScrollableResults) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFetchSize(50);
				query.setCacheable(false);
				query.setFlushMode(FlushMode.MANUAL);
				return query.scroll(ScrollMode.FORWARD_ONLY);
			}
		});
	}

	public ScrollableResults loadAllQuiz() {
		return query("from Quiz2 order by enrollmentpk");
	}

	public byte[] loadQuizFileData(String id) {
		Quizfile2 base = (Quizfile2) this.getHibernateTemplate().load(Quizfile2.class, id);
		if (base != null) {
			try {
				ByteArrayInputStream bis = new ByteArrayInputStream(base.getBasefile());
				ObjectInputStream ois = new ObjectInputStream(bis);
				FileObjectWrapper fileObject = (FileObjectWrapper) ois.readObject();
				ois.close();
				bis.close();
				byte[] data = fileObject.getData();
				getSession().evict(base);
				return data;
			} catch (ClassNotFoundException e) {
				logger.error(e);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return null;
	}

}
