package org.openuss.migration.from20to30;

import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
import org.openuss.lecture.Course;
import org.openuss.lecture.Institute;
import org.openuss.migration.legacy.domain.Assistantenrollment2;
import org.openuss.migration.legacy.domain.Assistantfaculty2;
import org.openuss.migration.legacy.domain.Studentenrollment2;
import org.openuss.migration.legacy.domain.Studentfaculty2;
import org.openuss.security.User;

/**
 * This Service migrate data from openuss 2.0 to openuss-plexus 3.0
 * 
 * @author Ingo Dueppe
 * 
 */
public class DesktopImport extends DefaultImport {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(DesktopImport.class);

	/** UserImport */
	private UserImport userImport;

	/** LectureImport */
	private LectureImport lectureImport;

	/** DesktopDao */
	private DesktopDao desktopDao;

	public void createDesktops() {
		logger.info("creating desktops for users");
		for (Long userId : userImport.loadAllNewUserIds()) {
			Desktop desktop = Desktop.Factory.newInstance();
			User user = userImport.loadUser(userId);
			desktop.setUser(user);
			desktopDao.create(desktop);
		}
	}
	
	public void importLinks() {
		logger.info("parsing desktop links...");
		logger.debug("creating assistant desktop links for courses...");
		ScrollableResults results = legacyDao.loadAllAssistantEnrollments();
		while (results.next()) {
			Assistantenrollment2 assistEnrollment = (Assistantenrollment2) results.get()[0];
			Desktop desktop = loadDesktop(assistEnrollment.getAssistant().getId());
			if (desktop != null) {
				Course course = lectureImport.getCourseByLegacyId(assistEnrollment.getEnrollment().getId());
				desktop.getCourses().add(course);
			} else {
				logger.debug("Skip link "+assistEnrollment.getId());
			}
		}
		results.close();
		logger.debug("creating assistant desktop links for faculties...");
		results = legacyDao.loadAllAssistantFaculty();
		while (results.next()) {
			Assistantfaculty2 assistFaculty = (Assistantfaculty2) results.get()[0];
			Desktop desktop = loadDesktop(assistFaculty.getAssistant().getId());
			if (desktop != null) {
				Institute institute = lectureImport.getInstituteByLegacyId(assistFaculty.getFaculty().getId());
				desktop.getInstitutes().add(institute);
			} else {
				logger.debug("Skip link "+assistFaculty.getId());
			}
		}
		results.close();
		logger.debug("creating student desktop links for courses...");
		results = legacyDao.loadAllStudentEnrollments();
		while (results.next()) {
			Studentenrollment2 studentEnrollment = (Studentenrollment2) results.get()[0];
			Desktop desktop = loadDesktop(studentEnrollment.getStudent().getId());
			if (desktop != null) {
				Course course = lectureImport.getCourseByLegacyId(studentEnrollment.getEnrollment().getId());
				desktop.getCourses().add(course);
			} else {
				logger.debug("Skip link "+studentEnrollment.getId());
			}
		}
		results.close();
		logger.debug("creating student desktop links for faculties...");
		results = legacyDao.loadAllStudentFaculty();
		while (results.next()) {
			Studentfaculty2 studentFaculty = (Studentfaculty2) results.get()[0];
			Desktop desktop = loadDesktop(studentFaculty.getStudent().getId());
			if (desktop != null) {
				Institute institute = lectureImport.getInstituteByLegacyId(studentFaculty.getFaculty().getId());
				desktop.getInstitutes().add(institute);
			} else {
				logger.debug("Skip link "+studentFaculty.getId());
			}
		}
		results.close();
	}


	private Desktop loadDesktop(String legacyId) {
		User user = userImport.loadUserByLegacyId(legacyId);
		Desktop desktop = desktopDao.findByUser(user);
		return desktop;
	}

	public void setUserImport(UserImport userImport) {
		this.userImport = userImport;
	}

	public void setDesktopDao(DesktopDao desktopDao) {
		this.desktopDao = desktopDao;
	}

	public void setLectureImport(LectureImport lectureImport) {
		this.lectureImport = lectureImport;
	}

}
