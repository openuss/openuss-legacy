package org.openuss.aop;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.UniversityInfo;

public class BookmarkMaintenanceAspectImpl {

	private static final Logger logger = Logger.getLogger(UniversityCreationAndDeletionAspect.class);
	private DesktopService2 desktopService;

	public DesktopService2 getDesktopService() {
		return desktopService;
	}

	public void setDesktopService(DesktopService2 desktopService) {
		this.desktopService = desktopService;
	}

	/**
	 * Create bookmark of given university for the creating user.
	 * 
	 * @param universityInfo,
	 *            userId
	 */
	public void bookmarkUniversity(UniversityInfo universityInfo, Long userId) {
		logger.debug("----------> BEGIN method bookmarkUniversity <----------");

		Validate.notNull(universityInfo,
				"UniversityCreationAndDeletionAspect.bookmarkUniversity - the universityInfo cannot be null.");
		Validate.notNull(userId, "UniversityCreationAndDeletionAspect.bookmarkUniversity - the userId cannot be null.");

		try {
			// Get DesktopInfo
			DesktopInfo desktopInfo = desktopService.findDesktopByUser(userId);

			// Link University
			desktopService.linkUniversity(desktopInfo.getId(), universityInfo.getId());
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method bookmarkUniversity <----------");
	}

	/**
	 * Delete bookmarks of the given university from all users.
	 * 
	 * @param universityId
	 */
	public void deleteBookmarksOfUniversity(Long universityId) {
		logger.debug("----------> BEGIN method deleteBookmarksOfUniversity <----------");

		Validate.notNull(universityId,
				"UniversityCreationAndDeletionAspect.deleteBookmarksOfUniversity - the universityId cannot be null.");

		try {
			desktopService.unlinkAllFromUniversity(universityId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method deleteBookmarksOfUniversity <----------");
	}

	public void bookmarkDepartment(DepartmentInfo departmentInfo, Long userId) {
		logger.debug("----------> BEGIN method bookmarkDepartment <----------");

		Validate.notNull(departmentInfo,
				"DepartmentCreationAndDeletionAspect.bookmarkDepartment - the departmentInfo cannot be null.");
		Validate.notNull(userId, "DepartmentCreationAndDeletionAspect.bookmarkDepartment - the userId cannot be null.");

		try {
			// Get DesktopInfo
			DesktopInfo desktopInfo = desktopService.findDesktopByUser(userId);

			// Link Department
			desktopService.linkDepartment(desktopInfo.getId(), departmentInfo.getId());
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method bookmarkDepartment <----------");
	}

	/**
	 * Delete bookmarks of the given department from all users.
	 * 
	 * @param departmentId
	 */
	public void deleteBookmarksOfDepartment(Long departmentId) {
		logger.debug("----------> BEGIN method deleteBookmarksOfDepartment <----------");

		Validate.notNull(departmentId,
				"DepartmentCreationAndDeletionAspect.deleteBookmarksOfDepartment - the departmentId cannot be null.");

		try {
			desktopService.unlinkAllFromDepartment(departmentId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method deleteBookmarksOfDepartment <----------");
	}

	/**
	 * Create bookmark of given institute for the creating user.
	 * 
	 * @param instituteInfo,
	 *            userId
	 */
	public void bookmarkInstitute(InstituteInfo instituteInfo, Long userId) {
		logger.debug("----------> BEGIN method bookmarkInstitute <----------");

		Validate.notNull(instituteInfo,
				"InstituteCreationAndDeletionAspect.bookmarkInstitute - the instituteInfo cannot be null.");
		Validate.notNull(userId, "InstituteCreationAndDeletionAspect.bookmarkInstitute - the userId cannot be null.");

		try {
			// Get DesktopInfo
			DesktopInfo desktopInfo = desktopService.findDesktopByUser(userId);

			// Link Institute
			desktopService.linkInstitute(desktopInfo.getId(), instituteInfo.getId());
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method bookmarkInstitute <----------");
	}

	/**
	 * Delete bookmarks of the given institute from all users.
	 * 
	 * @param instituteId
	 */
	public void deleteBookmarksOfInstitute(Long instituteId) {
		logger.debug("----------> BEGIN method deleteBookmarksOfInstitute <----------");

		Validate.notNull(instituteId,
				"InstituteCreationAndDeletionAspect.deleteBookmarksOfInstitute - the instituteId cannot be null.");

		try {
			desktopService.unlinkAllFromInstitute(instituteId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method deleteBookmarksOfInstitute <----------");
	}

	public void deleteBookmarksOfCourse(Long courseId) {
		logger.debug("----------> BEGIN method deleteBookmarksOfCourses <----------");

		Validate.notNull(courseId, "CourseCreationAspect.bookmarkCourse - the userId cannot be null.");

		try {
			// Unlink all
			desktopService.unlinkAllFromCourse(courseId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method deleteBookmarksOfCourses <----------");
	}
}
