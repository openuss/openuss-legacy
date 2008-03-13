package org.openuss.aop;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.Organisation;
import org.openuss.lecture.OrganisationDao;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.lecture.UniversityInfo;
import org.openuss.seminarpool.SeminarpoolInfo;

public class BookmarkMaintenanceAspectImpl {

	private static final Logger logger = Logger.getLogger(BookmarkMaintenanceAspectImpl.class);
	private DesktopService2 desktopService2;
	private OrganisationDao organisationDao;
	private UniversityDao universityDao;
	private DepartmentDao departmentDao;
	private InstituteDao instituteDao;

	/**
	 * Create bookmark for given organisation.
	 * @param organisationId
	 * @param userId
	 */
	public void bookmarkOrganisation (Long organisationId, Long userId) {
		logger.debug("----------> BEGIN method bookmarkOrganisation <----------");
		
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate.notNull(organisation, "No organisation found with the given organisationId");
		
		if (organisation instanceof University) {
			UniversityInfo universityInfo =	getUniversityDao().toUniversityInfo((University)organisation);
			this.bookmarkUniversity(universityInfo, userId);
		} else if (organisation instanceof Department) {
			DepartmentInfo departmentInfo = getDepartmentDao().toDepartmentInfo((Department) organisation);
			this.bookmarkDepartment(departmentInfo, userId);
		} else if (organisation instanceof Institute) {
			InstituteInfo instituteInfo = getInstituteDao().toInstituteInfo((Institute) organisation);
			this.bookmarkInstitute(instituteInfo, userId);
		}
	}
	
	/**
	 * Create bookmark of given university for the creating user.
	 * 
	 * @param universityInfo,
	 *            userId
	 */
	public void bookmarkUniversity(UniversityInfo universityInfo, Long userId) {
		logger.debug("----------> BEGIN method bookmarkUniversity <----------");

		Validate.notNull(universityInfo, "The universityInfo cannot be null.");
		Validate.notNull(userId, "The userId cannot be null.");

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

		Validate.notNull(universityId, "The universityId cannot be null.");

		try {
			desktopService2.unlinkAllFromUniversity(universityId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method deleteBookmarksOfUniversity <----------");
	}

	public void bookmarkDepartment(DepartmentInfo departmentInfo, Long userId) {
		logger.debug("----------> BEGIN method bookmarkDepartment <----------");

		Validate.notNull(departmentInfo, "The departmentInfo cannot be null.");
		Validate.notNull(userId, "The userId cannot be null.");

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

		Validate.notNull(departmentId, "The departmentId cannot be null.");

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

		Validate.notNull(instituteInfo,	"The instituteInfo cannot be null.");
		Validate.notNull(userId, "The userId cannot be null.");

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

		Validate.notNull(instituteId, "The instituteId cannot be null.");

		try {
			desktopService2.unlinkAllFromInstitute(instituteId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method deleteBookmarksOfInstitute <----------");
	}

	public void deleteBookmarksOfCourse(Long courseId) {
		logger.debug("----------> BEGIN method deleteBookmarksOfCourses <----------");

		Validate.notNull(courseId, "The courseId cannot be null.");

		try {
			// Unlink all
			desktopService2.unlinkAllFromCourse(courseId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method deleteBookmarksOfCourses <----------");
	}
	
	public void bookmarkSeminarpool(SeminarpoolInfo seminarpoolInfo, Long userId) {
		logger.debug("----------> BEGIN method bookmarkSeminarpool <----------");

		Validate.notNull(seminarpoolInfo, "The seminarpoolInfo cannot be null.");
		Validate.notNull(userId, "The userId cannot be null.");

		try {
			// Get DesktopInfo
			DesktopInfo desktopInfo = desktopService2.findDesktopByUser(userId);
			// Link Seminarpool
			desktopService2.linkSeminarpool(desktopInfo.getId(), seminarpoolInfo.getId());
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method bookmarkSeminarpool <----------");
	}

	/**
	 * Delete bookmarks of the given department from all users.
	 * 
	 * @param departmentId
	 */
	public void deleteBookmarksOfSeminarpool(Long seminarpoolId) {
		logger.debug("----------> BEGIN method deleteBookmarksOfSeminarpool <----------");

		Validate.notNull(seminarpoolId, "The seminarpoolId cannot be null.");

		try {
			desktopService2.unlinkAllFromSeminarpool(seminarpoolId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}

		logger.debug("----------> End method deleteBookmarksOfSeminarpool <----------");
	}
	
	public DesktopService2 getDesktopService() {
		return desktopService2;
	}

	public void setDesktopService(DesktopService2 desktopService) {
		this.desktopService2 = desktopService;
	}

	public OrganisationDao getOrganisationDao() {
		return organisationDao;
	}

	public void setOrganisationDao(OrganisationDao organisationDao) {
		this.organisationDao = organisationDao;
	}

	public UniversityDao getUniversityDao() {
		return universityDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}
	
}
