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

	private static final Logger logger = Logger.getLogger(BookmarkMaintenanceAspectImpl.class);
	private DesktopService2 desktopService2;

	public DesktopService2 getDesktopService() {
		return desktopService2;
	}

	public void setDesktopService(DesktopService2 desktopService) {
		this.desktopService2 = desktopService;
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
				"BookmarkMaintenanceAspectImpl.bookmarkUniversity - the universityInfo cannot be null.");
		Validate.notNull(userId, "BookmarkMaintenanceAspectImpl.bookmarkUniversity - the userId cannot be null.");

		try {
			// Get DesktopInfo
			DesktopInfo desktopInfo = desktopService2.findDesktopByUser(userId);

			// Link University
			desktopService2.linkUniversity(desktopInfo.getId(), universityInfo.getId());
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
				"BookmarkMaintenanceAspectImpl.deleteBookmarksOfUniversity - the universityId cannot be null.");

		try {
			desktopService2.unlinkAllFromUniversity(universityId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method deleteBookmarksOfUniversity <----------");
	}

	public void bookmarkDepartment(DepartmentInfo departmentInfo, Long userId) {
		logger.debug("----------> BEGIN method bookmarkDepartment <----------");

		Validate.notNull(departmentInfo,
				"BookmarkMaintenanceAspectImpl.bookmarkDepartment - the departmentInfo cannot be null.");
		Validate.notNull(userId, "BookmarkMaintenanceAspectImpl.bookmarkDepartment - the userId cannot be null.");

		try {
			// Get DesktopInfo
			DesktopInfo desktopInfo = desktopService2.findDesktopByUser(userId);

			// Link Department
			desktopService2.linkDepartment(desktopInfo.getId(), departmentInfo.getId());
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
				"BookmarkMaintenanceAspectImpl.deleteBookmarksOfDepartment - the departmentId cannot be null.");

		try {
			desktopService2.unlinkAllFromDepartment(departmentId);
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
				"BookmarkMaintenanceAspectImpl.bookmarkInstitute - the instituteInfo cannot be null.");
		Validate.notNull(userId, "BookmarkMaintenanceAspectImpl.bookmarkInstitute - the userId cannot be null.");

		try {
			// Get DesktopInfo
			DesktopInfo desktopInfo = desktopService2.findDesktopByUser(userId);

			// Link Institute
			desktopService2.linkInstitute(desktopInfo.getId(), instituteInfo.getId());
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method bookmarkInstitute <----------");
	}

	/**
	 * Delete Bookmarks of the given institute from all users.
	 * 
	 * @param instituteId
	 */
	public void deleteBookmarksOfInstitute(Long instituteId) {
		logger.debug("----------> BEGIN method deleteBookmarksOfInstitute <----------");

		Validate.notNull(instituteId,
				"BookmarkMaintenanceAspectImpl.deleteBookmarksOfInstitute - the instituteId cannot be null.");

		try {
			desktopService2.unlinkAllFromInstitute(instituteId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method deleteBookmarksOfInstitute <----------");
	}

	public void deleteBookmarksOfCourse(Long courseId) {
		logger.debug("----------> BEGIN method deleteBookmarksOfCourses <----------");

		Validate.notNull(courseId, "BookmarkMaintenanceAspectImpl.bookmarkCourse - the courseId cannot be null.");

		try {
			// Unlink all
			desktopService2.unlinkAllFromCourse(courseId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method deleteBookmarksOfCourses <----------");
	}
}
