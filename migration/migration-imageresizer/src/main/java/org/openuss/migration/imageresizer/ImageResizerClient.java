package org.openuss.migration.imageresizer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.openuss.documents.FileEntry;
import org.openuss.documents.FileEntryDao;
import org.openuss.framework.utilities.ImageUtils;
import org.openuss.repository.RepositoryFile;
import org.openuss.repository.RepositoryFileDao;
import org.openuss.security.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Client to resize all user profile images to fit into 100x100 pixel.
 * 
 * @author Ingo Dueppe
 * 
 */
public class ImageResizerClient {

	private static final Logger logger = Logger.getLogger(ImageResizerClient.class);

	public static void main(String[] args) {
		logger.info("Initializing application context");

		ApplicationContext context = new ClassPathXmlApplicationContext(getConfigLocations());

		ScrollableUserDao dao = (ScrollableUserDao) context.getBean("scrollableUserDao");

		FileEntryDao fileDao = (FileEntryDao) context.getBean("fileEntryDao");
		RepositoryFileDao repositoryDao = (RepositoryFileDao) context.getBean("repositoryFileDao");

		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		Session session = sessionFactory.openSession();
		session.setCacheMode(CacheMode.IGNORE);
		Transaction transaction = session.beginTransaction();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		ScrollableResults results = dao.loadAllUsers();

		User user = null;
		while (results.next()) {
			// remove last student from session
			if (user != null) {
				dao.evict(user);
			}
			user = (User) results.get()[0];

			Long imageId = user.getImageId();
			try {
				if (imageId != null) {
					FileEntry fileEntry = fileDao.load(imageId);
					RepositoryFile repositoryFile = repositoryDao.load(imageId);

					logger.info("Resize image of " + user.getUsername());

					InputStream is = repositoryFile.getInputStream();
					if (is != null) {
						byte[] bytes = ImageUtils.resizeImageToByteArray(repositoryFile.getInputStream(),
								ImageUtils.IMAGE_UNKNOWN, 100, 100);

						logger.info(" - " + bytes.length);
						fileEntry.setFileSize(bytes.length);

						repositoryFile.setInputStream(new ByteArrayInputStream(bytes));

						repositoryDao.update(repositoryFile);
						fileDao.update(fileEntry);
					}
				}
			} catch (Exception ex) {
				logger.error("User " + user.getUsername() + " (" + user.getId() + ") Image produced an error");
				ex.printStackTrace();
			}
		}
		results.close();
		transaction.commit();
		session.close();
	}

	protected static String[] getConfigLocations() {
		return new String[] { "classpath*:applicationContext.xml", "classpath*:applicationContext-beans.xml",
				"classpath*:applicationContext-lucene.xml", "classpath*:applicationContext-cache.xml",
				"classpath*:applicationContext-messaging.xml", "classpath*:applicationContext-resources.xml",
				// "classpath*:applicationContext-aop.xml",
				"classpath*:applicationContext-events.xml", "classpath*:applicationContext-resizing.xml",
				"classpath*:dataSource.xml" };

	}

}
