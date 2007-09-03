package org.openuss.migration.from20to30;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Institute;
import org.openuss.migration.legacy.domain.Assistantenrollment2;
import org.openuss.migration.legacy.domain.Assistantfaculty2;
import org.openuss.migration.legacy.domain.Studentenrollment2;
import org.openuss.migration.legacy.domain.Studentfaculty2;
import org.openuss.migration.legacy.domain.Studentsubject2;
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

	/** Map user to desktop */
	private Map<Long, Desktop> user2desktop = new HashMap<Long, Desktop>(36000);

	/** List of desktop objects */
	private Set<Desktop> desktops = new HashSet<Desktop>();

	public void perform() {
		logger.info("parsing desktops...");
		parseDesktops();
		logger.debug("persisting user desktops");
		desktopDao.create(desktops);

		ImportUtil.refresh(user2desktop);

		logger.info("parsing desktop links...");
		importLinks();
		logger.info("updating user desktops");
		desktopDao.update(desktops);

		logger.debug("clearing data");
		user2desktop = null;
		desktops = null;
	}

	private void parseDesktops() {
		logger.info("creating desktops for users");
		for (Long userId : userImport.loadAllNewUserIds()) {
			Desktop desktop = Desktop.Factory.newInstance();
			User user = userImport.loadUser(userId);
			desktop.setUser(user);
			user2desktop.put(userId, desktop);
			desktops.add(desktop);
		}
	}

	private void importLinks() {
		logger.debug("creating assistant desktop links for courses...");
		ScrollableResults results = legacyDao.loadAllAssistantEnrollments();
		while (results.next()) {
			Assistantenrollment2 assistEnrollment = (Assistantenrollment2) results.get()[0];
			Desktop desktop = user2desktop.get(assistEnrollment.getAssistant().getId());
			if (desktop != null) {
				Course course = lectureImport.getCourseByLegacyId(assistEnrollment.getEnrollment().getId());
				desktop.getCourses().add(course);
			}
		}
		results.close();
		logger.debug("creating assistant desktop links for faculties...");
		results = legacyDao.loadAllAssistantFaculty();
		while (results.next()) {
			Assistantfaculty2 assistFaculty = (Assistantfaculty2) results.get()[0];
			Desktop desktop = user2desktop.get(assistFaculty.getAssistant().getId());
			if (desktop != null) {
				Institute institute = lectureImport.getInstituteByLegacyId(assistFaculty.getFaculty().getId());
				desktop.getInstitutes().add(institute);
			}
		}
		results.close();
		logger.debug("creating student desktop links for courses...");
		results = legacyDao.loadAllStudentEnrollments();
		while (results.next()) {
			Studentenrollment2 studentEnrollment = (Studentenrollment2) results.get()[0];
			Desktop desktop = user2desktop.get(studentEnrollment.getStudent().getId());
			if (desktop != null) {
				Course course = lectureImport.getCourseByLegacyId(studentEnrollment.getEnrollment().getId());
				desktop.getCourses().add(course);
			}
		}
		results.close();
		logger.debug("creating student desktop links for subjects...");
		results = legacyDao.loadAllStudentSubject();
		while (results.next()) {
			Studentsubject2 studentSubject = (Studentsubject2) results.get()[0];
			Desktop desktop = user2desktop.get(studentSubject.getStudent().getId());
			if (desktop != null) {
				CourseType courseType = lectureImport.getCourseTypeByLegacyId(studentSubject.getSubject().getId());
				desktop.getCourseTypes().add(courseType);
			}
		}
		results.close();
		logger.debug("creating student desktop links for faculties...");
		results = legacyDao.loadAllStudentFaculty();
		while (results.next()) {
			Studentfaculty2 studentFaculty = (Studentfaculty2) results.get()[0];
			Desktop desktop = user2desktop.get(studentFaculty.getStudent().getId());
			if (desktop != null) {
				Institute institute = lectureImport.getInstituteByLegacyId(studentFaculty.getFaculty().getId());
				desktop.getInstitutes().add(institute);
			}
		}
		results.close();

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
