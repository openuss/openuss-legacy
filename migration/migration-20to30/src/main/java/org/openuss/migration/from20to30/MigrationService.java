package org.openuss.migration.from20to30;

import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Migration Service to migrate openuss 2.0 legacy data to openuss 3.0 data.
 * 
 * @author Ingo Dueppe
 *
 */
public class MigrationService {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(MigrationService.class);

	/** Hibernate SessionFactory for legacy database */
	private SessionFactory legacySessionFactory;
	
	/** User data import */
	private UserImport userImport;
	
	/** Lecture data import */
	private LectureImport lectureImport;
	
	/** Desktop data import */
	private DesktopImport desktopImport;
	
	/** News data import */
	private NewsImport newsImport;
	
	/** Course documents import */
	private DocumentImport documentImport;
	
	/** Course membership import */
	private CourseMemberImport courseMemberImport;
	
	/** NewsLetterImport */
	private NewsLetterImport newsLetterImport;
	
	/** QuizImport */
	private QuizImport quizImport;

	/** Legacy hibernate session */
	private Session legacySession;

	private Transaction legacyTx;
	
	/** Workaround to shutdown */
	private Scheduler scheduler;

	public void importData() {
		logger.info("initialize databses");

		legacySession = openAndBindNewSession(legacySessionFactory);
//		userImport.perform();
//		lectureImport.perform();
		newsImport.perform(); 
		desktopImport.perform();
		courseMemberImport.perform();
		documentImport.perform();
		newsLetterImport.perform();
		quizImport.perform();

		legacyTx.rollback();
		legacySession.close();
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			logger.error(e);
		}
	}

	private Session openAndBindNewSession(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		session.setCacheMode(CacheMode.IGNORE);
		legacyTx = session.beginTransaction();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		return session;
	}
	
	public void setLegacySessionFactory(SessionFactory legacySessionFactory) {
		this.legacySessionFactory = legacySessionFactory;
	}

	public void setUserImport(UserImport userImport) {
		this.userImport = userImport;
	}

	public void setLectureImport(LectureImport lectureImport) {
		this.lectureImport = lectureImport;
	}

	public void setDesktopImport(DesktopImport desktopImport) {
		this.desktopImport = desktopImport;
	}

	public void setNewsImport(NewsImport newsImport) {
		this.newsImport = newsImport;
	}

	public void setDocumentImport(DocumentImport documentImport) {
		this.documentImport = documentImport;
	}

	public void setCourseMemberImport(CourseMemberImport courseMemberImport) {
		this.courseMemberImport = courseMemberImport;
	}

	public void setQuizImport(QuizImport quizImport) {
		this.quizImport = quizImport;
	}

	public void setNewsLetterImport(NewsLetterImport newsLetterImport) {
		this.newsLetterImport = newsLetterImport;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

}
