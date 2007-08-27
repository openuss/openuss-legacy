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
import org.openuss.migration.legacy.domain.Assistant2;
import org.openuss.migration.legacy.domain.Assistantenrollment2;
import org.openuss.migration.legacy.domain.Assistantfaculty2;
import org.openuss.migration.legacy.domain.Student2;
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

	/** Map legacy id to desktop */
	private Map<String, Desktop> id2desktop = new HashMap<String, Desktop>(36000);

	/** List of desktop objects */
	private Set<Desktop> desktops = new HashSet<Desktop>();

	public UserImport getUserImport() {
		return userImport;
	}

	public void importDesktops(){
		logger.info("parsing desktops...");
		parseDesktops();
		logger.info("parsing desktop links...");
		importLinksForAssistants();
		importLinksForStudents();
		logger.info("saving desktop objects...");
		desktopDao.create(desktops);
	}
	
	private void parseDesktops() {
		logger.info("creating desktops for users");
		for (User user: userImport.getImportedUsers()) {
			Desktop desktop = Desktop.Factory.newInstance();
			desktop.setUser(user);
			id2desktop.put(userImport.legacyIdOfUser(user), desktop);
		}
		logger.debug("persisting user desktops");
		getDesktopDao().create(desktops);
	}
	
	private void importLinksForAssistants() {
		logger.debug("creating assistant desktop links for courses and institutes");
		for (Assistant2 assistant: userImport.getAssistants2()) {
			Desktop desktop = id2desktop.get(assistant.getId());
			if (desktop != null) {
				logger.trace("creating desktop links for "+assistant.getUusername());
				for (Assistantenrollment2 assistEnrollment: assistant.getAssistantenrollments()) {
					Course course = lectureImport.getCourseById(assistEnrollment.getEnrollment().getId());
					if (course != null) {
						desktop.getCourses().add(course);
					}
				}
				for (Assistantfaculty2 facultyLink: assistant.getAssistantinstitutes()) {
					Institute institute = lectureImport.getInstituteById(facultyLink.getFaculty().getId());
					if (institute != null) {
						desktop.getInstitutes().add(institute);
					}
				}
			} else {
				logger.trace("desktop for user "+assistant.getUusername()+" not found!");
			}
		}
	}
	
	private void importLinksForStudents() {
		logger.debug("creating students desktop links for institutes, coursetypes and courses");
		
		for (Student2 student : userImport.getStudents2()) {
			Desktop desktop = id2desktop.get(student.getId());
			if (desktop != null) {
				logger.trace("creating desktop links for "+student.getUusername());
				for (Studentfaculty2 facultyLink : student.getStudentinstitutes()) {
					Institute institute = lectureImport.getInstituteById(facultyLink.getFaculty().getId());
					if (institute != null) {
						desktop.getInstitutes().add(institute);
					} 
				}
				for (Studentsubject2 subjectLink : student.getStudentsubjects()) {
					CourseType courseType = lectureImport.getCourseTypeById(subjectLink.getSubject().getId());
					if (courseType != null) {
						desktop.getCourseTypes().add(courseType);
					}
				}
				
				for (Studentenrollment2 enrollmentLink : student.getStudentenrollments()) {
					Course course = lectureImport.getCourseById(enrollmentLink.getEnrollment().getId());
					if (course != null) {
						desktop.getCourses().add(course);
					}
				}
				
			} else {
				logger.trace("desktop for user "+student.getUusername()+" not found!");
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

}
