package org.openuss.migration.from20to30;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Institute;
import org.openuss.migration.legacy.dao.LegacyDao;
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
public class DesktopImport {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(DesktopImport.class);

	/** UserImport */
	private UserImport userImport;
	
	/** LectureImport */
	private LectureImport lectureImport;

	/** DesktopDao */
	private DesktopDao desktopDao;
	
	/** LegacyDao */
	private LegacyDao legacyDao;

	/** Map user to desktop */
	private Map<User, Desktop> user2desktop = new HashMap<User, Desktop>(36000);

	/** List of desktop objects */
	private Set<Desktop> desktops = new HashSet<Desktop>();

	public UserImport getUserImport() {
		return userImport;
	}

	public void importDesktops(){
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
		user2desktop.clear();
		desktops.clear();
	}
	
	private void parseDesktops() {
		logger.info("creating desktops for users");
		for (User user: userImport.getImportedUsers()) {
			Desktop desktop = Desktop.Factory.newInstance();
			desktop.setUser(user);
			user2desktop.put(user, desktop);
			desktops.add(desktop);
		}
	}
	
	private void importLinks() {
		logger.debug("creating assistant desktop links for courses...");
		for (Assistantenrollment2 assistEnrollment: legacyDao.loadAllAssistantEnrollments() ) {
			Desktop desktop = user2desktop.get(userImport.getUserByLegacyId(assistEnrollment.getAssistant().getId()));
			if (desktop != null) {
				Course course = lectureImport.getCourseById(assistEnrollment.getEnrollment().getId());
				desktop.getCourses().add(course);
			}
		}
		logger.debug("creating assistant desktop links for faculties...");
		for (Assistantfaculty2 assistFaculty: legacyDao.loadAllAssistantFaculty() ) {
			Desktop desktop = user2desktop.get(userImport.getUserByLegacyId(assistFaculty.getAssistant().getId()));
			if (desktop != null) {
				Institute institute = lectureImport.getInstituteById(assistFaculty.getFaculty().getId());
				desktop.getInstitutes().add(institute);
			}
		}
		logger.debug("creating student desktop links for courses...");
		for (Studentenrollment2 studentEnrollment: legacyDao.loadAllStudentEnrollments() ) {
			Desktop desktop = user2desktop.get(userImport.getUserByLegacyId(studentEnrollment.getStudent().getId()));
			if (desktop != null) {
				Course course = lectureImport.getCourseById(studentEnrollment.getEnrollment().getId());
				desktop.getCourses().add(course);
			}
		}
		logger.debug("creating student desktop links for subjects...");
		for (Studentsubject2 studentSubject: legacyDao.loadAllStudentSubject() ) {
			Desktop desktop = user2desktop.get(userImport.getUserByLegacyId(studentSubject.getStudent().getId()));
			if (desktop != null) {
				CourseType courseType = lectureImport.getCourseTypeById(studentSubject.getSubject().getId());
				desktop.getCourseTypes().add(courseType);
			}
		}
		logger.debug("creating student desktop links for faculties...");
		for (Studentfaculty2 studentFaculty: legacyDao.loadAllStudentFaculty() ) {
			Desktop desktop = user2desktop.get(userImport.getUserByLegacyId(studentFaculty.getStudent().getId()));
			if (desktop != null) {
				Institute institute = lectureImport.getInstituteById(studentFaculty.getFaculty().getId());
				desktop.getInstitutes().add(institute);
			}
		}
		
	}

	public void setUserImport(UserImport userImport) {
		this.userImport = userImport;
	}

	public DesktopDao getDesktopDao() {
		return desktopDao;
	}

	public void setDesktopDao(DesktopDao desktopDao) {
		this.desktopDao = desktopDao;
	}

	public LectureImport getLectureImport() {
		return lectureImport;
	}

	public void setLectureImport(LectureImport lectureImport) {
		this.lectureImport = lectureImport;
	}

	public LegacyDao getLegacyDao() {
		return legacyDao;
	}

	public void setLegacyDao(LegacyDao legacyDao) {
		this.legacyDao = legacyDao;
	}

}
