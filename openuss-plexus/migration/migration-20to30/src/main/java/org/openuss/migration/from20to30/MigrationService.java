package org.openuss.migration.from20to30;

import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
	
	/** Hibernate SessionFactory for the new plexus database */
	private SessionFactory plexusSessionFactory;
	
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

	/** Legacy hibernate session */
	private Session legacySession;

	private Transaction legacyTx;

	public void importData() {
		logger.info("initialize databses");

		legacySession = openAndBindNewSession(legacySessionFactory);
//		userImport.perform();
//		lectureImport.perform();
//		newsImport.perform(); 
//		desktopImport.perform();
//		courseMemberImport.perform();
		documentImport.perform();
		
		legacyTx.rollback();
		legacySession.close();
		
	}

	private Session openAndBindNewSession(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		session.setCacheMode(CacheMode.IGNORE);
		legacyTx = session.beginTransaction();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		return session;
	}
	
	public SessionFactory getLegacySessionFactory() {
		return legacySessionFactory;
	}


	public void setLegacySessionFactory(SessionFactory legacySessionFactory) {
		this.legacySessionFactory = legacySessionFactory;
	}


	public SessionFactory getPlexusSessionFactory() {
		return plexusSessionFactory;
	}


	public void setPlexusSessionFactory(SessionFactory plexusSessionFactory) {
		this.plexusSessionFactory = plexusSessionFactory;
	}

	public UserImport getUserImport() {
		return userImport;
	}

	public void setUserImport(UserImport userImport) {
		this.userImport = userImport;
	}

	public LectureImport getLectureImport() {
		return lectureImport;
	}

	public void setLectureImport(LectureImport lectureImport) {
		this.lectureImport = lectureImport;
	}

	public DesktopImport getDesktopImport() {
		return desktopImport;
	}

	public void setDesktopImport(DesktopImport desktopImport) {
		this.desktopImport = desktopImport;
	}

	public NewsImport getNewsImport() {
		return newsImport;
	}

	public void setNewsImport(NewsImport newsImport) {
		this.newsImport = newsImport;
	}

	public DocumentImport getDocumentImport() {
		return documentImport;
	}

	public void setDocumentImport(DocumentImport documentImport) {
		this.documentImport = documentImport;
	}

	public CourseMemberImport getCourseMemberImport() {
		return courseMemberImport;
	}

	public void setCourseMemberImport(CourseMemberImport courseMemberImport) {
		this.courseMemberImport = courseMemberImport;
	}

}
