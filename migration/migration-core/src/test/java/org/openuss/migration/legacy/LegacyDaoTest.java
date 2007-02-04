/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.migration.legacy;

import org.apache.log4j.Logger;
import org.openuss.migration.legacy.dao.LegacyDao;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * JUnit Test for Spring Hibernate LegacyDao class.
 * 
 */
public class LegacyDaoTest extends AbstractTransactionalDataSourceSpringContextTests {

	protected static final Logger logger = Logger.getLogger(LegacyDaoTest.class);

	protected LegacyDao legacyDao;

	public void testAssistantDao() {
//		try {
//			Collection<Assistant2> assistant2s = legacyDao.loadAllAssistants();
//			for (Assistant2 assistant2 : assistant2s) {
//				logger.info("found assistant " + assistant2.getUusername() + " " + assistant2.getEmailaddress());
//				Set<Faculty2> faculty2s = assistant2.getFaculties();
//				for (Faculty2 faculty2 : faculty2s) {
//					logger.info("\t" + faculty2.getName());
//				}
//				break;
//			}
//		} catch (Exception all) {
//			// do nothing
//		}
	}

	public void testStudentDao() {
//		try {
//			Collection<Student2> student2s = legacyDao.loadAllStudents();
//			int count = 0;
//			for (Student2 student2 : student2s) {
//				count++;
//				logger.info("" + count + " student " + student2.getUusername() + " " + student2.getEmailaddress());
//				Set<Studentfaculty2> faculties = student2.getStudentfaculties();
//				for (Studentfaculty2 faculty : faculties) {
//					try {
//						logger.info("\t" + faculty.getFaculty().getName());
//					} catch (ObjectNotFoundException ex) {
//						logger.error("faculty " + faculty.getId() + " not found", ex);
//					}
//				}
//				break;
//			}
//		} catch (Exception all) {
//			// do nothing
//		}
	}

	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext-legacyTransactionManager.xml",
			"classpath*:applicationContext-legacy.xml" };
	}

	public LegacyDao getLegacyDao() {
		return legacyDao;
	}

	public void setLegacyDao(LegacyDao legacyDao) {
		this.legacyDao = legacyDao;
	}
}